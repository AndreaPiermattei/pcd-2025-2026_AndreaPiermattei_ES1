package pcd.mainApplicationAssignmentOne.controller;

import pcd.mainApplicationAssignmentOne.model.board.Board;

public interface Cmd {
    void execute(Board model);
}
