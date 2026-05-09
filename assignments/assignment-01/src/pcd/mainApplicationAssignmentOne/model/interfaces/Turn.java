package pcd.mainApplicationAssignmentOne.model.interfaces;

public interface Turn {

    boolean isTurn();

    void stopTurn();

    void beginTurn();

    void stopPermanent();

    boolean hasStopedPermanently();

}