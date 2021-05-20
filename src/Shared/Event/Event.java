package Shared.Event;

import Shared.Event.Platform.PlatformFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Event {
    private int event_id;
    private final String time_create; //when creating this event
    private String time_start; //when the event starts
    private String time_end; //when the event ends
    //maybe change for the GUI
    private int dayS;
    private int monthS;
    private int yearS;
    private int dayE;
    private int monthE;
    private int yearE;
    private int hourS;
    private int minuteS;
    private int hourE;
    private int minuteE;

    private Calendar calendarS;
    private Calendar calendarE;
    private String title; //the title of the event
    private String description;
    private boolean isOnline;
    private String platformString; //if online, choose a platform
    private String onlineLink; //for share the link to fellows
    private PlatformFactory platformFactory;
    private int roomID; //if physical, choose a room (it could be another type, let's see in the future)

    //TODO add another constructor for physical room
    public Event(String title, String description, int yearS, int monthS, int dayS, int hourS, int minuteS,
                 int yearE, int monthE, int dayE, int hourE, int minuteE, boolean isOnline, String platform, String link) {
        this.event_id = 0;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        this.time_create = sdf.format(date);
        if (title != null) {
            this.title = title;
        } else throw new IllegalArgumentException("The title of the event cannot be null!");

        // i set a limit for time here, the meeting should only be held from 9 - 17 on workdays
        String timeFormat = "";
        timeFormat += String.valueOf(yearS);
        timeFormat += "-";
        timeFormat += setMonthSFull(monthS);
        timeFormat += "-";
        timeFormat += setDaySFull(dayS);
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
            this.hourS = hourS;
            this.minuteS = minuteS;
        } else throw new IllegalArgumentException("You should set time at work hours!");

        String timeFormat1 = "";
        timeFormat1 += String.valueOf(yearE);
        timeFormat1 += "-";
        timeFormat1 += setMonthEFull(monthE);
        timeFormat1 += "-";
        timeFormat1 += setDayEFull(dayE);
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
                this.hourE = hourE;
                this.minuteE = minuteE;
            } else throw new IllegalArgumentException("Invalid time set!");
        } else throw new IllegalArgumentException("You should set time at work hours!");

        this.description = description;
        this.isOnline = isOnline;

        this.roomID = 0;

        this.platformFactory = new PlatformFactory();
        if (isOnline) {
            this.platformString = platformFactory.getPlatform(platform).type(); // here i would use factory design pattern
            this.onlineLink = platformFactory.getPlatform(platform).meetingLink(link);
        } else {
            this.platformString = "";
            this.onlineLink = "";
        }
    }

    public Event(String title, String description, int yearS, int monthS, int dayS, int hourS, int minuteS,
                 int yearE, int monthE, int dayE, int hourE, int minuteE, boolean isOnline, int roomID) {
        this.event_id = 0;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        this.time_create = sdf.format(date);
        if (title != null) {
            this.title = title;
        } else throw new IllegalArgumentException("The title of the event cannot be null!");

        // i set a limit for time here, the meeting should only be held from 9 - 17 on workdays
        String timeFormat = "";
        timeFormat += String.valueOf(yearS);
        timeFormat += "-";
        timeFormat += setMonthSFull(monthS);
        timeFormat += "-";
        timeFormat += setDaySFull(dayS);
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
            this.hourS = hourS;
            this.minuteS = minuteS;
        } else throw new IllegalArgumentException("You should set time at work hours!");

        String timeFormat1 = "";
        timeFormat1 += String.valueOf(yearE);
        timeFormat1 += "-";
        timeFormat1 += setMonthEFull(monthE);
        timeFormat1 += "-";
        timeFormat1 += setDayEFull(dayE);
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
                this.hourE = hourE;
                this.minuteE = minuteE;
            } else throw new IllegalArgumentException("Invalid time set!");
        } else throw new IllegalArgumentException("You should set time at work hours!");

        this.description = description;
        this.isOnline = isOnline;

        if (!isOnline) {
            this.roomID = roomID;
        }
    }

    public Event() {
        this.event_id = 0;
        this.title = null;
        this.description = null;
        this.yearS = 0;
        this.monthS = 0;
        this.dayS = 0;
        this.yearE = 0;
        this.monthE = 0;
        this.dayE = 0;
        this.time_start = null;
        this.time_end = null;
        this.time_create = null;
        this.isOnline = false;
        this.platformFactory = null;
        this.roomID = 0;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
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

    public void setRoom(int room) {
        if (isOnline) {
            throw new IllegalArgumentException("You cannot choose a physical room if you have online meeting!");
        } else this.roomID = room;
    }

    public void setPlatform(String platform) {
        if (!isOnline) {
            throw new IllegalArgumentException("You cannot choose an online platform if you have physical meeting!");
        } else this.platformString = platformFactory.getPlatform(platform).type();
    }

    public void setOnlineLink(String link, String platform) {
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

    public int getRoomID() {
        return roomID;
    }

    public int getEvent_id() {
        return event_id;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public int getDayE() {
        return dayE;
    }

    public int getDayS() {
        return dayS;
    }

    public int getHourE() {
        return hourE;
    }

    public int getHourS() {
        return hourS;
    }

    public int getMinuteE() {
        return minuteE;
    }

    public int getMinuteS() {
        return minuteS;
    }

    public int getMonthE() {
        return monthE;
    }

    public int getMonthS() {
        return monthS;
    }

    public int getYearE() {
        return yearE;
    }

    public int getYearS() {
        return yearS;
    }

    public String setMonthSFull(int mS) {
        String ms = "";
        if (mS < 10) {
            ms = "0" + mS;
        } else ms = String.valueOf(mS);
        return ms;
    }

    public String setMonthEFull(int mE) {
        String me = "";
        if (mE < 10) {
            me = "0" + mE;
        } else me = String.valueOf(mE);
        return me;
    }

    public String setDaySFull(int dS) {
        String ds = "";
        if (dS < 10) {
            ds = "0" + dS;
        } else ds = String.valueOf(dS);
        return ds;
    }

    public String setDayEFull(int dE) {
        String de = "";
        if (dE < 10) {
            de = "0" + dE;
        } else de = String.valueOf(dE);
        return de;
    }

    public Calendar getCalendarS() {
        return calendarS;
    }

    public Calendar getCalendarE() {
        return calendarE;
    }

    @Override
    public String toString() {
        return "ID: " + getEvent_id() + " Title: " + getTitle() + ", Time create: " + getTime_create() + ", Start: " + getTime_start() + ", End: " + getTime_end()
                + ", Description: " + getDescription() + ", isOnline: " + isOnline() + ", (if online)Platform: " + getPlatform() +
                ", (if online)Link: " + getOnlineLink() + ", (if physical)Room: " + getRoomID();
    }

    public String contentString() {
        return getEvent_id() + " " + getTitle() + " " + getTime_create() + " " + getTime_start() + " " + getTime_end()
                + " " + getPlatform() + " " + getOnlineLink() + " " + getRoomID();
    }

    public String dateString(){
        return getTime_start() + " " + getTime_end();
    }
}
