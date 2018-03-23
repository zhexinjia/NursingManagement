package Model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Controller.LoginController;

/*
 * below is the insert statement check if exists before insert
 * 
 * INSERT INTO user_primary_info (ssn)
 * SELECT * FROM (SELECT '2') AS tmp
 * WHERE NOT EXISTS (
 *    SELECT ssn FROM training_history WHERE ssn = '2'
 * ) LIMIT 1;
 * 
 * 
 * 
 */


public class DBhelper {
	String urlGet = "http://zhexinj.cn/API/getdb.php";
	//String testGet = "http://zhexinj.cn/API/getdb2.php";
	String urlSend = "http://zhexinj.cn/API/sendPost.php";
	JSONParser parser = new JSONParser();
	String database;
	public DBhelper() {
		database = LoginController.database;
	}
	
	/*
	 * below is the helper of needed function
	 */
	
	//convert JSON to HashMap
	private HashMap<String, String> jsonToMap(String json){
		return jsonToList(json).get(0);
	}
	
	//convert JSON array to array-list of HashMap
	private ArrayList<HashMap<String, String>> jsonToList(String json){
		ArrayList<HashMap<String, String>> output = new ArrayList<HashMap<String, String>>();
		JSONArray objectArray = new JSONArray();
		try {
			objectArray = (JSONArray) parser.parse(json);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<?> arrayIterator = objectArray.iterator();
		while (arrayIterator.hasNext()) {
			Object object = arrayIterator.next();
			@SuppressWarnings("unchecked")
			HashMap<String, String> map = (HashMap<String, String>)object;
			output.add(map);
		}
		return output;
	}	
	
	//FIXME: security issue in future
	/*
	 * @param: sql statement
	 * return: boolean value check send success
	 */
	public Boolean sendPost(String url, String param) {
		String result = "";
        BufferedReader in = null;
        String final_param = "database="+database+"&"+param;
        byte[] postData = final_param.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        try {
            URL realUrl = new URL(url);
            //打开和URL之间的连接            
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoOutput( true );
            connection.setInstanceFollowRedirects( false );
            connection.setRequestMethod( "POST" );
            connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
            connection.setRequestProperty( "charset", "utf-8");
            connection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            connection.setUseCaches( false );
            connection.connect();
            
            try( DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            	   wr.write( postData );
            	}
            
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            
            String line;
            
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        		//FIXME: add POPUP
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        
        //使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (result.equals("success")) {
        		return true;
        }
        return false;
	}
	
	/*
	 * @param: sql statement
	 * @return: JSON formated string from executing SQL statement
	 */
	public String sendGet(String url, String param) {
		String result = "";
        BufferedReader in = null;
        String final_param = "database="+database+"&"+param;
        byte[] postData = final_param.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        try {
            URL realUrl = new URL(url);
            //打开和URL之间的连接            
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoOutput( true );
            connection.setInstanceFollowRedirects( false );
            connection.setRequestMethod( "POST" );
            connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
            connection.setRequestProperty( "charset", "utf-8");
            connection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            connection.setUseCaches( false );
            connection.connect();
            
            try( DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            	   wr.write( postData );
            	}
            
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            
            String line;
            
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
            //result = in.readLine();
        } catch (Exception e) {
        		//FIXME: add POPUP
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        
        //使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

	
	
	
	
	
	
	
	
	
	/*
	 * Below are all the method used in controller to insert, update, delete
	 * 
	 * 
	 */
	
	public String getSearchStatement(String[] searchColumn, String[] values) {
		String statement = "";
		for(int i = 0; i < searchColumn.length; i++) {
			if(i == searchColumn.length-1) {
				statement += searchColumn[i] + " = '" + values[i] + "';"; 
			}else {
				statement += searchColumn[i] + " = '" + values[i] + "' AND ";
			}
		}
		return statement;
	}
	public String getStatement(String tableName, String[] columns) {
		String statement = "";
		for (int i = 0; i<columns.length; i++) {
			if (i == columns.length-1) {
				statement += columns[i] + " from " + tableName + " ";
			}else {
				statement += columns[i] + ", ";
			}
		}
		return statement;
	}

	//get entire user table
	public ArrayList<HashMap<String, String>> getEntireList(String tableName){
		String sql = "sql=select * from " + tableName +";";
		String output = sendGet(urlGet, sql);
		return jsonToList(output);
	}

	//return same as above @param: column name and value
	public ArrayList<HashMap<String, String>> getEntireList(String[] searchColumn, String[] values, String tableName){
		String sql = "sql=select * from " + tableName + " where ";
		sql += getSearchStatement(searchColumn, values);
		String output = sendGet(urlGet, sql);
		return jsonToList(output);
	}
	
	//get selected user column's info
	/*
	public ArrayList<HashMap<String, String>> getList(String hospitalID, String tableName, String[] columns){
		String sql = "sql=select ";
		for (int i = 0; i<columns.length; i++) {
			if (i == columns.length-1) {
				sql += columns[i] + " from " + tableName + " where hospital_id = " + hospitalID + ";";
			}else {
				sql += columns[i] + ", ";
			}
		}
		String output = sendGet(urlGet, sql);
		return jsonToList(output);
	}
	*/
	//same function as above
	public ArrayList<HashMap<String, String>> getList(String tableName, String[] columns){
		String sql = "sql=select ";
		sql+=getStatement(tableName, columns);
		String output = sendGet(urlGet, sql);
		System.out.println(sql);
		return jsonToList(output);
	}
	
	//Keep this method only
	public ArrayList<HashMap<String, String>> getList(String[] searchColumn, String[] values, String tableName, String[] columns){
		String sql = "sql=select ";
		sql+=getStatement(tableName, columns);
		sql+=" where " + getSearchStatement(searchColumn, values);
		String output = sendGet(urlGet, sql);
		System.out.println(sql);
		return jsonToList(output);
	}
	
	public ArrayList<ArrayList<HashMap<String, String>>> getMultipleList(String sqlStatement){
		return null;
	}
	

	
	public ArrayList<HashMap<String, String>> getList(String sqlStatement, String tableName, String[] columns){
		String sql = "sql=select ";
		sql+=getStatement(tableName, columns);
		sql += sqlStatement;
		String output = sendGet(urlGet, sql);
		System.out.println(sql);
		return jsonToList(output);
	}
	
	//get selected user Info
	//TODO no test
	/*
	public HashMap<String, String> getEntireUserInfo(String hospitalID, String tableName, String ssn){
		String sql = "sql=select * from " + tableName + " where hospital_id = " + hospitalID + " AND ssn = " + ssn + ";";
		String output = sendGet(urlGet, sql);
		return jsonToMap(output);
	}
	*/
	
	

	public String insertUserSQL(HashMap<String, String> map) {
		String ssn = map.get("ssn");
		//TODO
		return null;
	}

	/*
	 * Used in UserNewController, given one HashMap insert into three tables, nurse primary, sub, nurse score
	 */
	public boolean insertUser(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return false;
	}	
	
	//used in UserModifyController, one HashMap as @Param, update two tables: primary, sub
	public void updateUser(HashMap<String, String> map) {
		
		// TODO finish
	}
	
	//used in UserListController, delete user info from 3 tables, maybe including all-user history
	public boolean deleteUser(HashMap<String, String> selectedUser) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String mapInsert(HashMap<String, String> map, String tableName) {
		String sql = "insert into " + tableName + " (";
		Set<String> keys = map.keySet();
		ArrayList<String> keyset = new ArrayList<String>(keys);
		for(int i = 0; i < keyset.size();i++) {
			if(i == keyset.size()-1) {
				sql+= keyset.get(i) + ") VALUES (";
			}else {
				sql+= keyset.get(i) + ", ";
			}
		}
		for(int i = 0; i < keyset.size();i++) {
			if(i == keyset.size()-1) {
				sql+= "'" + map.get(keyset.get(i)) + "');";
			}else {
				sql+= "'" + map.get(keyset.get(i)) + "', ";
			}
		}
		return sql;
	}
	//simple insert, insert map's value into table
	public boolean insert(HashMap<String, String> map, String tableName) {
		String sql = "sql=" + mapInsert(map, tableName);
		if(sendPost(urlSend, sql)) {
			return true;
		}
		return false;
	}
	
	
	public boolean update(HashMap<String, String> map, String tableName) {
		String sql = "sql=update " + tableName + " set ";
		ArrayList<String> keyset = new ArrayList<String>(map.keySet());
		for(int i = 0; i < keyset.size(); i++) {
			if(i == keyset.size()-1) {
				sql+= keyset.get(i) + " = " + map.get(keyset.get(i));
			}else {
				sql+= keyset.get(i) + " = " + map.get(keyset.get(i)) + ", ";
			}
		}
		sql += " where id = " + map.get("id") + ";";
		if(sendPost(urlSend, sql)) {
			return true;
		}
		return false;
	}
	
	
	//simple delete version, delete row by using hashmap.id
	public boolean delete(HashMap<String, String> map, String tableName) {
		String id = map.get("id");
		String sql = "sql=delete from " + tableName + " where id = " + id + ";";
		if(sendPost(urlSend, sql)) {
			success();
			return true;
		}
		fail();
		return false;
	}

	//delete all information about this test, given test id
	public boolean deleteExam(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//simply insert entire HashMap List into table

	public boolean insertList(ArrayList<HashMap<String, String>> list, String tableName) {
		String sql = "sql=";
		for(HashMap<String, String> map:list) {
			sql+=mapInsert(map, tableName);
		}
		System.out.println(sql);
		if(sendPost(urlSend, sql)) {
			return true;
		}
		return false;
	}

	/*
	 * hard insert function, insert list of user info into 3 tables
	 */
	public void insertUserList(ArrayList<HashMap<String, String>> maplist, String hospitalID) {
		// TODO Auto-generated method stub
		
	}

	//publishing item, test/meeting/study
	public boolean publish(ArrayList<HashMap<String, String>> userList, HashMap<String, String> publishingItem,
			String tableName) {
		// TODO Auto-generated method stub
		return false;
	}

	//delete meeting and all meeting history relate to this meeting
	public boolean deleteMeeting(HashMap<String, String> selectedMeeting) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteStudy(HashMap<String, String> selectedStudy) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean deleteTrainning(HashMap<String, String> selected) {
		// TODO Auto-generated method stub
		return false;
	}

	
	private boolean popSelection() {
		PopupWindow pop = new PopupWindow();
		pop.alertWindow("操作失败", "没有选中目标。");
		return false;
	}
	private void success() {
		PopupWindow pop = new PopupWindow();
		pop.confirmButton.setOnAction(e->{
			pop.stage.close();
		});
		pop.confirmWindow("操作成功", "点击确定返回");
	}
	private void fail() {
		PopupWindow pop = new PopupWindow();
		pop.errorWindow();
	}
	
	 /* 
	 * INSERT INTO user_primary_info (ssn)
	 * SELECT * FROM (SELECT '2') AS tmp
	 * WHERE NOT EXISTS (
	 * SELECT ssn FROM training_history WHERE ssn = '2'
	 * ) LIMIT 1;
	 */
	private String preventDupSQL(HashMap<String, String> map, String tableName) {
		String sql = "INSERT INTO " + tableName + " ";
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		Set<String> keyset = map.keySet();
		for (String key:keyset) {
			keys.add(key);
			values.add(map.get(key));	
		}
		String keyString = "(";
		String valueString = "(SELECT ";
		for(int i = 0; i< keys.size(); i++) {
			if(i == keys.size()-1) {
				keyString += keys.get(i) + ") ";
				valueString += "'" + values.get(i) + "') ";
			}else {
				keyString += keys.get(i) + ", ";
				valueString += "'" + values.get(i) + "', ";
			}
		}
		sql += keyString + "SELECT * FROM " + valueString + "AS tmp WHERE NOT EXISTS (SELECT ssn FROM " + tableName
				+ " WHERE ssn =" + map.get("ssn") + " AND training_id = " + map.get("training_id") + ");";
		return sql;
	}
	
	public boolean publishTraining(ArrayList<HashMap<String, String>> maplist, String tableName) {
		String sql = "sql=";
		for(HashMap<String, String> map:maplist) {
			sql+=preventDupSQL(map, tableName);
		}
		System.out.println(sql);
		if(sendPost(urlSend, sql)) {
			return true;
		}
		return false;
		
		
	}
	
	
	//given ssn, return list of test score histoys, list of study score history, list of training, list of meeting
	/*
	public ArrayList<ArrayList<HashMap<String, String>>> getRecordLists(String ssn){
		String sql = "sql=";
		sql+="select exam_list.examName, exam_history.score, exam_history.totalScore from "
				+ "exam_history inner join exam_list on exam_history.exam_id = exam_list.id where "
				+ "exam_history.ssn = " + ssn + ";";
		sql+="select study_list.name, study_history.finish_status from "
				+ "study_history inner join study_list on study_history.study_id = study_list.id where "
				+ "study_history.ssn = " + ssn + ";";
		sql+="select training_list.name, training_history.point, training_list.point from "
				+ "training_list inner join training_history on training_list.id = training_history.training_id where "
				+ "training_history.ssn = " + ssn + ";";
		sql+="select meeting_list.name, meeting_history.checkin, meeting_history.checkout from "
				+ "meeting_history inner join meeting_list on meeting_list.id = meeting_history.meeting_id where "
				+ "meeting_history.ssn = " + ssn + ";";
		String output = sendGet(testGet, sql);
		System.out.println(sql);
		System.out.println(output);
		return toList(output);
	}
	
	private ArrayList<ArrayList<HashMap<String, String>>> toList(String json){
		ArrayList<ArrayList<HashMap<String, String>>> ret = new ArrayList<ArrayList<HashMap<String, String>>>();
		JSONArray objectArray = new JSONArray();
		try {
			objectArray = (JSONArray) parser.parse(json);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<?> arrayIterator = objectArray.iterator();
		while (arrayIterator.hasNext()) {
			JSONArray object = (JSONArray) arrayIterator.next();
			ArrayList<HashMap<String, String>> output = new ArrayList<HashMap<String, String>>();
			Iterator<?> secondIterator = object.iterator();
			while(secondIterator.hasNext()) {
				Object obj = secondIterator.next();
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>)obj;
				output.add(map);
			}
			ret.add(output);
		}
		return ret;
	}	
	*/
	

	

	
}
