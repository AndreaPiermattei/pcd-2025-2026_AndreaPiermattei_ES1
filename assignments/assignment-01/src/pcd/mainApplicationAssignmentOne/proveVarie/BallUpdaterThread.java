package pcd.mainApplicationAssignmentOne.proveVarie;

import java.util.Random;

import pcd.mainApplicationAssignmentOne.model.ballUpdater.MonitorUpdateBalls;

public class BallUpdaterThread extends Thread{

    
    private final int indexFirstBall;
    private final int indexLastBall;
    private int currentBallIndex;
    private Random rand;


    private final MonitorUpdateBalls monitorParallelUpdateBall;

    public BallUpdaterThread(final String nameOfThread, final int numberOfThread, final int indexFirstBall, final int indexLastBall, MonitorUpdateBalls monitorInput){

        rand = new Random(numberOfThread);
        this.monitorParallelUpdateBall = monitorInput;
        this.setName(nameOfThread+numberOfThread);
        this.indexFirstBall=indexFirstBall;
        this.indexLastBall=indexLastBall;
        this.currentBallIndex=this.indexFirstBall;
    }

    private void sleepFor(long millis){
        try {
            Thread.sleep(millis);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }


    public void run() {
		while (true) {
            var t = rand.nextLong(100, 6001);
            System.out.println("thread "+this.getName()+" waiting "+t+"..."); //funzione utile solo per testing
            sleepFor(t); 
            if(currentBallIndex > indexLastBall)
                currentBallIndex = indexFirstBall; //refactor: one line version
            
            System.out.println("thread "+this.getName()+" is about to update ball n:"+this.currentBallIndex);
            monitorParallelUpdateBall.updateBall(0,currentBallIndex);
            System.out.println("thread "+this.getName()+" DONE!\n");
            currentBallIndex+=1;
            sleepFor(200);
		}		
	}
    
}
