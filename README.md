# Drone-Delivery-Scheduling

A drone launch scheduling program that maximizes the net promoter score (NPS) amongst drone-delivery customers.

## For running the DroneScheduling.java with arguments (args[0] for input file, args[1] for output file) example:
  java DroneScheduling /Users/ryanvo1/Documents/workspace/DroneDelivery/src/DroneInputs.txt     
  /Users/ryanvo1/Documents/workspace/DroneDelivery/src/DroneOutputs.txt

## For compiling DroneSchedulingTest.java: 
  On Linux or MacOS: javac -cp .:junit-4.13-beta-2.jar:hamcrest-2.1.jar DroneSchedulingTest.java
  On Windows: javac -cp .;junit-4.13-beta-2.jar;hamcrest-2.1.jar DroneSchedulingTest.java

## For running DroneSchedulingTest:
  On Linux or MacOS: java -cp .:junit-4.13-beta-2.jar:hamcrest-2.1.jar org.junit.runner.JUnitCore DroneSchedulingTest
  On Windows: java -cp .;junit-4.13-beta-2.jar;hamcrest-2.1.jar org.junit.runner.JUnitCore DroneSchedulingTest

