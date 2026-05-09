package pcd.mainApplicationAssignmentOne.controller;

import pcd.mainApplicationAssignmentOne.controller.interfaces.Cmd;
import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.util.V2d;

public class KickBallCmd implements Cmd{

    private final String name;
    private final double speedfactor;
    private final V2d directionKick;
    private final boolean debug = false;

    private V2d directionFromString(final String direction){
        V2d directionChosen;
        switch (direction) {
            case "UP":
                directionChosen = new V2d(0, 1);
                break;
            case "RIGHT":
                directionChosen = new V2d(1, 0);
                break;
            case "DOWN":
                directionChosen = new V2d(0, -1);
                break;
            case "LEFT":
                directionChosen = new V2d(-1, 0);
                break;
            default:
                directionChosen = new V2d(0, 0);
                break;
        }
        return directionChosen;
    }
    
    public KickBallCmd(final String direction, final double speed){
        this.name = direction;
        this.speedfactor = speed;
        this.directionKick = directionFromString(direction);
    }


    @Override
    public void execute(final Board model) {
        if(this.debug)
            this.simpleLog();
        model.getHumanBall().kick(this.directionKick.mul(this.speedfactor));
    }

    private void simpleLog(){
        System.out.println(this.name+" Kick executed");
    }
    
}
