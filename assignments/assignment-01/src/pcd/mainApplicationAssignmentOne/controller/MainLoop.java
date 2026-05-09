package pcd.mainApplicationAssignmentOne.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import pcd.mainApplicationAssignmentOne.controller.interfaces.Cmd;
import pcd.mainApplicationAssignmentOne.model.DumbEnemyAI;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.BallUpdater;
import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.model.interfaces.MonitorBallOfAI;
import pcd.mainApplicationAssignmentOne.model.interfaces.MonitorGameState;
import pcd.mainApplicationAssignmentOne.model.interfaces.MonitorUpdateBalls;
import pcd.mainApplicationAssignmentOne.model.monitors.MonitorBallOfAIImpl;
import pcd.mainApplicationAssignmentOne.model.monitors.MonitorGameStateImpl;
import pcd.mainApplicationAssignmentOne.model.monitors.MonitorUpdateBallsSimple;
import pcd.mainApplicationAssignmentOne.util.buffer.BoundedBuffer;
import pcd.mainApplicationAssignmentOne.util.buffer.BoundedBufferPollImpl;
import pcd.mainApplicationAssignmentOne.view.View;
import pcd.mainApplicationAssignmentOne.view.ViewModel;

public class MainLoop extends Thread{

    private BoundedBuffer<Cmd> bufferInputCommands;
    Random rand = new Random(6969420);
    private final Board board = new Board();
    private MonitorUpdateBalls monitorBalls;
    private MonitorGameState monitorGame;
    private MonitorBallOfAI monitorBallAI;
    private final ViewModel viewModel = new ViewModel();
	private final View view = new View(viewModel, 1200, 800, this);

    private List<Thread> createBallUpdaters(final Board board, final MonitorUpdateBalls monitorBalls, final MonitorGameState monitorGame){
       
        final var numberOfProcessors = Runtime.getRuntime().availableProcessors();
        final var numberOfBallsOnBoard = board.getBalls().size();

        System.out.println("CREATING BALL UPDATERS...\n\n    -N. processors available: "+numberOfProcessors
                            +"\n    -N. balls on the board: "+numberOfBallsOnBoard);

        final var lessBallsThanProcessors = numberOfBallsOnBoard <= numberOfProcessors;

        final var numberOfBallUpdaters = (lessBallsThanProcessors ?
                                            numberOfBallsOnBoard : 
                                            numberOfProcessors);
        final var sizeBallListForThread = (lessBallsThanProcessors ?
                                            1 : 
                                            (numberOfBallsOnBoard/(numberOfBallUpdaters))+1);
        System.out.println("    -N. of updaters to create: " + numberOfBallUpdaters+
        "\n    -N. of balls for each thread: " + sizeBallListForThread+"\n");

        final List<Thread> listOfThreads = new ArrayList<>();
        int firstball = 0;
        for(int i = 0; i<numberOfBallUpdaters; i++){
            var lastBall = ((firstball+sizeBallListForThread-1 >= numberOfBallsOnBoard) ? (numberOfBallsOnBoard-1) : (firstball+sizeBallListForThread-1));
            listOfThreads.add(new BallUpdater(i, monitorBalls, monitorGame, firstball, lastBall));
            firstball+=sizeBallListForThread;
        }

        System.out.println("DONE - CREATING BALL UPDATERS");
        return listOfThreads;
    }

    private void launchUpdaters(List<Thread> threads){
        
        if(threads.isEmpty()) throw new IllegalArgumentException("no threads were given to launch: EMPTHY LIST");
        for(int i=0; i<threads.size();i++){
            threads.get(i).start();
        }
        System.out.println("updaters Lanched");
    }

    public void initializeGame(){
        System.out.println("##-----SETTING UP MAIN THREAD-----##");
        this.setName("MAIN THREAD OF GAME");
        this.bufferInputCommands = new BoundedBufferPollImpl<Cmd>(100);
        
        this.board.init("S");
        this.monitorBalls = new MonitorUpdateBallsSimple(this.board);
        this.monitorGame = new MonitorGameStateImpl();
        this.monitorBallAI = new MonitorBallOfAIImpl(board);
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

    public void run(){

        var debug = false;
        
        var startForcedGameOver = System.currentTimeMillis();
        int nFrames = 0;
		var t0 = System.currentTimeMillis();
		var lastUpdateTime = System.currentTimeMillis();
        try{
            final var threadsCreated = createBallUpdaters(this.board, this.monitorBalls, this.monitorGame);
            this.monitorBalls.createTurnsOfUpdaters(threadsCreated.size());
            launchUpdaters(threadsCreated);

            final DumbEnemyAI ai = new DumbEnemyAI("AI-dumb", debug, monitorBallAI,monitorGame);
            ai.start();
            
        }catch(Exception e){
            e.printStackTrace();
            
            this.monitorGame.stopGame();
            this.monitorBalls.informGameOver();
            System.exit(1);
        }

        this.viewModel.update(board, 0);			
		this.view.render();
		
        System.out.println("\nBEGIN GAME\n");
        while(monitorGame.isGameInProgress()){
            long elapsed = System.currentTimeMillis() - lastUpdateTime;
			lastUpdateTime = System.currentTimeMillis();		
            try {
				Optional<Cmd> cmd = bufferInputCommands.poll();
                if(cmd.isPresent()){
                    //log("new cmd fetched:");
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

            /*the passive balls on 
            board have been updated, 
            now we can
            to check the collisions 
            sequentially */
            if(this.monitorBalls.areAllUpdatersDone()){
                this.monitorBalls.stopParallelUpdsatePhase();
                this.board.updateStateCollisions(); 
                this.board.updateScores();
            }
            
            /*render */
			if(this.monitorBalls.areAllUpdatersDone()){
                nFrames++;
                int framePerSec = 0;
                long dt = (System.currentTimeMillis() - t0);
                if (dt > 0) {
                    framePerSec = (int)(nFrames*1000/dt);
                }
                viewModel.update(board, framePerSec);			
                view.render();
                
                if(areGameOverConditionsTrue()){
                    this.monitorBalls.informGameOver();
                    this.monitorGame.stopGame();
                }else{
                    this.monitorBalls.beginUpdatePhase();
                }
               
            }
            if(debug)
                debugForceGameOver(startForcedGameOver);

        }

        bufferInputCommands.deleteALL();

        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.board.checkWhoWins();
        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);

    }

    private boolean areGameOverConditionsTrue() {
        return this.monitorBalls.areAllBallsDead() || 
            !this.board.getAiBall().isAlive() || 
            !this.board.getHumanBall().isAlive();
    }

    private synchronized void debugForceGameOver(long beginTime) {
        if(System.currentTimeMillis()-beginTime > 15_000){
            System.out.println("kill all");
            this.board.getBalls().stream().forEach(elem->elem.kill());
            this.monitorGame.stopGame();
            this.monitorBalls.informGameOver();
        }
    }    
}
