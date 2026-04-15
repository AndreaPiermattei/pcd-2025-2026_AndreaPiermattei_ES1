package pcd.mainApplicationAssignmentOne.model.board;

public class BoardSimple extends Board{

    @Override
    public void updateState(long dt) {
        playerBall.updateState(dt, this);
    }
}
