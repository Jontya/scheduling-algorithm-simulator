import java.util.*;
import java.io.*;

public class A1 {
    private static String filename;
    private static int dispatcherTime;
    private ProcessQueue<Process> processQueue = new ProcessQueue<>();

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
                    dispatcherTime = Integer.parseInt(scanner.next());
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
                    processQueue.push(new Process(id, arriveTime, execSize));
                }
            }
        }
        

    }

    private void run(){
        FirstComeFirstServe FCFS = new FirstComeFirstServe(dispatcherTime, processQueue);
        RoundRobin RR = new RoundRobin(dispatcherTime, processQueue, 4);
        FCFS.runAlgo();
    }

    private void printResults(){
        // Print to console and output file
    }

}
