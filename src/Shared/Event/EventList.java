package Shared.Event;

import Shared.Date;

import java.util.ArrayList;

public class EventList
{
    /**
     * An arraylist containing all the events.
     */
    private ArrayList<Event> events;

    /**
     * Zero - argument constructor.
     * Initial the arraylist of events.
     */
    public EventList()
    {
        this.events = new ArrayList<>();
    }

    /**
     * Add the new event.
     * If the event doesn't exist, add it into the list.
     * @param event An event object.
     */
    public void add(Event event)
    {
        for (Event e:
             events)
        {
            if (e.getID() == event.getID())
                return;
        }
        events.add(event);
    }

    /**
     * If the eventList is empty, adds all
     * if not picks the events that are not in the event list and adds them
     * @param newEvents An arraylist of the events will be added.
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

    /**
     * Gets all the events.
     * @return An arraylist containing all the added events.
     */
    public ArrayList<Event> getAll()
    {
        return events;
    }

    /**
     * Gets an event by its ID. Null - no matched events found.
     * @param id An integer storing the event's ID.
     * @return An event object containing the information in the event or null.
     */
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

    /**
     * Gets events by date.
     * @param date A string containing date as requirement.
     * @return An arraylist storing all the matched events.
     */
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

    /**
     * Gets events by a text.
     * @param text A string containing a text as the requirement.
     * @return An arraylist storing all the matched events.
     */
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

    /**
     * Gets all the events by physical room.
     * @param roomID An integer containing the physical room ID.
     * @return An arraylist storing all the matched events.
     */
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

    /**
     * Remove an event by its serial number in the list.
     * @param index An integer storing the serial number in the list.
     */
    public void remove(int index)
    {
        events.remove(index);
    }

    /**
     * Remove an event by its ID.
     * @param e An event object which used to read its ID.
     */
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

    /**
     * Remove an event by its ID.
     * @param id An integer containing the event ID.
     */
    public void removeByID(int id)
    {
        for (Event e : events)
        {
            if (e.getID() == id)
                events.remove(e);
        }
    }

    /**
     * Check the target physical room is available. True - the room is available; False - the room has been occupied.
     * @param roomID An integer storing the physical room ID.
     * @param startTime A long integer storing the UNIX timestamp of start time.
     * @param endTime A long integer storing the UNIX timestamp of end time.
     * @return The result of checking.
     */
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
