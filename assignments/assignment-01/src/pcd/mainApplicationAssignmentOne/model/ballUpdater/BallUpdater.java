package pcd.mainApplicationAssignmentOne.model.ballUpdater;

public class BallUpdater extends Thread{

    private final MonitorUpdateBalls monitorParallelUpdateBall;
    private final int indexFirstBall;
    private final int indexLastBall;
    private int currentBallIndex;
    private boolean gameInProgress = true;

    public BallUpdater(final int numberThread, final MonitorUpdateBalls monitorParallelUpdateBall, final int indexFirstBall, final int indexLastBall) {
        this.setName("Updater_N."+numberThread);
        this.monitorParallelUpdateBall = monitorParallelUpdateBall;
        this.indexFirstBall = indexFirstBall;
        this.indexLastBall = indexLastBall;
        this.currentBallIndex = indexFirstBall;
        System.out.println(this.getName()+" created with range: "+this.indexFirstBall+" - "+this.indexLastBall);
    }

    private void sleepFor(long millis){
        try {
            Thread.sleep(millis);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
    public void run() {
		while (gameInProgress) {
            if(this.currentBallIndex <= this.indexLastBall){
                System.out.println("thread "+this.getName()+" is about to update ball n:"+this.currentBallIndex);
                this.monitorParallelUpdateBall.updateBall(currentBallIndex);
                //this.monitorParallelUpdateBall.resolveCollisionWithPlayerBall(dt, currentBallIndex);
                System.out.println("thread "+this.getName()+" DONE!\n");
                currentBallIndex+=1;
            }else{
                this.currentBallIndex = this.indexFirstBall;
            }
		}		
	}
}
