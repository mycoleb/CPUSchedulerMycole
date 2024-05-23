package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scheduler sc = new Scheduler(new ReadyQueueRR());
        readProcessedData(sc);
        sc.SimulateScheduler();
        sc.generateReport();
    }

    public static void readProcessedData (Scheduler sc) {

        File processedFile = new File("/Users/emiguel/opSys/CPU_Scheduler/Data/ProcessData");

        try {
            Scanner pF = new Scanner(processedFile);
            String sourceFile = "";
            int countProcesses = pF.nextInt();
            pF.nextLine();

            while (pF.hasNextLine() && countProcesses > 0) {
                sourceFile = pF.nextLine();
                //P1 {5,27,3,31,5,43,4,18,6,22,4,26,3,24,4}
                String[] strArray = sourceFile.split("[{]");
                strArray[1] = strArray[1].substring(0,strArray[1].length()-1);

                //System.out.println(strArray[0] + ".." + strArray[1]);
                String[] d = strArray[1].split(",");
                ArrayList<Integer> duration = new ArrayList<>();
                for(int i = 0; i<d.length; i++){
                    duration.add(Integer.parseInt(d[i]));
                }

                MyProcess p = new MyProcess(strArray[0], duration);
                sc.addProcess(p);
                //System.out.println(duration);
                countProcesses--;
            }
            //System.out.println(sourceFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
