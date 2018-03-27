package Model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//import static org.junit.jupiter.api.Assertions.*;

class Test {
	DBhelper dbHelper = new DBhelper();
	@org.junit.jupiter.api.Test
	
	void test() {
		HashMap<String, String> map = new HashMap<String, String>();
	
		//每个test case都要写这句话
		dbHelper.database = "medic";
		
		map.put("ssn", "8818");
		map.put("name", "hahahahaa");
		map.put("department", "Oral");
		
		//test
		boolean aaa  = dbHelper.insertUser(map);
		System.out.println(aaa);
	
		assertEquals("aa", "aa");
	}
	

}
