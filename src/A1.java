import java.util.*;
import java.io.*;

public class A1 {
    private static String filename;
    private static int dispatcher;
    private ArrayList<Process> processes = new ArrayList<>();
    private FirstComeFirstServe firstComeFirstServe = new FirstComeFirstServe();
    private RoundRobin roundRobin = new RoundRobin();
    private NarrowRoundRobin narrowRoundRobin = new NarrowRoundRobin();
    private FeedbackConstant feedbackConstant = new FeedbackConstant();
    
    public static void main(String[] args) throws Exception{
        try{
            filename = args[0];
            A1 schedulingAlgorithmSimulator = new A1();
            schedulingAlgorithmSimulator.readFile();
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Missing Filename Argument");
        }
    }

    private void readFile() throws Exception{
        File file = new File(filename);
        if(!file.exists()){
            System.out.println("File Not Found");
            return;
        }

        Scanner scanner = new Scanner(file);

        if(scanner.next().equals("BEGIN")){
            while(scanner.hasNext()){
                String next = scanner.next();
                if(next.equals("DISP:")){
                    dispatcher = Integer.parseInt(scanner.next());
                }

                if(next.equals("END")){
                    String id = "";
                    int arriveTime = 0;
                    int execSize = 0;

                    next = scanner.next();

                    if(next.equals("EOF")){
                        run();
                        return;
                    }

                    if(next.equals("ID:")){
                        id = scanner.next();
                    }
                    if(scanner.next().equals("Arrive:")){
                        arriveTime = Integer.parseInt(scanner.next());
                    }
                    if(scanner.next().equals("ExecSize:")){
                        execSize = Integer.parseInt(scanner.next());
                    }
                    processes.add(new Process(id, arriveTime, execSize));
                }
            }
        }
        

    }

    private void run(){
        
    }

    private void printResults(){
        // Print to console and output file
    }

}
