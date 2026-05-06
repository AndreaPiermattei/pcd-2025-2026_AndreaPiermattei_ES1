package pcd.proveVarie;

import java.util.LinkedList;
import java.util.List;

public class ProvaMaster extends Thread{
    private final static int NUM_SLAVES = 7;
    private ProvaMonitorMuro monitor = new ProvaMonitorMuro(NUM_SLAVES);
    public ProvaMaster() {
        this.setName("Master");
    }
    
    public void run(){
        //lancia thread
        List<Thread> slaves = new LinkedList<>();
        for(int i= 0; i < 7; i++){
            slaves.add(new ProvaSimpleWorker(i, monitor));
        }
        System.out.println("MASTER STARTING ");
        this.monitor.showResultOfSlaves();
        for(var s: slaves){
            s.start();
        }
        try {
            sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        var flagPrint = true;
        while(true){
            if(this.monitor.areAllSlavesDone()){
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flagPrint = true;
                this.monitor.stopSlavePhase();
                System.out.println("BLABLABLABLA");
                this.monitor.showResultOfSlaves();
                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("AWAKEN ALL SLAVES");
                this.monitor.beginSlavePhase();
            }else{
                if(flagPrint){
                    flagPrint = false;
                    System.out.println("master waiting for job done");
                    
                }
            }
        }
    }
}
