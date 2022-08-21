import java.util.ArrayList;

public abstract class Algorithm {
    private static int dispatcherTime;

    protected int currTime;
    protected int timeQuantum;
    protected ProcessQueue<Process> processQueue;

    public Algorithm(int _dispatcherTime, ProcessQueue<Process> _processQueue){
        currTime = 0;

        dispatcherTime = _dispatcherTime;
        processQueue = _processQueue;
    }

    public int getDispTime(){
        return dispatcherTime;
    }

    public abstract void runAlgo();

    public abstract String getAlgoStats();
}
