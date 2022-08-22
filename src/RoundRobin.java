public class RoundRobin extends Algorithm{
    public RoundRobin(int _dispatcherTime, ProcessQueue<Process> _processQueue, int _timeQuantum){
        super(_dispatcherTime, _processQueue);
        timeQuantum = _timeQuantum;
    }

    public void runAlgo(){
        if(processQueue.getSize() != 0){
            primeReadyQueue();
        }

        while(readyQueue.getSize() != 0){
            currTime += getDispTime();
            currProcess = readyQueue.pop();
            if(currProcess.getExecSize() > timeQuantum){
                if(readyQueue.getSize() == 0 && processQueue.getSize() == 0){
                    currTime += currProcess.getExecSize();
                    currProcess.setProcessingStats(currTime - currProcess.getArriveTime(), currTime - currProcess.getServiceTime() - currProcess.getArriveTime(), currTime);
                }
                else{
                    currProcess.setExecSize(currProcess.getExecSize() - timeQuantum);
                    currTime += timeQuantum;

                    if(processQueue.getSize() != 0){
                        primeReadyQueue();
                    }

                    readyQueue.push(currProcess);
                }
            } 
            else{
                currTime += currProcess.getExecSize();
                currProcess.setProcessingStats(currTime - currProcess.getArriveTime(), currTime - currProcess.getServiceTime() - currProcess.getArriveTime(), currTime);

                if(processQueue.getSize() != 0){
                    primeReadyQueue();
                }
            }
        }
    }

    public String getAlgoStats(){
        return "";
    }
}
