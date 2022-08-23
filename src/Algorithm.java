import java.util.ArrayList;
import java.io.Console;

public abstract class Algorithm {
    private static int dispatcherTime;

    protected String algoName;
    protected Process currProcess;
    protected int currTime;
    protected int timeQuantum;

    protected ProcessQueue<Process> processQueue;
    protected ProcessQueue<Process> readyQueue;

    protected ArrayList<String> dispatcherEvents;
    protected ArrayList<String[]> processingEvents;

    String fmt = "%1$4s %2$10s %3$10s%n";  
    static Console col = System.console();  
    

    public Algorithm(int _dispatcherTime, ProcessQueue<Process> _processQueue){
        currTime = 0;

        dispatcherTime = _dispatcherTime;
        processQueue = _processQueue;
        currProcess = null;

        readyQueue = new ProcessQueue<>();
        dispatcherEvents = new ArrayList<>();
        processingEvents = new ArrayList<>();
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
        String algorithmStats = algoName + ":\n";
        for(int i = 0; i < dispatcherEvents.size(); i++){
            algorithmStats += dispatcherEvents.get(i);
        }

        // TO DO: Sort processing events queue, output to file and make algos only run once when they have one item left

        algorithmStats += "\nProcess Turnaround Time Waiting Time\n";
        for(int i = 0; i < processingEvents.size(); i++){
            algorithmStats += String.format("%-7s %-15s %-1s", processingEvents.get(i)[0], processingEvents.get(i)[1], processingEvents.get(i)[2]) + "\n";
    
        }
        return algorithmStats;
    }

    public abstract void runAlgo();
}
