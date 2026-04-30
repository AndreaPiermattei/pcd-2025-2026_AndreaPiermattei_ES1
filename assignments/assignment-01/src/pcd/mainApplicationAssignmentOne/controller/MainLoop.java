package pcd.mainApplicationAssignmentOne.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import pcd.mainApplicationAssignmentOne.model.MonitorGameStateImpl;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.BallUpdater;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.MonitorUpdateBalls;
import pcd.mainApplicationAssignmentOne.model.ballUpdater.MonitorUpdateBallsSimple;
import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.util.V2d;
import pcd.mainApplicationAssignmentOne.util.buffer.BoundedBuffer;
import pcd.mainApplicationAssignmentOne.util.buffer.BoundedBufferPollImpl;
import pcd.mainApplicationAssignmentOne.view.View;
import pcd.mainApplicationAssignmentOne.view.ViewModel;

public class MainLoop extends Thread{

    private BoundedBuffer<Cmd> bufferInputCommands;

    Random rand = new Random(6969420);
    private int scorePlayer = 0;
    private int scoreAI = 0;
    private boolean gameInProgress = false;
    private final Board board = new Board();
    private MonitorUpdateBalls monitorBalls;
    private MonitorGameStateImpl monitorGame;
    private final ViewModel viewModel = new ViewModel();
	private final View view = new View(viewModel, 1200, 800, this);

    private List<Thread> createBallUpdaters(final Board board, final MonitorUpdateBalls monitorBalls, final MonitorGameStateImpl monitorGame){
       
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
        this.gameInProgress = true;
        this.board.init("L");
        this.monitorBalls = new MonitorUpdateBallsSimple(this.board);
        this.monitorGame = new MonitorGameStateImpl();
    }

    private void waitAbit() {
		try {
			Thread.sleep(3000);
		} catch (Exception ex) {}
	}

    public void notifyNewCmd(Cmd cmd) {
		try {
			bufferInputCommands.put(cmd);
            //System.out.println("comd found - put in buffer");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


    private double chooseRandomAngle(){
       return rand.nextDouble()*Math.PI*0.25; 
    }

    private V2d calculateVelocityVector(final double angle){
        return new V2d(Math.cos(angle),Math.sin(angle)).mul(1.5);
    }

    public void run(){
        var ai = this.board.getAiBall();
        //waitAbit();
        var startForcedGameOver = System.currentTimeMillis();
        int nFrames = 0;
		var t0 = System.currentTimeMillis();
		var lastUpdateTime = System.currentTimeMillis();
        var lastKickTime = t0;
        try{
            final var threadsCreated = createBallUpdaters(this.board, this.monitorBalls, this.monitorGame);
            this.monitorBalls.createTurnsOfUpdaters(threadsCreated.size());
            launchUpdaters(threadsCreated);
        }catch(Exception e){
            e.printStackTrace();
            this.gameInProgress = false;
            this.monitorGame.stopGame();
            System.exit(1);
        }

        this.viewModel.update(board, 0);			
		this.view.render();
		
        System.out.println("BEGIN GAME");
        while(monitorBalls.isGameInProgress()){
            try {
				Optional<Cmd> cmd = bufferInputCommands.poll();
                if(cmd.isPresent()){
                    //log("new cmd fetched:");
                    cmd.get().execute(board);
                }
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			/* update board state */
			
			long elapsed = System.currentTimeMillis() - lastUpdateTime;
			lastUpdateTime = System.currentTimeMillis();	
            this.monitorBalls.updateTime(elapsed);

            if (ai.getVel().abs() < 0.05 && System.currentTimeMillis() - lastKickTime > 1000) {
				ai.kick(calculateVelocityVector(chooseRandomAngle()));
				lastKickTime = System.currentTimeMillis();
			}
            //this.board.updatePlayerBall(elapsed);	
            this.board.updateStateCollisions(); //??? perchè non funziona se lo metto nell' if ??? adesso funziona ma è molto meno veloce (fps)
            this.board.updateEveryPlayerBall(elapsed);

            //this.board.updateStateCollisions(); //??? perchè non funziona se lo metto nell' if ???
            

			/* render */
			if(this.monitorBalls.isTimeToRender()){
                this.scorePlayer = this.monitorBalls.calculateHumanScore();
                this.scoreAI = this.monitorBalls.calculateAIScore();

                //System.out.println("not HOLD");
                nFrames++;
                int framePerSec = 0;
                long dt = (System.currentTimeMillis() - t0);
                if (dt > 0) {
                    framePerSec = (int)(nFrames*1000/dt);
                }
                viewModel.update(board, framePerSec);			
                view.render();
                this.monitorBalls.beginUpdatePhase();
                
                if(this.monitorBalls.areAllBallsDead() || 
                    !this.board.getAiBall().isAlive() || 
                    !this.board.getHumanBall().isAlive()){
                        this.monitorBalls.stopGame();
                }
            }
            debugForceGameOver(startForcedGameOver);

        }
        
        checkWhoWins();
        //System.exit(0);

    }

    private void checkWhoWins(){

        if(!this.board.getAiBall().isAlive()){
            System.out.println("ai dead - human wins");
        
        }else if(!this.board.getHumanBall().isAlive()){
            System.out.println("human dead - ai wins");
        }else if(this.scoreAI > this.scorePlayer){
           System.out.println("ai wins");
           System.out.println(this.scoreAI);
        }else if(this.scoreAI < this.scorePlayer){
            System.out.println("human wins");
            System.out.println(this.scorePlayer);
        }else{
            System.out.println("TIE");
        }

    }

    private synchronized void debugForceGameOver(long beginTime) {
        if(System.currentTimeMillis()-beginTime > 15_000){
            System.out.println("kill all");
            //this.board.getBalls().stream().forEach(elem->elem.kill());
            this.monitorBalls.stopGame();
        }
    }

    private void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() + "]["+this.getName()+ "]" + msg);
	}
    
}
