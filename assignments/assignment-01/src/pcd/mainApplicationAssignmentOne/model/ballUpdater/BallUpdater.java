package pcd.mainApplicationAssignmentOne.model.ballUpdater;

public class BallUpdater extends Thread{

    private final MonitorUpdateBalls monitorParallelUpdateBall;
    private final int indexFirstBall;
    private final int indexLastBall;
    private int currentBallIndex;
    private boolean gameInProgress = true;
    private final int idThread;

    public BallUpdater(final int numberThread, final MonitorUpdateBalls monitorParallelUpdateBall, final int indexFirstBall, final int indexLastBall) {
        this.setName("Updater_N."+numberThread);
        this.idThread = numberThread;
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

    private void logicVersion1(){
        while (gameInProgress) {
            if(this.currentBallIndex <= this.indexLastBall){
                this.monitorParallelUpdateBall.updateBall(currentBallIndex);
                this.currentBallIndex+=1;
            }else{
                this.monitorParallelUpdateBall.timeToStop(this.idThread);
                this.monitorParallelUpdateBall.waitForUpdatePhase(this.idThread);
                
                this.currentBallIndex = this.indexFirstBall;
            }
		}	
    }

    private void logicVersion2(){
        while(gameInProgress){
            for(this.currentBallIndex=this.indexFirstBall;this.currentBallIndex<=this.indexLastBall;this.currentBallIndex++){
                this.monitorParallelUpdateBall.updateBall(this.currentBallIndex);
            }
            this.monitorParallelUpdateBall.timeToStop(this.idThread);
            this.monitorParallelUpdateBall.waitForUpdatePhase(this.idThread);

        }
    }
    
    public void run() {
		logicVersion2();	
	}
}
