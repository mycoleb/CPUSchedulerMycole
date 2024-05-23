package src;

public interface ReadyBaseQueue {
    public void addProcess(Event event, int currentTime);

    public Event getNextEvent();

    public Event peakNextEvent();

    public boolean isEmpty();
}
