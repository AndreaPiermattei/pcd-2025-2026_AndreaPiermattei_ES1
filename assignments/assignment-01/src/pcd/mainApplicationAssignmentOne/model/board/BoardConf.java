package pcd.mainApplicationAssignmentOne.model.board;

import java.util.List;

import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.model.Hole;

public interface BoardConf {

	Boundary getBoardBoundary();
	
	Ball getPlayerBall();
	
	List<Ball> getSmallBalls();

	List<Hole> getHoles();
}
