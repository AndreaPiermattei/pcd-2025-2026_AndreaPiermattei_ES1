package pcd.mainApplicationAssignmentOne.model.ballUpdater;

public class Collision {
    private final int indexFirstBall;
    private final int indexSecondBall;

    public Collision(int indexFirstBall, int indexSecondBall) {
        this.indexFirstBall = indexFirstBall;
        this.indexSecondBall = indexSecondBall;
    }

    public int getIndexFirstBall() {
        return indexFirstBall;
    }

    public int getIndexSecondBall() {
        return indexSecondBall;
    }

    
}
