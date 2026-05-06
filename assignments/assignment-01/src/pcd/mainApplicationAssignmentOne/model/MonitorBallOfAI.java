package pcd.mainApplicationAssignmentOne.model;

import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.util.V2d;

public class MonitorBallOfAI {

    private final Board board;

    public MonitorBallOfAI(Board board) {
        this.board = board;
    }

    public synchronized void kickBallAI(final V2d velocity){
        this.board.getAiBall().kick(velocity);
    }

    public synchronized boolean isBallMoving(){
        return this.board.getAiBall().getVel().abs() >= 0.05;
    }

    public synchronized void update(final long dt){
        this.board.updateBallAI(dt);
    }
    
}
