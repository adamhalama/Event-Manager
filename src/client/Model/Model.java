package client.Model;

import Shared.Event.Event;

import java.util.ArrayList;

public interface Model {
    //only for event now
    //add other methods for other classes later

    //part1 class event
    void setTitle(String title);
    void setTimeS(int year, int month, int day, int hour, int minute);
    void setTimeE(int year, int month, int day, int hour, int minute);
    void setDescription(String des);
    void setOnline(boolean isOnline);
    void setRoom(String room);
    void setPlatform(String platform);
    void setOnlineLink(String link, String platform);
    String getTitle();
    String getDescription();
    String getPlatform();
    String getOnlineLink();
    String getTime_create();
    String getTime_start();
    String getTime_end();
    String getRoom();
    boolean isOnline();
    //part2 class eventList
    void add(Event event);
    ArrayList<Event> getEvents();
    ArrayList<Event> getEventByCreateTime(String date);
    ArrayList<Event> getEventByStartTime(String date);
    ArrayList<Event> getEventByTitle(String title);
    Event getEventByIndex(int index);
    Event getEvent(Event e);
    void remove(int index);
    void removeByEvent(Event e);
    ArrayList<Event> getEventsOnline();
    ArrayList<Event> getEventsPhysical();
    ArrayList<Event> getEventsDiscord();
    ArrayList<Event> getEventsZoom();
    ArrayList<Event> getEventTeams();
}
