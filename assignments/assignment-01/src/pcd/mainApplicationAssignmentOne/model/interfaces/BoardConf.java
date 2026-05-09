package pcd.mainApplicationAssignmentOne.model.interfaces;

import java.util.List;

import pcd.mainApplicationAssignmentOne.model.Ball;
import pcd.mainApplicationAssignmentOne.model.Hole;
import pcd.mainApplicationAssignmentOne.model.board.Boundary;

public interface BoardConf {

	Boundary getBoardBoundary();
	
	Ball getPlayerBall();

	List<Ball> getPlayersBalls();
	
	List<Ball> getSmallBalls();

	List<Hole> getHoles();
}
