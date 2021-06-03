package Shared.Event;

import client.View.Helpers.ConvertTime;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable
{
    /**
     * An integer for event id.
     */
    private int id;
    /**
     * An integer for message room if.
     */
    private int messageRoomID;
    /**
     * An integer for physical room id.
     */
    private int roomID;
    /**
     * An integer for creator employee if.
     */
    private int creatorID;
    /**
     * A long integer storing the timestamp of start time.
     */
    private long timeStart;
    /**
     * A long integer storing the timestamp of end time.
     */
    private long timeEnd;
    /**
     * A string storing title of the event.
     */
    private String title;
    /**
     * A string containing the description of the event.
     */
    private String description;
    /**
     * A string containing the online platform of the event.
     */
    private String platform;
    /**
     * A string containing the online platform's link of the event.
     */
    private String onlineLink;
    /**
     * An arraylist storing all the participants' ids.
     */
    private ArrayList<Integer> participants;

    /**
     * General event constructor
     */
    public Event(int id, int messageRoomID, int roomID, int creatorID, long timeStart, long timeEnd, String title, String description, String platform, String onlineLink, ArrayList<Integer> participants)
    {
        if (id != -1)
        {
            this.id = id;
        }
        if (messageRoomID != -1)
        {
            this.messageRoomID = messageRoomID;
        }
        if (roomID != -1)
        {
            this.roomID = roomID;
        }
        if (creatorID != -1)
        {
            this.creatorID = creatorID;
        }
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.title = title;
        this.description = description;
        this.platform = platform;
        this.onlineLink = onlineLink;
        this.participants = participants;
    }

    /**
     * Gets event's ID.
     * @return An integer storing event's ID.
     */
    public int getID()
    {
        return id;
    }

    /**
     * Sets the event's ID.
     * @param id An integer storing event's ID.
     */
    public void setID(int id)
    {
        if (id != -1)
        {
            this.id = id;
        }
    }

    /**
     * Gets the message room ID.
     * @return An integer storing message room ID.
     */
    public int getMessageRoomID()
    {
        return messageRoomID;
    }

    /**
     * Sets the message room ID.
     * @param messageRoomID An integer storing message room ID.
     */
    public void setMessageRoomID(int messageRoomID)
    {
        if (messageRoomID != -1)
        {
            this.messageRoomID = messageRoomID;
        }
    }

    /**
     * Gets the physical room ID.
     * @return An integer storing physical room ID.
     */
    public int getRoomID()
    {
        return roomID;
    }

    /**
     * Sets the physical room ID.
     * @param roomID An integer storing physical room ID.
     */
    public void setRoomID(int roomID)
    {
        if (roomID != -1)
        {
            this.roomID = roomID;
        }
    }

    /**
     * Gets the creator's employeeID.
     * @return An integer storing creator's employeeID.
     */
    public int getCreatorID()
    {
        return creatorID;
    }

    /**
     * Gets the timestamp of the start time.
     * @return A long integer of the timestamp of start time.
     */
    public long getTimeStart()
    {
        return timeStart;
    }

    /**
     * Gets the timestamp of the end time.
     * @return A long integer of the timestamp of end time.
     */
    public long getTimeEnd()
    {
        return timeEnd;
    }

    /**
     * Gets both start time and end time.
     * @return A string storing the converted start time and end time.
     */
    public String getTimeStartEnd()
    {
        return ConvertTime.getFormattedDateTime(timeStart) + " - " + ConvertTime.getFormattedTime(timeEnd);
    }

    /**
     * Gets the event's title.
     * @return A string storing the title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets title of the event.
     * @param title A string storing the title.
     */
    public void setTitle(String title)
    {
        if (title != null)
        {
            this.title = title;
        } else throw new IllegalArgumentException("The title cannot be empty!");
    }

    /**
     * Gets description of the event.
     * @return A string storing the description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets description of the event.
     * @param description A string storing the description.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Gets the online platform.
     * @return A string storing the platform.
     */
    public String getPlatform()
    {
        return platform;
    }

    /**
     * Sets the online platform.
     * @param platform A string storing the platform.
     */
    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    /**
     * Gets the online link of online meeting.
     * @return A string storing the link.
     */
    public String getOnlineLink()
    {
        return onlineLink;
    }

    /**
     * Gets all the participants in the event.
     * @return An arraylist containing all the participants.
     */
    public ArrayList<Integer> getParticipants()
    {
        return participants;
    }

    /**
     * Sets all the participants in the event.
     * @param employees An arraylist containing all the participants..
     */
    public void setParticipants(ArrayList<Integer> employees)
    {
        this.participants = employees;
    }

    @Override
    public String toString()
    {
        return "ID: " + getID() + " Title: " + getTitle() + ", Start: " + ConvertTime.getFormattedDateTime(getTimeStart())
                + ", End: " + ConvertTime.getFormattedDateTime(getTimeEnd())
                + ", Description: " + getDescription() + ", Platform: " + getPlatform()
                + ", Link: " + getOnlineLink() + ", Room: " + getRoomID() + ", Creator: " + getCreatorID();
    }
}
