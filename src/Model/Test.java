package Model;

import static org.junit.Assert.assertEquals;

//import static org.junit.jupiter.api.Assertions.*;

class Test {
	DBhelper dbHelper = new DBhelper();
	@org.junit.jupiter.api.Test
	void test() {
		String output = dbHelper.print();
		assertEquals(output, "hello");
	}

}
