import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadPoemTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	final void testStartStage() {
        String str1="This is the testcase in this class";
        assertEquals("This is the testcase in this class", str1); // TODO
	}

	@Test
	final void testCountOccurrences() {
        String str2="This is not the testcase in this class";
        assertEquals("This is the testcase in this class", str2);; // TODO
	}

	@Test
	final void testMain() {
        String str3="This is another testcase in this class";
        assertEquals("This is another testcase in this class", str3); // TODO
	}

}
