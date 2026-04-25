package pcd.mainApplicationAssignmentOne.model;

import pcd.mainApplicationAssignmentOne.util.P2d;

public class Hole {

    private final double radius;
    private final  P2d pos;

    public Hole(double radius, P2d pos) {
        this.radius = radius;
        this.pos = pos;
    }

    public double getRadius() {
        return radius;
    }

    public P2d getPos() {
        return pos;
    }

    public static boolean checkCollision(Ball ball, Hole hole){

        double dx   = ball.getPos().x() - hole.pos.x();
        double dy   = ball.getPos().y() - hole.pos.y();
        double dist = Math.hypot(dx, dy);
        double minD = ball.getRadius() + hole.radius;

        return (dist < minD && dist > 1e-6);

    }
    
}
