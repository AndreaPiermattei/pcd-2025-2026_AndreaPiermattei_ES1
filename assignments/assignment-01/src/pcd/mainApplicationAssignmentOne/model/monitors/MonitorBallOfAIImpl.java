package pcd.mainApplicationAssignmentOne.model.monitors;

import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.model.interfaces.MonitorBallOfAI;
import pcd.mainApplicationAssignmentOne.util.V2d;

public class MonitorBallOfAIImpl implements MonitorBallOfAI {

    private final Board board;

    public MonitorBallOfAIImpl(Board board) {
        this.board = board;
    }

    @Override
    public synchronized void kickBallAI(final V2d velocity){
        this.board.getAiBall().kick(velocity);
    }

    @Override
    public synchronized boolean isBallMoving(){
        return this.board.getAiBall().getVel().abs() >= 0.05;
    }

    @Override
    public synchronized void update(final long dt){
        this.board.updateBallAI(dt);
    }
    
}
