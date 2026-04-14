package pcd.mainApplicationAssignmentOne.proveVarie;

import java.util.Random;

public class BallUpdaterThread extends Thread{

    
    private final int indexFirstBall;
    private final int indexLastBall;
    private int currentBallIndex;
    private double stupidValue;

    private final Random rand = new Random(2);


    private final MonitorUpdateBalls monitorParallelUpdateBall;

    public BallUpdaterThread(final String nameOfThread, final int numberOfThread, final int indexFirstBall, final int indexLastBall, MonitorUpdateBalls monitorInput){

        this.monitorParallelUpdateBall = monitorInput;
        this.stupidValue = numberOfThread*1.0;
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
            
            
            if(currentBallIndex > indexLastBall)
                currentBallIndex = indexFirstBall; //refactor: one line version
            sleepFor(rand.nextLong(500, 2001));
            System.out.println("thread "+this.getName()+" is about to update ball n:"+this.currentBallIndex);
            monitorParallelUpdateBall.updatePositionBall(currentBallIndex, stupidValue, stupidValue);
            System.out.println("thread "+this.getName()+" DONE!\n");
            currentBallIndex+=1;
            sleepFor(200);
		}		
	}
    
}
