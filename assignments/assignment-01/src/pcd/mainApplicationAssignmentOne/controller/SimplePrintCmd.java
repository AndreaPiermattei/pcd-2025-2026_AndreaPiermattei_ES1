package pcd.mainApplicationAssignmentOne.controller;

import pcd.mainApplicationAssignmentOne.model.board.Board;

public class SimplePrintCmd implements Cmd{

    final String message;

    public SimplePrintCmd(final String messageInput){
        this.message = messageInput;
    }

    @Override
    public void execute(final Board model) {
        System.out.println("print message: "+this.message);
    }
    
}
