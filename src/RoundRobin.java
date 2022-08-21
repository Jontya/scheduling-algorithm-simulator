import java.util.ArrayList;

public class RoundRobin extends Algorithm{
    public RoundRobin(int _dispatcherTime, ProcessQueue<Process> _processQueue, int _timeQuantum){
        super(_dispatcherTime, _processQueue);
        timeQuantum = _timeQuantum;
    }

    public void runAlgo(){
        ProcessQueue<Process> readyQueue = new ProcessQueue<>();
        Process currProcess;
        int processQueueSize;
        boolean done = false;

        if(processQueue.getSize() != 0){
            processQueueSize = processQueue.getSize();
            for(int i = 0; i < processQueueSize; i++){
                Process temp = processQueue.pop();
                if(temp.getArriveTime() <= currTime){
                    readyQueue.push(temp);
                }
                else{
                    processQueue.push(temp);
                }
            }
        }

        while(readyQueue.getSize() != 0){
            currTime += getDispTime();
            currProcess = readyQueue.pop();
            if(currProcess.getExecSize() > timeQuantum){
                if(readyQueue.getSize() == 0 && processQueue.getSize() == 0){
                    currTime += currProcess.getExecSize();
                    System.out.println("\nID: " + currProcess.getId() + "\nFIN: " + currTime + "\nTR: " + (currTime - currProcess.getArriveTime()) + "\nTW: " + 0);
                }
                else{
                    currProcess.setExecSize(currProcess.getExecSize() - timeQuantum);
                    currTime += timeQuantum;

                    if(processQueue.getSize() != 0){
                        processQueueSize = processQueue.getSize();
                        for(int i = 0; i < processQueueSize; i++){
                            Process temp = processQueue.pop();
                            if(temp.getArriveTime() <= currTime){
                                readyQueue.push(temp);
                            }
                            else{
                                processQueue.push(temp);
                            }
                        }
                    }

                    readyQueue.push(currProcess);
                }
            } 
            else{
                currTime += currProcess.getExecSize();

                if(processQueue.getSize() != 0){
                    processQueueSize = processQueue.getSize();
                    for(int i = 0; i < processQueueSize; i++){
                        Process temp = processQueue.pop();
                        if(temp.getArriveTime() <= currTime){
                            readyQueue.push(temp);
                        }
                        else{
                            processQueue.push(temp);
                        }
                    }
                }

                System.out.println("\nID: " + currProcess.getId() + "\nFIN: " + currTime + "\nTR: " + (currTime - currProcess.getArriveTime()) + "\nTW: " + 0);
            }
        }
    }

    public String getAlgoStats(){
        return "";
    }
}
