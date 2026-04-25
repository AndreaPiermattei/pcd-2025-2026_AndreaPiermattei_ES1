package pcd.mainApplicationAssignmentOne.model;

public class MonitorGameStateImpl {

    private boolean gameInProgress = true;

    public synchronized boolean isGameInProgress() {
        return gameInProgress;
    }

    public synchronized void stopGame() {
        this.gameInProgress = false;
    }

    
    
}
