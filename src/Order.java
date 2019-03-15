import java.time.LocalTime;

/**
 * Implementation of an order with its ID, coordinates, timestamp, finish time, and distance from the warehouse.
 * @author Quang Vo
 */
public class Order {
	
	private String id;
	private Location location;
	private LocalTime timeStamp;
	private LocalTime finishTime;
	private double distance;
	
	public Order(String id, Location location, LocalTime timeStamp, LocalTime finishTime, double distance){
		this.id = id;
		this.location = location;
		this.timeStamp = timeStamp;
		this.finishTime = finishTime;
		this.distance = distance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public LocalTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public LocalTime getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTime(LocalTime finishTime){
		this.finishTime = finishTime;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", location=" + location + ", timeStamp=" + timeStamp + ", finishTime=" + finishTime
				+ ", distance=" + distance + "]";
	}

}
