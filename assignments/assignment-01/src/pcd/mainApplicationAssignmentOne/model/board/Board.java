package pcd.mainApplicationAssignmentOne.model.board;

import java.util.*;
import pcd.mainApplicationAssignmentOne.model.Ball;

public class Board {

    protected List<Ball> balls;    
    protected Ball playerBall;
    protected Boundary bounds;
    
    public Board(){} 
    
    public void init(final String configurationType) {
        final BoardConf configurationOfBoard;
        switch (configurationType) {
            case "L":
                System.out.println("LARGE CONFIGURATION SELECTED");
                configurationOfBoard = new LargeBoardConf();
                break;
            
            case "S":
                System.out.println("MINIMAL CONFIGURATION SELECTED");
                configurationOfBoard = new MinimalBoardConf();
                break;

            case "M":
                System.out.println("MASSIVE CONFIGURATION SELECTED");
                configurationOfBoard = new MassiveBoardConf();
                break;
        
            default:
                System.out.println("SELECTION NOT RECOGNIZED!\n    DEFAULT SELECTED: Large");
                configurationOfBoard = new LargeBoardConf();
                break;
        }

    	balls = configurationOfBoard.getSmallBalls();    	
    	playerBall = configurationOfBoard.getPlayerBall(); 
    	bounds = configurationOfBoard.getBoardBoundary();
    }
    
    public void updateState(long dt) {

    	//playerBall.updateState(dt, this);      	
    	
    	/*for (int i = 0; i < balls.size() - 1; i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball.resolveCollision(balls.get(i), balls.get(j));
            }
        }
    	for (var b: balls) {
    		Ball.resolveCollision(playerBall, b);
    	} */
    	   	    	
    }

    public void updatePlayerBall(final long dt){
        playerBall.updateState(dt, this);
    }

    public void updateStateCollisions(){
        for (int i = 0; i < balls.size() - 1; i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball.resolveCollision(balls.get(i), balls.get(j));
            }
        }
    	for (var b: balls) {
    		Ball.resolveCollision(playerBall, b);
    	} 
    }
    
    public List<Ball> getBalls(){
    	return balls;
    }
    
    public Ball getPlayerBall() {
    	return playerBall;
    }
    
    public  Boundary getBounds(){
        return bounds;
    }
}
