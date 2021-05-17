package Shared.Event;

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
        event.setEvent_id(events.size() - 1);
    }

    public ArrayList<Event> getEvents()
    {
        return events;
    }

    public ArrayList<Event> getEventByCreateTime(String date)
    {
        ArrayList<Event> eventsC = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getTime_create().contains(date))
            {
                eventsC.add(events.get(i));
            }
        }
        return eventsC;
    }

    public ArrayList<Event> getEventByStartTime(String date)
    {
        ArrayList<Event> eventsS = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getTime_start().contains(date))
            {
                eventsS.add(events.get(i));
            }
        }
        return eventsS;
    }

    public ArrayList<Event> getEventByTitle(String title)
    {
        ArrayList<Event> eventsT = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getTitle().equals(title))
            {
                eventsT.add(events.get(i));
            }
        }
        return eventsT;
    }

    public Event getEventByIndex(int index)
    {
        return events.get(index);
    }

    public Event getEvent(Event e)
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

    public void remove(int index)
    {
        events.remove(index);
    }

    public void removeByEvent(Event e)
    {
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).equals(e))
            {
                events.remove(e);
            }
        }
    }

    public ArrayList<Event> getEventsOnline()
    {
        ArrayList<Event> eventsOnline = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).isOnline())
            {
                eventsOnline.add(events.get(i));
            }
        }
        return eventsOnline;
    }

    public ArrayList<Event> getEventsPhysical()
    {
        ArrayList<Event> eventsOffline = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (!events.get(i).isOnline())
            {
                eventsOffline.add(events.get(i));
            }
        }
        return eventsOffline;
    }

    public ArrayList<Event> getEventsDiscord()
    {
        ArrayList<Event> eventsDiscord = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getPlatform().equals("Discord"))
            {
                eventsDiscord.add(events.get(i));
            }
        }
        return eventsDiscord;
    }

    public ArrayList<Event> getEventsZoom()
    {
        ArrayList<Event> eventsZoom = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getPlatform().equals("Zoom"))
            {
                eventsZoom.add(events.get(i));
            }
        }
        return eventsZoom;
    }

    public ArrayList<Event> getEventTeams()
    {
        ArrayList<Event> eventsTeams = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getPlatform().equals("Teams"))
            {
                eventsTeams.add(events.get(i));
            }
        }
        return eventsTeams;
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
