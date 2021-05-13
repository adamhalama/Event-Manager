package Shared.Event;

import java.util.ArrayList;

public class EventList {
    private ArrayList<Event> events;

    public EventList(){
        this.events = new ArrayList<>();
    }

    public void add(Event event){
        events.add(event);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    //TODO the selection should be more detailed
    //by specific date, month, year, hour
    //and also the platform and room later

    public ArrayList<Event> getEventByCreateTime(String date){
        ArrayList<Event> eventsC = new ArrayList<>();
        for (int i = 0; i < events.size(); i ++){
            if (events.get(i).getTime_create().contains(date)){
                eventsC.add(events.get(i));
            }
        }
        return eventsC;
    }

    public ArrayList<Event> getEventByStartTime(String date){
        ArrayList<Event> eventsS = new ArrayList<>();
        for (int i = 0; i < events.size(); i++){
            if (events.get(i).getTime_start().contains(date)){
                eventsS.add(events.get(i));
            }
        }
        return eventsS;
    }

    public ArrayList<Event> getEventByTitle(String title){
        ArrayList<Event> eventsT = new ArrayList<>();
        for (int i = 0; i < events.size(); i++){
            if (events.get(i).getTitle().equals(title)){
                eventsT.add(events.get(i));
            }
        }
        return eventsT;
    }

    public Event getEventByIndex(int index){
        return events.get(index);
    }

    public Event getEvent(Event e){
        for (int i = 0; i < events.size(); i++){
            if (events.get(i).equals(e)){
                return e;
            }
        }
        throw new IllegalArgumentException("No event found.");
    }

    public void remove(int index){
        events.remove(index);
    }

    public void removeByEvent(Event e){
        for (int i = 0; i < events.size(); i++){
            if (events.get(i).equals(e)){
                events.remove(e);
            }
        }
    }

    @Override
    public String toString() {
        String eventInfo = "";
        for (int i = 0; i < events.size(); i++){
            eventInfo += events.get(i).toString();
            eventInfo += '\n';
        }
        return eventInfo;
    }
}
