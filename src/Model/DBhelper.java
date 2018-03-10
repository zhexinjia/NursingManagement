package Model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DBhelper {
	String urlGet = "http://zhexinj.cn/API/getdb.php";
	String urlSend = "http://zhexinj.cn/API/sendPost.php";
	JSONParser parser = new JSONParser();
	public DBhelper() {
		
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
        byte[] postData = param.getBytes(StandardCharsets.UTF_8);
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
        byte[] postData = param.getBytes(StandardCharsets.UTF_8);
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
	
	
	//get entire user table
	public ArrayList<HashMap<String, String>> getEntireList(String hospitalID, String tableName){
		String sql = "sql=select * from " + tableName + " where hospital_id = " + hospitalID +";";
		String output = sendGet(urlGet, sql);
		return jsonToList(output);
	}
	//return same as above @param: column name and value
	public ArrayList<HashMap<String, String>> getEntireList(String column, String value, String tableName){
		String sql = "sql=select * from " + tableName + " where " + column + " = " + value + ";";
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
	public ArrayList<HashMap<String, String>> getList(String[] searchColumn, String[] values, String tableName, String[] columns){
		String sql = "sql=select ";
		for (int i = 0; i<columns.length; i++) {
			if (i == columns.length-1) {
				sql += columns[i] + " from " + tableName + " where ";
			}else {
				sql += columns[i] + ", ";
			}
		}
		for(int i = 0; i < searchColumn.length; i++) {
			if(i == searchColumn.length-1) {
				sql += searchColumn[i] + " = '" + values[i] + "';"; 
			}else {
				sql += searchColumn[i] + " = '" + values[i] + "' AND ";
			}
		}
		String output = sendGet(urlGet, sql);
		return jsonToList(output);
	}
	public ArrayList<HashMap<String, String>> getList(String sqlStatement, String tableName, String[] columns){
		String sql = "sql=select ";
		for (int i = 0; i<columns.length; i++) {
			if (i == columns.length-1) {
				sql += columns[i] + " from " + tableName + " where ";
			}else {
				sql += columns[i] + ", ";
			}
		}
		sql += sqlStatement;
		String output = sendGet(urlGet, sql);
		return jsonToList(output);
	}
	
	//get selected user Info
	//TODO no test
	public HashMap<String, String> getEntireUserInfo(String hospitalID, String tableName, String ssn){
		String sql = "sql=select * from " + tableName + " where hospital_id = " + hospitalID + " AND ssn = " + ssn + ";";
		String output = sendGet(urlGet, sql);
		return jsonToMap(output);
	}
	
	


	/*
	 * Used in UserNewController, given one HashMap insert into three tables, nurse primary, sub, nurse score
	 */
	public void insertUser(HashMap<String, String> map, String hospitalID) {
		// TODO Auto-generated method stub
		System.out.println(map.size());
	}	
	
	//used in UserModifyController, one HashMap as @Param, update two tables: primary, sub
	public void updateUser(HashMap<String, String> map, String hospitalID) {
		// TODO finish
	}
	
	//used in UserListController, delete user info from 3 tables, maybe including all-user history
	public boolean deleteUser(HashMap<String, String> selectedUser, String hospitalID) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//simple insert, insert map's value into table
	public boolean insert(HashMap<String, String> map, String tableName) {
		String sql = "sql=insert into " + tableName + " (";
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
		if(sendPost(urlSend, sql)) {
			return true;
		}
		return false;
	}
	
	public boolean update(HashMap<String, String> map, String tableName) {
		//TODO:
		return false;
	}
	
	//simple delete version, delete row by using hashmap.id
	public boolean delete(HashMap<String, String> map, String tableName) {
		String id = map.get("id");
		String sql = "sql=delete from " + tableName + " where id = " + id + ";";
		return sendPost(urlSend, sql);
	}

	//delete all information about this test, given test id
	public boolean deleteExam(HashMap<String, String> map, String hospitalID) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//simply insert entire HashMap List into table

	public boolean insertList(ArrayList<HashMap<String, String>> list, String tableName) {
		// TODO Auto-generated method stub
		System.out.println(list.size());
		System.out.println(list.get(0).get("question"));
		return false;
	}

	/*
	 * hard insert function, insert list of user info into 3 tables
	 */
	public void insertUserList(ArrayList<HashMap<String, String>> maplist, String hospitalID) {
		// TODO Auto-generated method stub
		
	}

	//publishing item, test/meeting/study
	public void publish(ArrayList<HashMap<String, String>> userList, HashMap<String, String> publishingItem,
			String tableName) {
		// TODO Auto-generated method stub
	}

	//delete meeting and all meeting history relate to this meeting
	public void deleteMeeting(HashMap<String, String> selectedMeeting) {
		// TODO Auto-generated method stub
		
	}
}
