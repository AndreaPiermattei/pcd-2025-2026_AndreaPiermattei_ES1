package pcd.mainApplicationAssignmentOne.util.timeMenager;

public class TimeMenagerImpl implements TimeMenager{

    private int nFrames = 0;
	private long t0;
	private long lastUpdateTime;
    private long elapsed;
    private int framePerSec;

     @Override
    public void init() {
        this.t0 = System.currentTimeMillis();
        this.lastUpdateTime = System.currentTimeMillis();
        this.elapsed = System.currentTimeMillis();
    }

    @Override
    public void updateTime() {
        this.elapsed = System.currentTimeMillis() - lastUpdateTime;
        lastUpdateTime = System.currentTimeMillis();

    }

    @Override
    public long getTimeElapsed() {
        return elapsed;
    }

    
}
