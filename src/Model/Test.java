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
	
		
		
		map.put("ssn", "888");
		map.put("name", "hahahahaa");
		map.put("department", "Oral");
		String output = dbHelper.insertUserHelper(map);
		//String outttput = dbHelper.mapInsert(map, "user_sub_info");
		
		
		//test
		boolean aaa  = dbHelper.insertUser(map);
		//boolean bbb  = dbHelper.insert(map, "user_sub_info");
		System.out.println(output);
		//System.out.println(outttput);
		System.out.println(aaa);
		//System.out.println(bbb);
		
		
		assertEquals("aa", "aa");
	}
	

}
