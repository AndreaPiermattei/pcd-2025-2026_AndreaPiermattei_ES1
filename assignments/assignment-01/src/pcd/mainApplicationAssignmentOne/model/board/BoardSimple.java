package pcd.mainApplicationAssignmentOne.model.board;

import pcd.mainApplicationAssignmentOne.model.Ball;

public class BoardSimple extends Board{
    @Override
    public void updateState(long dt) {
        playerBall.updateState(dt, this);
        
        for (int i = 0; i < balls.size() - 1; i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball.resolveCollision(balls.get(i), balls.get(j));
            }
        }
    	for (var b: balls) {
    		Ball.resolveCollision(playerBall, b);
    	} 
    }
}
