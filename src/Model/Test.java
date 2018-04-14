package Model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//import static org.junit.jupiter.api.Assertions.*;

class Test {
	DBhelper dbHelper = new DBhelper();
	@org.junit.jupiter.api.Test
	
	/*
	void inserUsertest() {
		HashMap<String, String> map = new HashMap<String, String>();
	
		//每个test case都要写这句话
		dbHelper.database = "medic";
		
		map.put("ssn", "8898");
		map.put("name", "hahahahaa");
		map.put("department", "Oral");
		
		//test
		String output = dbHelper.insertUserHelper(map);
		boolean aaa  = dbHelper.insertUser(map);
		System.out.println(output);
		System.out.println(aaa);
	
		assertEquals("aa", "aa");
	}
	*/
	
	/*
	void deleteUserTest() {
		HashMap<String, String> map = new HashMap<String, String>();

		dbHelper.database = "medic";
		
		map.put("ssn", "666");
	//	map.put("name", "hahahahaa");
	//	map.put("department", "Oral");
		
		boolean output = dbHelper.deleteUser(map);
		System.out.println(output);
		
		assertEquals("aa", "aa");
		
	}
	*/
	
	/*
	void updateUser() {
		HashMap<String, String> map = new HashMap<String, String>();

		dbHelper.database = "medic";
		
		map.put("ssn", "333");
		map.put("name", "hlolllo");
		map.put("department", "O_O");
		map.put("sex", "male");
		map.put("address", "kinsrow ave.");
		
		String output = dbHelper.updateUserHelper(map);
		System.out.println(output);
		boolean bbb = dbHelper.updateUser(map);
		System.out.println(bbb);
		
	}
	 */
	
	/*
	void insertUserListTest() {
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();

		dbHelper.database = "medic";
		
		map.put("ssn", "1010");
		map.put("name", "yyyyy");
		map.put("department", "@_@");
		map.put("sex", "male");
		map.put("address", "kinsrow ave.");
		
		map2.put("ssn", "1111");
		map2.put("name", "xxxxx");
		map2.put("department", "X_X");
		map2.put("sex", "female");
		map2.put("address", "marine dr.");
		
		ArrayList<HashMap <String, String>> maplist = new ArrayList<HashMap<String, String>>();
		maplist.add(map);
		maplist.add(map2);
		System.out.println(maplist);
		
		boolean output = dbHelper.insertUserList(maplist);
		System.out.println(output);
				
	}
	*/
	
	/*
	void publishTest() {
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> item = new HashMap<String, String>();

		dbHelper.database = "medic";
		
		map.put("ssn", "1010");
		map.put("name", "yyyyy");
		map.put("department", "@_@");
		map.put("sex", "male");
		map.put("address", "kinsrow ave.");
		map.put("totalScore", "100");
		
		map2.put("ssn", "1111");
		map2.put("name", "xxxxx");
		map2.put("department", "X_X");
		map2.put("sex", "female");
		map2.put("address", "marine dr.");
		map2.put("totalScore", "200");
		
		item.put("id", "2");
		item.put("totalPoint", "10");
		//item.put("examName", "hello world");
		
		ArrayList<HashMap <String, String>> userMaplist = new ArrayList<HashMap<String, String>>();
		userMaplist.add(map);
		userMaplist.add(map2);
		
		boolean output = dbHelper.publish(userMaplist, item, "exam_list");
		System.out.println(output);
		
	}
	*/
	
	
	/*
	void deleteMeetingTest() {
		HashMap<String, String> map = new HashMap<String, String>();
		
		dbHelper.database = "medic";
		
		map.put("id", "2");
		boolean output = dbHelper.deleteMeeting(map);
		System.out.println(output);
	}
	 */
	
	/*
	void deleteStudyTest() {
		HashMap<String, String> map = new HashMap<String, String>();
		
		//每个test case都要写这句话
		dbHelper.database = "medic";
		
		map.put("id", "2");
		boolean output = dbHelper.deleteStudy(map);
		System.out.println(output);
	}
	 
	*/
	
	/*
	void emptyScoreTest() {
		dbHelper.database = "medic";
		boolean output = dbHelper.emptyScore();
		System.out.println(output);
	}
	*/
	
	void updatePoint() {
		dbHelper.database = "medic";
		String output = dbHelper.totalPoint("3");
		System.out.println(output);
	}
	
}
