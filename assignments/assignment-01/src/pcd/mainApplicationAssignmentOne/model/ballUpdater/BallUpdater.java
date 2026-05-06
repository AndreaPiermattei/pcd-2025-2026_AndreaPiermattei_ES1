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

    private void logicVersionWithFor(){
        var lastUpdateTime = System.currentTimeMillis();

        while(this.monitorGame.isGameInProgress()){
            //System.out.println(this.thradNumber+" begin update");
            long elapsed = System.currentTimeMillis() - lastUpdateTime;
            for(this.currentBallIndex=this.indexFirstBall;this.currentBallIndex<=this.indexLastBall;this.currentBallIndex++){
                
                this.monitorParallelUpdateBall.updateBallWithDt(elapsed,this.currentBallIndex);
                this.monitorParallelUpdateBall.checkCollisionWithHoles(this.currentBallIndex);
                
            }
            lastUpdateTime = System.currentTimeMillis() ;
            this.monitorParallelUpdateBall.timeToStop(this.thradNumber);
            this.monitorParallelUpdateBall.waitForUpdatePhase(this.thradNumber);
        }
        System.out.println(this.getName()+" shutting down");
    }
    
    public void run() {
		logicVersionWithFor();	
	}
}
