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

    /**
     * If the eventList is empty, adds all
     * if not picks the events that are not in the event list and adds them
     * @param newEvents
     */
    public void addAll(ArrayList<Event> newEvents)
    {
        if (events.size() == 0)
        {
            events.addAll(newEvents);
            return;
        }

        for1:
        for (Event e:
             newEvents)
        {
            for (Event event:
                events)
            {
                if (event.getID() == e.getID())
                    continue for1;
            }
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

    public void removeByID(int id)
    {
        for (Event e : events)
        {
            if (e.getID() == id)
                events.remove(e);
        }
    }

    public boolean isRoomAvailable(int roomID, long startTime, long endTime)
    {
        for (Event e:
             events)
        {
            if(e.getRoomID() == roomID)
            {
                if ( (e.getTimeStart() < startTime && startTime < e.getTimeEnd() )  // if the start time is between start and end
                ||( e.getTimeStart() < endTime && endTime < e.getTimeEnd()))  //or the end is between start end end
                {
                    return false;
                }
            }
        }
        return true;
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
