package pcd.mainApplicationAssignmentOne.controller.interfaces;

import pcd.mainApplicationAssignmentOne.model.board.Board;

public interface Cmd {
    void execute(Board model);
}
