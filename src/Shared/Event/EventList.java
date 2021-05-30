package Shared.Event;

import java.util.ArrayList;

public class EventList {
    private ArrayList<Event> events;
    private int id;

    public EventList() {
        this.events = new ArrayList<>();
        this.id = 1;
    }

    public void add(Event event) {
        events.add(event);
        event.setEvent_id(id);
        id++;
    }

    public Event addEvent(Event e) {
        events.add(e);
        e.setEvent_id(id);
        id++;
        return e;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Event> getEventByCreateTime(String date) {
        ArrayList<Event> eventsC = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getTime_create().contains(date)) {
                eventsC.add(events.get(i));
            }
        }
        return eventsC;
    }

    public ArrayList<Event> getEventByStartTime(String date) {
        ArrayList<Event> eventsS = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getTime_start().contains(date)) {
                eventsS.add(events.get(i));
            }
        }
        if (eventsS != null) {
            return eventsS;
        } else throw new NullPointerException("No matched event found.");
    }

    public ArrayList<Event> getEventByTitle(String title) {
        ArrayList<Event> eventsT = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getTitle().equals(title)) {
                eventsT.add(events.get(i));
            }
        }
        return eventsT;
    }

    public ArrayList<Event> getEventByAnything(String s, String date) {
        ArrayList<Event> list = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).contentString().contains(s)
            && events.get(i).dateString().contains(date)) {
                list.add(events.get(i));
            }
        }
        if (list != null) {
            return list;
        } else {
            throw new NullPointerException("No result.");
        }
    }

    public ArrayList<Event> getEventExceptDate(String s){
        ArrayList<Event> list = new ArrayList<>();
        for (int i = 0; i < events.size(); i++){
            if (events.get(i).contentString().contains(s)){
                list.add(events.get(i));
            }
        }

        if (list != null) {
            return list;
        } else {
            throw new NullPointerException("No result.");
        }
    }

    public ArrayList<Event> getEventOnlyDate(String date){
        ArrayList<Event> list = new ArrayList<>();
        for (int i = 0; i < events.size(); i++){
            if (events.get(i).dateString().contains(date)){
                list.add(events.get(i));
            }
        }

        if (list != null) {
            return list;
        } else {
            throw new NullPointerException("No result.");
        }
    }

    public ArrayList<Event> getEventsByRoom(int roomID)
    {
        ArrayList<Event> eventsByRoom = new ArrayList<>();

        for (Event e:
             events)
        {
            if (e.getRoomID() == roomID)
                eventsByRoom.add(e);
        }
        return eventsByRoom;
    }


    public Event getEventByIndex(int index) {
        return events.get(index);
    }

    public Event getEvent(Event e) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).equals(e)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No event found.");
    }

    public void remove(int index) {
        events.remove(index);
    }

    public void removeByEvent(Event e) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getTitle().equals(e.getTitle()) && events.get(i).getTime_create().equals(e.getTime_create()) &&
                    events.get(i).getTime_start().equals(e.getTime_start()) && events.get(i).getTime_end().equals(e.getTime_end()) &&
                    events.get(i).isOnline() == e.isOnline() && events.get(i).getPlatform().equals(e.getPlatform()) &&
                    events.get(i).getRoomID() == e.getRoomID()) {
                events.remove(e);
            }
        }
    }




    public void removeByEventID(int id) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEvent_id() == id) {
                events.remove(events.get(i));
            }
        }
    }

    public void removeAll() {
        events.removeAll(events);
    }

    public ArrayList<Event> getEventsOnline() {
        ArrayList<Event> eventsOnline = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).isOnline()) {
                eventsOnline.add(events.get(i));
            }
        }
        return eventsOnline;
    }

    public Event getEventByID(int id){
        for (int i = 0; i < events.size(); i++){
            if (events.get(i).getEvent_id() == id){
                return events.get(i);
            }
        }
        return null;
    }

    public ArrayList<Event> getEventsPhysical() {
        ArrayList<Event> eventsOffline = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (!events.get(i).isOnline()) {
                eventsOffline.add(events.get(i));
            }
        }
        return eventsOffline;
    }

    public ArrayList<Event> getEventsDiscord() {
        ArrayList<Event> eventsDiscord = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getPlatform().equals("Discord")) {
                eventsDiscord.add(events.get(i));
            }
        }
        return eventsDiscord;
    }

    public ArrayList<Event> getEventsZoom() {
        ArrayList<Event> eventsZoom = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getPlatform().equals("Zoom")) {
                eventsZoom.add(events.get(i));
            }
        }
        return eventsZoom;
    }

    public ArrayList<Event> getEventTeams() {
        ArrayList<Event> eventsTeams = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getPlatform().equals("Teams")) {
                eventsTeams.add(events.get(i));
            }
        }
        return eventsTeams;
    }

    public int getSize() {
        return events.size();
    }

    @Override
    public String toString() {
        String eventInfo = "";
        for (int i = 0; i < events.size(); i++) {
            eventInfo += events.get(i).toString();
            eventInfo += '\n';
        }
        return eventInfo;
    }
}
