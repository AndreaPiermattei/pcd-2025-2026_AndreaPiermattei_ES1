package pcd.mainApplicationAssignmentOne.model.interfaces;

import pcd.mainApplicationAssignmentOne.util.V2d;

public interface MonitorBallOfAI {

    void kickBallAI(V2d velocity);

    boolean isBallMoving();

    void update(long dt);

}