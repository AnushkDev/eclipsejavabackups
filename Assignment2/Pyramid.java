/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Height of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	public void run() {
		int xPos, yPos; 
		yPos = (getHeight()-BRICK_HEIGHT);
		int lastRowCount = BRICKS_IN_BASE;	
		int rowsDone = 0;
		
		for (lastRowCount = BRICKS_IN_BASE; lastRowCount >= 1; lastRowCount--) {
			for (int numberOfBricks=0; numberOfBricks < lastRowCount; numberOfBricks++) {
				xPos = ((getWidth()/2)-((BRICK_WIDTH * lastRowCount)/2))+(numberOfBricks*BRICK_WIDTH);
				GRect brick = new GRect(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT);
				add(brick);
			}
			rowsDone++;
			yPos = yPos - BRICK_HEIGHT;
		}
	}
	
}

