package pcd.provetask;

import java.util.LinkedList;
import java.util.List;

import pcd.proveVarie.StupidData;

public class ProvaMonitorTask {
    final private int totalWorkers;
    private int doneWorkers = 0;
    private List<StupidData> stupidList = new LinkedList<>();

    public ProvaMonitorTask(int totalWorkers) {
        this.totalWorkers = totalWorkers;
        for(int i=0; i<this.totalWorkers;i++){
            stupidList.add(new StupidData());
        }
    }

    public synchronized void resetWorkers(){
        this.doneWorkers = 0;
    }
    
    public synchronized boolean areAllSlavesDone(){ //non posso fare waiting perchè il loop di gioco deve continuare
        return this.totalWorkers == this.doneWorkers;
    }

    public synchronized void addToDoneList(){
        this.doneWorkers+=1;
    }

    public void parallelModifyList(Integer newValue, int worker){ //chiamato dal runnable
        stupidList.get(worker).setPerData(newValue.intValue());
    }

    public void showResultOfSlaves(){
        for(var s: stupidList){
            System.out.print(s.getPerData()+" ");
        }
        
        System.out.println("\n--");
        
    }
    
}
