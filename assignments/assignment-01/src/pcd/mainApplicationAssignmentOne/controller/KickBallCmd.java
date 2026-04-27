package pcd.mainApplicationAssignmentOne.controller;

import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.util.V2d;

public class KickBallCmd implements Cmd{

    private final String name;
    private final double speedfactor;
    private final V2d directionKick;

    public KickBallCmd(final String direction, final double speed){
        this.name = direction;
        this.speedfactor = speed;
        switch (direction) {
            case "UP":
                this.directionKick = new V2d(0, 1);
                break;
            case "RIGHT":
                this.directionKick = new V2d(1, 0);
                break;
            case "DOWN":
                this.directionKick = new V2d(0, -1);
                break;
            case "LEFT":
                this.directionKick = new V2d(-1, 0);
                break;
            default:
                this.directionKick = new V2d(0, 0);
                break;
        }
    }

    @Override
    public void execute(final Board model) {
        //simplelog
        model.getPlayerBall().kick(this.directionKick.mul(this.speedfactor));
    }

    private void simpleLog(){
        System.out.println(this.name+" Kick executed");
    }
    
}
