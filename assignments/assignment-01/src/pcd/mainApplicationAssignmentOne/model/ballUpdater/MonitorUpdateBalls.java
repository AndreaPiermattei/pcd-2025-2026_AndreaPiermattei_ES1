package pcd.mainApplicationAssignmentOne.model.ballUpdater;

public interface MonitorUpdateBalls {

    void updateBall(final int ballNumber);
    void updateTime(long dt);
    void timeToStop(int numberOfUpdater);
    void timeTiBegin(int numberOfUpdater);
    void createTurnsOfUpdaters(int numberOfUpdaters);
    void waitForUpdatePhase(int numberOfUpdater);
    void beginUpdatePhase();
    boolean isTimeToRender();
    int calculateScores();
    void checkCollisionWithHoles(int ballNumber);
    boolean areAllBallsDead();
    void resolveCollisionsBalls();
    void updatePlayersBalls();
}
