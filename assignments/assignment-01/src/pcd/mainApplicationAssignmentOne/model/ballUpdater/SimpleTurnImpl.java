package pcd.mainApplicationAssignmentOne.model.ballUpdater;

public class SimpleTurnImpl {
    private boolean turn = true;

    public boolean isTurn() {
        return turn;
    }

    public void stopTurn(){
        this.turn = false;
    }

    public void beginTurn(){
        this.turn = true;
    }

    
}
