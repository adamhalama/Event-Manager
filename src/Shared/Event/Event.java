package Shared.Event;

import Shared.Event.Platform.PlatformFactory;
import client.Model.Model;
import org.junit.Ignore;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Event implements Serializable
{
    private int id;
    private int roomID;
    private int creatorID;
    private int messageRoomId;
    private long timeStart;
    private long timeEnd;
    private String title;
    private String description;
    private String platformString;
    private String onlineLink;
    private ArrayList<Integer> participants;

    /**
     * General event constructor
     */
    public Event(int id, int messageRoomId, int roomID, int creatorID, long timeStart, long timeEnd, String title, String description, String platformString, String onlineLink, ArrayList<Integer> participants) {
        this.id = id;
        this.messageRoomId = messageRoomId;
        if(roomID != -1) {
            this.roomID = roomID;
        }
        this.creatorID = creatorID;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.title = title;
        this.description = description;
        this.platformString = platformString;
        this.onlineLink = onlineLink;
        this.participants = participants;
    }

    /**
     * Physical event
     */
    public Event(int id, int messageRoomId, int roomID, int creatorID, long timeStart, long timeEnd, String title, String description) {
        this(id, messageRoomId, roomID, creatorID, timeStart, timeEnd, title, description, null, null, new ArrayList<>());
    }

    /**
     * Online event
     */
    public Event(int id, int messageRoomId, int creatorID, long timeStart, long timeEnd, String title, String description, String platformString, String onlineLink) {
        this(id, messageRoomId, -1, creatorID, timeStart, timeEnd, title, description, platformString, onlineLink, new ArrayList<>());
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        } else throw new IllegalArgumentException("The title cannot be empty!");
    }

    public void setMessageRoomId(int messageRoomId) {
        this.messageRoomId = messageRoomId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRoom(int room) {
        if(room != -1) {
            this.roomID = room;
        }
    }

    public void setPlatform(String platform) {
        this.platformString = platform;
    }

    public void setOnlineLink(String link) {
        this.onlineLink = link;
    }

    public void addParticipant(int id) {
        participants.add(id);
    }

    public void removeParticipant(int id) {
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i) == id) {
                participants.remove(i);
                break;
            }
        }
    }

    public void setParticipants(ArrayList<Integer> employees) {
        this.participants = employees;
    }

    public ArrayList<Integer> getParticipants() {
        return participants;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPlatform() {
        return platformString;
    }

    public String getOnlineLink() {
        return onlineLink;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getID() {
        return id;
    }

    public String participantString() {
        String participant = "";
        for (int i = 0; i < participants.size(); i++) {
            participant += getParticipants().get(i);
            participant += ", ";
        }
        return participant;
    }

    @Override
    public String toString() {
        try {
            return "ID: " + getID() + " Title: " + getTitle() + ", Start: " + getTime_start() + ", End: " + getTime_end()
                    + ", Description: " + getDescription() + ", isOnline: " + isOnline() + ", (if online)Platform: " + getPlatform() +
                    ", (if online)Link: " + getOnlineLink() + ", (if physical)Room: " + getRoomID() + ", " + creatorParticipantString();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "error";
        //TODO i(adam) added this so the app can run;
    }

    public String contentString() {
        return getEvent_id() + " " + getTitle() + " " + getTime_create() + " " + getTime_start() + " " + getTime_end()
                + " " + getPlatform() + " " + getOnlineLink() + " " + getRoomID();
    }

    public String dateString() {
        return getTime_start() + " " + getTime_end();
    }

    public int getMessageRoomID() {
        return mesasageRoomId;
    }
}
