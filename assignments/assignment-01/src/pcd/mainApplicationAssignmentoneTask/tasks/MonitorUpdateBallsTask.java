package pcd.mainApplicationAssignmentoneTask.tasks;

public interface MonitorUpdateBallsTask {

    void updateBallWithDt(long dtime, int ballNumber);

    void timeToStop();

    boolean areAllUpdatersDone();

    void checkCollisionWithHoles(int ballNumber);

    boolean areAllBallsDead();

    void beginUpdatePhase();

    void updateNumberOfThreads(int actualNumberOfUpdaters);

    void resolveCollisions();

}