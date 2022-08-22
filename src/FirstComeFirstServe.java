public class FirstComeFirstServe extends Algorithm{
    public FirstComeFirstServe(int _dispatcherTime, ProcessQueue<Process> _processQueue){
        super(_dispatcherTime, _processQueue);
    }

    public void runAlgo(){

        if(processQueue.getSize() != 0){
            primeReadyQueue();
        }

        while(readyQueue.getSize() != 0){
            currProcess = readyQueue.pop();
            currTime += getDispTime();
            currTime += currProcess.getExecSize();
            currProcess.setProcessingStats(currTime - currProcess.getArriveTime(), currTime - currProcess.getServiceTime() - currProcess.getArriveTime(), currTime);
            System.out.println(currProcess.processInfo());
            if(processQueue.getSize() != 0){
                primeReadyQueue();
            }
        }
    }

    public String getAlgoStats(){
        return "";
    }
}
