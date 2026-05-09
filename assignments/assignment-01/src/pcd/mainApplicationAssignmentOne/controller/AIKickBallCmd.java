package pcd.mainApplicationAssignmentOne.controller;


import pcd.mainApplicationAssignmentOne.controller.interfaces.Cmd;
import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.util.V2d;

public class AIKickBallCmd implements Cmd{

    
    private final double speedfactor;
    private final V2d directionKick;
    private final boolean debug = false;
    
    public AIKickBallCmd(double speedfactor, V2d directionKick) {
        this.speedfactor = speedfactor;
        this.directionKick = directionKick;
    }

    private void simpleLog(){
        System.out.println("AI Kick executed");
    }
    
    @Override
    public void execute(Board model) {
        if(this.debug)
            this.simpleLog();
        model.getAiBall().kick(this.directionKick.mul(this.speedfactor));
    }
    
}
