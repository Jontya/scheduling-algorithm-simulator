public class Process {
    private String id;
    private int arriveTime;
    private int execSize;

    private int finishedTime;
    private int turnaroundTime;
    private int waitingTime;


    public Process(String _id, int _arriveTime, int _execSize){
        id = _id;
        arriveTime = _arriveTime;
        execSize = _execSize;
    }

    public void setProcessingStats(int _turnaroundTime, int _waitingTime, int _finishedTime){
        turnaroundTime = _turnaroundTime;
        waitingTime = _waitingTime;
        finishedTime = _finishedTime;
    }

    public String getId(){
        return id;
    }

    public int getArriveTime(){
        return arriveTime;
    }

    public int getExecSize(){
        return execSize;
    }

    public void setExecSize(int _execSize){
        execSize = _execSize;
    }

    public String processInfo(){
        return "ID: " + id + "\nFIN: " + finishedTime + "\nTR: " + turnaroundTime + "\nTW: " + waitingTime + "\n";
    }

}