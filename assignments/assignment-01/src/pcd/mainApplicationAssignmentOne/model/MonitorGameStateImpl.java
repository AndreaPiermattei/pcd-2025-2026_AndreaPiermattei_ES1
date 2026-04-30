package pcd.mainApplicationAssignmentOne.model;

public class MonitorGameStateImpl {

    private boolean gameInProgress = true;

    public synchronized boolean isGameInProgress() {
        System.err.println("NO IMPL");
        return false;
    }

    public synchronized void stopGame() {
         System.err.println("NO IMPL");
       // this.gameInProgress = false;
    }

    
    
}
