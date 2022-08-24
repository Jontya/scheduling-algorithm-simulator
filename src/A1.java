import java.util.*;
import java.io.*;

public class A1{
    private static String filename;
    private static int dispatcherTime;
    private int timeQuantum = 4;

    public static void main(String[] args) throws Exception{
        try{
            filename = args[0];
            A1 schedulingAlgorithmSimulator = new A1();
            schedulingAlgorithmSimulator.run();
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Missing Filename Argument");
        }
    }

    private ProcessQueue<Process> readFile() throws Exception{
        ProcessQueue<Process> processQueue = new ProcessQueue<>();
        File file = new File(filename);
        if(!file.exists()){
            System.out.println("File Not Found");
            return null;
        }

        Scanner scanner = new Scanner(file);

        try{
            if(scanner.next().equals("BEGIN")){
                while(scanner.hasNext()){
                    String next = scanner.next();
                    if(next.equals("DISP:")){
                        dispatcherTime = Integer.parseInt(scanner.next());
                        if(dispatcherTime < 0){
                            dispatcherTime = 0;
                            System.out.println("Non positive Integer set to zero");
                        }
                    }
    
                    if(next.equals("END")){
                        String id = "";
                        int arriveTime = 0;
                        int execSize = 0;
    
                        next = scanner.next();
    
                        if(next.equals("EOF")){
                            return processQueue;
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
        finally{
            scanner.close();
        }
        return null;
    }

    private void run() throws Exception{
        String output = "";
        ArrayList<Algorithm> algorithms = new ArrayList<>();
        algorithms.add(new FirstComeFirstServe(dispatcherTime, readFile()));
        algorithms.add(new RoundRobin(dispatcherTime, readFile(), timeQuantum));
        algorithms.add(new NarrowRoundRobin(dispatcherTime, readFile(), timeQuantum));
        algorithms.add(new FeedbackConstant(dispatcherTime, readFile(), timeQuantum));

        for(int i = 0; i < algorithms.size(); i++){
            algorithms.get(i).runAlgo();
            output += algorithms.get(i).getAlgorithmStats();
        }

        printResults(output);
    }


    private void printResults(String output) throws Exception{
        try{
            FileWriter outputFile = new FileWriter(filename.split("[.]", 0)[0] + "_output.txt");
            outputFile.write(output);
            outputFile.close();
            System.out.println(output);
        }
        catch (IOException e){
            System.out.println("Output Error");
        }
    }
}
