package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scheduler sc = new Scheduler();
        readProcessedData(sc);
        sc.SimulateScheduler();
        sc.generateReport();
    }

    public static void readProcessedData (Scheduler sc) {
        //TODO read the data from processed data.

        File processedFile = new File("/Users/emiguel/IdeaProjects/CPUScheduler/Data/ProcessData");

        try {
            Scanner pF = new Scanner(processedFile);
            String sourceFile = "";
            int countProcesses = pF.nextInt();

            while (pF.hasNextLine() && countProcesses > 0) {
                sourceFile = pF.nextLine();

                countProcesses--;
            }
            System.out.println(sourceFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
