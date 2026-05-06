package pcd.mainApplicationAssignmentOne.model.ballUpdater;

public interface MonitorUpdateBalls {

    void updateBall(final int ballNumber);
    void updateTime(long dt);
    void timeToStop(int numberOfUpdater);
    void timeTiBegin(int numberOfUpdater);
    void createTurnsOfUpdaters(int numberOfUpdaters);
    void waitForUpdatePhase(int numberOfUpdater);
    void beginUpdatePhase();
    boolean areAllUpdatersDone();
    void checkCollisionWithHoles(int ballNumber);
    boolean areAllBallsDead();
    void resolveCollisionsBalls();
    void updatePlayersBalls();
    boolean isGameInProgress();
    void stopGame();
    boolean isParallelUpdatePhase();
    void stopParallelUpdsatePhase();
    void updateBallWithDt(long dtime, int ballNumber);
}
