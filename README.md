# Drone-Delivery-Scheduling

### Overview:
A drone launch scheduling program that schedules drone-carried deliveries to customers in a small town. The town is organized in a perfect grid, with a warehouse and drone-launch facility at the center. All deliveries originate at the warehouse and are carried by a drone to a customer location. A drone's "ground speed" is exactly one horizontal or vertical grid block per minute.
The program needs to maximize the net promoter score (NPS) amongst drone-delivery customers. Net promoter score is defined as the percentage of promoters minus the percentage of detractors. The town owns one drone, which is allowed to operate from 6 a.m. until 10 p.m.

### Input Description:
An input file that contains one line of input for each order. That line includes an order identifier, followed by a space, the customer's grid coordinate, another space, and finally the order timestamp. The order identifier will have the format: WM####. The customer coordinate will have a North/South direction indicator (N or S) and a East/West indicator (E or W) -- for example: N5E10. The timestamp will have the format: HH:MM:SS. The orders in the input file will be sorted by order timestamp. For example:
  * WM001 N11W5 05:11:50 
  * WM002 S3E2 05:11:55 
  * WM003 N7E50 05:31:50 
  * WM004 N11E5 06:11:50

**For compiling DroneScheduling.java, Location.java, Order.java:**
  - javac DroneScheduling.java Location.java Order.java

**For running the DroneScheduling.java with arguments (args[0] for input file, args[1] for output file) example:**
  - java DroneScheduling /Users/ryanvo1/Documents/workspace/DroneDelivery/src/DroneInputs.txt  /Users/ryanvo1/Documents/workspace/DroneDelivery/src/DroneOutputs.txt

**For compiling DroneSchedulingTest.java:**
  - **_On Linux or MacOS_**: javac -cp .:junit-4.13-beta-2.jar:hamcrest-2.1.jar DroneSchedulingTest.java
  - **_On Windows_**: javac -cp .;junit-4.13-beta-2.jar;hamcrest-2.1.jar DroneSchedulingTest.java

**For running DroneSchedulingTest:**
  - **_On Linux or MacOS_**: java -cp .:junit-4.13-beta-2.jar:hamcrest-2.1.jar org.junit.runner.JUnitCore DroneSchedulingTest
  - **_On Windows_**: java -cp .;junit-4.13-beta-2.jar;hamcrest-2.1.jar org.junit.runner.JUnitCore DroneSchedulingTest

