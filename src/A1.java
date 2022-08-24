//---------------------------------------------------------------------------------------------------
/** COMP2240 A1
*** Jonty Atkinson (C3391110)
*** 24/08/22
***
*** A1:
*** Main class for the program, contains a method for reading in processes, running the algorithms,
*** and printing the output to both a file and the console.
**/
//---------------------------------------------------------------------------------------------------

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class A1{
    private static String filename;
    private static int dispatcherTime;
    private int timeQuantum = 4;

    public static void main(String[] args) throws Exception{
        try{
            filename = args[0]; // stores file name
            A1 schedulingAlgorithmSimulator = new A1(); // creates a new instance of A1
            schedulingAlgorithmSimulator.run(); // runs the program
        }
        catch(ArrayIndexOutOfBoundsException e){ // Throws exception if the filename is not entered
            System.out.println("Missing Filename Argument");
        }
    }

    private ProcessQueue<Process> readFile() throws Exception{
        ProcessQueue<Process> processQueue = new ProcessQueue<>();
        File file = new File(filename);

        // Checks if the file exists
        if(!file.exists()){
            System.out.println("File Not Found");
            return null;
        }

        Scanner scanner = new Scanner(file);

        try{
            String next = "";
            while(!next.equals("BEGIN")){ // Checks for the file entry point
                next = scanner.next();
            }
            if(next.equals("BEGIN")){ // If it exists
                while(scanner.hasNext()){
                    next = scanner.next(); // Gets the next item in the file
                    if(next.equals("DISP:")){ // Checks for dispatcher time
                        dispatcherTime = Integer.parseInt(scanner.next()); // Sets the dispatcher time
                        if(dispatcherTime < 0){
                            dispatcherTime = 0;
                            System.out.println("Non positive Integer set to zero"); // Sets to 0 if < 0
                        }
                    }
    
                    if(next.equals("END")){ // Looks to read in a new process if one exists
                        String id = "";
                        int arriveTime = 0;
                        int execSize = 0;
    
                        next = scanner.next();
    
                        if(next.equals("EOF")){ // Checks if another process exists or if its the end of the file
                            return processQueue;
                        }
    
                        if(next.equals("ID:")){ // Checks and sets the process ID
                            id = scanner.next();
                        }
                        if(scanner.next().equals("Arrive:")){ // Checks and sets process arrive time 
                            arriveTime = Integer.parseInt(scanner.next());
                        }
                        if(scanner.next().equals("ExecSize:")){ // Checks and sets process execution time / size
                            execSize = Integer.parseInt(scanner.next());
                        }
                        processQueue.push(new Process(id, arriveTime, execSize)); // Adds the new process to the list
                    }
                }
            }
        } 
        finally{
            scanner.close(); // closes scanner
        }

        // returns null if the file format is invalid
        return null;
    }

    private void run() throws Exception{
        String output = "";
        String summary = "";
        ArrayList<Algorithm> algorithms = new ArrayList<>(); // Creates algorithm list and adds each algorithm
        algorithms.add(new FirstComeFirstServe(dispatcherTime, readFile()));
        algorithms.add(new RoundRobin(dispatcherTime, readFile(), timeQuantum));
        algorithms.add(new NarrowRoundRobin(dispatcherTime, readFile(), timeQuantum));
        algorithms.add(new FeedbackConstant(dispatcherTime, readFile(), timeQuantum));

        for(int i = 0; i < algorithms.size(); i++){ // Runs each algorithm and adds its output stats to the output string
            algorithms.get(i).runAlgo();
            output += algorithms.get(i).getAlgorithmStats();
        }

        summary += "\nAlgorithm       Average Turnaround Time   Average Waiting Time\n";
        for(int i = 0; i < algorithms.size(); i++){
            summary += algorithms.get(i).getAlgorithmSummary();
        }


        printResults(output + summary); // Prints the results
    }


    private void printResults(String output) throws Exception{
        try{
            FileWriter outputFile = new FileWriter(filename.split("[.]", 0)[0] + "_output.txt"); // creates a new file
            outputFile.write(output); // Writes to a new file
            outputFile.close();
            System.out.println(output); // Prints to the console
        }
        catch (IOException e){
            System.out.println("Output Error");
        }
    }
}
