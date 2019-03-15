
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;


/**
 * A drone scheduling program to schedule drone-carried deliveries to customers in a small town
 * that can maximize the net promoter score (NPS) amongst drone-delivery customers.
 * @author Quang Vo
 */
public class DroneScheduling {
	
	private static List<Order> orders = new ArrayList<Order>();
	private static final Location warehouse = new Location(0, 0);
	private static final LocalTime droneStartTime = LocalTime.of(6, 0, 0);
	private static final LocalTime droneEndTime = LocalTime.of(22, 0, 0);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			orders = readFromInputFile(args[0]); //read orders from input file
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		sortOrdersByFinishTime(orders); //sort orders by their finish times
		//debug sorted orders
		System.out.println("");
		for(int i = 0; i < orders.size(); i++){
			System.out.println(orders.get(i).toString());
		}
		
		try {
			writeToOutputFile(args[1]); //save the orders to output file
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * Read each order as each line from input file 
	 * @throws FileNotFoundException 
	 */
	public static List<Order> readFromInputFile(String inputFile) throws FileNotFoundException{
		
		List<Order> orders = new ArrayList<Order>();
		
		Scanner sc = new Scanner(new File(inputFile));
		while(sc.hasNextLine()){
			String str = sc.nextLine();
			Order newOrder = extractOrderInfo(str);
			orders.add(newOrder);
		}
		sc.close();
		
		return orders;
	}
	
	/**
	 * Get the order ID, customer's coordinates, order timestamp from input file
	 * @param str each line of the input file, represented each order
	 * @return an order with necessary information
	 */
	public static Order extractOrderInfo(String str){
		
		String[] splitStr = str.trim().split("\\s+"); //trim() for just in case starts with a whitespace 
		
		String id = splitStr[0]; // get order identifier
		
		String[] coordinates = splitStr[1].substring(1).split("[WE]");
		
		double y = Double.parseDouble(coordinates[0]);
		double x = Double.parseDouble(coordinates[1]);
		
		if(x < 0 || y < 0){
			throw new IllegalArgumentException("Coordinates can not be less than 0");
		}
		//should I have max coordinates allowed here? For ex: 500 * 500 grid?
		
		Location location = new Location(x, y);
		
		LocalTime timeStamp = LocalTime.parse(splitStr[2]);
						
		//distance for back and forth to the warehouse
		double distance = calculateDistance(warehouse, location) * 2;
		
		LocalTime finishTime = calculateTime(distance, timeStamp);
		
		Order newOrder = new Order(id, location, timeStamp, finishTime, distance);
		//System.out.println(newOrder.toString());
		
		return newOrder;
		
	}
	
	/**
	 * Sort the orders by their finish times
	 */
	public static void sortOrdersByFinishTime(List<Order> orders){
		//anonymous class
		Collections.sort(orders, new Comparator<Order>(){
			@Override
			public int compare(Order o1, Order o2){
				return o1.getFinishTime().compareTo(o2.getFinishTime());
			}
		});
	}
	
	/**
	 * Save the order identifier and the drone's departure time for that order
	 * @throws IOException 
	 */
	public static void writeToOutputFile(String outputFile) throws IOException{
		File file = new File(outputFile);
		if(!file.exists()){
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); //append mode
		BufferedWriter bw = new BufferedWriter(fw);
		
		LocalTime departureTime = droneStartTime;
		
		int promoters = 0;
		int detractors = 0;
		
		//Interval scheduling, earliest-finish-time first algorithm			
		for(int i = 0; i < orders.size(); i++){
			Order order = orders.get(i);
			bw.write(order.getId() + " " + departureTime + "\n");
			LocalTime arrivalTime = calculateTime(order.getDistance(), departureTime);
			
			if(arrivalTime.isAfter(droneEndTime)){ //can't deliver after 10pm
				System.out.println("The arrival time of order " + order.getId() + " is " + arrivalTime);
				System.out.println("No drone delivery after 10:00 PM, please schedule this order tomorrow!");
			}
			
			long minutesBetween = Duration.between(order.getTimeStamp(), arrivalTime).toMinutes();
			
			//System.out.println("diff between " + order.getTimeStamp() + " and " + arrivalTime + " is: " + minutesBetween);
			if(minutesBetween <= 60){ //<= 1 hour => promoters
				promoters++; 
			}
			else if(minutesBetween > 180){ //> 3 hours => detractors
				detractors++;
			}
			departureTime = arrivalTime;
		}
		int NPS = calculateNetPromoterScore(promoters, detractors, orders.size()); //calculate NPS
		bw.write("NPS " + NPS + "\n");
		
        bw.flush(); 
        bw.close();
	}
	
	/**
	 * Calculate distance between the warehouse and customer's address
	 * @param warehouse at coordinates (0, 0) in the grid
	 * @param dest customer's address at coordinates (x, y) from input file
	 * @return the distance
	 */
	public static double calculateDistance(Location warehouse, Location dest){
		//The distance from cell (x1, y1) to cell (x2, y2) is calculated by Euclidean distance formula
		//sqrt((x1 - x2)^2 + (y1 - y2)^2)
		
		double Xdiff = warehouse.getX() - dest.getX();
		double Ydiff = warehouse.getY() - dest.getY();
		
		double distance = Math.sqrt(Math.pow(Xdiff, 2) + Math.pow(Ydiff, 2));
				
		distance = Math.round (distance * 100.0) / 100.0;
		return distance;		
	}
	
	/**
	 * Calculate the finish time with the given start time
	 * @param distance the between the warehouse and customer's address
	 * @param startTime the start time
	 * @return the finish time
	 */
	public static LocalTime calculateTime(double distance, LocalTime startTime){
		
		String dist = String.format("%.2f", distance);
		String[] distTime = dist.trim().split("[.]");
		
		LocalTime finishTime = startTime.plusMinutes(Long.parseLong(distTime[0]));
		finishTime = finishTime.plusSeconds(Long.parseLong(distTime[1]));
						
		return finishTime;
	}
	
	/**
	 * Calculate the Net Promoter Score (NPS) of the schedule
	 * @return the NPS
	 */
	public static int calculateNetPromoterScore(int promoters, int detractors, int responses){
		//NPS = % promoters - % detractors
		double promotersPercentage = (double)promoters / responses * 100;
		double detractorsPercentage = (double)detractors / responses * 100;
		
		int NPS = (int) (promotersPercentage - detractorsPercentage);
		
		return NPS;
	}

}
