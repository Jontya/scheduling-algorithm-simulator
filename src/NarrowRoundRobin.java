public class NarrowRoundRobin extends Algorithm{
    public NarrowRoundRobin(int _dispatcherTime, ProcessQueue<Process> _processQueue, int _timeQuantum){
        super(_dispatcherTime, _processQueue);
        timeQuantum = _timeQuantum;
        algoName = "NRR";
    }

    @Override
    public void runAlgo(){

        for(int i = 0; i < processQueue.getSize(); i++){
            currProcess = processQueue.pop();
            currProcess.setTimeQuantum(timeQuantum, 2);
            processQueue.push(currProcess);
        }

        if(processQueue.getSize() != 0){
            primeReadyQueue();
        }

        while(readyQueue.getSize() != 0){
            loadNextProcess(readyQueue);
    
            if(currProcess.getRemainingTime() > currProcess.getTimeQuantum()){
                if(readyQueue.getSize() == 0 && processQueue.getSize() == 0){
                    currTime += currProcess.getRemainingTime();
                    String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getFirstArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getFirstArriveTime())};
                    processingEvents.addProcessingEvent(processingEvent);
                    return;
                }

                currProcess.setRemainingTime(currProcess.getRemainingTime() - currProcess.getTimeQuantum());
                currTime += currProcess.getTimeQuantum();
                currProcess.setArriveTime(currTime);
                currProcess.decrementTimeQuantum();

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
