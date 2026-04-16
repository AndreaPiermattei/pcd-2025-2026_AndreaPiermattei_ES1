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
    public synchronized void updateTime() {
        this.elapsed = System.currentTimeMillis() - lastUpdateTime;
        lastUpdateTime = System.currentTimeMillis();

        nFrames++;
        framePerSec = 0;
        long dt = (System.currentTimeMillis() - t0);
        if (dt > 0) {
            framePerSec = (int)(nFrames*1000/dt);
        }
    }

    @Override
    public long getTimeElapsed() {
        return elapsed;
    }

    @Override
    public int getFramePerSec() {
        return framePerSec;
    }

   
    
}
