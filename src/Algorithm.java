import java.util.ArrayList;

public abstract class Algorithm {
    private static int dispatcherTime;

    protected String algoName;
    protected Process currProcess;
    protected int currTime;
    protected int timeQuantum;
    protected int processes;

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

        processes = processQueue.getSize();
    }

    public int getDispTime(){
        return dispatcherTime;
    }
    
    public void primeReadyQueue(){
        int processQueueSize;
        Process temp;
        processQueueSize = processQueue.getSize();

        for(int i = 0; i < processQueueSize; i++){
            temp = processQueue.pop(); 
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

    public String getAlgorithmSummary(){
        String algorithmSummary = "";
        ArrayList<String[]> processEvents = processingEvents.getProcessingEvents();
        double sumTurnaroundTime = 0;
        double sumWaitingTime = 0;


        for(int i = 0; i < processEvents.size(); i++){
            sumTurnaroundTime += Integer.parseInt(processEvents.get(i)[1]);
            sumWaitingTime += Integer.parseInt(processEvents.get(i)[2]);
        }

        algorithmSummary += String.format("%-15s %-25.2f %-1.2f", algoName, (sumTurnaroundTime / processEvents.size()), (sumWaitingTime / processEvents.size())) + "\n";
        
        return algorithmSummary; 
    }

    public void loadNextProcess(ProcessQueue<Process> queue){
        if(queue.getSize() != 1){
            Process temp;
            currProcess = queue.pop();
            for(int i = 0; i < queue.getSize(); i++){
                temp = queue.pop();
                if(temp.getArriveTime() <= currProcess.getArriveTime()){
                    if(temp.getArriveTime() == currProcess.getArriveTime()){
                        if(temp.getIdIdentifier() > currProcess.getIdIdentifier()){
                            queue.push(currProcess);
                            currProcess = temp;
                        }
                        else{
                            queue.push(temp);
                        }
                    }
                    else{
                        queue.push(currProcess);
                        currProcess = temp;
                    }
                }
                else{
                    queue.push(temp);
                }
            }
        }
        else{
            currProcess = queue.pop();
        }

        currTime += getDispTime();
        processingEvents.addDispatcherEvent("T" + currTime + ": " + currProcess.getId() + "\n");
    }

    public abstract void runAlgo();

}
