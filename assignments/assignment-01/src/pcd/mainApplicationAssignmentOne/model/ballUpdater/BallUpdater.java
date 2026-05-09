package pcd.mainApplicationAssignmentOne.model.ballUpdater;

import pcd.mainApplicationAssignmentOne.model.MonitorGameStateImpl;

public class BallUpdater extends Thread{

    private final MonitorUpdateBalls monitorParallelUpdateBall;
    private final MonitorGameStateImpl monitorGame;
    private final int indexFirstBall;
    private final int indexLastBall;
    private int currentBallIndex;
    private final int thradNumber;
    private final int numberOfBallsForUpdater;

    public BallUpdater(final int numberThread, final MonitorUpdateBalls monitorParallelUpdateBall, final  MonitorGameStateImpl monitorGame, final int indexFirstBall, final int indexLastBall) {
        this.setName("Updater_N."+numberThread);
        this.monitorGame = monitorGame;
        this.thradNumber = numberThread;
        this.monitorParallelUpdateBall = monitorParallelUpdateBall;
        this.indexFirstBall = indexFirstBall;
        this.indexLastBall = indexLastBall;
        this.currentBallIndex = indexFirstBall;
        this.numberOfBallsForUpdater = this.indexLastBall-this.indexFirstBall+1;
        System.out.println(this.getName()+" created!\n- number of balls: "+this.numberOfBallsForUpdater+"\n- index range: "+this.indexFirstBall+" - "+this.indexLastBall);
    }

    private boolean areAllUpdaterBallsDead(final int deadBalls){
        return deadBalls == this.numberOfBallsForUpdater;
    }

    private void logicVersionWithFor(){
        var lastUpdateTime = System.currentTimeMillis();
        var numberOfDeadBalls = 0;
        while(this.monitorGame.isGameInProgress() && !areAllUpdaterBallsDead(numberOfDeadBalls)){
            //System.out.println(this.thradNumber+" begin update");
            long elapsed = System.currentTimeMillis() - lastUpdateTime;
            for(this.currentBallIndex=this.indexFirstBall;this.currentBallIndex<=this.indexLastBall;this.currentBallIndex++){
                
                this.monitorParallelUpdateBall.updateBallWithDt(elapsed,this.currentBallIndex);
                var flagCollision = this.monitorParallelUpdateBall.checkCollisionWithHoles(this.currentBallIndex);
                if(flagCollision){
                    System.out.println(this.currentBallIndex+"_ball dead");
                    numberOfDeadBalls+=1;
                }
                
            }
            lastUpdateTime = System.currentTimeMillis() ;
            this.monitorParallelUpdateBall.timeToStop(this.thradNumber);
            this.monitorParallelUpdateBall.waitForUpdatePhase(this.thradNumber);
        }
        if(areAllUpdaterBallsDead(numberOfDeadBalls)){
            this.monitorParallelUpdateBall.timeToStopPermanent(this.thradNumber);
            System.out.println(this.getName()+": "+numberOfDeadBalls+"/"+this.numberOfBallsForUpdater+" dead -->early shutting down");
        }else{
            System.out.println(this.getName()+" shutting down");
        }
    }
    
    public void run() {
		logicVersionWithFor();	
	}
}
