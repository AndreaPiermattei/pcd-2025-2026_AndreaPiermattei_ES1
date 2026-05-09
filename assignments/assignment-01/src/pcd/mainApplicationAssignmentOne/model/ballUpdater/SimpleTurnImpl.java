package pcd.mainApplicationAssignmentOne.model.ballUpdater;

import pcd.mainApplicationAssignmentOne.model.interfaces.Turn;

public class SimpleTurnImpl implements Turn {
    private boolean turn = true;
    private boolean permanentStop = false;

    @Override
    public boolean isTurn() {
        return turn;
    }

    @Override
    public void stopTurn(){
        this.turn = false;
    }

    @Override
    public void beginTurn(){
        this.turn = true;
    }

    @Override
    public void stopPermanent(){
        this.permanentStop = true;
    }
    
    @Override
    public boolean hasStopedPermanently(){
        return this.permanentStop;
    }
}
