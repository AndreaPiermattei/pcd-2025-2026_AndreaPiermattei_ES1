package pcd.mainApplicationAssignmentOne.model.board;

import java.util.*;
import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.model.Hole;

public class Board {

    protected List<Ball> balls;    
    protected List<Hole> holes;    

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
        holes = configurationOfBoard.getHoles();
    }
    
    /*public void updateState(long dt) {

    	playerBall.updateState(dt, this);      	
    	
    	for (int i = 0; i < balls.size() - 1; i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball.resolveCollision(balls.get(i), balls.get(j));
            }
        }
    	for (var b: balls) {
    		Ball.resolveCollision(playerBall, b);
    	}

        for(int i = 0; i<holes.size();i++){
            for (int j = 0; j < balls.size(); j++) {
                if(Hole.checkCollision(balls.get(j),holes.get(i))){
                    balls.get(j).kill();
                }
            }
        }
    	   	    	
    }*/

    public void updatePlayerBall(final long dt){
        playerBall.updateState(dt, this);
    }

    public void updateStateCollisions(){
        for (int i = 0; i < balls.size() - 1; i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                if(balls.get(i).isAlive() && balls.get(j).isAlive())
                    Ball.resolveCollision(balls.get(i), balls.get(j));
            }
        }
        /*for(int i = 0; i<holes.size();i++){
            for (int j = 0; j < balls.size(); j++) {
                if(Hole.checkCollision(balls.get(j),holes.get(i))){
                    balls.get(j).kill();
                }
            }
        }*/ //sostiyuito dal controllo fatto con i Updaters
    	for (var b: balls.stream().filter(ball -> ball.isAlive()).toList()) {
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

    public List<Hole> getHoles() {
        return holes;
    }

}
