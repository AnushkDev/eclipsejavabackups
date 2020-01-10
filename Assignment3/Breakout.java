/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels.  IMPORTANT NOTE:
  * ON SOME PLATFORMS THESE CONSTANTS MAY **NOT** ACTUALLY BE THE DIMENSIONS
  * OF THE GRAPHICS CANVAS.  Use getWidth() and getHeight() to get the 
  * dimensions of the graphics canvas. */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board.  IMPORTANT NOTE: ON SOME PLATFORMS THESE 
  * CONSTANTS MAY **NOT** ACTUALLY BE THE DIMENSIONS OF THE GRAPHICS
  * CANVAS.  Use getWidth() and getHeight() to get the dimensions of
  * the graphics canvas. */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60; //60
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		addMouseListeners();
		drawSetup();
		playGame();
	}
	
	public int playedTurns = 0;
	public int bricksLeft;
	public GRect paddle;
	public GOval ball;
	public GLabel text;
	
	public void drawSetup() {
		//brick setup
		for (int rowsDone = 0; rowsDone < NBRICK_ROWS; rowsDone++) {
			for (int bricksLaid = 0; bricksLaid < NBRICKS_PER_ROW; bricksLaid++ ) {
				GRect brick = new GRect (getWidth()/2 - BRICK_WIDTH/2 * (NBRICKS_PER_ROW + 1) + bricksLaid * (BRICK_WIDTH + BRICK_SEP) , BRICK_Y_OFFSET + rowsDone * (BRICK_HEIGHT + BRICK_SEP), BRICK_WIDTH, BRICK_HEIGHT);
				add(brick);
				brick.setFilled(true);
				//brick colors
				if (rowsDone < 2) {
					brick.setFillColor(Color.RED);
					brick.setColor(Color.RED);
				}
				else if (rowsDone < 4) {
					brick.setFillColor(Color.ORANGE);
					brick.setColor(Color.ORANGE);
				}
				else if (rowsDone < 6) {
					brick.setFillColor(Color.YELLOW);
					brick.setColor(Color.YELLOW);
				}
				else if (rowsDone < 8) {
					brick.setFillColor(Color.GREEN);
					brick.setColor(Color.GREEN);
				}
				else {
					brick.setFillColor(Color.CYAN);
					brick.setColor(Color.CYAN);
				}
			bricksLeft = NBRICK_ROWS * NBRICKS_PER_ROW;
			playedTurns = 0;
			}
		}
		
		//paddle
		int xPaddle = getWidth()/2;
		paddle = new GRect (xPaddle - PADDLE_WIDTH/2, getHeight()- PADDLE_Y_OFFSET - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setFillColor(Color.BLACK);
		add(paddle);
		
		//ball
		ball = new GOval(getWidth()/2 - BALL_RADIUS, getHeight()/2 - BALL_RADIUS*2, 2*BALL_RADIUS, 2*BALL_RADIUS);
		ball.setFilled(true);
		ball.sendBackward();
		add(ball);	
	}
	
	public void playGame() {
		ball.setLocation(getWidth()/2 - BALL_RADIUS, getHeight()/2 - BALL_RADIUS*2);
		text = new GLabel ("You Won!");
		text.setColor(Color.RED);
		
		//score keeping
		GLabel score = new GLabel ( "SCORE: " + Integer.toString(NBRICK_ROWS * NBRICKS_PER_ROW - bricksLeft));
		score.setLocation(getWidth()/2 - score.getWidth()/2 , getHeight() - score.getAscent());
		score.setFont(text.getFont().deriveFont(Font.BOLD, 10));
		add(score);
		
		double vy = 3;
		double vx;
		RandomGenerator rgen = RandomGenerator.getInstance();
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		
		while(playedTurns < NTURNS) {
			score.setLabel( "SCORE: " + Integer.toString(NBRICK_ROWS * NBRICKS_PER_ROW - bricksLeft));
			score.setLocation(getWidth()/2 - score.getWidth()/2 , getHeight() - score.getAscent());
			
			ball.move(vx, vy);
	
			if (ball.getY() < 0) {
				vy = -vy;
			}
			if (ball.getX() > getWidth() - 2 * BALL_RADIUS) {
				vx = -vx;
			}
			else if (ball.getX() < 0) {
				vx = -vx;
			}
			else if (ball.getY() > getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS) {
				playedTurns++;
				ball.setLocation(getWidth()/2 - BALL_RADIUS, getHeight()/2 - BALL_RADIUS*2);
				if (playedTurns != 3) {
					text.setLabel(NTURNS - playedTurns + " tr" + (NTURNS - playedTurns == 1 ? "y" : "ies") +  " left.");
					text.setLocation(getWidth()/2 - text.getWidth()/2, getHeight()/2 + 20);
					add(text);
					pause(1000);
					remove (text);
				} else {
					text.setLabel("Game Over. Click to Start Again.");
					text.setLocation(getWidth()/2 - text.getWidth()/2, getHeight()/2 + 20);
					add(text);
					pause(1000);
					remove (text);
					waitForClick();
					reDraw();
				}
			}

		GObject collider = getCollider();
		if (collider == paddle) {
			if(ball.getY() > getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS*2 ) {
				vy = -vy;
			}
		}else if (collider != null ) {
			remove (collider);
			bricksLeft--;
			if (bricksLeft == 0) {
				reDraw();
			}else {
				vy = -vy;
			}
		}	
		pause(15);
		}
		
		if (playedTurns < NTURNS) {
			ball.setLocation(getWidth()/2 - BALL_RADIUS, getHeight()/2 - BALL_RADIUS*2);
			paddle.setLocation(getWidth()/2 - PADDLE_WIDTH/2, getHeight()- PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}
	
	public void reDraw() {
		if (bricksLeft == 0) {
			text.setLabel("You Won! Click to start Again");
			add(text);
			waitForClick();
		}
		remove (ball);
		remove (paddle);
		drawSetup();
	}
	
	//mouse tracking
	public void mouseMoved(MouseEvent e) {
		if (e.getX() - PADDLE_WIDTH/2 > getWidth() - PADDLE_WIDTH){
			paddle.setLocation(getWidth() - PADDLE_WIDTH, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
		else if (e.getX() < 0 + PADDLE_WIDTH/2) {
			paddle.setLocation(0, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
		else {
			paddle.setLocation(e.getX() - PADDLE_WIDTH/2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}
	
	public GObject getCollider() {
		//top left
		if( (getElementAt(ball.getX(), ball.getY() )) != null ) {
			return getElementAt(ball.getX(), ball.getY());
		}
		//top right
		else if ( (getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() )) != null) {
			return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY());
		}
		//bottom left
		else if ( (getElementAt(ball.getX(), ball.getY() + BALL_RADIUS*2 )) != null ) {
			return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS*2 );
		}
		//bottom right
		else if ( (getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() + BALL_RADIUS*2 )) != null ) {
			return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() + BALL_RADIUS*2 );
		}
		else {
			return null;
		}
	}
	
}

