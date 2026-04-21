package pcd.mainApplicationAssignmentOne.model.ballUpdater;

public interface MonitorUpdateBalls {

    public void updateBall(final int ballNumber);
    public void resolveCollisionWithPlayerBall(final int ballNumber);
    public void updateTime(long dt);
}
