package Shared.Tests;

import Shared.Event.Event;
import Shared.Event.EventList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class EventTest {
    private EventList eventList = new EventList();
    ArrayList<Integer> participants = new ArrayList<>();
    private Event event1 = new Event(1, 1, 1, 1, 1622728883, 1622732483
             , "title1", "null", "Zoom", "url", participants);
    private Event event2 = new Event(2, 2, 2, 2, 1622728883, 1622732483
             , "title2", "null", "Zoom", "url", participants);
    private Event event3 = new Event(3, 1, 0, 1, 1622728883, 1622732483
             , "title3", "null", "Zoom", "url", participants);
    private Event event4 = new Event(4, 1, 1, 3, 1622728883, 1622732483
             , "title4", "null", "Zoom", "url", participants);
    private Event event5 = new Event(5, 3, 3, 1, 1622728883, 1622732483
             , "title5", "null", "Zoom", "url", participants);
    private Event event6 = new Event(6, 1, 2, 5, 1622728883, 1622732483
             , "title6", "null", "Zoom", "url", participants);
    private Event event7 = new Event(7, 3, 1, 4, 1622728883, 1622732483
            , "title7", "null", "Zoom", "url", participants);


    @Test void getTitle(){
        assertEquals("title7", event7.getTitle());
    }

    @Test void getEvent(){
        System.out.println(eventList.getByDate("2021-6-3"));
    }

    @Test void getTime(){
        assertEquals(1622728883, event3.getTimeStart());
    }

    @Test void getEndTime(){
        assertEquals(1622732483, event1.getTimeEnd());
    }

    @Test void addAndGetEvents(){
        eventList.add(event1); eventList.add(event2); eventList.add(event3); eventList.add(event4); eventList.add(event5);
        eventList.add(event6); eventList.add(event7);

        assertEquals("title3", eventList.getByID(3).getTitle());
        assertEquals(1622728883, eventList.getByID(2).getTimeStart());
        assertEquals(1622732483, eventList.getByID(2).getTimeEnd());
    }
}
