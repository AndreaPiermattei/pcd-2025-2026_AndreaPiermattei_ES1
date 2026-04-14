package pcd.mainApplicationAssignmentOne;

import java.util.ArrayList;
import java.util.List;

import pcd.mainApplicationAssignmentOne.model.DumbEnemyAI;

import pcd.mainApplicationAssignmentOne.proveVarie.BallUpdaterThread;
import pcd.mainApplicationAssignmentOne.proveVarie.DumbBall;
import pcd.mainApplicationAssignmentOne.proveVarie.MonitorUpdateBalls;
import pcd.mainApplicationAssignmentOne.proveVarie.MonitorUpdateBallsDumb;

public class MainAppAssignmentOne {
    public static void main(String[] argv) {
        System.err.println("APPLICATION V0");

        //final var enemy = new DumbEnemyAI("#DUMB_AI_BALL",true);
        //enemy.start();
        int sizeListBalls = 15;
        List<DumbBall> balls = new ArrayList<>();
        for(int i=0; i<sizeListBalls;i++){
            balls.add(new DumbBall());
        }
        MonitorUpdateBalls monitor = new MonitorUpdateBallsDumb(balls);

        BallUpdaterThread th1 = new BallUpdaterThread("UPDATERBALL",1, 0, 4, monitor);
        BallUpdaterThread th2 = new BallUpdaterThread("UPDATERBALL",2, 5, 9, monitor);
        BallUpdaterThread th3 = new BallUpdaterThread("UPDATERBALL",3, 10, 14, monitor);

        th1.start();
        th2.start();
        th3.start();
    }
}
