package Tests.Event;

import Shared.Event.Event;
import Shared.Event.EventList;
import client.Model.Model;
import client.Model.ModelManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    private EventList list = new EventList();

    @BeforeEach
    void setList() {
//        Event e1 = new Event("A", "None", 2021, 5, 14, 9, 50, 2021, 5, 14, 14, 30,
//                 true, "Discord","link");
//        Event e2 = new Event("B", "None", 2021, 5, 14, 15, 50, 2021, 5, 14, 16, 30,
//                true, "Teams","link");
//        Event e3 = new Event("C", "None", 2021, 5, 14, 10, 50, 2021, 5, 14, 14, 30,
//                true, "Zoom","link");
//        Event e4 = new Event("D", "None", 2021, 5, 14, 12, 50, 2021, 5, 14, 14, 30,
//                true, "Discord","link");
//        Event e5 = new Event("E", "None", 2021, 5, 3, 9, 50, 2021, 5, 3, 14, 30,
//                 false,1);
//        Event e6 = new Event("F", "None", 2021, 5, 14, 9, 50, 2021, 5, 14, 14, 30,
//                false, 2);
//        Event e7 = new Event("G", "None", 2021, 5, 14, 9, 50, 2021, 5, 14, 14, 30,
//                false, 1);
//        list.add(e1);
//        list.add(e2);
//        list.add(e3);
//        list.add(e4);
//        list.add(e5);
//        list.add(e6);
//        list.add(e7);
    }

    @Test
    void printWholeList() {
        System.out.println(list.toString());
    }

    @Test
    void printSpecificItem() {
        assertEquals("A", list.getEventByIndex(0).getTitle());
        assertEquals("C", list.getEventByIndex(2).getTitle());
        assertEquals("G", list.getEventByIndex(6).getTitle());
    }

    @Test
    void setStartAfterEnd() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getEventByIndex(2).setTimeE(16, 30);
        });
        assertEquals("Invalid time set!", e.getMessage());
    }

    @Test
    void setEndDateNotWorkDays() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getEventByIndex(2).setTimeE(16, 30);
        });
        assertEquals("You should set time at work hours!", e.getMessage());
    }

    @Test
    void setEndTimeNotWorkHour() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getEventByIndex(2).setTimeE(17, 30);
        });
        assertEquals("You should set time at work hours!", e.getMessage());
    }

    @Test
    void setEndDateNotSameDay() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getEventByIndex(2).setTimeE(16, 30);
        });
        assertEquals("Invalid time set!", e.getMessage());
    }

    @Test
    void setStartDateNotWorkDays() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getEventByIndex(4).setTimeS(2021, 5, 2, 16, 30);
        });
        assertEquals("Invalid time set!", e.getMessage());
    }

    @Test
    void setStartTimeNotWorkHour() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getEventByIndex(4).setTimeS(2021, 5, 3, 5, 30);
        });
        assertEquals("You should set time at work hours!", e.getMessage());
    }

    @Test
    void setStartDateNotSameDay() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getEventByIndex(4).setTimeS(2021, 4, 30, 9, 30);
        });
        assertEquals("Invalid time set!", e.getMessage());
    }

    @Test
    void setRoomForOnline() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getEventByIndex(0).setRoom(0);
        });
        assertEquals("You cannot choose a physical room if you have online meeting!", e.getMessage());
    }

    @Test
    void setOnlineRoomForPhysical() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getEventByIndex(6).setPlatform("Zoom");
        });
        assertEquals("You cannot choose an online platform if you have physical meeting!", e.getMessage());
    }

    @Test
    void addParticipant() {
        Model model = new ModelManager();
        ArrayList<Integer> list = new ArrayList<>();
        EventList eventList = new EventList();
        list.add(1);
        list.add(2);
        Event e5 = new Event("E", "None", 2021, 5, 3, 9, 50, 14, 30,
                false, 1, model, list);
        eventList.add(e5);
        System.out.println(eventList.getEventByID(1).creatorParticipantString());
    }
}
