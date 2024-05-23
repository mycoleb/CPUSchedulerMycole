package src;

public interface IOEventBaseQueue {
    public void addProcess(Event event);

    public Event getNextEvent();

    public Event peakNextEvent();
    public boolean isEmpty();
}
