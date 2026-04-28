package pcd.mainApplicationAssignmentOne.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.model.Hole;
import pcd.mainApplicationAssignmentOne.util.P2d;
import pcd.mainApplicationAssignmentOne.util.V2d;

public class MinimalBoardConf implements BoardConf {

	@Override
	public Ball getPlayerBall() {
		Optional<Integer> numberPlayer = Optional.of(1);
    	return new Ball(new P2d(0, 0), 0.06, 1, new V2d(0,0.5),numberPlayer); 
	}

	@Override
	public List<Ball> getSmallBalls() {		
        var balls = new ArrayList<Ball>();
    	var b1 = new Ball(new P2d(0, 0.5), 0.05, 0.75, new V2d(0,0),Optional.empty());
    	var b2 = new Ball(new P2d(0.05, 0.55), 0.025, 0.25, new V2d(0,0),Optional.empty());
    	balls.add(b1);
    	balls.add(b2);
    	return balls;
	}

	@Override
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
    	return holes;	}

	@Override
	public List<Ball> getPlayersBalls() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPlayersBalls'");
	}

}
