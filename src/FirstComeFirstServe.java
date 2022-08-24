public class FirstComeFirstServe extends Algorithm{
    // Constructor
    public FirstComeFirstServe(int _dispatcherTime, ProcessQueue<Process> _processQueue){
        super(_dispatcherTime, _processQueue);
        timeQuantum = 0;
        algoName = "FCFS";
    }

    @Override
    public void runAlgo(){
        primeReadyQueue();
        while(readyQueue.getSize() != 0){
            loadNextProcess(readyQueue);
            currTime += currProcess.getExecSize();

            String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getArriveTime())};
            processingEvents.addProcessingEvent(processingEvent);
            if(processQueue.getSize() != 0){
                primeReadyQueue();
            }
        }
    }
}
