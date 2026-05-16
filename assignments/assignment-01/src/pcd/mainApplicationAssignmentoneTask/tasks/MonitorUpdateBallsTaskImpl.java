package pcd.mainApplicationAssignmentoneTask.tasks;

import pcd.mainApplicationAssignmentOne.model.Hole;
import pcd.mainApplicationAssignmentOne.model.board.Board;

public class MonitorUpdateBallsTaskImpl implements MonitorUpdateBallsTask {

    private final Board board;
    private int totalNumberOfUpdaters;
    private int numberOfUpdatersDone=0;

    public MonitorUpdateBallsTaskImpl(Board board, int totalNumberOfUpdaters) {
        this.board = board;
        this.totalNumberOfUpdaters = totalNumberOfUpdaters;
    }

    @Override
    public void updateBallWithDt(final long dtime,final int ballNumber){
        if(this.board.getBalls().get(ballNumber).isAlive()){
            this.board.getBalls().get(ballNumber).updateState(dtime, board); 
        }
    }

    @Override
    public synchronized void timeToStop(){
        this.numberOfUpdatersDone +=1;
    }

    @Override
    public synchronized boolean areAllUpdatersDone(){
        return numberOfUpdatersDone >= totalNumberOfUpdaters;
    }

    @Override
    public void checkCollisionWithHoles(final int ballNumber) {
        
        for(var hole : this.board.getHoles()){
            if(this.board.getBalls().get(ballNumber).isAlive() 
                && Hole.checkCollision(this.board.getBalls().get(ballNumber), hole)) {
                this.board.getBalls().get(ballNumber).kill();
                
            }   
        }
    }

    @Override
    public void beginUpdatePhase(){
        this.numberOfUpdatersDone=0;
    }

    @Override
    public boolean areAllBallsDead(){
        return this.board.getBalls().stream().filter(elem->elem.isAlive()).toList().size() == 0;
    }

    @Override
    public void updateNumberOfThreads(final int actualNumberOfUpdaters){
        this.totalNumberOfUpdaters = actualNumberOfUpdaters;
    }

    @Override
    public void resolveCollisions(){
        this.board.updateStateCollisions();
    }
}
