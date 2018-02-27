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
		String output = dbHelper.print();
		assertEquals(output, "hello");
	}
	@org.junit.jupiter.api.Test
	void excelReadTest() {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> temp = null;
		try {
			temp = ExcelHelper.readXLSXFile("/Users/Ben/Desktop/test.xlsx", new String[]{"name", "phone"});
			for (HashMap<String, String> map:temp) {
				System.out.println(map.entrySet().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(result, temp);
	}

}
