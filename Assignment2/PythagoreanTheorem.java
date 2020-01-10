/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		mean();
	}
	public void mean() {
		double value = 1;
		double NumberofNumbers = 0;
		double x;
		double y;

		println("Enter values to compute for the mean");
		println("Enter 0 once you're done");
		x = 0;
		
		while (value != 0) {
			value = readInt("Value:");
			x = x + value;
			NumberofNumbers++;
		}
		NumberofNumbers = NumberofNumbers - 1;
		y = x / NumberofNumbers ;
		println("Output: " + y);
		
	}
	public void pt() {
		double a;
		double b;
		double c;

		println("Enter the values to solve for c");
		a = readInt("a = ");
		b = readInt("b = ");
		c = Math.sqrt(a * a + b * b);

		println("output:" + c); 
	}
}

//c^2 = a^2 + b^2
//c = square root of a^2 + b^2
// + - / *
//!= means not equal to

