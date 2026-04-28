package pcd.mainApplicationAssignmentOne.model.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.util.P2d;
import pcd.mainApplicationAssignmentOne.util.V2d;

public class SmallBoardConf extends LargeBoardConf{

    @Override
	public List<Ball> getSmallBalls() {		
		var ballRadius = 0.01;
        var balls = new ArrayList<Ball>();

    	for (int row = 0; row < 5; row++) {
    		for (int col = 0; col < 5; col++) {
        		var px = -0.25 + col*0.025;
        		var py =  row*0.025;
        		var b = new Ball(new P2d(px, py), ballRadius, 0.25, new V2d(0,0),Optional.empty());
            	balls.add(b);    			
    		}
    	}		
    	return balls;
	}
    
}
