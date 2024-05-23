package src;

import java.util.ArrayList;
public class MyProcess {
    String ID;
    int priority;
    ArrayList<Integer> events;
    int eventsIndex;
    int waitTime;
    int turnAroundTime;
    int responseTime;
    int QAddTime;

    int arrivalTime;
    boolean flag;

    public String getID() {
        return ID;
    }

    public int getPriority() {
        return priority;
    }

    public int getCurrentEvent() {return events.get(eventsIndex);}

    public void updateCurrentEvent(int update) {
        Integer changedEvent = events.get(0).intValue() + update;
        events.set(eventsIndex, changedEvent);
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ArrayList<Integer> getEvents() {
        return events;
    }
    public int getNextEventDuration() {
        if (eventsIndex < events.size()) {
            return events.get(eventsIndex);
        }
        return -1;
    }
    public void moveIndex() {
        eventsIndex++;
    }
    public int getEventsIndex() {
        return eventsIndex;
    }

    public void setEventsIndex(int eventsIndex) {
        this.eventsIndex = eventsIndex;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getQAddTime() {
        return QAddTime;
    }

    public void setQAddTime(int QAddTime) {
        this.QAddTime = QAddTime;
    }

    MyProcess(String ID, ArrayList<Integer> events) {
        this.events = events;
        eventsIndex = 0;
        waitTime = 0;
        turnAroundTime = 0;
        responseTime = 0;
        QAddTime = 0;
        priority = 0;

        this.ID = ID;
        this.flag = true;

    }

}

