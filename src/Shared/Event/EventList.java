package Shared.Event;

import Shared.Date;

import java.util.ArrayList;

public class EventList
{
    private ArrayList<Event> events;

    public EventList()
    {
        this.events = new ArrayList<>();
    }

    public void add(Event event)
    {
        events.add(event);
    }

    public void addAll(ArrayList<Event> newEvents)
    {
        for (Event e:
             newEvents)
        {
            for (Event event:
                events)
                if (event.getID() != e.getID())
                    events.add(e);
        }
    }

    public ArrayList<Event> getAll()
    {
        return events;
    }

    public Event getByID(int id)
    {
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getID() == id)
            {
                return events.get(i);
            }
        }
        return null;
    }

    public ArrayList<Event> getByDate(String date)
    {
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (Date.timestampToDate(events.get(i).getTimeStart()).contains(date) || Date.timestampToDate(events.get(i).getTimeEnd()).contains(date))
            {
                filteredEvents.add(events.get(i));
            }
        }
        return filteredEvents;
    }

    public ArrayList<Event> getByTitle(String text)
    {
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getTitle().contains(text))
            {
                filteredEvents.add(events.get(i));
            }
        }
        return filteredEvents;
    }

    public ArrayList<Event> getByText(String text)
    {
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).toString().contains(text))
            {
                filteredEvents.add(events.get(i));
            }
        }
        return filteredEvents;
    }

    public Event getByEvent(Event e)
    {
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).equals(e))
            {
                return e;
            }
        }
        throw new IllegalArgumentException("No event found.");
    }

    public ArrayList<Event> getEventsByRoom(int roomID)
    {
        ArrayList<Event> eventsByRoom = new ArrayList<>();

        for (Event e : events)
        {
            if (e.getRoomID() == roomID)
                eventsByRoom.add(e);
        }
        return eventsByRoom;
    }

    public void remove(int index)
    {
        events.remove(index);
    }

    public void removeByID(Event e)
    {
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getID() == e.getID())
            {
                events.remove(e);
            }
        }
    }

    public void removeAll()
    {
        events.removeAll(events);
    }

    @Override
    public String toString()
    {
        String eventInfo = "";
        for (int i = 0; i < events.size(); i++)
        {
            eventInfo += events.get(i).toString();
            eventInfo += '\n';
        }
        return eventInfo;
    }
}
