package pcd.mainApplicationAssignmentOne.model.ballUpdater;

import java.util.List;

import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.model.board.BoardSimple;

public class MonitorUpdateBallsSimple implements MonitorUpdateBalls {

    private final Board board;
    private long dt = 0;
    private int totalNumberOfUpdaters;
    private int numberOfUpdatersDone=0;
    private List<Boolean> statesOfUpdaters;

    public MonitorUpdateBallsSimple(final Board board2) {
        this.board = board2;
    }

    @Override
    public void updateBall(int ballNumber) {
        System.out.println("    -updating ball N."+ballNumber+"----dt:"+this.dt);
        //this.board.getBalls().get(ballNumber).updateState(this.dt, board);
        //System.out.println("    +updated ball N."+ballNumber+"\n position-> "+this.board.getBalls().get(ballNumber).getPos());
    }

    @Override
    public synchronized void resolveCollisionWithPlayerBall(final int ballNumber) {
        //System.out.println("    -checking collision with ball N."+ballNumber);
        Ball.resolveCollision(this.board.getPlayerBall(), this.board.getBalls().get(ballNumber));
        //System.out.println("    -done collision with ball N."+ballNumber);

    }

    @Override
    public synchronized void updateTime(final long dt){
        this.dt = dt;
    }

}
