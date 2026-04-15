package pcd.mainApplicationAssignmentOne.model.board;

import java.util.List;

import pcd.mainApplicationAssignmentOne.model.Ball;

public interface BoardConf {

	Boundary getBoardBoundary();
	
	Ball getPlayerBall();
	
	List<Ball> getSmallBalls();
}
