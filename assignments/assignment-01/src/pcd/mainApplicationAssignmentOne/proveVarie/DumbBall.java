package pcd.mainApplicationAssignmentOne.proveVarie;

public class DumbBall {

    private double x=-1.0;
    private double y=-1.0;
    private boolean stateDumb = false;

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public boolean getState() {
        return stateDumb;
    }

    public void updateState(){

        this.x = 5;
        this.y = 7;
        this.stateDumb = !this.stateDumb;

     }
    public boolean isStateDumb() {
        return stateDumb;
    }
    
}
