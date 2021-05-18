package client.ViewModel;

import Shared.Event.Event;
import javafx.beans.property.*;
import javafx.scene.control.DatePicker;

public class EventViewModel {
    private IntegerProperty idProperty;
    private StringProperty titleProperty;
    private StringProperty descriptionProperty;
    private StringProperty createDate;
    private StringProperty startDate;
    private StringProperty endTime;
    private BooleanProperty isOnline;
    private IntegerProperty roomProperty;
    private StringProperty platformProperty;
    private StringProperty linkProperty;

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
}