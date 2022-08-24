import java.util.ArrayList;

public class FeedbackConstant extends Algorithm{
    // Constructor
    public FeedbackConstant(int _dispatcherTime, ProcessQueue<Process> _processes, int _timeQuantum){
        super(_dispatcherTime, _processes);
        timeQuantum = _timeQuantum;
        algoName = "FB (constant)";
    }

    @Override
    public void runAlgo(){
        ArrayList<ProcessQueue<Process>> priorityQueues = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            priorityQueues.add(new ProcessQueue<Process>());
        }

        primeReadyQueue();
        while(readyQueue.getSize() != 0){
            processReadyQueue(priorityQueues);
            if(processQueue.getSize() != 0){
                primeReadyQueue();
            }
        }

        
        if(checkPriorityLevels(priorityQueues)){
            for(int i = 0; i < priorityQueues.size() - 1; i++){
                if(priorityQueues.get(i).getSize() != 0){
                    while(priorityQueues.get(i).getSize() != 0){
                        loadNextProcess(priorityQueues.get(i));

                        if(currProcess.getRemainingTime() > timeQuantum){
                            if(!checkPriorityLevels(priorityQueues) && readyQueue.getSize() == 0 && processQueue.getSize() == 0){
                                currTime += currProcess.getRemainingTime();
                                String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getFirstArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getFirstArriveTime())};
                                processingEvents.addProcessingEvent(processingEvent);
                                return;
                            }

                            currTime += timeQuantum;
                            currProcess.setArriveTime(currTime);
                            currProcess.setRemainingTime(currProcess.getRemainingTime() - timeQuantum);
                            priorityQueues.get(i + 1).push(currProcess);
                        }
                        else{
                            currTime += currProcess.getRemainingTime();
                            String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getFirstArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getFirstArriveTime())};
                            processingEvents.addProcessingEvent(processingEvent);
                        }

                        if(processQueue.getSize() != 0){
                            primeReadyQueue();
                            if(readyQueue.getSize() != 0){
                                processReadyQueue(priorityQueues);
                            }
                        }
                    }
                }
            }
            if(priorityQueues.get(4).getSize() != 0){
                while(priorityQueues.get(4) .getSize() != 0){
                    loadNextProcess(priorityQueues.get(4));

                    currTime += currProcess.getRemainingTime();

                    if(processQueue.getSize() != 0){
                        primeReadyQueue();
                        if(readyQueue.getSize() != 0){
                            processReadyQueue(priorityQueues);
                        }
                    }

                    String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getFirstArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getFirstArriveTime())};
                    processingEvents.addProcessingEvent(processingEvent);
                }
            }
        }

    }

    public void processReadyQueue(ArrayList<ProcessQueue<Process>> priorityQueues){
        loadNextProcess(readyQueue);

        if(currProcess.getExecSize() > timeQuantum){
            if(!checkPriorityLevels(priorityQueues) && readyQueue.getSize() == 0 && processQueue.getSize() == 0){
                currTime += currProcess.getExecSize();
                String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getFirstArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getFirstArriveTime())};
                processingEvents.addProcessingEvent(processingEvent);
                return;
            }

            currTime += timeQuantum;
            currProcess.setRemainingTime(currProcess.getExecSize() - timeQuantum);
            currProcess.setArriveTime(currTime);
            priorityQueues.get(0).push(currProcess);
        }
        else{
            currTime += currProcess.getExecSize();
            String[] processingEvent = {currProcess.getId(), Integer.toString(currTime - currProcess.getFirstArriveTime()), Integer.toString(currTime - currProcess.getExecSize() - currProcess.getFirstArriveTime())};
            processingEvents.addProcessingEvent(processingEvent);
        }

        if(processQueue.getSize() != 0){
            primeReadyQueue();
        }
    }

    private boolean checkPriorityLevels(ArrayList<ProcessQueue<Process>> priorityQueues){
        for(int i = 0; i < (priorityQueues.size() - 1); i++){
            if(priorityQueues.get(i).getSize() > 0){
                return true;
            }
        }
        return false;
    }
}
