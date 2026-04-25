package pcd.mainApplicationAssignmentOne.model.ballUpdater;

public interface MonitorUpdateBalls {

    public void updateBall(final int ballNumber);
    public void updateTime(long dt);
    public void timeToStop(int numberOfUpdater);
    public void timeTiBegin(int numberOfUpdater);
    public void createTurnsOfUpdaters(int numberOfUpdaters);
    public void waitForUpdatePhase(int numberOfUpdater);
    public void beginUpdatePhase();
    public boolean isTimeToRender();
}
