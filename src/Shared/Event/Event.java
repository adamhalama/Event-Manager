package Shared.Event;

import Shared.Employee.Employee;
import Shared.Event.Platform.PlatformFactory;
import client.Model.Model;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Event {
    private int event_id;
    private String time_create; //when creating this event
    private String time_start; //when the event starts
    private String time_end; //when the event ends
    private String dateString;
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
    private long timestamp;
    private Calendar calendarS;

    private long startTime;
    private long endTime;



    private String title; //the title of the event
    private String description;
    private boolean isOnline;
    private String platformString; //if online, choose a platform
    private String onlineLink; //for share the link to fellows
    private PlatformFactory platformFactory;
    private int roomID; //if physical, choose a room (it could be another type, let's see in the future)
    private int creatorID;
    private ArrayList<Integer> participants;
    private Model model;


    public Event(String title, String description, long startTime, long endTime, String platform, String onlineLink, int creatorID,
                 ArrayList<Integer> participants, Model model)
    {
        isOnline = true;
    }

    public Event(String title, String description, long startTime, long endTime, int roomID, int creatorID,
                 ArrayList<Integer> participants, Model model)
    {
        isOnline = false;
    }




    public Event(String title, String description, int yearS, int monthS, int dayS, int hourS, int minuteS,
                 int hourE, int minuteE, boolean isOnline, String platform, String link, Model model,
                  ArrayList<Integer> participants) {
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
        dateString = "";
        timeFormat += String.valueOf(yearS);
        dateString += String.valueOf(yearS);
        timeFormat += "-";
        dateString += "-";
        timeFormat += setMonthSFull(monthS);
        dateString += setMonthSFull(monthS);
        timeFormat += "-";
        dateString += "-";
        timeFormat += setDaySFull(dayS);
        dateString += setDaySFull(dayS);
        timeFormat += "  ";
        timeFormat += setHourFull(hourS);
        timeFormat += ":";
        timeFormat += setMinuteFull(minuteS);
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
            this.timestamp = dateToStamp(timeFormat);
        } else throw new IllegalArgumentException("You should set time at work hours!");

        String timeFormat1 = "";
        timeFormat1 += setHourFull(hourE);
        timeFormat1 += ":";
        timeFormat1 += setMinuteFull(minuteE);
        if (hourE >= 9 && hourE < 17) {
            this.time_end = timeFormat1;
            this.hourE = hourE;
            this.minuteE = minuteE;
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

        this.model = model;
        this.creatorID = model.getLoggedClientID();
        this.participants = participants;
    }

    public Event(String title, String description, int yearS, int monthS, int dayS, int hourS, int minuteS,
                 int hourE, int minuteE, boolean isOnline, int roomID,
                 Model model, ArrayList<Integer> participants) {
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
        dateString = "";
        timeFormat += String.valueOf(yearS);
        dateString += String.valueOf(yearS);
        timeFormat += "-";
        dateString += "-";
        timeFormat += setMonthSFull(monthS);
        dateString += setMonthSFull(monthS);
        timeFormat += "-";
        dateString += "-";
        timeFormat += setDaySFull(dayS);
        dateString += setDaySFull(dayS);
        timeFormat += "  ";
        timeFormat += setHourFull(hourS);
        timeFormat += ":";
        timeFormat += setMinuteFull(minuteS);
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
            this.timestamp = dateToStamp(timeFormat);
        } else throw new IllegalArgumentException("You should set time at work hours!");

        String timeFormat1 = "";
        timeFormat1 += setHourFull(hourE);
        timeFormat1 += ":";
        timeFormat1 += setMinuteFull(minuteE);
        if (hourE >= 9 && hourE < 17) {
            this.time_end = timeFormat1;
            this.hourE = hourE;
            this.minuteE = minuteE;
        } else throw new IllegalArgumentException("You should set time at work hours!");

        this.description = description;
        this.isOnline = isOnline;
        this.onlineLink = "None";

        if (!isOnline) {
            this.roomID = roomID;
        }

        this.model = model;
        this.creatorID = model.getLoggedClientID();
        this.participants = participants;
    }

    public Event() {
        this.event_id = -1;
        this.time_create = null;
        this.time_start = null;
        this.time_end = null;
        this.dateString = null;
        this.dayS = 0;
        this.monthS = 0;
        this.yearS = 0;
        this.dayE = 0;
        this.monthE = 0;
        this.yearE = 0;
        this.hourS = 0;
        this.minuteS = 0;
        this.hourE = 0;
        this.minuteE = 0;
        this.timestamp = 0;
        this.roomID = 0;
        this.creatorID = -1;
        this.participants = null;
    }

    public long dateToStamp(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = sdf.parse(date);
            return dateTime.getTime();
        } catch (ParseException e) {
            e.getMessage();
        }
        return 0;
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
        String timeFormat1 = "";
        timeFormat1 += String.valueOf(year);
        timeFormat1 += "-";
        timeFormat1 += setMonthEFull(month);
        timeFormat1 += "-";
        timeFormat1 += setDayEFull(day);
        timeFormat1 += "  ";
        timeFormat1 += setHourFull(hour);
        timeFormat1 += ":";
        timeFormat1 += setMinuteFull(minute);
        calendarS.set(year, month - 1, day, hour, minute);
        if (hour >= 9 && hour < 17) {
            time_start = timeFormat1;
            dayS = day;
            monthS = month;
            yearS = year;
            hourS = hour;
            minuteS = minute;
            timestamp = dateToStamp(timeFormat1);
        } else throw new IllegalArgumentException("You should set time at work hours!");
    }

    public void setTimeE(int hour, int minute) {
        // i set a limit for time here, the meeting should only be held from 9 - 17 on workdays
        String timeFormat1 = "";
        timeFormat1 += setHourFull(hour);
        timeFormat1 += ":";
        timeFormat1 += setMinuteFull(minute);
        if (hour >= 9 && hour < 17) {
            this.time_end = timeFormat1;
            this.hourE = hour;
            this.minuteE = minute;
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

    public void setDate(int y, int m, int d) {
        String timeFormat1 = "";
        timeFormat1 += String.valueOf(y);
        timeFormat1 += "-";
        timeFormat1 += setMonthEFull(m);
        timeFormat1 += "-";
        timeFormat1 += setDayEFull(d);
        timeFormat1 += "  ";
        timeFormat1 += setHourFull(hourS);
        timeFormat1 += ":";
        timeFormat1 += setMinuteFull(minuteS);

        String timeFormat2 = "";
        timeFormat2 += setHourFull(hourE);
        timeFormat2 += ":";
        timeFormat2 += setMinuteFull(minuteE);
        calendarS.set(y, m - 1, d);
        if ((calendarS.get(Calendar.DAY_OF_WEEK) >= 2 && calendarS.get(Calendar.DAY_OF_WEEK) <= 6)) {
            time_start = timeFormat1;
            time_end = timeFormat2;
            dayS = d;
            monthS = m;
            yearS = y;
        } else throw new IllegalArgumentException("You should set time at work hours!");
    }

    public void setStartTime(int h, int m) {
        String timeFormat1 = "";
        timeFormat1 += String.valueOf(yearS);
        timeFormat1 += "-";
        timeFormat1 += setMonthEFull(monthS);
        timeFormat1 += "-";
        timeFormat1 += setDayEFull(dayS);
        timeFormat1 += "  ";
        timeFormat1 += setHourFull(h);
        timeFormat1 += ":";
        timeFormat1 += setMinuteFull(m);
        if (h >= 9 && h < 17 && (h < hourE)) {
            time_start = timeFormat1;
            hourS = h;
            minuteS = m;
        } else throw new IllegalArgumentException("Invalid start time!");
    }

    public void setEndTime(int h, int m) {
        String timeFormat1 = "";
        timeFormat1 += setHourFull(h);
        timeFormat1 += ":";
        timeFormat1 += setMinuteFull(m);
        if (h >= 9 && h < 17 && h > hourS) {
            time_end = timeFormat1;
            hourE = h;
            minuteE = m;
        }
        throw new IllegalArgumentException("Invalid end time!");
    }

    public void setTime(int hS, int mS, int hE, int mE) {
        String timeFormat1 = "";
        timeFormat1 += String.valueOf(yearS);
        timeFormat1 += "-";
        timeFormat1 += setMonthEFull(monthS);
        timeFormat1 += "-";
        timeFormat1 += setDayEFull(dayS);
        timeFormat1 += "  ";
        timeFormat1 += setHourFull(hS);
        timeFormat1 += ":";
        timeFormat1 += setMinuteFull(mS);

        String timeFormat2 = "";
        timeFormat2 += setHourFull(hE);
        timeFormat2 += ":";
        timeFormat2 += setMinuteFull(mE);
        calendarS.set(yearS, monthS - 1, dayS, hS, mS);
        if ((hS >= 9 && hS < 17) && (hE >= 9 && hE < 17) && (hS < hE) && (calendarS.get(Calendar.DAY_OF_WEEK) >= 2 && calendarS.get(Calendar.DAY_OF_WEEK) <= 6)) {
            time_start = timeFormat1;
            time_end = timeFormat2;
            hourS = hS;
            hourE = hE;
            minuteS = mS;
            minuteE = mE;
        } else throw new IllegalArgumentException("You should set time at work hours!");
    }

    public void addParticipants(int id) {
        if (id > 0) {
            participants.add(id);
        } else throw new IllegalArgumentException("Invalid employee id!");
    }

    public void removeParticipants(int id) {
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i) == id) {
                participants.remove(i);
            }
        }
    }

    public void setParticipants(ArrayList<Integer> employees){
        this.participants = employees;
    }

    public ArrayList<Integer> getParticipants() {
        return participants;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public String getCreator()
    {
        try
        {
            return model.getEmployeeByID(creatorID).getFullName();
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }//TODO i(adam) added this so the app can run;
        return null;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDateString() {
        System.out.println(dateString);
        LocalDate localDate = LocalDate.parse(dateString);
        return localDate;
    }

    public long getTimestamp() {
        return timestamp;
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

    public int getMonthS() {
        return monthS;
    }

    public int getYearS() {
        return yearS;
    }

    public int participantsSize(){
        return participants.size();
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

    public String setHourFull(int hour) {
        String hf = "";
        if (hour < 10) {
            hf = "0" + hour;
        } else hf = String.valueOf(hour);
        return hf;
    }

    public String setMinuteFull(int minute) {
        String mf = "";
        if (minute == 0) {
            mf = "00";
        } else mf = String.valueOf(minute);
        return mf;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String creatorParticipantString() throws RemoteException
    {
        return "Creator: " + getCreator() + ", Participant(ID): " + getParticipants();
    }

    public String participantString(){
        String participant = "";
        for (int i = 0; i < participants.size(); i++){
            participant += getParticipants().get(i);
            participant += ", ";
        }
        return participant;
    }

    @Override
    public String toString() {
        try
        {
            return "ID: " + getEvent_id() + " Title: " + getTitle() + ", Time create: " + getTime_create() + ", Start: " + getTime_start() + ", End: " + getTime_end()
                    + ", Description: " + getDescription() + ", isOnline: " + isOnline() + ", (if online)Platform: " + getPlatform() +
                    ", (if online)Link: " + getOnlineLink() + ", (if physical)Room: " + getRoomID() + ", " + creatorParticipantString();
        } catch (RemoteException e)
        {
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
}
