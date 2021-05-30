package client.ViewModel;

import Shared.Event.Event;
import javafx.beans.property.*;
import javafx.scene.control.DatePicker;

public class EventViewModel {
    private IntegerProperty idProperty;
    private StringProperty titleProperty;
    private StringProperty descriptionProperty;
    private StringProperty createDate;
    private IntegerProperty yearSProperty;
    private IntegerProperty monthSProperty;
    private IntegerProperty daySProperty;
    private IntegerProperty yearEProperty;
    private IntegerProperty monthEProperty;
    private IntegerProperty dayEProperty;
    private IntegerProperty hourSProperty;
    private IntegerProperty minuteSProperty;
    private IntegerProperty hourEProperty;
    private IntegerProperty minuteEProperty;
    private StringProperty startDate;
    private StringProperty endTime;
    private BooleanProperty isOnline;
    private IntegerProperty roomProperty;
    private StringProperty platformProperty;
    private StringProperty linkProperty;
    private StringProperty wholeMessage;
    private StringProperty creatorProperty;
    private IntegerProperty sizeProperty;

    public EventViewModel(Event event) {
        this.idProperty = new SimpleIntegerProperty(event.getEvent_id());
        this.titleProperty = new SimpleStringProperty(event.getTitle());
        this.descriptionProperty = new SimpleStringProperty(event.getDescription());
        this.createDate = new SimpleStringProperty(event.getTime_create());
        this.startDate = new SimpleStringProperty(event.getTime_start());
        this.endTime = new SimpleStringProperty(event.getTime_end());
        this.isOnline = new SimpleBooleanProperty(event.isOnline());
        this.roomProperty = new SimpleIntegerProperty(event.getRoomID());
        this.platformProperty = new SimpleStringProperty(event.getPlatform());
        this.linkProperty = new SimpleStringProperty(event.getOnlineLink());
        this.wholeMessage = new SimpleStringProperty(event.toString());
        this.yearSProperty = new SimpleIntegerProperty(event.getYearS());
        this.yearEProperty = yearSProperty;
        this.monthSProperty = new SimpleIntegerProperty(event.getMonthS());
        this.monthEProperty = monthSProperty;
        this.daySProperty = new SimpleIntegerProperty(event.getDayS());
        this.dayEProperty = daySProperty;
        this.hourSProperty = new SimpleIntegerProperty(event.getHourS());
        this.hourEProperty = new SimpleIntegerProperty(event.getHourE());
        this.minuteSProperty = new SimpleIntegerProperty(event.getMinuteS());
        this.minuteEProperty = new SimpleIntegerProperty(event.getMinuteE());
        this.creatorProperty = new SimpleStringProperty(event.getCreator());
        this.sizeProperty = new SimpleIntegerProperty(event.participantsSize());
    }

    public EventViewModel(int id, String title, String date, String endTime, String creator, int numberOfParticipants) // made for roomEventsView
    {
        this.idProperty = new SimpleIntegerProperty(id);
        this.titleProperty = new SimpleStringProperty(title);
        this.startDate = new SimpleStringProperty(date);
        this.endTime = new SimpleStringProperty(endTime);
        this.creatorProperty = new SimpleStringProperty(creator);
        this.sizeProperty = new SimpleIntegerProperty(numberOfParticipants);
    }


    public IntegerProperty getIdProperty() {
        return idProperty;
    }

    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    public StringProperty getCreateDate() {
        return createDate;
    }

    public StringProperty getStartDate() {
        return startDate;
    }

    public StringProperty getEndTime() {
        return endTime;
    }

    public BooleanProperty isOnline() {
        return isOnline;
    }

    public IntegerProperty getRoomProperty() {
        return roomProperty;
    }

    public StringProperty getPlatformProperty() {
        return platformProperty;
    }

    public StringProperty getLinkProperty() {
        return linkProperty;
    }

    public StringProperty getWholeMessage() {
        return wholeMessage;
    }

    public StringProperty getCreatorProperty() {
        return creatorProperty;
    }

    public IntegerProperty getSizeProperty() {
        return sizeProperty;
    }
}
