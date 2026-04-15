package pcd.mainApplicationAssignmentOne;

import java.util.ArrayList;
import java.util.List;

import pcd.mainApplicationAssignmentOne.model.DumbEnemyAI;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.MonitorUpdateBalls;
import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.model.board.LargeBoardConf;
import pcd.mainApplicationAssignmentOne.model.board.MassiveBoardConf;
import pcd.mainApplicationAssignmentOne.proveVarie.BallUpdaterThread;
import pcd.mainApplicationAssignmentOne.proveVarie.DumbBall;
import pcd.mainApplicationAssignmentOne.proveVarie.MonitorUpdateBallsDumb;
import pcd.mainApplicationAssignmentOne.view.View;
import pcd.mainApplicationAssignmentOne.view.ViewModel;

public class MainAppAssignmentOne {

    private static void waitAbit() {
		try {
			Thread.sleep(2000);
		} catch (Exception ex) {}
	}

    private static void createBallUpdaters(final Board board){
       
        final var numberOfProcessors = Runtime.getRuntime().availableProcessors();
        final var numberOfBallsOnBoard = board.getBalls().size();

        System.out.println("CREATING BALL UPDATERS...\n    -N. processors available: "+numberOfProcessors
                            +"\n    -N. balls on the board: "+numberOfBallsOnBoard);
                            
        final var numberOfBallUpdaters = (numberOfBallsOnBoard < numberOfProcessors ?
                                                                    numberOfBallsOnBoard : 
                                                                    numberOfProcessors+1);
        final var sizeBallListForThread = (numberOfBallsOnBoard < numberOfProcessors ?
                                                                    1 : 
                                                                    (numberOfBallsOnBoard/(numberOfBallUpdaters)))+1;
        System.out.println("    -N. threads to create: " + numberOfBallUpdaters);
        System.out.println("    -N. of balls for each thread: " + sizeBallListForThread);
        
        List<Thread> listOfThreads = new ArrayList<>();
        for(int i = 0; i<numberOfBallUpdaters; i++){
            var nameOfThread = "BallUpdater N.-"+(i+1);
        }

        System.out.println("DONE - CREATING BALL UPDATERS");
    }

    public static void main(String[] argv) {
        System.err.println("APPLICATION V0");

        //final var enemy = new DumbEnemyAI("#DUMB_AI_BALL",true);
        //enemy.start();
        //int sizeListBalls = 15;
        //List<DumbBall> balls = new ArrayList<>();
        //for(int i=0; i<sizeListBalls;i++){
       //     balls.add(new DumbBall());
        //}
        //MonitorUpdateBalls monitor = new MonitorUpdateBallsDumb(balls);

       // BallUpdaterThread th1 = new BallUpdaterThread("UPDATERBALL",1, 0, 4, monitor);
       // BallUpdaterThread th2 = new BallUpdaterThread("UPDATERBALL",2, 5, 9, monitor);
       // BallUpdaterThread th3 = new BallUpdaterThread("UPDATERBALL",3, 10, 14, monitor);

       // th1.start();
        //th2.start();
       // th3.start();

        //--------------------

        Board board = new Board();
		board.init(new LargeBoardConf());
        
        createBallUpdaters(board);

        ViewModel viewModel = new ViewModel();
		View view = new View(viewModel, 1200, 800);
        viewModel.update(board, 0);			
		view.render();
		waitAbit();

        
        

        


    }
}
