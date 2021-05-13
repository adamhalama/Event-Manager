package Shared.Event;

import Shared.Event.Platform.PlatformFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Event {
    private String time_create; //when creating this event
    private String time_start; //when the event starts
    private String time_end; //when the event ends
    private int dayS = 0;
    private int monthS = 0;
    private int yearS = 0;
    private int dayE = 0;
    private int monthE = 0;
    private int yearE = 0;
//    private int hourS = 0;
//    private int minuteS = 0;
//    private int hourE = 0;
//    private int minuteE = 0;
    private Calendar calendarS;
    private Calendar calendarE;
    private String title; //the title of the event
    private String description;
    private boolean isOnline;
    private String platformString; //if online, choose a platform
    private String onlineLink; //for share the link to fellows
    private PlatformFactory platformFactory;
    private String room; //if physical, choose a room (it could be another type, let's see in the future)

    //TODO add another constructor for physical room
    public Event(String title, String description, int yearS, int monthS, int dayS, int hourS, int minuteS,
                 int yearE, int monthE, int dayE, int hourE, int minuteE, boolean isOnline, String platform, String link) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        this.time_create = sdf.format(date);
        this.title = title;

        // i set a limit for time here, the meeting should only be held from 9 - 17 on workdays
        String timeFormat = "";
        timeFormat += String.valueOf(yearS);
        timeFormat += "-";
        timeFormat += String.valueOf(monthS);
        timeFormat += "-";
        timeFormat += String.valueOf(dayS);
        timeFormat += "  ";
        timeFormat += String.valueOf(hourS);
        timeFormat += ":";
        timeFormat += String.valueOf(minuteS);
        this.calendarS = Calendar.getInstance();
        calendarS.set(yearS, monthS - 1, dayS, hourS, minuteS);
        if ((hourS >= 9 && hourS < 17) &&
                (calendarS.get(Calendar.DAY_OF_WEEK) >= 2 && calendarS.get(Calendar.DAY_OF_WEEK) <= 6)) {
            this.time_start = timeFormat;
            this.dayS = dayS;
            this.monthS = monthS;
            this.yearS = yearS;
        } else throw new IllegalArgumentException("You should set time at work hours!");

        //TODO the time of the start and end should be the same day
        //add this limitation
        String timeFormat1 = "";
        timeFormat1 += String.valueOf(yearE);
        timeFormat1 += "-";
        timeFormat1 += String.valueOf(monthE);
        timeFormat1 += "-";
        timeFormat1 += String.valueOf(dayE);
        timeFormat1 += "  ";
        timeFormat1 += String.valueOf(hourE);
        timeFormat1 += ":";
        timeFormat1 += String.valueOf(minuteE);
        this.calendarE = Calendar.getInstance();
        calendarE.set(yearE, monthE - 1, dayE, hourE, minuteE);
        if ((hourE >= 9 && hourE < 17) && (calendarE.get(Calendar.DAY_OF_WEEK) >= 2 && calendarE.get(Calendar.DAY_OF_WEEK) <= 6)) {
            if (!calendarE.before(calendarS) && (yearS == yearE && monthS == monthE && dayS == dayE)) {
                this.time_end = timeFormat1;
                this.dayE = dayE;
                this.monthE = monthE;
                this.yearE = yearE;
            } else throw new IllegalArgumentException("Invalid time set!");
        } else throw new IllegalArgumentException("You should set time at work hours!");

        this.description = description;
        this.isOnline = isOnline;

        this.room = "room";

        this.platformFactory = new PlatformFactory();
        if (isOnline) {
            this.platformString = platformFactory.getPlatform(platform).type(); // here i would use factory design pattern
            this.onlineLink = platformFactory.getPlatform(platform).meetingLink(link);
        } else {
            this.platformString = "";
            this.onlineLink = "";
        }
        //TODO add platform and room
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        } else throw new IllegalArgumentException("The title cannot be empty!");
    }

    public void setTimeS(int year, int month, int day, int hour, int minute) {
        // i set a limit for time here, the meeting should only be held from 9 - 17 on workdays
        String timeFormat = "";
        timeFormat += String.valueOf(year);
        timeFormat += "-";
        timeFormat += String.valueOf(month);
        timeFormat += "-";
        timeFormat += String.valueOf(day);
        timeFormat += "  ";
        timeFormat += String.valueOf(hour);
        timeFormat += ":";
        timeFormat += String.valueOf(minute);
        calendarS.set(year, month - 1, day, hour, minute);
        if ((hour >= 9 && hour < 17) && (calendarE.get(Calendar.DAY_OF_WEEK) >= 2 && calendarE.get(Calendar.DAY_OF_WEEK) <= 6)) {
            if (!calendarE.before(calendarS) && (year == yearE && month == monthE && day == dayE)) {
                this.time_start = timeFormat;
                this.dayS = day;
                this.monthS = month;
                this.yearS = year;
            } else throw new IllegalArgumentException("Invalid time set!");
        } else throw new IllegalArgumentException("You should set time at work hours!");
    }

    public void setTimeE(int year, int month, int day, int hour, int minute) {
        // i set a limit for time here, the meeting should only be held from 9 - 17 on workdays
        String timeFormat = "";
        timeFormat += String.valueOf(year);
        timeFormat += "-";
        timeFormat += String.valueOf(month);
        timeFormat += "-";
        timeFormat += String.valueOf(day);
        timeFormat += "  ";
        timeFormat += String.valueOf(hour);
        timeFormat += ":";
        timeFormat += String.valueOf(minute);
        calendarE.set(year, month - 1, day, hour, minute);
        if ((hour >= 9 && hour < 17) && (calendarE.get(Calendar.DAY_OF_WEEK) >= 2 && calendarE.get(Calendar.DAY_OF_WEEK) <= 6)) {
            if (!calendarE.before(calendarS) && (yearS == year && monthS == month && dayS == day)) {
                this.time_end = timeFormat;
                this.dayE = day;
                this.monthE = month;
                this.yearE = year;
            } else throw new IllegalArgumentException("Invalid time set!");
        } else throw new IllegalArgumentException("You should set time at work hours!");
    }

    public void setDescription(String des) {
        this.description = des;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public void setRoom(String room) {
        if (isOnline) {
            throw new IllegalArgumentException("You cannot choose a physical room if you have online meeting!");
        } else this.room = room;
    }

    public void setPlatform(String platform) {
        if (!isOnline) {
            throw new IllegalArgumentException("You cannot choose an online platform if you have physical meeting!");
        } else this.platformString = platformFactory.getPlatform(platform).type();
    }

    public void setOnlineLink(String link, String platform){
        if (!isOnline) {
            throw new IllegalArgumentException("You cannot choose an online platform if you have physical meeting!");
        } else this.onlineLink = platformFactory.getPlatform(platform).meetingLink(link);
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

    public String getTime_create() {
        return time_create;
    }

    public String getTime_start() {
        return time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public String getRoom() {
        return room;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public Calendar getCalendarS() {
        return calendarS;
    }

    public Calendar getCalendarE() {
        return calendarE;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + ", Time create: " + getTime_create() + ", Start: " + getTime_start() + ", End: " + getTime_end()
                + ", Description: " + getDescription() + ", isOnline: " + isOnline() + ", (if online)Platform: " + getPlatform() +
                ", (if online)Link: " + getOnlineLink();
        //should print the room or platform also
    }
}
