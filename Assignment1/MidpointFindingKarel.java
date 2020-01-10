/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

   public void run() {
	   checkBoardh();
   }
   
   
   public void turnAround() {
	   turnLeft();
	   turnLeft();
   }
   public void turnRight(){
	   turnLeft();
	   turnLeft();
	   turnLeft();
   }
   
  
   
   public void checkBoardh() {
	   while(notFacingEast()) {
		   turnLeft();
	   }
	   if (frontIsBlocked()) {
		   turnAround();
		   if(frontIsBlocked()) {
			   vertical();
		   }
		   else {
			   horizontal();
		   }
	   }
	   else {
		   horizontal();
	   }
   }
   
   public void horizontal() {
	   while(notFacingEast()) {
		   turnLeft();
	   }
	   
	   turnAround();
	   
	   while(frontIsClear()) {
		   move();
	   }
	   turnAround();
	   putBeeper();
	   
	   while(frontIsClear()) {
		   move();
	   }
	   turnAround();
	   putBeeper();
	   move();
	   
	   	while(noBeepersPresent()) {
	   		move();
	   			if (beepersPresent()) {
	   				turnAround();
	   				move();
	   				putBeeper();
	   				move();
	   			}
	   }
	   turnAround();
	   move();
	   checkBoardv();
}
   
   public void checkBoardv(){
	   while (notFacingNorth()){
		   turnLeft();
	   }
	   turnAround();
	   if(frontIsBlocked()) {
		   turnAround();
		   if(frontIsBlocked()) {
			   pickBeeper();
		   }
		   else {
			   vertical();
		   }
	   }
   }
   
   public void vertical() {
	   
	   while(notFacingNorth()) {
		   turnLeft();
	   }
	   
	   turnAround();
	   while(frontIsClear()) {
		   move();
	   }
	   putBeeper();
	   turnAround();
	   while(frontIsClear()) {
	   move();
	   }
	   turnAround();
	   putBeeper();
	   move();
	   
	   while(noBeepersPresent()) {
		   move();
		   if (beepersPresent()) {
			   turnAround();
			   move();
			   putBeeper();
			   move();
		   }
	   }
	   turnAround();
	   move();
	   pickBeeper();

   }

}