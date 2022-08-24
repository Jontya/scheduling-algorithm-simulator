import java.util.ArrayList;

public abstract class Algorithm {
    private static int dispatcherTime;

    protected String algoName;
    protected Process currProcess;
    protected int currTime;
    protected int timeQuantum;

    protected ProcessQueue<Process> processQueue;
    protected ProcessQueue<Process> readyQueue;

    protected ProcessorEvents processingEvents;

    public Algorithm(int _dispatcherTime, ProcessQueue<Process> _processQueue){
        currTime = 0;

        dispatcherTime = _dispatcherTime;
        processQueue = _processQueue;
        currProcess = null;

        readyQueue = new ProcessQueue<>();
        processingEvents = new ProcessorEvents();
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

    public String getAlgorithmStats(){
        ArrayList<String> dispatcherEvents = processingEvents.getDispatcherEvents();
        ArrayList<String[]> processEvents = processingEvents.getProcessingEvents();

        
        String algorithmStats = "";
        if(!algoName.equals("FCFS")){
            algorithmStats += "\n" + algoName + ":\n";
        }
        else{
            algorithmStats += algoName + ":\n";
        }

        for(int i = 0; i < dispatcherEvents.size(); i++){
            algorithmStats += dispatcherEvents.get(i);
        }
        
        algorithmStats += "\nProcess Turnaround Time Waiting Time\n";
        for(int i = 0; i < processEvents.size(); i++){
            algorithmStats += String.format("%-7s %-15s %-1s", processEvents.get(i)[0], processEvents.get(i)[1], processEvents.get(i)[2]) + "\n";
        }
        return algorithmStats;
    }

    public abstract void runAlgo();
}
