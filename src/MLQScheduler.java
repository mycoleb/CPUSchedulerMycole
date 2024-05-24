import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a process with an ID, burst time, and priority.
 */
class Process {
    int id;
    int burstTime;
    int priority;
    private int responseTime = 0;
    private int turnAroundTime = 0;
    private int waitTime = 0;
    private int eventsIndex = 0;
    private List<Integer> events;
    private int qAddTime = 0;

    /**
     * Constructs a new Process.
     *
     * @param id        the process ID
     * @param burstTime the burst time of the process
     * @param priority  the priority of the process
     */
    Process(int id, int burstTime, int priority) {
        this.id = id;
        this.burstTime = burstTime;
        this.priority = priority;
        this.events = new ArrayList<>();
        this.events.add(burstTime);
    }

    // Getters and Setters for new attributes
    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getEventsIndex() {
        return eventsIndex;
    }

    public void setEventsIndex(int eventsIndex) {
        this.eventsIndex = eventsIndex;
    }

    public List<Integer> getEvents() {
        return events;
    }

    public void setQAddTime(int qAddTime) {
        this.qAddTime = qAddTime;
    }

    public int getQAddTime() {
        return qAddTime;
    }

    public int getID() {
        return id;
    }

    public int getCurrentEvent() {
        return events.get(eventsIndex);
    }
}

/**
 * Multi-Level Queue (MLQ) Scheduler that schedules processes based on their priority and a time quantum.
 */
class MLQScheduler {
    private List<List<Process>> queues;
    private int timeQuantum;
    private int currentTime = 0;
    private boolean printDetailedOutput = true;
    private ArrayList<Process> readyQueue = new ArrayList<>();
    private ArrayList<Process> ioQueue = new ArrayList<>();
    private List<Process> processes;

    /**
     * Constructs a new MLQScheduler.
     *
     * @param numQueues   the number of priority queues
     * @param timeQuantum the time quantum for process execution
     */
    MLQScheduler(int numQueues, int timeQuantum) {
        queues = new ArrayList<>(numQueues);
        for (int i = 0; i < numQueues; i++) {
            queues.add(new ArrayList<>());
        }
        this.timeQuantum = timeQuantum;
        this.processes = new ArrayList<>();
    }

    /**
     * Adds a process to the appropriate priority queue.
     *
     * @param process the process to be added
     */
    void addProcess(Process process) {
        queues.get(process.priority).add(process);
        processes.add(process);
    }

    /**
     * Runs the MLQ scheduler.
     */
    void run() {
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

    /**
     * Moves processes to the ready queue based on their add time.
     */
    private void moveProcessesToReadyQueue() {
        for (List<Process> queue : queues) {
            for (Process process : queue) {
                if (process.getQAddTime() <= currentTime && !readyQueue.contains(process) && process.getEventsIndex() < process.getEvents().size() && !ioQueue.contains(process)) {
                    readyQueue.add(process);
                }
            }
        }
    }

    /**
     * Moves processes from I/O to the ready queue.
     */
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

    /**
     * Selects the next process to execute based on the shortest job first (SJF) strategy.
     *
     * @return the next process to execute
     */
    private Process selectNextProcess() {
        return readyQueue.stream()
                .min(Comparator.comparingInt(Process::getCurrentEvent))
                .orElse(null);
    }

    /**
     * Executes the selected process.
     *
     * @param process the process to execute
     */
    private void executeProcess(Process process) {
        readyQueue.remove(process);
        if (process.getResponseTime() == 0 && process.getEventsIndex() == 0) {
            process.setResponseTime(currentTime - process.getQAddTime());
        }
        printDetailedOutput(process);
        int executionTime = Math.min(process.getCurrentEvent(), timeQuantum);
        currentTime += executionTime;
        process.setEventsIndex(process.getEventsIndex() + 1);

        if (process.getEventsIndex() < process.getEvents().size()) {
            ioQueue.add(process);
            process.setQAddTime(currentTime + process.getEvents().get(process.getEventsIndex() - 1));
        } else {
            process.setTurnAroundTime(currentTime - process.getQAddTime());
            process.setWaitTime(process.getTurnAroundTime() - process.getEvents().stream().mapToInt(Integer::intValue).sum());
        }
    }

    /**
     * Checks if all processes have been completed.
     *
     * @return true if all processes are completed, false otherwise
     */
    private boolean allProcessesCompleted() {
        return processes.stream().allMatch(p -> p.getEventsIndex() >= p.getEvents().size());
    }

    /**
     * Prints detailed output of the current process and queue states.
     *
     * @param process the process currently being executed
     */
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

    /**
     * Generates a report summarizing the execution of all processes.
     */
    void generateReport() {
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
