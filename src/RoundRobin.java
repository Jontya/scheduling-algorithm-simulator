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
            loadNextProcess(readyQueue);
            if(currProcess.getRemainingTime() > timeQuantum){
                if(readyQueue.getSize() == 0 && processQueue.getSize() == 0){
                    currTime += currProcess.getRemainingTime();
                    String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getFirstArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getFirstArriveTime())};
                    processingEvents.addProcessingEvent(processingEvent);
                    return;
                }
                
                currTime += timeQuantum;
                currProcess.setRemainingTime(currProcess.getRemainingTime() - timeQuantum);
                currProcess.setArriveTime(currTime);
                readyQueue.push(currProcess);

            }
            else{
                currTime += currProcess.getRemainingTime();
                String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getFirstArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getFirstArriveTime())};
                processingEvents.addProcessingEvent(processingEvent);
            }

            if(processQueue.getSize() != 0){
                primeReadyQueue();
            }
        }
    }
}
