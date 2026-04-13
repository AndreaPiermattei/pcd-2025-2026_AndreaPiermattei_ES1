package pcd.mainApplicationAssignmentOne.model;

import java.util.Random;

import pcd.mainApplicationAssignmentOne.util.V2d;

public class DumbEnemyAI extends Thread{

    private final boolean debugMode;
    private final Random rand = new Random(2);

    public DumbEnemyAI(final String name, final Boolean isInDebug){
        this.setName(name);
        this.debugMode = isInDebug;
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
    
    private void makeRandomMovement(){
        if(this.debugMode) 
            System.err.println(this.getName()+": choosing move");
        sleepFor(200);
		var vectorVelocity = this.calculateVelocityVector(this.chooseRandomAngle());
        if(this.debugMode)
            System.err.println(this.getName()+" has chosen: velocity-> "+vectorVelocity);
        sleepFor(200);
    }

    public void run() {
		while (true) {
            sleepFor(5000);
            this.makeRandomMovement();
		}		
	}
	
    
}
