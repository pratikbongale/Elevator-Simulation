/*
 * Person.java
 *
 * Version:
 *      $Id Person.java, 1.0 2016/10/30 18:35:03 $
 *
 * Revisions:
 *      $Log$
 */

import java.util.*;

/**
 * Person is a thread which stores details of a person entity in this program.
 * This thread calls the elevator and waits until the elevator recieves it and helps it reach its destination.
 * 
 * @author      Pratik S Bongale
 * @author      Shardul P Dabholkar
 */

public class Person extends Thread implements Comparable<Person> {

	int weight;
	int inFloor;
	int outFloor;
	String direction;
	Controller controller;

	Person(int w, int in, int out, Controller c) {
		weight = w;
		inFloor = in;
		outFloor = out;
		controller = c;
		
		System.out.println("Person Created :\n[ID: " + getId() + " weight : " + weight + " in : " + inFloor + " out : " + outFloor + "]\n");
		
		// also check condition for infloor == outfloor
		if(inFloor == outFloor) {
			System.out.println("Source floor and destination floor cannot be the same.");
			System.exit(1);
		}
		
		if(weight < 40) {
			System.out.println("Person is Underweight : weight < 40 ");
			System.exit(1);
		}
		
		if(weight > 100) {
			System.out.println("Person is Overweight : weight > 100");
			System.exit(1);
		}
	
		
		direction = (inFloor < outFloor) ? "up" : "down";
	}

	public void run() {
		try{
			synchronized(controller) {
				controller.callElevator(this);	
				
			}	

			synchronized(this) {
				//System.out.println("thread : " + this + " ");
				wait();	
			}	

			System.out.println("End of thread : " + this);

		} catch(InterruptedException ie) {
				ie.printStackTrace();
		}
	}

	void wakeUp( ) {
		try {
			System.out.println("thread : " + this + " waking up....");
			synchronized(this) {
				notify();	
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public int compareTo( Person p ) {
		if( this.inFloor < p.inFloor ) {
			return -1;
		}
		else if( this.inFloor > p.inFloor ) {
			return 1;
		} else {
			return 0;
		}

	}


	public String toString() {
		return "ID: " + getId(); // + " weight : " + weight + " in : " + inFloor + " out : " + outFloor;
	}
}