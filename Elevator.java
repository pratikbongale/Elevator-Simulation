/*
 * Elevator.java
 *
 * Version:
 *      $Id Elevator.java, 1.0 2016/10/30 18:35:03 $
 *
 * Revisions:
 *      $Log$
 */

import java.util.*;

/**
 * Elevator is a thread that represents the elevator and serves the people on every floor.
 * It stores details of about the elevator such as the maximum capacity allowed in the elevator, maximum floor of the building etc.
 * 
 * @author      Pratik S Bongale
 * @author      Shardul P Dabholkar
 */

public class Elevator extends Thread {
	String name;
	final static int maxWeight = 300;
	final static int minWeight = 40;
	final static int minFloor = 1;
	static int maxFloor = 10;
	static int currentFloor;
	String direction;
	String door;
	Controller controller = null;

	LinkedList<Person> upList;
	LinkedList<Person> downList;
	LinkedList<Person> inList;

	Elevator(int n) {
		maxFloor = n;
		currentFloor = minFloor;
		direction = "up";
		door = "closed";

		upList = new LinkedList<Person>();
		downList = new LinkedList<Person>();
		inList = new LinkedList<Person>();

	}

	public void run() {
		while(true) {


			if(isInterrupted()) {
				//interrupted(); 		// clears the interrupted status
				while( upList.head != null || downList.head != null || inList.head != null ) {
					if( direction == "up" && currentFloor <= maxFloor) {
						servePeopleGoingUp();
						direction = "down";
					}
					else if( direction == "down" && currentFloor >= minFloor) {
						servePeopleGoingDown();
						direction = "up";
					}
				}
				
				break;	
			}
			 
			
		}
	}

	void servePeopleGoingUp() {
		Node<Person> currentPersonInUpList = upList.head;
		Node<Person> currentPersonInElevator = inList.head;
		LinkedList<Person> tempList = new LinkedList<Person>();
		boolean isDoorOpen = false;

		while( currentPersonInUpList != null || currentPersonInElevator != null ) {	// if anyone wants to get out / get in

			System.out.println("\n------------------ FLOOR : " + currentFloor + " : ---------------------\n");
			
			if(currentPersonInElevator != null) {			// if there is someone who wants to get out
				
				// check all the people in lift
				currentPersonInElevator = inList.head;
				while(currentPersonInElevator != null) {
					if( currentPersonInElevator.data.outFloor == currentFloor ) {		// check someone has to get out at this floor
						
						if(!isDoorOpen) {
							openDoor();
							isDoorOpen = false;
						}
					
						inList.remove(currentPersonInElevator.data);
						System.out.println("Person : " + currentPersonInElevator.data + " is leaving lift");
						currentPersonInElevator.data.wakeUp();
					
					}
					currentPersonInElevator = currentPersonInElevator.next;					
				}
				 
			}

			if(currentPersonInUpList != null) {		// check if  anyone wants to get in 	
				if( currentPersonInUpList.data.inFloor == currentFloor ) {
					openDoor();
					while( currentPersonInUpList.data.inFloor == currentFloor ) {
						System.out.println("Taking in : " + currentPersonInUpList.data + " at floor : " + currentFloor );
						inList.addAsc(currentPersonInUpList.data);
						if( overweight() ) {
							inList.remove(currentPersonInUpList.data);
							System.out.println(currentPersonInUpList.data + " you should go out : Elevator overweight!!");
							upList.remove(currentPersonInUpList.data);
							tempList.addAsc(currentPersonInUpList.data);
							
						} else {
							upList.remove(currentPersonInUpList.data);
						}
							
						currentPersonInUpList = currentPersonInUpList.next;
						if(currentPersonInUpList == null)
							break;				
					}
					
					closeDoor();
					
				}
			} else {
				closeDoor();
			}

			//System.out.println("Floor served : " + currentFloor);
			//System.out.println("Before :");
			System.out.println("Uplist : " + upList);
			System.out.println("Inlist : " + inList);
			System.out.println("Downlist : " + downList);
			//controller.updateView();
			
			//System.out.println("After :");
			//controller.updateView();
			
			
			currentPersonInUpList = upList.head;
			currentPersonInElevator = inList.head;
			

			if( currentPersonInUpList != null || currentPersonInElevator != null )
				currentFloor = currentFloor + 1;
		}
		
		// add people back to upList to be served on the next round
		Node<Person> currentPersonInTempList = tempList.head;
		while(currentPersonInTempList != null) {
			upList.addAsc(currentPersonInTempList.data);
			currentPersonInTempList = currentPersonInTempList.next;
		}			
		
		// what if you have not reached the top floor from where the downlist will be served
		if(downList.head != null) {
			int downFloorStart = downList.head.data.inFloor;
			if(downFloorStart > currentFloor) {
				//System.out.println("preparing to serve people going down");
				while(downFloorStart != currentFloor) {
					currentFloor = currentFloor + 1;
					System.out.println("\n------------------ FLOOR : " + currentFloor + " : ---------------------\n");
					System.out.println("Uplist : " + upList);
					System.out.println("Inlist : " + inList);
					System.out.println("Downlist : " + downList);
				}				
					
			}			
		}
		 
		
	}

	void servePeopleGoingDown() {
		
		Node<Person> currentPersonInDownList = downList.head;
		Node<Person> currentPersonInElevator = inList.head;
		LinkedList<Person> tempList = new LinkedList<Person>();
		boolean isDoorOpen = false;

		while( currentPersonInDownList != null || currentPersonInElevator != null ) {	// if anyone wants to get out / get in

			System.out.println("\n------------------ FLOOR : " + currentFloor + " : ---------------------\n");
			
			if(currentPersonInElevator != null) {			// if there is someone who wants to get out
				// check all the people in lift
				currentPersonInElevator = inList.head;
				while(currentPersonInElevator != null) {
					if( currentPersonInElevator.data.outFloor == currentFloor ) {		// check someone has to get out at this floor
						
						if(!isDoorOpen) {
							openDoor();
							isDoorOpen = false;
						}
					
						inList.remove(currentPersonInElevator.data);
						System.out.println("Person : " + currentPersonInElevator.data + " is leaving lift");
						currentPersonInElevator.data.wakeUp();
					
					}
					currentPersonInElevator = currentPersonInElevator.next;					
				}				
			}

			if(currentPersonInDownList != null) {		// check if  anyone wants to get in 	
				if( currentPersonInDownList.data.inFloor == currentFloor ) {
					openDoor();
					//try{sleep(10000);}catch(Exception e){e.printStackTrace();}
					while( currentPersonInDownList.data.inFloor == currentFloor ) {
						System.out.println("Taking in : " + currentPersonInDownList.data + " at floor : " + currentFloor );
						inList.addDesc(currentPersonInDownList.data);
						if( overweight() ) {
							inList.remove(currentPersonInDownList.data);
							System.out.println(currentPersonInDownList.data + " you should go out : Elevator overweight!!");
							downList.remove(currentPersonInDownList.data);
							tempList.addDesc(currentPersonInDownList.data);
							
						} else {
							downList.remove(currentPersonInDownList.data);
						}
							
						currentPersonInDownList = currentPersonInDownList.next;
						if(currentPersonInDownList == null)
							break;				
					}
					
					closeDoor();
					
				}
			} else {
				closeDoor();
			}

			//System.out.println("Floor served : " + currentFloor);
			//System.out.println("Before :");
			System.out.println("Uplist : " + upList);
			System.out.println("Inlist : " + inList);
			System.out.println("Downlist : " + downList);
			//controller.updateView();
			
			//System.out.println("After :");
			//controller.updateView();
			
			
			currentPersonInDownList = downList.head;
			currentPersonInElevator = inList.head;
			

			if( currentPersonInDownList != null || currentPersonInElevator != null )
				currentFloor = currentFloor - 1;
		}
		
		// add people back to upList to be served on the next round
		Node<Person> currentPersonInTempList = tempList.head;
		while(currentPersonInTempList != null) {
			downList.addDesc(currentPersonInTempList.data);
			currentPersonInTempList = currentPersonInTempList.next;
		}			
		
		// what if you have not reached the bottom floor from where the uplist will be served
		if(upList.head != null) {
			int upFloorStart = upList.head.data.inFloor;
			if(upFloorStart < currentFloor) {
				//System.out.println("preparing to serve people going down");
				while(upFloorStart != currentFloor) {
					currentFloor = currentFloor - 1;
					System.out.println("\n------------------ FLOOR : " + currentFloor + " : ---------------------\n");
					System.out.println("Uplist : " + upList);
					System.out.println("Inlist : " + inList);
					System.out.println("Downlist : " + downList);
				}				
					
			}			
		}
		
	}

	boolean overweight() {
		boolean goesOver = false;
		Node<Person> currentPersonInElevator = inList.head;
		int totalWeight = 0;
		while( currentPersonInElevator != null ) {
			totalWeight = totalWeight + currentPersonInElevator.data.weight;
			currentPersonInElevator = currentPersonInElevator.next;
		}

		if(totalWeight > maxWeight)
			goesOver = true;

		return goesOver;
	}

	void openDoor() {
		door = "open";
		System.out.println("Door opened");
	}

	void closeDoor() {
		door = "closed";
		System.out.println("Door Closed");
	}

	public String toString() {
		return "[ Floor : " + currentFloor + ", Door : " + door + ", going " + direction + " ]";
	}




}