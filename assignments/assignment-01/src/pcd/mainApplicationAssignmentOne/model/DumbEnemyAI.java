package pcd.mainApplicationAssignmentOne.model;

import java.util.Random;

import pcd.mainApplicationAssignmentOne.util.V2d;

public class DumbEnemyAI extends Thread{

    private final static long WAITING_TIME = 3_000;
    private final boolean debugMode;
    private final Random rand = new Random(System.currentTimeMillis());
    private final MonitorBallOfAI monitorBall;
    private final MonitorGameStateImpl monitorGame;

    public DumbEnemyAI(final String name, final Boolean isInDebug, final MonitorBallOfAI monitorBall, final MonitorGameStateImpl monitorGame){
        this.setName(name);
        this.debugMode = isInDebug;
        this.monitorBall = monitorBall;
        this.monitorGame = monitorGame;
    }


    private double chooseRandomAngle(){
       return rand.nextDouble()*Math.PI*0.25; 
    }

    private V2d calculateVelocityVector(final double angle){
        return new V2d(Math.cos(angle),Math.sin(angle)).mul(1.5);
    }

    private long isTimeToKick(long lastKicked) {
        return System.currentTimeMillis() - lastKicked;
    }

    private void makeRandomMovement(){
        if(this.debugMode) 
            System.out.println(this.getName()+": choosing move");
		var vectorVelocity = this.calculateVelocityVector(this.chooseRandomAngle());
        if(this.debugMode)
            System.out.println(this.getName()+" has chosen: velocity-> "+vectorVelocity); 
        monitorBall.kickBallAI(vectorVelocity);
    }

    public void run() {
        var lastKicked = System.currentTimeMillis();
		while (monitorGame.isGameInProgress()) {
            //sleepFor(WAITING_TIME);
            if (isTimeToKick(lastKicked) > WAITING_TIME && !monitorBall.isBallMoving()) {
                this.makeRandomMovement();
				lastKicked = System.currentTimeMillis();
			}
            
		}	
        System.out.println(this.getName()+" shutting down");	
	}

}
