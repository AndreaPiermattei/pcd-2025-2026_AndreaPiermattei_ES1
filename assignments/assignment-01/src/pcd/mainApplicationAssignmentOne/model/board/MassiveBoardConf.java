package pcd.mainApplicationAssignmentOne.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.model.Hole;
import pcd.mainApplicationAssignmentOne.util.P2d;
import pcd.mainApplicationAssignmentOne.util.V2d;

public class MassiveBoardConf implements BoardConf {

	@Override
	public Ball getPlayerBall() {
		Optional<Integer> numberPlayer = Optional.of(1);
		return  new Ball(new P2d(0, -0.75), 0.05, 1.5, new V2d(0,0), numberPlayer); 
	}

	@Override
	public List<Ball> getSmallBalls() {		
		var ballRadius = 0.007;
        var balls = new ArrayList<Ball>();

    	for (int row = 0; row < 30; row++) {
    		for (int col = 0; col < 100; col++) {
        		var px = -1.0 + col*0.0155;
        		var py =  row*0.0155;
        		var b = new Ball(new P2d(px, py), ballRadius, 0.25, new V2d(0,0),Optional.empty());
            	balls.add(b);    			
    		}
    	}		
    	return balls;
	}

	public Boundary getBoardBoundary() {
        return new Boundary(-1.5,-1.0,1.5,1.0);
	}

	@Override
	public List<Hole> getHoles() {
		var holeRadius = 0.1;
        var holes = new ArrayList<Hole>();
		var py =  0.025;
    	for (int row = 0; row < 20; row++) {
        	var px = -0.25 + row*0.025;
        	var hole = new Hole(holeRadius, new P2d(px, py));
			holes.add(hole);
    	}		
    	return holes;
	}

	@Override
	public List<Ball> getPlayersBalls() {
		var numberOfPlayers = 2;
		List<Ball> players = new ArrayList<>();
		for(int i = 0; i < numberOfPlayers; i++){
			players.add(new Ball(new P2d(0.5*i, -0.75), 0.05, 1.5, new V2d(0,0), Optional.of(i+1)));
		}
		return players;
	}
}
