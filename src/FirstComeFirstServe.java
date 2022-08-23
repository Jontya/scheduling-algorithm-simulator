public class FirstComeFirstServe extends Algorithm{
    public FirstComeFirstServe(int _dispatcherTime, ProcessQueue<Process> _processQueue){
        super(_dispatcherTime, _processQueue);
        timeQuantum = 0;
        algoName = "FCFS";
    }

    public void runAlgo(){

        if(processQueue.getSize() != 0){
            primeReadyQueue();
        }

        while(readyQueue.getSize() != 0){
            currTime += getDispTime();
            currProcess = readyQueue.pop();

            dispatcherEvents.add("T" + currTime + ": " + currProcess.getId() + "\n");

            currTime += currProcess.getExecSize();

            String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getArriveTime())};
            processingEvents.add(processingEvent);

            if(processQueue.getSize() != 0){
                primeReadyQueue();
            }
        }
    }
}
