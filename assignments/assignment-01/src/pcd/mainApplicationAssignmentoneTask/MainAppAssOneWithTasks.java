package pcd.mainApplicationAssignmentoneTask;

import pcd.mainApplicationAssignmentoneTask.controller.MainLoopWithTask;

public class MainAppAssOneWithTasks {
    public static void main(String[] argv) {
        System.err.println("APPLICATION V2");
        MainLoopWithTask mainLoop = new MainLoopWithTask();
        mainLoop.initializeGame();
        mainLoop.run();
    }
}
