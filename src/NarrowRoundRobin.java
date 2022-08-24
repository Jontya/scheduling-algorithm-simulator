public class NarrowRoundRobin extends Algorithm{
    public NarrowRoundRobin(int _dispatcherTime, ProcessQueue<Process> _processQueue, int _timeQuantum){
        super(_dispatcherTime, _processQueue);
        timeQuantum = _timeQuantum;
        algoName = "NRR";
    }

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
            currTime += getDispTime();
            currProcess = readyQueue.pop();
            processingEvents.addDispatcherEvent("T" + currTime + ": " + currProcess.getId() + "\n");
    
            if(currProcess.getRemainingTime() > currProcess.getTimeQuantum()){
                if(readyQueue.getSize() == 0 && processQueue.getSize() == 0){
                    currTime += currProcess.getRemainingTime();

                    String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getArriveTime())};
                    processingEvents.addProcessingEvent(processingEvent);
                }
                else{
                    currProcess.setRemainingTime(currProcess.getRemainingTime() - currProcess.getTimeQuantum());
                    currTime += currProcess.getTimeQuantum();
                    currProcess.decrementTimeQuantum();

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

    public String getAlgoStats(){
        return "";
    }
}
