package pcd.mainApplicationAssignmentoneTask.tasks;

public class CollisionCheckTask implements Runnable{

    private final MonitorUpdateBallsTask monitorParallelUpdateBall;
    
    public CollisionCheckTask(MonitorUpdateBallsTask monitorParallelUpdateBall) {
        this.monitorParallelUpdateBall = monitorParallelUpdateBall;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
