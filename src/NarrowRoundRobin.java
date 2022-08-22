public class NarrowRoundRobin extends Algorithm{
    public NarrowRoundRobin(int _dispatcherTime, ProcessQueue<Process> _processQueue, int _timeQuantum){
        super(_dispatcherTime, _processQueue);
        timeQuantum = _timeQuantum;
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
            //System.out.println(currProcess.getId());
            if(currProcess.getExecSize() > currProcess.getTimeQuantum()){
                if(readyQueue.getSize() == 0 && processQueue.getSize() == 0){
                    currTime += currProcess.getExecSize();
                    currProcess.setProcessingStats(currTime - currProcess.getArriveTime(), currTime - currProcess.getServiceTime() - currProcess.getArriveTime(), currTime);
                    System.out.println(currProcess.processInfo());
                }
                else{
                    currProcess.setExecSize(currProcess.getExecSize() - currProcess.getTimeQuantum());
                    currTime += currProcess.getTimeQuantum();
                    currProcess.incrementTimeQuantum();

                    if(processQueue.getSize() != 0){
                        primeReadyQueue();
                    }

                    readyQueue.push(currProcess);
                }
            } 
            else{
                currTime += currProcess.getExecSize();
                currProcess.setProcessingStats(currTime - currProcess.getArriveTime(), currTime - currProcess.getServiceTime() - currProcess.getArriveTime(), currTime);
                System.out.println(currProcess.processInfo());

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
