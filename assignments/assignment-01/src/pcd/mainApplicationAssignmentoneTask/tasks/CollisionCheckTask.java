package pcd.mainApplicationAssignmentoneTask.tasks;

public class CollisionCheckTask implements Runnable{

    private final MonitorUpdateBallsTask monitorBall;
    
    public CollisionCheckTask(MonitorUpdateBallsTask monitorParallelUpdateBall) {
        this.monitorBall = monitorParallelUpdateBall;
    }

    @Override
    public void run() {
        monitorBall.resolveCollisions();
    }
    
}
