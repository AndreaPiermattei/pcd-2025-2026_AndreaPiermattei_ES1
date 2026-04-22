package pcd.mainApplicationAssignmentOne.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pcd.mainApplicationAssignmentOne.model.ballUpdater.BallUpdater;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.MonitorUpdateBalls;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.MonitorUpdateBallsSimple;
import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.model.board.BoardConf;
import pcd.mainApplicationAssignmentOne.model.board.LargeBoardConf;
import pcd.mainApplicationAssignmentOne.model.board.MassiveBoardConf;
import pcd.mainApplicationAssignmentOne.util.V2d;
import pcd.mainApplicationAssignmentOne.view.View;
import pcd.mainApplicationAssignmentOne.view.ViewModel;

public class MainLoop extends Thread{

    private BoardConf boardConf = new LargeBoardConf();
    private Board board = new Board();
    private ViewModel viewModel = new ViewModel();
	private View view = new View(viewModel, 1200, 800);
    private MonitorUpdateBalls monitor;

    private List<Thread> createBallUpdaters(final Board board, final MonitorUpdateBalls monitor){
       
        final var numberOfProcessors = Runtime.getRuntime().availableProcessors();
        final var numberOfBallsOnBoard = board.getBalls().size();

        System.out.println("CREATING BALL UPDATERS...\n    -N. processors available: "+numberOfProcessors
                            +"\n    -N. balls on the board: "+numberOfBallsOnBoard);

        final var numberOfBallUpdaters = (numberOfBallsOnBoard < numberOfProcessors ?
                                                                    numberOfBallsOnBoard : 
                                                                    numberOfProcessors+1);
        final var sizeBallListForThread = (numberOfBallsOnBoard < numberOfProcessors ?
                                                                    1 : 
                                                                    (numberOfBallsOnBoard/(numberOfBallUpdaters))+1);
        System.out.println("    -N. threads to create: " + numberOfBallUpdaters);
        System.out.println("    -N. of balls for each thread: " + sizeBallListForThread);
        
        List<Thread> listOfThreads = new ArrayList<>();
        int firstball = 0;
        for(int i = 0; i<numberOfBallUpdaters; i++){

            var lastBall = ((firstball+sizeBallListForThread-1 >= numberOfBallsOnBoard) ? (numberOfBallsOnBoard-1) : (firstball+sizeBallListForThread-1));
            listOfThreads.add(new BallUpdater(i, monitor, firstball, lastBall));
            firstball+=sizeBallListForThread;
        }

        System.out.println("DONE - CREATING BALL UPDATERS");
        return listOfThreads;
    }

    private List<Thread> createOneUpdater(final Board board, final MonitorUpdateBalls monitor){

        List<Thread> list = new ArrayList<>();
        list.add(new BallUpdater(0, monitor, 0, board.getBalls().size()-1));
        return list;
        
    }


    private void launchUpdaters(List<Thread> threads){
        
        if(threads.isEmpty()) throw new IllegalArgumentException("no threads were given: EMPTHY LIST");
        


        for(int i=0; i<threads.size();i++){
            threads.get(i).start();
        }
    }

    public void initializeGame(){
        System.out.println("SETTING UP MAIN THREAD");
        this.setName("MAIN THREAD OF GAME");
        this.board.init(boardConf);
        this.viewModel.update(board, 0);			
		this.view.render();
        this.monitor = new MonitorUpdateBallsSimple(this.board);
    }

    private void waitAbit() {
		try {
			Thread.sleep(3000);
		} catch (Exception ex) {}
	}

    public void run(){
        
        
        waitAbit();
        int nFrames = 0;
		long t0 = System.currentTimeMillis();
		long lastUpdateTime = System.currentTimeMillis();
			
		var pb = board.getPlayerBall();
		var rand = new Random(2);
		var lastKickTime = t0;
        
        try{
            var threadsCreated = createBallUpdaters(board, monitor);
            this.monitor.createTurnsOfUpdaters(threadsCreated.size());
            launchUpdaters(threadsCreated);
        }catch(Exception e){
            System.err.println(e);
            System.exit(1);
        }
		
        System.out.println("BEGIN GAME");
        while(true){

            if (pb.getVel().abs() < 0.05 && System.currentTimeMillis() - lastKickTime > 2000) {
				var angle = rand.nextDouble() * Math.PI * 0.25;
				var v = new V2d(Math.cos(angle), Math.sin(angle)).mul(1.5);
				pb.kick(v);
				lastKickTime = System.currentTimeMillis();
			}
			
			/* update board state */
			
			long elapsed = System.currentTimeMillis() - lastUpdateTime;
			lastUpdateTime = System.currentTimeMillis();	
            this.monitor.updateTime(elapsed);		
			board.updateState(elapsed);
			
			/* render */
			if(this.monitor.isTimeToRender()){
                nFrames++;
                int framePerSec = 0;
                long dt = (System.currentTimeMillis() - t0);
                if (dt > 0) {
                    framePerSec = (int)(nFrames*1000/dt);
                }

                viewModel.update(board, framePerSec);			
                view.render();

                this.monitor.beginUpdatePhase();
            }
			
        }

    }
    
}
