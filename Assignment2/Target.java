/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	private static int INCH = 72;
	public void run() {
		double ratio;
		GOval oval1 = new GOval (getWidth()/2-INCH, getHeight()/2-INCH, 2*INCH, 2*INCH);
		oval1.setFilled(true);
		oval1.setFillColor(Color.RED);
		add(oval1);
		
		ratio = 0.65;
		GOval oval2 = new GOval (getWidth()/2-(INCH*ratio), getHeight()/2-(INCH*ratio), 2*(INCH*ratio), 2*(INCH*ratio) );
		oval2.setFilled(true);
		oval2.setFillColor(Color.WHITE);
		add(oval2);
		
		ratio = 0.30;
		GOval oval3 = new GOval(getWidth()/2-(INCH*ratio), getHeight()/2-(INCH*ratio), 2*(INCH*ratio), 2*(INCH*ratio) );
		oval3.setFilled(true);
		oval3.setFillColor(Color.RED);
		add(oval3);
	}
}
