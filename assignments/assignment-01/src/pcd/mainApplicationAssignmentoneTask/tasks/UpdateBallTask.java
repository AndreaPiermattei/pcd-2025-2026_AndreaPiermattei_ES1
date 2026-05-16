package pcd.mainApplicationAssignmentoneTask.tasks;

public class UpdateBallTask implements Runnable{


    private final MonitorUpdateBallsTask monitorParallelUpdateBall;
    private final int indexFirstBall;
    private final int indexLastBall;
    private int currentBallIndex;
    private long lastUpdateTime;

    public UpdateBallTask(MonitorUpdateBallsTask monitorParallelUpdateBall,int indexFirstBall, 
        int indexLastBall, long lastUpdated) {

        this.monitorParallelUpdateBall = monitorParallelUpdateBall;
        this.indexFirstBall = indexFirstBall;
        this.indexLastBall = indexLastBall;
        this.lastUpdateTime = lastUpdated;
    }

    @Override
    public void run() {
        long elapsed = System.currentTimeMillis() - lastUpdateTime;
        System.out.println("begin update balls");
        for(this.currentBallIndex=this.indexFirstBall;this.currentBallIndex<=this.indexLastBall;this.currentBallIndex++){
            
            this.monitorParallelUpdateBall.updateBallWithDt(elapsed,this.currentBallIndex);
            this.monitorParallelUpdateBall.checkCollisionWithHoles(this.currentBallIndex);
        }   
        this.monitorParallelUpdateBall.timeToStop(); 
        System.out.println("pausing"); 
    }
    
}
