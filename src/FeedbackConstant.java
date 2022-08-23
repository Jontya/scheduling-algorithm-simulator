import java.util.ArrayList;

public class FeedbackConstant extends Algorithm{
    public FeedbackConstant(int _dispatcherTime, ProcessQueue<Process> _processes, int _timeQuantum){
        super(_dispatcherTime, _processes);
        timeQuantum = _timeQuantum;
        algoName = "FB (constant)";
    }

    public void runAlgo(){
        ArrayList<ProcessQueue<Process>> priorityQueues = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            priorityQueues.add(new ProcessQueue<Process>());
        }

        primeReadyQueue();
        processReadyQueue(priorityQueues);

    
        if(checkPriorityLevels(priorityQueues)){
            for(int i = 0; i < (priorityQueues.size() - 1); i++){
                while(priorityQueues.get(i).getSize() != 0){
                    currTime += getDispTime();
                    currProcess = priorityQueues.get(i).pop();
                    dispatcherEvents.add("T" + currTime + ": " + currProcess.getId() + "\n");

                    if(currProcess.getRemainingTime() > timeQuantum){
                        currTime += timeQuantum;
                        currProcess.setRemainingTime(currProcess.getRemainingTime() - timeQuantum);
                        priorityQueues.get(i+1).push(currProcess);

                        if(processQueue.getSize() != 0){
                            primeReadyQueue();
                            if(readyQueue.getSize() != 0){
                                processReadyQueue(priorityQueues);
                            }
                        }
                    }
                    else{
                        currTime += currProcess.getRemainingTime();
                        

                        if(processQueue.getSize() != 0){
                            primeReadyQueue();
                            if(readyQueue.getSize() != 0){
                                processReadyQueue(priorityQueues);
                            }
                        }

                        String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getArriveTime())};
                        processingEvents.add(processingEvent);
                    }
                }
            }
            if(priorityQueues.get(4).getSize() != 0){
                while(priorityQueues.get(4).getSize() != 0){
                    currTime += getDispTime();
                    currProcess = priorityQueues.get(4).pop();
                    dispatcherEvents.add("T" + currTime + ": " + currProcess.getId() + "\n");

                    
                    currTime += currProcess.getRemainingTime();

                    if(processQueue.getSize() != 0){
                        primeReadyQueue();
                        if(readyQueue.getSize() != 0){
                            processReadyQueue(priorityQueues);
                        }

                        String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getArriveTime())};
                        processingEvents.add(processingEvent);
                    }
                }            
            }
        }
    }

    public boolean checkPriorityLevels(ArrayList<ProcessQueue<Process>> priorityQueues){
        for(int i = 0; i < (priorityQueues.size() - 1); i++){
            if(priorityQueues.get(i).getSize() > 0){
                return true;
            }
        }
        return false;
    }

    public void processReadyQueue(ArrayList<ProcessQueue<Process>> priorityQueues){
        while(readyQueue.getSize() != 0){
            currTime += getDispTime();
            currProcess = readyQueue.pop();
            dispatcherEvents.add("T" + currTime + ": " + currProcess.getId() + "\n");

            if(currProcess.getExecSize() > timeQuantum){
                currTime += timeQuantum;
                currProcess.setRemainingTime(currProcess.getExecSize() - timeQuantum);
                priorityQueues.get(0).push(currProcess);
                if(processQueue.getSize() != 0){
                    primeReadyQueue();
                }
            }
            else{
                currTime += currProcess.getExecSize();

                if(processQueue.getSize() != 0){
                    primeReadyQueue();
                }

                String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getArriveTime())};
                processingEvents.add(processingEvent);
            }
        }
    }
}
