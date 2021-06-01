package Shared.Event;

import client.View.Helpers.ConvertTime;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable
{
    private int id;
    private int messageRoomID;
    private int roomID;
    private int creatorID;
    private long timeStart;
    private long timeEnd;
    private String title;
    private String description;
    private String platform;
    private String onlineLink;
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

    public void addParticipant(int id)
    {
        participants.add(id);
    }

    public void removeParticipant(int id)
    {
        for (int i = 0; i < participants.size(); i++)
        {
            if (participants.get(i) == id)
            {
                participants.remove(i);
                break;
            }
        }
    }

    public int getID()
    {
        return id;
    }

    public void setID(int id)
    {
        if (id != -1)
        {
            this.id = id;
        }
    }

    public int getMessageRoomID()
    {
        return messageRoomID;
    }

    public void setMessageRoomID(int messageRoomID)
    {
        if (messageRoomID != -1)
        {
            this.messageRoomID = messageRoomID;
        }
    }

    public int getRoomID()
    {
        return roomID;
    }

    public void setRoomID(int roomID)
    {
        if (roomID != -1)
        {
            this.roomID = roomID;
        }
    }

    public int getCreatorID()
    {
        return creatorID;
    }

    public long getTimeStart()
    {
        return timeStart;
    }

    public void setTimeStart(long timestamp)
    {
        this.timeStart = timestamp;
    }

    public long getTimeEnd()
    {
        return timeEnd;
    }

    public void setTimeEnd(long timestamp)
    {
        this.timeEnd = timestamp;
    }

    public String getTimeStartEnd()
    {
        return ConvertTime.getFormattedDateTime(timeStart) + " - " + ConvertTime.getFormattedTime(timeEnd);
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        if (title != null)
        {
            this.title = title;
        } else throw new IllegalArgumentException("The title cannot be empty!");
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getOnlineLink()
    {
        return onlineLink;
    }

    public void setOnlineLink(String link)
    {
        this.onlineLink = link;
    }

    public ArrayList<Integer> getParticipants()
    {
        return participants;
    }

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
