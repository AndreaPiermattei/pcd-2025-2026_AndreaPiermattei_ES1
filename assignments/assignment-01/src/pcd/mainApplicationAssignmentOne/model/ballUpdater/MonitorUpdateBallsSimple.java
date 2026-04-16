package pcd.mainApplicationAssignmentOne.model.ballUpdater;

import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.model.board.BoardSimple;
import pcd.mainApplicationAssignmentOne.util.timeMenager.TimeMenager;

public class MonitorUpdateBallsSimple implements MonitorUpdateBalls {

    private final Board board;
    private final TimeMenager timer;

    public MonitorUpdateBallsSimple(final BoardSimple board, final TimeMenager timer) {

        this.board = board;
        this.timer = timer;
    }

    @Override
    public synchronized void updateBall(long dt, int ballNumber) {
        System.out.println("    -updating ball N."+ballNumber);
        this.board.getBalls().get(ballNumber).updateState(dt, board);
        System.out.println("    +updated ball N."+ballNumber+"\n position-> "+this.board.getBalls().get(ballNumber).getPos());
    }

    @Override
    public synchronized void resolveCollisionWithPlayerBall(final long dt, final int ballNumber) {
        System.out.println("    -checking collision with ball N."+ballNumber);
        Ball.resolveCollision(this.board.getPlayerBall(), this.board.getBalls().get(ballNumber));
        System.out.println("    -done collision with ball N."+ballNumber);

    }

    @Override
    public long getTimeElapsed() {
        return timer.getTimeElapsed();
    }
    
}
