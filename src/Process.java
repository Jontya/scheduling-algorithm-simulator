public class Process {
    private String id;
    private int arriveTime;
    private int execSize;

    public Process(String _id, int _arriveTime, int _execSize){
        id = _id;
        arriveTime = _arriveTime;
        execSize = _execSize;
    }

    public String getId(){
        return id;
    }

    public int getArriveTime(){
        return arriveTime;
    }

    public int getExecTime(){
        return execSize;
    }

    public String processInfo(){
        return "ID: " + id + "\nARRIVETIME: " + arriveTime + "\nEXECSIZE: " + execSize + "\n";
    }
}
