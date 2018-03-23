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
	
		
		
		map.put("ssn", "333");
		map.put("name", "hahahahaa");
		String output = dbHelper.insertUserSQL(map);
		
		
		
		//test
		boolean aaa  = dbHelper.insertUser(map);
		System.out.println(output);
		
		
		assertEquals("aa", "aa");
	}
	

}
