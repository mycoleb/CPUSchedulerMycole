package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        SJFNonPreemptiveScheduler sc = new SJFNonPreemptiveScheduler();
        readProcessedData(sc);
        sc.SimulateScheduler();
        sc.generateReport();
    }

    public static void readProcessedData(Scheduler sc) {
        File processedFile = new File("/Users/cartergarcia/Library/CloudStorage/OneDrive-Personal/Documents/School/WWU/2023-2024/CS 447 Operating Systems/CPUScheduler/Data/ProcessData");

        try (Scanner pF = new Scanner(processedFile)) {
            while (pF.hasNextLine()) {
                String line = pF.nextLine().trim();
                if (!line.isEmpty()) {
                    System.out.println("Reading process data: " + line);
                    
                    String processID = line.substring(0, line.indexOf(' ')).trim();
                    String eventsString = line.substring(line.indexOf('{') + 1, line.indexOf('}')).trim();
                    String[] eventsArray = eventsString.split(",");

                    ArrayList<Integer> events = new ArrayList<>();
                    for (String event : eventsArray) {
                        events.add(Integer.parseInt(event.trim()));
                    }

                    Process p = new Process(processID);
                    p.setEvents(events);
                    sc.addProcess(p);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
