package pcd.mainApplicationAssignmentOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pcd.mainApplicationAssignmentOne.controller.MainLoop;
import pcd.mainApplicationAssignmentOne.model.DumbEnemyAI;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.BallUpdater;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.MonitorUpdateBalls;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.MonitorUpdateBallsSimple;
import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.model.board.BoardSimple;
import pcd.mainApplicationAssignmentOne.model.board.LargeBoardConf;
import pcd.mainApplicationAssignmentOne.model.board.MassiveBoardConf;
import pcd.mainApplicationAssignmentOne.model.board.MinimalBoardConf;
import pcd.mainApplicationAssignmentOne.proveVarie.BallUpdaterThread;
import pcd.mainApplicationAssignmentOne.proveVarie.DumbBall;
import pcd.mainApplicationAssignmentOne.proveVarie.MonitorUpdateBallsDumb;
import pcd.mainApplicationAssignmentOne.util.V2d;
import pcd.mainApplicationAssignmentOne.util.timeMenager.TimeMenager;
import pcd.mainApplicationAssignmentOne.util.timeMenager.TimeMenagerImpl;
import pcd.mainApplicationAssignmentOne.view.View;
import pcd.mainApplicationAssignmentOne.view.ViewModel;

public class MainAppAssignmentOne {

    private static void waitAbit() {
		try {
			Thread.sleep(3000);
		} catch (Exception ex) {}
	}

    private static List<Thread> createBallUpdaters(final Board board, final MonitorUpdateBalls monitor){
       
        final var numberOfProcessors = Runtime.getRuntime().availableProcessors();
        final var numberOfBallsOnBoard = board.getBalls().size();

        System.out.println("CREATING BALL UPDATERS...\n    -N. processors available: "+numberOfProcessors
                            +"\n    -N. balls on the board: "+numberOfBallsOnBoard);

        final var numberOfBallUpdaters = (numberOfBallsOnBoard < numberOfProcessors ?
                                                                    numberOfBallsOnBoard : 
                                                                    numberOfProcessors+1);
        final var sizeBallListForThread = (numberOfBallsOnBoard < numberOfProcessors ?
                                                                    1 : 
                                                                    (numberOfBallsOnBoard/(numberOfBallUpdaters)));
        System.out.println("    -N. threads to create: " + numberOfBallUpdaters);
        System.out.println("    -N. of balls for each thread: " + sizeBallListForThread);
        
        List<Thread> listOfThreads = new ArrayList<>();
        int firstball = 0;
        for(int i = 0; i<numberOfBallUpdaters; i++){
            var nameOfThread = "BallUpdater N-"+(i+1);
            var lastBall = ((firstball+sizeBallListForThread-1 >= numberOfBallsOnBoard) ? (numberOfBallsOnBoard-1) : (firstball+sizeBallListForThread-1));
            listOfThreads.add(new BallUpdater(nameOfThread, monitor, firstball, lastBall));
            firstball+=sizeBallListForThread;
        }

        System.out.println("DONE - CREATING BALL UPDATERS");
        return listOfThreads;
    }
    private static List<Thread> createOneUpdater(final Board board, final MonitorUpdateBalls monitor){

        List<Thread> list = new ArrayList<>();
        list.add(new BallUpdater("ONLY THREAD", monitor, 0, board.getBalls().size()-1));
        return list;
        
    }
    private static void launchUpdaters(List<Thread> threads){
        
        if(threads.isEmpty()) throw new IllegalArgumentException("no threads were given: EMPTHY LIST");
        
        for(int i=0; i<threads.size();i++){
            threads.get(i).start();
        }
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

        BoardSimple board = new BoardSimple();
		board.init(new LargeBoardConf());
        TimeMenager timer = new TimeMenagerImpl();
        timer.init();

        MonitorUpdateBalls monitor = new MonitorUpdateBallsSimple(board,timer);
        //createBallUpdaters(board, monitor);
        
       // int nFrames = 0;
		//long t0 = System.currentTimeMillis();
        var lastKickTime = System.currentTimeMillis();
		//long lastUpdateTime = System.currentTimeMillis();
			
		var pb = board.getPlayerBall();
		var rand = new Random(2);
		
        try{
            //launchUpdaters(createOneUpdater(board, monitor));
        }catch(Exception e){
            System.err.println(e);
            System.exit(1);
        }
		waitAbit();

        //ViewModel viewModel = new ViewModel();
		//View view = new View(viewModel, 1200, 800);
        //viewModel.update(board, 0);			
		//view.render();
        //timer.updateTime();	
        //waitAbit();
        System.out.println("BEGIN!");
        
        MainLoop mainThrad = new MainLoop();
        mainThrad.start();

    }
}
