package pcd.provetask;

public class ProvaStupidTask implements Runnable{

    final private int myNumber;
    private boolean stupid;
    private ProvaMonitorTask monitor;

    public ProvaStupidTask(int myNumber, ProvaMonitorTask monitorT, boolean stupid) {
        this.myNumber = myNumber;
        this.monitor = monitorT;
        this.stupid = stupid;
    }

    @Override
    public void run() {
        
        System.out.println(this.myNumber+" calculating");
        if(stupid){
            stupid = false;
            monitor.parallelModifyList(1, myNumber);
        }else{
            stupid = true;
            monitor.parallelModifyList(0, myNumber);

        }
        monitor.addToDoneList();
        System.out.println(this.myNumber+" done calc");
            
    }
    
}
