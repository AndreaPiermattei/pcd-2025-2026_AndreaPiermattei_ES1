package pcd.provetask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProvaService {

	private int numTasks;
	private ExecutorService executor;

    public ProvaService(int numTasks, int poolSize) {
        this.numTasks = numTasks;
        executor = Executors.newFixedThreadPool(poolSize);
    }

    public void computeParallel(){
        ProvaMonitorTask result = new ProvaMonitorTask(numTasks);
        var phase = false;
        var flag = true;
        var flagPrint = true;
        while(true){
            if(flag != phase){
                flag = phase;
                System.out.println("begin update");
                for (int i = 0; i < numTasks; i++) {
                    try {
                        executor.execute(new ProvaStupidTask(i,result,phase));
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
		        }
            }
            
            if(result.areAllSlavesDone()){
                flagPrint = true;
                phase = !phase;
                result.resetWorkers();
                result.showResultOfSlaves();
                
            }else{
                if(flagPrint){
                    System.out.println("aspettando");
                    flagPrint = false;
                }
            }
            
            
        }
    }
    
}
