package pcd.mainApplicationAssignmentOne.model.ballUpdater;

public interface MonitorUpdateBalls {

    public long getTimeElapsed();
    public void updateBall(final long dt, final int ballNumber);
    public void resolveCollisionWithPlayerBall(final long dt, final int ballNumber);
}
