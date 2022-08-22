public class FirstComeFirstServe extends Algorithm{
    public FirstComeFirstServe(int _dispatcherTime, ProcessQueue<Process> _processQueue){
        super(_dispatcherTime, _processQueue);
    }

    public void runAlgo(){

        if(processQueue.getSize() != 0){
            primeReadyQueue();
        }

        while(readyQueue.getSize() != 0){
            Process process = readyQueue.pop();
            currTime += getDispTime();
            currTime += process.getExecSize();
            System.out.println("\nFIN: " + currTime + "\nTR: " + (currTime - process.getArriveTime()) + "\nTW: " + (currTime - process.getExecSize() - process.getArriveTime()));
            if(processQueue.getSize() != 0){
                primeReadyQueue();
            }
        }
    }

    public String getAlgoStats(){
        return "";
    }
}
