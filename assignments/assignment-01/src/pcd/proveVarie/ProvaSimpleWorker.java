package pcd.proveVarie;

import java.util.Random;

public class ProvaSimpleWorker extends Thread{

    final private int myNumber;
    final ProvaMonitorMuro monitor;
    private Random rnad;

    public ProvaSimpleWorker(int number, ProvaMonitorMuro monitor){
        rnad= new Random(number);
        this.myNumber = number;
        this.setName("slave "+this.myNumber);
        this.monitor=monitor;
    }

    public void run(){
        var stupid = true;
        while (true) {
            System.out.println(this.getName()+" calculating");
            calculationsFake();
            if(stupid){
                this.monitor.parallelModifyList(1, this.myNumber);
                stupid = false;
            }else{
                this.monitor.parallelModifyList(2, this.myNumber);
                stupid = true;
            }
            System.out.println(this.getName()+" done calc");
            this.monitor.addToWaitingGroup(this.myNumber);
            this.monitor.waitingForMaster(this.myNumber);
        }
        

    }

    private void calculationsFake() {
        try {

            sleep(rnad.nextLong(200,3501));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
