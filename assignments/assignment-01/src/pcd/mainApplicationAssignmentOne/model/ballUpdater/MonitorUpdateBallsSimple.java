package pcd.mainApplicationAssignmentOne.model.ballUpdater;

import java.util.ArrayList;
import java.util.List;

import pcd.mainApplicationAssignmentOne.model.Hole;
import pcd.mainApplicationAssignmentOne.model.board.Board;

public class MonitorUpdateBallsSimple implements MonitorUpdateBalls {

    private static final int INDEX_HUMAN_BALL = 0;
    private static final int INDEX_AI_BALL = 1;

    private final Board board;
    private long dt = 0;
    private int totalNumberOfUpdaters;
    private int numberOfUpdatersDone=0;
    private List<SimpleTurnImpl> statesOfUpdaters;
    private volatile boolean gameInProgress = true;

    @Override
    public synchronized boolean isGameInProgress() {
        return gameInProgress;
    }

    @Override
    public synchronized void stopGame() {
        this.gameInProgress = false;
        notifyAll();
    }

    private int calculateScorePlayer(int player){
        return this.board.getBalls()
        .stream()
        .filter(elem->!elem.isAlive())
        .filter(elem->elem.getBallCollidedWith().isPresent())
        .filter(elem->elem.getBallCollidedWith().get() == player)
        .toList()
        .size();
    }

    @Override
    public int calculateAIScore() {
        return calculateScorePlayer(INDEX_AI_BALL);
    }

    @Override
    public synchronized int calculateHumanScore(){
        return calculateScorePlayer(INDEX_HUMAN_BALL);
    }

    public MonitorUpdateBallsSimple(final Board board2) {
        this.board = board2;
        statesOfUpdaters = new ArrayList<>();
        
    }

    @Override
    public void createTurnsOfUpdaters(final int numberOfUpdaters){
        if(statesOfUpdaters.isEmpty()){
            this.totalNumberOfUpdaters = numberOfUpdaters;
            for(int i = 0; i < numberOfUpdaters; i++){
                statesOfUpdaters.add(new SimpleTurnImpl());
            }
        } 
    }

    @Override
    public void updateBall(final int ballNumber) {
        if(this.board.getBalls().get(ballNumber).isAlive()){
            this.board.getBalls().get(ballNumber).updateState(this.dt, board); 
        }
    }

    @Override
    public void checkCollisionWithHoles(final int ballNumber) {
        for(var hole : this.board.getHoles()){
            if(Hole.checkCollision(this.board.getBalls().get(ballNumber), hole)) {
                this.board.getBalls().get(ballNumber).kill();
            }   
        }
    }

    @Override
    public synchronized void updateTime(final long dt){
        this.dt = dt;
    }

    @Override
    public synchronized void timeToStop(final int numberOfUpdater){
        this.statesOfUpdaters.get(numberOfUpdater).stopTurn();
        this.numberOfUpdatersDone+=1;
    }

    @Override
    public synchronized void waitForUpdatePhase(final int numberOfUpdater){
        while(!this.statesOfUpdaters.get(numberOfUpdater).isTurn() && isGameInProgress()){
            try {
                
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }

    @Override
    public synchronized void beginUpdatePhase(){

        for(int i = 0; i< this.totalNumberOfUpdaters;i++){
            this.statesOfUpdaters.get(i).beginTurn();
        }
        this.numberOfUpdatersDone = 0;
        notifyAll();

    }

    @Override
    public synchronized void timeTiBegin(final int numberOfUpdater){
        this.statesOfUpdaters.get(numberOfUpdater).beginTurn();
    }

    @Override
    public synchronized boolean isTimeToRender(){
        return this.numberOfUpdatersDone == this.totalNumberOfUpdaters;
    }

    @Override
    public synchronized boolean areAllBallsDead(){
        return this.board.getBalls().stream().filter(elem->elem.isAlive()).toList().size() == 0;
    }

    @Override
    public synchronized void resolveCollisionsBalls(){
        this.board.updateStateCollisions();
    }

    @Override
    public synchronized void updatePlayersBalls(){
        this.board.updateEveryPlayerBall(dt);
    }

   

}
