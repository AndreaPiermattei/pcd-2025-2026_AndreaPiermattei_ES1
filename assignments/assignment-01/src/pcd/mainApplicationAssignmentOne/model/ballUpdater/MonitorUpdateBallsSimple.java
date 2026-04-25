package pcd.mainApplicationAssignmentOne.model.ballUpdater;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import pcd.mainApplicationAssignmentOne.model.board.Board;

public class MonitorUpdateBallsSimple implements MonitorUpdateBalls {

    private final Board board;
    private long dt = 0;
    private int totalNumberOfUpdaters;
    private int numberOfUpdatersDone=0;
    private List<SimpleTurnImpl> statesOfUpdaters;

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
        this.board.getBalls().get(ballNumber).updateState(this.dt, board);
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
        
        while(!this.statesOfUpdaters.get(numberOfUpdater).isTurn()){
            //System.out.println(numberOfUpdater+"wait");
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
    public void timeTiBegin(final int numberOfUpdater){
        this.statesOfUpdaters.get(numberOfUpdater).beginTurn();
    }

    @Override
    public synchronized boolean isTimeToRender(){
        return this.numberOfUpdatersDone == this.totalNumberOfUpdaters;
    }

}
