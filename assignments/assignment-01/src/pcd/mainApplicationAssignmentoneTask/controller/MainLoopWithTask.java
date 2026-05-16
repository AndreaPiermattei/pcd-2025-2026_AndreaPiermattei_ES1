package pcd.mainApplicationAssignmentoneTask.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pcd.mainApplicationAssignmentOne.controller.interfaces.Cmd;
import pcd.mainApplicationAssignmentOne.model.DumbEnemyAI;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.BallUpdater;
import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.model.interfaces.MonitorBallOfAI;
import pcd.mainApplicationAssignmentOne.model.interfaces.MonitorGameState;
import pcd.mainApplicationAssignmentOne.model.monitors.MonitorBallOfAIImpl;
import pcd.mainApplicationAssignmentOne.model.monitors.MonitorGameStateImpl;
import pcd.mainApplicationAssignmentOne.util.buffer.BoundedBuffer;
import pcd.mainApplicationAssignmentOne.util.buffer.BoundedBufferPollImpl;

import pcd.mainApplicationAssignmentoneTask.tasks.MonitorUpdateBallsTask;
import pcd.mainApplicationAssignmentoneTask.tasks.MonitorUpdateBallsTaskImpl;
import pcd.mainApplicationAssignmentoneTask.tasks.UpdateBallTask;
import pcd.mainApplicationAssignmentoneTask.view.View;
import pcd.mainApplicationAssignmentoneTask.view.ViewModel;

public class MainLoopWithTask{
    private int numTasks;
	private ExecutorService executor;

    private BoundedBuffer<Cmd> bufferInputCommands;
    //Random rand = new Random(6969420);
    private final Board board = new Board();
    private MonitorUpdateBallsTask monitorBalls;
    private MonitorGameState monitorGame;
    private MonitorBallOfAI monitorBallAI;
    private final ViewModel viewModel = new ViewModel();
	private final View view = new View(viewModel, 1200, 800, this);

    public void initializeGame(){
        System.out.println("##-----SETTING UP MAIN THREAD-----##");
        this.bufferInputCommands = new BoundedBufferPollImpl<Cmd>(100);
        this.board.init("L");
        this.monitorBalls = new MonitorUpdateBallsTaskImpl(board, numTasks);
        this.monitorGame = new MonitorGameStateImpl();
        this.monitorBallAI = new MonitorBallOfAIImpl(board);
        numTasks = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(numTasks);
    }

    public void notifyNewCmd(Cmd cmd) {
		try {
            if(monitorGame.isGameInProgress()){
                bufferInputCommands.put(cmd);
            }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

    private void executeUpdaters(final long elapsed){

        final var numberOfBallsOnBoard = board.getBalls().stream().filter(b->b.isAlive()).toList().size();

        final var lessBallsThanProcessors = numberOfBallsOnBoard <= numTasks;

        if(lessBallsThanProcessors){
            monitorBalls.updateNumberOfThreads(numberOfBallsOnBoard);
        }

        final var sizeBallListForThread = (lessBallsThanProcessors ?
                                            1 : 
                                            (numberOfBallsOnBoard/(numTasks))+1);

        int firstball = 0;
        for(int i = 0; i< numTasks && i<numberOfBallsOnBoard; i++){
            var lastBall = ((firstball+sizeBallListForThread-1 >= numberOfBallsOnBoard) ? (numberOfBallsOnBoard-1) : (firstball+sizeBallListForThread-1));
            executor.execute(new UpdateBallTask(monitorBalls, firstball, lastBall, elapsed));
            firstball+=sizeBallListForThread;
        }

    }

    private void executeUpdatersV2(final long lastUpdateTime){

        final var numberOfBallsOnBoard = board.getBalls().stream().filter(b->b.isAlive()).toList().size(); 
        //makes sure to destribute dinamically balls to tasks as musch as possible

        int numberOfTasks=0;
        Double ballsPerThread = ((numberOfBallsOnBoard*1.0)/(numTasks*1.0));
        
        if(ballsPerThread.compareTo(1.0) <= 0){
            numberOfTasks = numberOfBallsOnBoard;
            for(int i = 0; i < numberOfTasks; i++){
                executor.execute(new UpdateBallTask(monitorBalls, i, i, lastUpdateTime));
            }
        }else{
            var integerBallsPerThread = ballsPerThread.intValue()+1;
            var firstBall = 0;
            var lastBall = 0;
            for(int i = 0; i <numTasks; i++){
                if(firstBall < numberOfBallsOnBoard){
                    lastBall = firstBall+integerBallsPerThread-1;
                    if(lastBall >= numberOfBallsOnBoard){
                        lastBall = numberOfBallsOnBoard-1;
                    }
                    executor.execute(new UpdateBallTask(monitorBalls, firstBall, lastBall, lastUpdateTime));
                    firstBall = firstBall+integerBallsPerThread;
                }
            }
        }

    }

    public void run(){

        var debug = false;
        var phaseUpdate = true;
        int nFrames = 0;
		var t0 = System.currentTimeMillis();
		var lastUpdateTime = System.currentTimeMillis();
        
        final DumbEnemyAI ai = new DumbEnemyAI("AI-dumb", debug, monitorBallAI,monitorGame);
        ai.start();
        this.viewModel.update(board, 0);			
		this.view.render();
		
        System.out.println("\nBEGIN GAME\n");
        while(monitorGame.isGameInProgress()){
            
            long elapsed = System.currentTimeMillis() - lastUpdateTime;
            if(phaseUpdate){
                phaseUpdate = false;
                executeUpdatersV2(lastUpdateTime);
            }
                
            try {
				Optional<Cmd> cmd = bufferInputCommands.poll();
                if(cmd.isPresent()){
                    //System.out.println("comm rec");
                    cmd.get().execute(board);
                }
			} catch (Exception ex) {
				ex.printStackTrace();
			}
        
			/* update players state */

            //the use of a simple monitor (monitorBallAI)
            // ensures mutual exclusion on AIball 
            //(MainLoop and DumbEnemyAI are threads 
            // that could  modify the ball at the same time)
            this.monitorBallAI.update(elapsed); 

            //player ball doesn't need any monitor since
            //  only mainthreads modifies the ball
            this.board.updateHumanBall(elapsed);
            lastUpdateTime = System.currentTimeMillis();
            /*the passive balls on 
            board have been updated, 
            now we can
            to check the collisions 
            sequentially */
            if(this.monitorBalls.areAllUpdatersDone()){
                this.board.updateStateCollisions(); 
                this.board.updateScores();
            }
            
            /*render */
			if(this.monitorBalls.areAllUpdatersDone()){
                //System.out.println("Draw");
                nFrames++;
                int framePerSec = 0;
                long dt = (System.currentTimeMillis() - t0);
                if (dt > 0) {
                    framePerSec = (int)(nFrames*1000/dt);
                }
                viewModel.update(board, framePerSec);			
                view.render();
                
                if(areGameOverConditionsTrue()){
                    this.monitorGame.stopGame();
                }
                this.monitorBalls.beginUpdatePhase();
                
                phaseUpdate = true;
            }
        }

        bufferInputCommands.deleteALL();

        System.exit(0);

    }

    private boolean areGameOverConditionsTrue() {
        return this.monitorBalls.areAllBallsDead() || 
            !this.board.getAiBall().isAlive() || 
            !this.board.getHumanBall().isAlive();
    }



}
