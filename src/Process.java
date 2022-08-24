public class Process {
    private String id;
    private int arriveTime;
    private int execSize;
    private int remainingTime;
    private int firstArriveTime;
    
    private int finishedTime;
    private int turnaroundTime;
    private int waitingTime;

    private int timeQuantum;
    private int minTimeQuantum;

    public Process(String _id, int _arriveTime, int _execSize){
        id = _id;
        arriveTime = _arriveTime;
        execSize = _execSize;
        remainingTime = _execSize;
        firstArriveTime = _arriveTime;
    }

    public void setProcessingStats(int _turnaroundTime, int _waitingTime, int _finishedTime){
        turnaroundTime = _turnaroundTime;
        waitingTime = _waitingTime;
        finishedTime = _finishedTime;
    }

    public String getId(){
        return id;
    }

    public int getFirstArriveTime(){
        return firstArriveTime;
    }

    public int getIdIdentifier(){
        return Character.getType(id.charAt(id.length() - 1));
    }

    public int getArriveTime(){
        return arriveTime;
    }

    public int getRemainingTime(){
        return remainingTime;
    }

    public int getExecSize(){
        return execSize;
    }

    public int getTimeQuantum(){
        return timeQuantum;
    }

    public void setArriveTime(int _arriveTime){
        arriveTime = _arriveTime;
    }

    public void setTimeQuantum(int _timeQuantum, int _minTimeQuantum){
        timeQuantum = _timeQuantum;
        minTimeQuantum = _minTimeQuantum;
    }
    
    public void setRemainingTime(int _remainingTime){
        remainingTime = _remainingTime;
    }

    public void decrementTimeQuantum(){
        if(timeQuantum <= minTimeQuantum){
            return;
        }
        else{
            timeQuantum--;
        }
    }

    public String processInfo(){
        return "ID: " + id + "\nFIN: " + finishedTime + "\nTR: " + turnaroundTime + "\nTW: " + waitingTime + "\n";
    }

}