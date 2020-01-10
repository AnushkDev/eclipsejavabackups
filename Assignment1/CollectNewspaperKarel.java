/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends Karel {
	//hippity hoppity 
	//public void run() is required
	//good example of while loops
	public void run(){
		while(frontIsClear()) { 
			move();
		}
		turnRight();
		move();
		turnLeft();
		move();
		pickBeeper();
		turnAround();
		move();
		turnRight();
		move();
		turnLeft();
		while(frontIsClear()) {
			move();
		}
		putBeeper();
	}
	
	public void turnRight() {
		turnLeft();
		turnLeft();
		turnLeft();
	}
	public void turnAround() {
		turnLeft();
		turnLeft();
	}
}
