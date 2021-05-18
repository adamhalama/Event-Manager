package client.Model;

import Shared.Event.Event;
import Shared.Event.EventList;

import java.util.ArrayList;

public class ModelManager implements Model {
    private Event event;
    private EventList eventList;

    public ModelManager() {
        this.event = new Event();
        this.eventList = new EventList();
    }

    @Override
    public void setTitle(String title) {
        event.setTitle(title);
    }

    @Override
    public void setTimeS(int year, int month, int day, int hour, int minute) {
        event.setTimeS(year, month, day, hour, minute);
    }

    @Override
    public void setTimeE(int year, int month, int day, int hour, int minute) {
        event.setTimeE(year, month, day, hour, minute);
    }

    @Override
    public void setDescription(String des) {
        event.setDescription(des);
    }

    @Override
    public void setOnline(boolean isOnline) {
        event.setOnline(isOnline);
    }

    @Override
    public void setRoom(int room) {
        event.setRoom(room);
    }

    @Override
    public void setPlatform(String platform) {
        event.setPlatform(platform);
    }

    @Override
    public void setOnlineLink(String link, String platform) {
        event.setOnlineLink(link, platform);
    }

    @Override
    public String getTitle() {
        return event.getTitle();
    }

    @Override
    public String getDescription() {
        return event.getDescription();
    }

    @Override
    public String getPlatform() {
        return event.getPlatform();
    }

    @Override
    public String getOnlineLink() {
        return event.getOnlineLink();
    }

    @Override
    public String getTime_create() {
        return event.getTime_create();
    }

    @Override
    public String getTime_start() {
        return event.getTime_start();
    }

    @Override
    public String getTime_end() {
        return event.getTime_end();
    }

    @Override
    public int getRoomID() {
        return event.getRoomID();
    }

    @Override
    public boolean isOnline() {
        return event.isOnline();
    }

    @Override
    public void add(Event event) throws IllegalArgumentException {
        eventList.add(event);
    }

    @Override
    public ArrayList<Event> getEvents() {
        return eventList.getEvents();
    }

    @Override
    public ArrayList<Event> getEventByCreateTime(String date) {
        return eventList.getEventByCreateTime(date);
    }

    @Override
    public ArrayList<Event> getEventByStartTime(String date) {
        return eventList.getEventByStartTime(date);
    }

    @Override
    public ArrayList<Event> getEventByTitle(String title) {
        return eventList.getEventByTitle(title);
    }

    @Override
    public Event getEventByIndex(int index) {
        return eventList.getEventByIndex(index);
    }

    @Override
    public Event getEvent(Event e) {
        return eventList.getEvent(e);
    }

    @Override
    public void remove(int index) {
        eventList.remove(index);
    }

    @Override
    public void removeByEvent(Event e) {
        eventList.removeByEvent(e);
    }

    @Override
    public ArrayList<Event> getEventsOnline() {
        return eventList.getEventsOnline();
    }

    @Override
    public ArrayList<Event> getEventsPhysical() {
        return eventList.getEventsPhysical();
    }

    @Override
    public ArrayList<Event> getEventsDiscord() {
        return eventList.getEventsDiscord();
    }

    @Override
    public ArrayList<Event> getEventsZoom() {
        return eventList.getEventsZoom();
    }

    @Override
    public ArrayList<Event> getEventTeams() {
        return eventList.getEventTeams();
    }
}
