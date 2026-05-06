package pcd.mainApplicationAssignmentOne.model;

import java.util.Random;

import pcd.mainApplicationAssignmentOne.util.V2d;

public class DumbEnemyAI extends Thread{

    private final static long WAITING_TIME = 3_000;
    private final boolean debugMode;
    private final Random rand = new Random(System.currentTimeMillis());
    private final MonitorBallOfAI monitor;

    public DumbEnemyAI(final String name, final Boolean isInDebug, final MonitorBallOfAI monitor){
        this.setName(name);
        this.debugMode = isInDebug;
        this.monitor = monitor;
    }

    private void sleepFor(long millis){
        try {
            Thread.sleep(millis);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private double chooseRandomAngle(){
       return rand.nextDouble()*Math.PI*0.25; 
    }

    private V2d calculateVelocityVector(final double angle){
        return new V2d(Math.cos(angle),Math.sin(angle)).mul(1.5);
    }

    private void decideIfTimeToKick(){

    }
    
    private void makeRandomMovement(){
        if(this.debugMode) 
            System.err.println(this.getName()+": choosing move");
		var vectorVelocity = this.calculateVelocityVector(this.chooseRandomAngle());
        if(this.debugMode)
            System.err.println(this.getName()+" has chosen: velocity-> "+vectorVelocity); 
        
        
        monitor.kickBallAI(vectorVelocity);
    }

    public void run() {
        var lastKicked = System.currentTimeMillis();
		while (true) {
            //sleepFor(WAITING_TIME);
            if (System.currentTimeMillis() - lastKicked > WAITING_TIME && !monitor.isBallMoving()) {
                this.makeRandomMovement();
				lastKicked = System.currentTimeMillis();
			}
            
		}		
	}
	
    
}
