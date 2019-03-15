import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test case to unit test the DroneScheduling.java
 * @author Quang Vo
 *
 */
public class DroneSchedulingTest {
	
	private static List<Order> orders;
	private static final Location warehouse = new Location(0, 0);
	private static final LocalTime droneStartTime = LocalTime.of(6, 0, 0);
	private static final LocalTime droneEndTime = LocalTime.of(22, 0, 0);
	
	@Before
	public void init(){
		orders = new ArrayList<Order>();
	}
	
	@Test(expected = FileNotFoundException.class) 
	public void testInvalidInputFile() throws FileNotFoundException{
		String inputFile = "hello.txt";
		DroneScheduling.readFromInputFile(inputFile);
	}
	
	@Test
	public void testValidInputFile() throws FileNotFoundException{
		String inputFile = "/Users/ryanvo1/Documents/workspace/DroneDelivery/src/DroneInputs.txt";
		orders = DroneScheduling.readFromInputFile(inputFile);
		assertEquals("Checking number of orders", 4, orders.size());
	}
	
	@Test
	public void testExtractOrderInfo(){
		Location location = new Location(9, 10);
		LocalTime timeStamp = LocalTime.of(9, 30, 45);
		double distance = 26.90; //distance back and forth
		LocalTime finishTime = LocalTime.of(9, 58, 15);
		Order expectedOrder = new Order("WM009", location, timeStamp, finishTime, distance);
		Order actualOrder = DroneScheduling.extractOrderInfo("WM009 N10E9 09:30:45");
		assertTrue("Order's info is not correct", expectedOrder.toString().equals(actualOrder.toString()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidLocation(){
		Order order = DroneScheduling.extractOrderInfo("WM011 N-12W-45 09:36:45");
	}
	
	@Test
	public void testSortOrdersByFinishTime(){
		//DroneScheduling.extractOrderInfo() is already tested
		Order order1 = DroneScheduling.extractOrderInfo("WM001 N10W10 05:00:15"); //05:28:43
		Order order2 = DroneScheduling.extractOrderInfo("WM002 N5W5 05:05:15"); //05:19:29
		Order order3 = DroneScheduling.extractOrderInfo("WM003 N3W3 05:10:15"); //05:19:03
		orders.add(order1);
		orders.add(order2);
		orders.add(order3);
		DroneScheduling.sortOrdersByFinishTime(orders);
		assertEquals("Orders are sorted by their finish times", "WM003", orders.get(0).getId());
	}

	@Test
	public void testCalculateDistance() {
		Location dst1 = new Location(2, 3);
		double distance = DroneScheduling.calculateDistance(warehouse, dst1);
		assertEquals("failure - distance is not correct", 3.61, distance, 0);
	}
	
	@Test
	public void testCalculateTime(){
		double distance = 3.30;
		LocalTime startTime = LocalTime.of(6, 10, 15);
		LocalTime finishTime = DroneScheduling.calculateTime(distance, startTime);
		assertEquals("failure - finish time is not correct", LocalTime.of(6, 13, 45), finishTime);
	}
	
	@Test
	public void testCalculateNetPromoterScore(){
		int promoters = 80;
		int detractors = 40;
		int responses = 150;
		int expectedNPS = 26;
		int actualNPS = DroneScheduling.calculateNetPromoterScore(promoters, detractors, responses);
		assertEquals("failure - NPS is not correct", expectedNPS, actualNPS);
	}
	
	//Sorry I didn't have enough time to test this method :(
//	@Test
//	public void testWriteToOutputFile(){
//		String outputFile = "/Users/ryanvo1/Documents/workspace/DroneDelivery/src/DroneOutputs.txt";
//		
//	}

}

