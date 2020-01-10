/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run(){
		checkColumn();
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

	
	public void checkColumn() {
	while (notFacingNorth()) {
	turnLeft();	
	}
	if(frontIsBlocked()) {
		turnAround();
		if (frontIsBlocked()) {
			while (notFacingEast()) {
			turnLeft();
			}
			move();
			checkColumn();
		}
		else {
			turnAround();
			while(frontIsClear())
				move();
		}
		turnAround();
	}
	while (frontIsClear()) {
	move();
		if (beepersPresent()) {
			fill();
		}
	}
	
	turnRight();
	while(notFacingEast()) {
		turnLeft();
	}
	newColumn();
	}
	
	public void fill() {
	
	while (notFacingSouth()) {
		turnLeft();
	}
	while (frontIsClear()) {
		move();
	}
	turnAround();
	while(frontIsClear()) {
		if(noBeepersPresent()) {
			putBeeper();
		}
	move();
	}
	if(noBeepersPresent()) {
	putBeeper();
	}
	}
	
	public void newColumn(){
		while(notFacingSouth()) {
			turnLeft();
		}
		while(frontIsClear()) {
			move();
		}
		turnLeft();
		if(frontIsClear()) {
		move();
		checkColumn();
		}
		else {
			turnAround();
		}
	}

}
