package pcd.mainApplicationAssignmentOne;

import pcd.mainApplicationAssignmentOne.controller.MainLoop;

public class MainAppAssignmentOne {
    public static void main(String[] argv) {
        System.err.println("APPLICATION V0");
        MainLoop mainThrad = new MainLoop();
        mainThrad.initializeGame();
        mainThrad.start();
    }
}
