package pcd.mainApplicationAssignmentOne.model.monitors;

import pcd.mainApplicationAssignmentOne.model.interfaces.MonitorGameState;

public class MonitorGameStateImpl implements MonitorGameState {

    private volatile boolean gameInProgress = true;

    @Override
    public boolean isGameInProgress() { 
        return gameInProgress;
    }
    @Override
    public synchronized void stopGame() {
        gameInProgress = false;
    }
}
