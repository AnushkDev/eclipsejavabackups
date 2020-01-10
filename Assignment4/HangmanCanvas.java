/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
public class HangmanCanvas extends GCanvas {
/* public variables */
	private GLabel wordDrawn = new GLabel ("");
	private GLabel lettersRight = new GLabel ("");
	
/** Resets the display so that only the scaffold appears */
	public void reset() {	
		lettersWrong = "";
		lettersRight.setLabel("");
		removeAll();
		GLine scaffold = new GLine (getWidth()/2 - BEAM_LENGTH, getHeight()/2 - SCAFFOLD_HEIGHT/2, getWidth()/2 -BEAM_LENGTH, SCAFFOLD_HEIGHT);
		add(scaffold);
		GLine beam = new GLine (getWidth()/2 - BEAM_LENGTH, getHeight()/2 - SCAFFOLD_HEIGHT/2, getWidth()/2, getHeight()/2 - SCAFFOLD_HEIGHT/2);	
		add(beam);	
		GLine rope = new GLine (getWidth()/2, getHeight()/2 - SCAFFOLD_HEIGHT/2, getWidth()/2, getHeight()/2 - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH);
		add(rope);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		wordDrawn.setLabel(word);
		wordDrawn.setFont("DialogInput-32");
		wordDrawn.setLocation(getWidth()/2 - wordDrawn.getWidth()/2, getHeight() - wordDrawn.getAscent());
		add(wordDrawn);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	private String lettersWrong = "";
	
	public void noteCorrectGuess(String guess) {
		lettersRight.setLabel(lettersRight.getLabel()+guess);
		lettersRight.setFont("DialogInput-15");
		lettersRight.setLocation(wordDrawn.getX(), wordDrawn.getY() + lettersRight.getAscent());
		add(lettersRight);
	}
	
	public void noteIncorrectGuess(String letter) {
		lettersWrong += letter;
		int heightAfterRope = getHeight()/2 - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH;
		if (lettersWrong.length() == 1) {
			GOval head = new GOval(getWidth()/2 - HEAD_RADIUS,  heightAfterRope, HEAD_RADIUS*2, HEAD_RADIUS*2 );
			add(head);
		}
		else if (lettersWrong.length() == 2) {
			GLine body = new GLine(getWidth()/2, heightAfterRope + HEAD_RADIUS*2, getWidth()/2, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH);
			add(body);
		}
		else if(lettersWrong.length() == 3) {
			GLine upperLeftArm = new GLine (getWidth()/2, heightAfterRope + HEAD_RADIUS*2 + ARM_OFFSET_FROM_HEAD, getWidth()/2 - UPPER_ARM_LENGTH, heightAfterRope + HEAD_RADIUS*2 + ARM_OFFSET_FROM_HEAD);
			GLine lowerLeftArm = new GLine (getWidth()/2 - UPPER_ARM_LENGTH, heightAfterRope + HEAD_RADIUS*2 + ARM_OFFSET_FROM_HEAD, getWidth()/2 - UPPER_ARM_LENGTH, heightAfterRope + HEAD_RADIUS*2 + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
			add (upperLeftArm);
			add (lowerLeftArm);
		}
		else if(lettersWrong.length() == 4) {
			GLine upperRightArm = new GLine (getWidth()/2, heightAfterRope + HEAD_RADIUS*2 + ARM_OFFSET_FROM_HEAD, getWidth()/2 + UPPER_ARM_LENGTH, heightAfterRope + HEAD_RADIUS*2 + ARM_OFFSET_FROM_HEAD);
			GLine lowerRightArm = new GLine (getWidth()/2 + UPPER_ARM_LENGTH, heightAfterRope + HEAD_RADIUS*2 + ARM_OFFSET_FROM_HEAD, getWidth()/2 + UPPER_ARM_LENGTH, heightAfterRope + HEAD_RADIUS*2 + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
			add (upperRightArm);
			add (lowerRightArm);
		}
		else if(lettersWrong.length() == 5) {
			GLine leftHip = new GLine (getWidth()/2, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH, getWidth()/2 - HIP_WIDTH, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH);
			add(leftHip);
			GLine leftLeg = new GLine (getWidth()/2 - HIP_WIDTH, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH, getWidth()/2 - HIP_WIDTH, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH + LEG_LENGTH);
			add (leftLeg);
		}
		else if(lettersWrong.length() == 6) {
			GLine rightHip = new GLine (getWidth()/2, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH, getWidth()/2 + HIP_WIDTH, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH);
			add(rightHip);
			GLine rightLeg = new GLine (getWidth()/2 + HIP_WIDTH, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH, getWidth()/2 + HIP_WIDTH, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH + LEG_LENGTH);
			add (rightLeg);
		}
		else if(lettersWrong.length() == 7) {
			GLine leftFoot = new GLine (getWidth()/2 - HIP_WIDTH, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH + LEG_LENGTH, getWidth()/2 - FOOT_LENGTH - HIP_WIDTH, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH + LEG_LENGTH);
			add(leftFoot);
		}
		else if(lettersWrong.length() == 8) {
			GLine rightFoot = new GLine (getWidth()/2 + HIP_WIDTH, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH + LEG_LENGTH, getWidth()/2 + FOOT_LENGTH + HIP_WIDTH, heightAfterRope + HEAD_RADIUS*2 + BODY_LENGTH + LEG_LENGTH);
			add(rightFoot);
		}
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
