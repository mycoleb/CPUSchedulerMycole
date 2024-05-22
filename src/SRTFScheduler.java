package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class SRTFScheduler {
    ArrayList<Process> IOList;
    Process runningProcess;
    PriorityQueue<Process> processQueue;
    int timeQuantum;
    boolean printOutput = true;
    SRTFScheduler(Process[] processes) {
        IOList = new ArrayList<Process>();
        runningProcess = null;
        processQueue = new PriorityQueue<>();
        for (int i = 0; i < processes.length; i++) {
            processQueue.add(processes[i]);
        }
        timeQuantum = 0;
    }

    public void scheduleCPU() {
        while (!processQueue.isEmpty() || !IOList.isEmpty() || runningProcess != null) {
            if (runningProcess == null && !processQueue.isEmpty()) {
                moveProcessOntoCPU();
            }
            if (runningProcess.getCurrentEvent() == 0) {
                processToIO();
                moveProcessOntoCPU();
            }
        }
    }

    public void moveProcessOntoCPU () {
        if (!processQueue.isEmpty()) {
            runningProcess = processQueue.poll();
        }
    }

    public void processToIO() {
        if (runningProcess != null) {
            runningProcess.setEventsIndex(runningProcess.getEventsIndex() + 1);
            IOList.add(runningProcess);
            runningProcess = null;
        }
    }

    public void IO_to_Queue(int index) {
        processQueue.add(IOList.remove(index));
    }

    public void printCPUStatus() {

    }
}
