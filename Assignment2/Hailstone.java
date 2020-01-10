/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;
import acm.util.RandomGenerator;

import java.util.*;
public class Hailstone extends ConsoleProgram {
	public void run() {
		/** int steps = 0;
		int input = readInt("Enter a number: ");
		while (input != 1) {
			if ((input % 2 ) != 0) {
				println(input + " is odd so I use 3n + 1");
				input =  3 * input + 1;
				steps++;
			}
			else {
				println(input + " is even so I take half");
				input = input/2;
				steps++;
			}
		}
		println (input + " is now 1.");
		println("It took " + steps + " step" + (steps == 1 ? "" : "s") + " to reach 1.");
	**/
		
		// using this for tests
		
		//rgen
		RandomGenerator rgen = RandomGenerator.getInstance();
		
		//dieresults array
		int[] dieResults = new int[5];
		for (int i = 0; i < dieResults.length; i++) {
			dieResults[i] = rgen.nextInt(0, dieResults.length);
			println("DieNum: " + i + " Result: " + dieResults[i]);
		}
	}

}
