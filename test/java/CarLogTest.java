import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CarLogTest {

	Car car = new Car();
	CarLog carLog = new CarLog();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(System.out);
	}


	@Test
	public void testCarLogPrint() {
		car.moveForward();
		carLog.printLog(car);
		assertEquals("Car position: 5 0", outContent.toString());
	}



}
