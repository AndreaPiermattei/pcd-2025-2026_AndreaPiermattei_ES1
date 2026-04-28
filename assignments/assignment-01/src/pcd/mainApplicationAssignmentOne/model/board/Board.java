package pcd.mainApplicationAssignmentOne.model.board;

import java.util.*;
import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.model.Hole;

public class Board {


    private static final int INDEX_HUMAN_BALL = 0;
    private static final int INDEX_AI_BALL = 1;

    protected List<Ball> balls;    
    protected List<Hole> holes;    
    private List<Ball> playersBalls;

    //protected Ball playerBall;
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
                configurationOfBoard = new SmallBoardConf();
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

        this.playersBalls = configurationOfBoard.getPlayersBalls();
    	this.balls = configurationOfBoard.getSmallBalls();    	
    	//this.playerBall = configurationOfBoard.getPlayerBall(); 
    	this.bounds = configurationOfBoard.getBoardBoundary();
        this.holes = configurationOfBoard.getHoles();
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

    public void updateControlledPlayerBall(final long dt){
        getHumanBall().updateState(dt, this);
    }

    public void updateEveryPlayerBall(final long dt){
        this.playersBalls.forEach(player -> player.updateState(dt, this));
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
            playersBalls.forEach(player -> Ball.resolveCollision(player, b));
    		//Ball.resolveCollision(playerBall, b);
    	} 

        Ball.resolveCollision(getHumanBall(), getAiBall());
    }
    
    public List<Ball> getBalls(){
    	return balls;
    }
    
    /*public Ball getPlayerBall() {
    	return playerBall;
    }*/
    
    public  Boundary getBounds(){
        return bounds;
    }

    public List<Hole> getHoles() {
        return holes;
    }

    public List<Ball> getPlayersBalls(){
        return this.playersBalls;
    }

    public Ball getHumanBall() {
    	return this.playersBalls.get(INDEX_HUMAN_BALL);
    }

    public Ball getAiBall(){
        return this.playersBalls.get(INDEX_AI_BALL);
    }

}
