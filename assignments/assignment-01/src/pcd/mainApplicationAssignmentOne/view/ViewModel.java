package pcd.mainApplicationAssignmentOne.view;

import java.util.ArrayList;
import java.util.Optional;

import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.util.P2d;

record BallViewInfo(P2d pos, double radius, Optional<Integer> player, Optional<Integer> ballCollideWith) {}
record HoleViewInfo(P2d pos, double radius) {}

public class ViewModel {

	private static final int INDEX_AI_BALL = 1;
	private static final int INDEX_HUMAN_BALL = 0;

	private ArrayList<BallViewInfo> aliveBalls;
	private ArrayList<HoleViewInfo> holes;
	private ArrayList<BallViewInfo> deadBallsForDebug;
	private ArrayList<BallViewInfo> playersBalls;
	private int scoreHuman=0;
	private int scoreAI=0;

	private int framePerSec=0;
	
	public ViewModel() {
		aliveBalls = new ArrayList<>();
		deadBallsForDebug = new ArrayList<>();
		holes = new ArrayList<>();
		playersBalls = new ArrayList<>();
		//framePerSec = 0;
	}
	
	public synchronized void update(Board board, int framePerSec) {
		aliveBalls.clear();
		holes.clear();
		deadBallsForDebug.clear();
		playersBalls.clear();
		scoreAI = board.getScoreAI();
		scoreHuman=board.getScorePlayer();
		for (var b: board.getBalls().stream().filter(ball ->  ball.isAlive()).toList()){
			aliveBalls.add(new BallViewInfo(b.getPos(), b.getRadius(),b.getPlayer(),b.getBallCollidedWith()));
		}
		for (var b: board.getBalls().stream().filter(ball ->  !ball.isAlive()).toList()){
			deadBallsForDebug.add(new BallViewInfo(b.getPos(), b.getRadius(),b.getPlayer(),b.getBallCollidedWith()));
		}
		for (var h: board.getHoles()){
			holes.add(new HoleViewInfo(h.getPos(), h.getRadius()));
		}
		for (var p: board.getPlayersBalls()){
			playersBalls.add(new BallViewInfo(p.getPos(), p.getRadius(),p.getPlayer(),p.getBallCollidedWith()));
		}

		this.framePerSec = framePerSec;
		
	}
	

	public ArrayList<BallViewInfo> getDeadBallsForDebug() {
		var copy = new ArrayList<BallViewInfo>();
		copy.addAll(deadBallsForDebug);
		return copy;
	}

	public synchronized ArrayList<BallViewInfo> getAliveBalls(){
		var copy = new ArrayList<BallViewInfo>();
		copy.addAll(aliveBalls);
		return copy;
		
	}

	public synchronized ArrayList<HoleViewInfo> getHoles() {
		var copy = new ArrayList<HoleViewInfo>();
		copy.addAll(holes);
		return holes;
	}

	public synchronized int getFramePerSec() {
		return framePerSec;
	}

	/*public synchronized BallViewInfo getPlayerBall() {
		return player;
	}*/

	public synchronized BallViewInfo getHumanBall(){
		return playersBalls.get(INDEX_HUMAN_BALL);
	}

	public synchronized BallViewInfo getAiBall(){
		return playersBalls.get(INDEX_AI_BALL);
	}

	public int getScoreHuman() {
		return scoreHuman;
	}

	public int getScoreAI() {
		return scoreAI;
	}
	
	
}
