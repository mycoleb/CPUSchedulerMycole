package src;
import java.util.ArrayList;

import java.util.ArrayList;

public class Scheduler {
    ArrayList<MyProcess> processes;
    ReadyBaseQueue readyQ;
    IOEventQueue IOQ;
    //private boolean isPreemptive;
    int currentTime;

    final int CPUBURSTTIME = 5;
    Scheduler(ReadyBaseQueue readyQ) {
        processes = new ArrayList<MyProcess>();
        this.readyQ = readyQ;
        this.IOQ = new IOEventQueue();
        currentTime = 0;
    }

    public void addProcess(MyProcess p) {
        this.processes.add(p);
        readyQ.addProcess(new Event(p,p.getNextEventDuration(),EventName.READY), currentTime);
    }

    public void SimulateScheduler() {
        Event e = getNextEvent ();

       while (e != null) {
           if (e.event == EventName.IO) {
               Event e1 = IOQ.getNextEvent();
               if (e1 != null) {
                   MyProcess p = e1.p;
                   currentTime += e1.completionTime;
                   IOQ.advanceTime(e1.completionTime);
                   int nextDuration = e1.p.getNextEventDuration();
                   if (nextDuration < 0) {
                       continue;
                   }


                   readyQ.addProcess(new Event(p, p.getNextEventDuration(), EventName.READY), currentTime);
               }
           } else {
               Event e1 = readyQ.getNextEvent();
               if (e1 != null) {

                   int cpuTime = e1.completionTime;
                   if (cpuTime > CPUBURSTTIME) {
                       cpuTime = CPUBURSTTIME;
                       e1.completionTime -= cpuTime;
                       IOQ.advanceTime(cpuTime);
                       currentTime += cpuTime;
                       readyQ.addProcess(e, currentTime);
                   } else {

                       currentTime += cpuTime;
                       IOQ.advanceTime(cpuTime);
                       int nextDuration = e1.p.getNextEventDuration();
                       if (nextDuration < 0) {
                           continue;
                       }

                       MyProcess p = e1.p;
                       IOQ.addProcess(new Event(p, p.getNextEventDuration(), EventName.IO));
                   }
               }
           }
           System.out.printf ("CurrentTime: %d , ProcessID:  %s , EventName: %s , Burst: %d\n", currentTime, e.p.ID, e.event, e.completionTime);
           e = getNextEvent();

    }

    }

    private Event getNextEvent() {
        if (readyQ.isEmpty() && IOQ.isEmpty()) {
            return null;
        }

        int nextIO = Integer.MAX_VALUE;
        int nextCPU = Integer.MAX_VALUE;
        if(!readyQ.isEmpty()) {
            nextCPU = readyQ.peakNextEvent().completionTime;

        }
        if (!IOQ.isEmpty()){
            nextIO = IOQ.peakNextEvent().completionTime;
        }

        if(nextCPU <= nextIO) {
            return readyQ.peakNextEvent();

        } else {
            return IOQ.peakNextEvent();
        }

    }

    public void generateReport() {
    }
}


