package src;
import java.util.LinkedList;
import java.util.Queue;
public class ReadyQueueRR implements ReadyBaseQueue {
    Queue<Event> eventsQ;

    ReadyQueueRR() {
        eventsQ = new LinkedList<Event>();
    }

    public void addProcess(Event event, int currentTime) {
        eventsQ.add(event);
        event.p.QAddTime = currentTime;
    }

    public Event getNextEvent() {
        Event e = eventsQ.poll();
        if (e == null){
            return null;
        }
        e.p.moveIndex();
        return e;

    }

    public Event peakNextEvent() {
        return eventsQ.peek();

    }
    public boolean isEmpty() {
        return eventsQ.isEmpty();
    }
    public void completeCPUBurst(){

    }
}
