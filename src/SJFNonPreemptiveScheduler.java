package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class SJFNonPreemptiveScheduler extends Scheduler {
    private int currentTime = 0;
    private boolean printDetailedOutput = true;
    private ArrayList<Process> readyQueue = new ArrayList<>();
    private ArrayList<Process> ioQueue = new ArrayList<>();

    @Override
    public void SimulateScheduler() {
        while (!allProcessesCompleted()) {
            moveProcessesToReadyQueue();
            Process currentProcess = selectNextProcess();
            if (currentProcess != null) {
                executeProcess(currentProcess);
            } else {
                currentTime++;
            }
            moveProcessesFromIoToReadyQueue();
        }
        generateReport();
    }

    private void moveProcessesToReadyQueue() {
        for (Process process : processes) {
            if (process.getQAddTime() <= currentTime && !readyQueue.contains(process) && process.getEventsIndex() < process.getEvents().size() && !ioQueue.contains(process)) {
                readyQueue.add(process);
            }
        }
    }

    private void moveProcessesFromIoToReadyQueue() {
        Iterator<Process> iterator = ioQueue.iterator();
        while (iterator.hasNext()) {
            Process process = iterator.next();
            if (process.getQAddTime() <= currentTime) {
                readyQueue.add(process);
                iterator.remove();
            }
        }
    }

    private Process selectNextProcess() {
        return readyQueue.stream()
                .min(Comparator.comparingInt(Process::getCurrentEvent))
                .orElse(null);
    }

    private void executeProcess(Process process) {
        readyQueue.remove(process);
        if (process.getResponseTime() == 0 && process.getEventsIndex() == 0) {
            process.setResponseTime(currentTime - process.getQAddTime());
        }
        printDetailedOutput(process);
        currentTime += process.getCurrentEvent();
        process.setEventsIndex(process.getEventsIndex() + 1);

        if (process.getEventsIndex() < process.getEvents().size()) {
            ioQueue.add(process);
            process.setQAddTime(currentTime + process.getEvents().get(process.getEventsIndex() - 1));
        } else {
            process.setTurnAroundTime(currentTime - process.getQAddTime());
            process.setWaitTime(process.getTurnAroundTime() - process.getEvents().stream().mapToInt(Integer::intValue).sum());
        }
    }

    private boolean allProcessesCompleted() {
        return processes.stream().allMatch(p -> p.getEventsIndex() >= p.getEvents().size());
    }

    private void printDetailedOutput(Process process) {
        if (!printDetailedOutput) return;
        System.out.println("Current Time: " + currentTime);
        System.out.println("Next process on the CPU: " + process.getID());
        System.out.println("..................................................");
        System.out.println("List of processes in the ready queue:");
        System.out.println("Process\tBurst");
        for (Process p : readyQueue) {
            System.out.println(p.getID() + "\t" + p.getCurrentEvent());
        }
        System.out.println("..................................................");
        System.out.println("List of processes in I/O:");
        System.out.println("Process\tRemaining I/O time");
        for (Process p : ioQueue) {
            System.out.println(p.getID() + "\t" + (p.getQAddTime() - currentTime));
        }
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::");
    }

    @Override
    public void generateReport() {
        System.out.println("Summary of the execution:");
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int totalResponseTime = 0;

        for (Process process : processes) {
            System.out.println("Process " + process.getID() + ":");
            System.out.println("\tWaiting Time: " + process.getWaitTime());
            System.out.println("\tTurnaround Time: " + process.getTurnAroundTime());
            System.out.println("\tResponse Time: " + process.getResponseTime());
            totalWaitingTime += process.getWaitTime();
            totalTurnaroundTime += process.getTurnAroundTime();
            totalResponseTime += process.getResponseTime();
        }

        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / processes.size());
        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / processes.size());
        System.out.println("Average Response Time: " + (double) totalResponseTime / processes.size());
    }
}
