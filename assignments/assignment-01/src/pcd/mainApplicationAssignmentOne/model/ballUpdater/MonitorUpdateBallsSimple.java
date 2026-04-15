package pcd.mainApplicationAssignmentOne.model.ballUpdater;

import java.util.List;

import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.model.board.Board;

public class MonitorUpdateBallsSimple implements MonitorUpdateBalls {

    private final List<Ball> ballsOnBoard;
    private final Board board;

    public MonitorUpdateBallsSimple(List<Ball> ballsOnBoard, Board board) {
        this.ballsOnBoard = ballsOnBoard;
        this.board = board;
    }

    @Override
    public void updateBall(long dt, int ballNumber) {
        this.ballsOnBoard.get(ballNumber).updateState(dt, board);
    }
    
}
