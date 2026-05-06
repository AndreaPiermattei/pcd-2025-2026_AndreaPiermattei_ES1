package pcd.mainApplicationAssignmentOne.model.board;

import java.util.*;
import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.model.Hole;

public class Board {

    private int scorePlayer = 0;
    private int scoreAI = 0;

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
    

    public void updateControlledPlayerBall(final long dt){
        getHumanBall().updateState(dt, this);
    }

    public void updateEveryPlayerBall(final long dt){
        this.playersBalls.forEach(player -> player.updateState(dt, this));
        for(var p: this.playersBalls){
            //p.updateState(dt, this);
            for(var h: this.holes){
                if(Hole.checkCollision(p, h)){
                    p.kill();
                }
            }
        }

    }

    public void updateStateCollisions(){
        for (int i = 0; i < balls.size() - 1; i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                if(balls.get(i).isAlive() && balls.get(j).isAlive())
                    Ball.resolveCollision(balls.get(i), balls.get(j));
            }
        }
    	for (var b: balls.stream().filter(ball -> ball.isAlive()).toList()) {
            playersBalls.forEach(player -> Ball.resolveCollision(player, b));
    	} 
        Ball.resolveCollision(getHumanBall(), getAiBall());
    }
    
    public List<Ball> getBalls(){
    	return balls;
    }
    
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

     private int calculateScorePlayer(final int player){
        return getBalls()
        .stream()
        .filter(elem->!elem.isAlive())
        .filter(elem->elem.getBallCollidedWith().isPresent())
        .filter(elem->elem.getBallCollidedWith().get() == player)
        .toList()
        .size();
    }

    
    private void updateAIScore() {
        this.scoreAI = calculateScorePlayer(2);
    }

    
    private void updateHumanScore(){
        this.scorePlayer = calculateScorePlayer(1);
    }

    public void updateScores(){
        this.updateAIScore();
        this.updateHumanScore();
    }

    public int getScorePlayer() {
        return scorePlayer;
    }

    public int getScoreAI() {
        return scoreAI;
    }

    public void checkWhoWins(){

        System.out.println("score:\n");
        System.out.println("AI:"+this.scoreAI);
        System.out.println("human:"+this.scorePlayer);
        if(!getAiBall().isAlive()){
            System.out.println("ai dead - human wins");
        
        }else if(!getHumanBall().isAlive()){
            System.out.println("human dead - ai wins");
        }else if(this.scoreAI > this.scorePlayer){
           System.out.println("ai wins");
        }else if(this.scoreAI < this.scorePlayer){
            System.out.println("human wins");
        }else{
            System.out.println("TIE");
        }

    }

}
