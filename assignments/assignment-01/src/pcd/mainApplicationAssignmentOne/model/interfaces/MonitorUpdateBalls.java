package pcd.mainApplicationAssignmentOne.model.interfaces;

public interface MonitorUpdateBalls {

    void updateBall(final int ballNumber);
    void updateTime(long dt);
    void timeToStop(int numberOfUpdater);
    void timeTiBegin(int numberOfUpdater);
    void createTurnsOfUpdaters(int numberOfUpdaters);
    void waitForUpdatePhase(int numberOfUpdater);
    void beginUpdatePhase();
    boolean areAllUpdatersDone();
    boolean checkCollisionWithHoles(int ballNumber);
    boolean areAllBallsDead();
    void resolveCollisionsBalls();
    void updatePlayersBalls();
    boolean isParallelUpdatePhase();
    void stopParallelUpdsatePhase();
    void updateBallWithDt(long dtime, int ballNumber);
    void informGameOver();
    boolean isGameInProgress();
    void timeToStopPermanent(int numberOfUpdater);
}
