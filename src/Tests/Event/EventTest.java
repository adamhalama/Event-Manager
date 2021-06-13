package Tests.Event;

import Shared.Event.Event;
import Shared.Event.EventList;
import client.Model.Model;
import client.Model.ModelManager;
import client.RmiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    private EventList list = new EventList();

    @Test
    void printWholeList() {
        System.out.println(list.toString());
    }

    @Test
    void printSpecificItem() {
        assertEquals("A", list.getByID(0).getTitle());
        assertEquals("C", list.getByID(2).getTitle());
        assertEquals("G", list.getByID(6).getTitle());
    }

    @Test
    void setStartAfterEnd() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {

        });
        assertEquals("Invalid time set!", e.getMessage());
    }

    @Test
    void setEndDateNotWorkDays() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {

        });
        assertEquals("You should set time at work hours!", e.getMessage());
    }

    @Test
    void setEndTimeNotWorkHour() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {

        });
        assertEquals("You should set time at work hours!", e.getMessage());
    }

    @Test
    void setEndDateNotSameDay() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {

        });
        assertEquals("Invalid time set!", e.getMessage());
    }

    @Test
    void setStartDateNotWorkDays() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {

        });
        assertEquals("Invalid time set!", e.getMessage());
    }

    @Test
    void setStartTimeNotWorkHour() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {

        });
        assertEquals("You should set time at work hours!", e.getMessage());
    }

    @Test
    void setStartDateNotSameDay() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {

        });
        assertEquals("Invalid time set!", e.getMessage());
    }

    @Test
    void setRoomForOnline() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getByID(0).setRoom(0);
        });
        assertEquals("You cannot choose a physical room if you have online meeting!", e.getMessage());
    }

    @Test
    void setOnlineRoomForPhysical() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            list.getByID(6).setPlatform("Zoom");
        });
        assertEquals("You cannot choose an online platform if you have physical meeting!", e.getMessage());
    }

    //remove the test methods for old version

    @Test
    void addParticipant() {

    }

    @Test
    void getTimeStamp(){

    }
}
