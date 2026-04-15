package pcd.mainApplicationAssignmentOne.model.ballUpdater;

import pcd.mainApplicationAssignmentOne.model.board.Board;

public class MonitorUpdateBallsSimple implements MonitorUpdateBalls {

    private final Board board;

    public MonitorUpdateBallsSimple( Board board) {

        this.board = board;
    }

    @Override
    public void updateBall(long dt, int ballNumber) {
        System.out.println("    -updating ball N."+ballNumber);
        this.board.getBalls().get(ballNumber).updateState(dt, board);
        System.out.println("    +updated ball N."+ballNumber+"\n position-> "+this.board.getBalls().get(ballNumber).getPos());
    }
    
}
