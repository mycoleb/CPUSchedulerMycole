package src;

import java.util.ArrayList;

public class Process {
    String ID;
    int priority;
    ArrayList<Integer> events;

    int eventsIndex;
    int waitTime;
    int turnAroundTime;
    int responseTime;
    int QAddTime;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ArrayList<Integer> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Integer> events) {
        this.events = events;
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

    Process(String ID) {
        events = new ArrayList<Integer>();
        eventsIndex = 0;
        waitTime = 0;
        turnAroundTime = 0;
        responseTime = 0;
        QAddTime = 0;
        priority = 0;

        this.ID = ID;

    }

}

