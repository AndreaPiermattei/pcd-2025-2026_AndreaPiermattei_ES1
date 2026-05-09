package pcd.mainApplicationAssignmentOne.model.monitors;

import pcd.mainApplicationAssignmentOne.model.interfaces.MonitorGameState;

public class MonitorGameStateImpl implements MonitorGameState {

    private boolean gameInProgress = true;

    @Override
    public synchronized boolean isGameInProgress() { 
        return gameInProgress;
    }
    @Override
    public synchronized void stopGame() {
        gameInProgress = false;
    }
}
