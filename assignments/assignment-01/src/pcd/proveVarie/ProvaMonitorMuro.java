package pcd.proveVarie;

import java.util.LinkedList;
import java.util.List;

public class ProvaMonitorMuro {
    final private int totalWorkers;
    private int waitingWorkers;
    private boolean phase = true;

    private List<StupidData> stupidList = new LinkedList<>();
    private List<ProvaTurn> waitingList = new LinkedList<>();

    public ProvaMonitorMuro(int totalWorkers) {
        this.totalWorkers = totalWorkers;
        for(int i=0; i<this.totalWorkers;i++){
            stupidList.add(new StupidData());
            waitingList.add(new ProvaTurn());
        }
    }

    public synchronized void beginSlavePhase(){
        for(var t: waitingList){
            t.setTurn(true);
        }
        this.phase = true;
        this.waitingWorkers = 0;
        notifyAll();
    }

    public synchronized boolean areAllSlavesDone(){
        return this.totalWorkers == this.waitingWorkers;
    }

    public synchronized boolean isItSlaveTime(){
        return phase;
    }

    public synchronized void addToWaitingGroup(int worker){
        waitingList.get(worker).setTurn(false);
        waitingWorkers+=1;
        
    }

    public void stopSlavePhase(){
        this.phase = false;
    }

    public synchronized void waitingForMaster(int worker){
        var flagPrint = true;
        while(!isItSlaveTime() || !this.waitingList.get(worker).isTurn()){
            try {
                if(flagPrint){
                    System.out.println(worker+" begin waiting");
                    flagPrint = false; 
                } 
                wait();
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
        }
        System.out.println(worker+" awaken");
    }

    public void parallelModifyList(Integer newValue, int worker){
        stupidList.get(worker).setPerData(newValue.intValue());
    }

    public void showResultOfSlaves(){
        for(var s: stupidList){
            System.out.print(s.getPerData()+" ");
        }
        System.out.println("\n--");
        for(var w: waitingList){
            System.out.print(w.isTurn()+" ");
        }
        System.out.println("\n--");
        
    }
}
