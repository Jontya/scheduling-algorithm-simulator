public abstract class Algorithm {
    private static int dispatcherTime;

    protected Process currProcess;
    protected int currTime;
    protected int timeQuantum;
    protected ProcessQueue<Process> processQueue;
    protected ProcessQueue<Process> readyQueue;

    public Algorithm(int _dispatcherTime, ProcessQueue<Process> _processQueue){
        currTime = 0;

        dispatcherTime = _dispatcherTime;
        processQueue = _processQueue;
        currProcess = null;

        readyQueue = new ProcessQueue<>();
    }

    public int getDispTime(){
        return dispatcherTime;
    }

    public void primeReadyQueue(){
        int processQueueSize;
        processQueueSize = processQueue.getSize();
        for(int i = 0; i < processQueueSize; i++){
            Process temp = processQueue.pop();
            if(temp.getArriveTime() <= currTime){
                readyQueue.push(temp);
            }
            else{
                processQueue.push(temp);
            }
        }
    }

    public abstract void runAlgo();

    public abstract String getAlgoStats();
}
