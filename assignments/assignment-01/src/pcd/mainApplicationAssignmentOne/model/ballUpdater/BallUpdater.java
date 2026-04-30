package pcd.mainApplicationAssignmentOne.model.ballUpdater;

import pcd.mainApplicationAssignmentOne.model.MonitorGameStateImpl;

public class BallUpdater extends Thread{

    private final MonitorUpdateBalls monitorParallelUpdateBall;
    private final MonitorGameStateImpl monitorGame;
    private final int indexFirstBall;
    private final int indexLastBall;
    private int currentBallIndex;
    private final int thradNumber;
    private final boolean permanentStop = false;

    public BallUpdater(final int numberThread, final MonitorUpdateBalls monitorParallelUpdateBall, final  MonitorGameStateImpl monitorGame, final int indexFirstBall, final int indexLastBall) {
        this.setName("Updater_N."+numberThread);
        this.monitorGame = monitorGame;
        this.thradNumber = numberThread;
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

    private void logicVersionWithFor(){
        while(monitorParallelUpdateBall.isGameInProgress()){
            //System.out.println(this.thradNumber+" begin update");
            for(this.currentBallIndex=this.indexFirstBall;this.currentBallIndex<=this.indexLastBall;this.currentBallIndex++){
                
                this.monitorParallelUpdateBall.updateBall(this.currentBallIndex);
                this.monitorParallelUpdateBall.checkCollisionWithHoles(this.currentBallIndex);
                
            }
            this.monitorParallelUpdateBall.timeToStop(this.thradNumber);
            this.monitorParallelUpdateBall.waitForUpdatePhase(this.thradNumber);
        }
        //System.out.println(this.getName()+" shutting down");
    }
    
    public void run() {
		logicVersionWithFor();	
	}
}
