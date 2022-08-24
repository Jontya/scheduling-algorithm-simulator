public class RoundRobin extends Algorithm{
    public RoundRobin(int _dispatcherTime, ProcessQueue<Process> _processQueue, int _timeQuantum){
        super(_dispatcherTime, _processQueue);
        timeQuantum = _timeQuantum;
        algoName = "RR";
    }

    @Override
    public void runAlgo(){
        if(processQueue.getSize() != 0){
            primeReadyQueue();
        }

        while(readyQueue.getSize() != 0){
            currTime += getDispTime();
            currProcess = readyQueue.pop();
            processingEvents.addDispatcherEvent("T" + currTime + ": " + currProcess.getId() + "\n");

            if(currProcess.getRemainingTime() > timeQuantum){
                if(readyQueue.getSize() == 0 && processQueue.getSize() == 0){
                    currTime += currProcess.getRemainingTime();
                    String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getArriveTime())};
                    processingEvents.addProcessingEvent(processingEvent);
                }
                else{
                    currProcess.setRemainingTime(currProcess.getRemainingTime() - timeQuantum);
                    currTime += timeQuantum;

                    if(processQueue.getSize() != 0){
                        primeReadyQueue();
                    }

                    readyQueue.push(currProcess);
                }
            } 
            else{
                currTime += currProcess.getRemainingTime();
                
                if(processQueue.getSize() != 0){
                    primeReadyQueue();
                }

                String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getArriveTime())};
                processingEvents.addProcessingEvent(processingEvent);
            }
        }
    }
}
