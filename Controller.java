/*
 * Controller.java
 *
 * Version:
 *      $Id Controller.java, 1.0 2016/10/30 18:35:03 $
 *
 * Revisions:
 *      $Log$
 */

import java.util.*;

/**
 * Controller controls the execution of the program. It runs test cases for different number of people 
 * which are generated with random values of weight, start floor and destination floor.
 * It does the job of a controller in MVC paradigm.
 * 
 * @author      Pratik S Bongale
 * @author      Shardul P Dabholkar
 */

class Controller {

	Elevator elevator;
	private static Scanner sc;

	Controller() {
		System.out.print("Please enter the number of floors in this building [2-n]: ");
		int n = sc.nextInt();
		if(n < 2) {
			System.out.print("Invalid input");
			System.exit(1);
		}
		elevator = new Elevator(n);
		elevator.start();

	}

	void callElevator(Person p) {
		
		if(p.direction == "up")
			elevator.upList.addAsc(p);
		else
			elevator.downList.addDesc(p);	
	}

	void updateView() {
		System.out.println(elevator);
	}

	public static void main(String args[]) {
		
		sc = new Scanner(System.in);
 		Controller c = new Controller();
 		System.out.print("Please enter the number of people you wish to generate for testing : ");
 		int n = sc.nextInt();

		if(n >= 0) {
 			c.testCase(n);
 			//c.testCase2(n);
		} else {
 			System.out.println("Invalid input, please provide a positive number of people.");
 			System.exit(1);
 		}
 				
	}

	void testCase( int n ) {
		elevator.controller = this;
				
		Random r = new Random();
		int weight; 
		int source;
		int dest;
		Person p;
		
		for(int i = 0; i < n; i++) {
			weight = r.nextInt(61) + 40;
			source = r.nextInt(Elevator.maxFloor) + 1;
			dest = r.nextInt(Elevator.maxFloor) + 1;
			
			p = new Person(weight, source, dest, this);
			p.start();
		}
		
		try{
			Thread.sleep(1000);
		}catch(Exception e){ e.printStackTrace(); }

		elevator.interrupt();

	}
	
	void testCase2(int n) {
		elevator.controller = this;
		
		int weight; 
		int source;
		int dest;
		Person p;
		System.out.println();
		for(int i = 0; i < n; i++) {
			System.out.print("weight :");
			weight = sc.nextInt();
			System.out.print("Getting in at floor :");
			source = sc.nextInt();
			System.out.print("Getting out at floor :");
			dest = sc.nextInt();
			
			p = new Person(weight, source, dest, this);
			p.start();
		}
							
		try{
			Thread.sleep(1000);
			
		}catch(Exception e){ e.printStackTrace(); }

		elevator.interrupt();

	}
}