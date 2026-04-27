package pcd.mainApplicationAssignmentOne.view;

import java.util.ArrayList;
import java.util.Optional;

import pcd.mainApplicationAssignmentOne.model.board.Board;
import pcd.mainApplicationAssignmentOne.util.P2d;

record BallViewInfo(P2d pos, double radius, Optional<Integer> player, Optional<Integer> ballCollideWith) {}

record HoleViewInfo(P2d pos, double radius) {}

public class ViewModel {

	private ArrayList<BallViewInfo> aliveBalls;
	private ArrayList<HoleViewInfo> holes;
	private ArrayList<BallViewInfo> deadBallsForDebug;
	private BallViewInfo player;
	private int framePerSec;
	
	public ViewModel() {
		aliveBalls = new ArrayList<BallViewInfo>();
		deadBallsForDebug = new ArrayList<BallViewInfo>();
		holes = new ArrayList<HoleViewInfo>();
		framePerSec = 0;
	}
	
	public synchronized void update(Board board, int framePerSec) {
		aliveBalls.clear();
		holes.clear();
		deadBallsForDebug.clear();
		//board.getBalls().stream().filter(ball ->  ball.isAlive()).toList();

		//for (var b: board.getBalls()) {
		//	balls.add(new BallViewInfo(b.getPos(), b.getRadius()));
		//}
		for (var b: board.getBalls().stream().filter(ball ->  ball.isAlive()).toList()){
			aliveBalls.add(new BallViewInfo(b.getPos(), b.getRadius(),b.getPlayer(),b.getBallCollidedWith()));
		}
		for (var b: board.getBalls().stream().filter(ball ->  !ball.isAlive()).toList()){
			deadBallsForDebug.add(new BallViewInfo(b.getPos(), b.getRadius(),b.getPlayer(),b.getBallCollidedWith()));
		}
		for (var h: board.getHoles()){
			holes.add(new HoleViewInfo(h.getPos(), h.getRadius()));
		}

		this.framePerSec = framePerSec;
		var p = board.getPlayerBall();
		player = new BallViewInfo(p.getPos(), p.getRadius(),p.getPlayer(),p.getBallCollidedWith());
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

	public synchronized BallViewInfo getPlayerBall() {
		return player;
	}
	
}
