package src;

public class Event {
    public MyProcess p;

    public int completionTime;

    public EventName event;

    Event(MyProcess p, int completionTime, EventName event){
        this.p = p;
        this.completionTime = completionTime;
        this.event = event;
    }

}
