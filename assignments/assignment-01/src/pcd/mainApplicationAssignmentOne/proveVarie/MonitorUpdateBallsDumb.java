package pcd.mainApplicationAssignmentOne.proveVarie;

import java.util.ArrayList;
import java.util.List;

import pcd.mainApplicationAssignmentOne.model.ballUpdater.MonitorUpdateBalls;

public class MonitorUpdateBallsDumb implements MonitorUpdateBalls{

    private List<DumbBall> ballsOnBoard;

    public MonitorUpdateBallsDumb(final List<DumbBall> elements) {
        this.ballsOnBoard = new ArrayList<DumbBall>(elements);
    }

    @Override
    public void updateBall(long dt,int ballNumber) {
        this.ballsOnBoard.get(ballNumber).updateState();
        System.out.println("\nball number: "+ballNumber+"    "+this.ballsOnBoard.get(ballNumber).getState());

    }

    
}
