package pcd.mainApplicationAssignmentOne;

import pcd.mainApplicationAssignmentOne.model.DumbEnemyAI;

public class MainAppAssignmentOne {
    public static void main(String[] argv) {
        System.err.println("APPLICATION V0");

        final var enemy = new DumbEnemyAI("#DUMB_AI_BALL",true);

        enemy.start();
    }
}
