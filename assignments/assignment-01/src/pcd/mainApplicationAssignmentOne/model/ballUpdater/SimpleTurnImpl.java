package pcd.mainApplicationAssignmentOne.model.ballUpdater;

public class SimpleTurnImpl {
    private boolean turn = true;
    private boolean permanentStop = false;

    public boolean isTurn() {
        return turn;
    }

    public void stopTurn(){
        this.turn = false;
    }

    public void beginTurn(){
        this.turn = true;
    }

    public void stopPermanent(){
        this.permanentStop = true;
    }
    
    public boolean hasStopedPermanently(){
        return this.permanentStop;
    }
}
