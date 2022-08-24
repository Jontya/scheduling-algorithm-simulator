import java.util.ArrayList;

public class ProcessorEvents{
    private ArrayList<String> dispatcherEvents;
    private ArrayList<String[]> processingEvents;

    public ProcessorEvents(){
        dispatcherEvents = new ArrayList<>();
        processingEvents = new ArrayList<>();
    }

    public void addDispatcherEvent(String newEvent){
        dispatcherEvents.add(newEvent);
    }

    public void addProcessingEvent(String[] newEvent){
        processingEvents.add(newEvent);
    }

    public ArrayList<String> getDispatcherEvents(){
        return dispatcherEvents;
    }

    public ArrayList<String[]> getProcessingEvents(){
        sortProcessingEvents();
        return processingEvents;
    }

    private void sortProcessingEvents(){
        ArrayList<String[]> temp = new ArrayList<>();
        int position = 1;
        while(temp.size() != processingEvents.size()){
            for(int i = 0; i < processingEvents.size(); i++){
                if(compareProcesses(processingEvents.get(i)[0], position) == 1){
                    temp.add(processingEvents.get(i));
                }
            }
            position++;
        }
        processingEvents = temp;
    }

    private int compareProcesses(String _p1, int p2){
        int p1 = Character.getNumericValue(_p1.charAt(_p1.length() - 1));
        if(p1 == p2){
            return 1;
        }
        return 0;
    }
    
}
