package client.ViewModel;

import client.Model.Model;
import javafx.beans.property.*;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreateEventViewModel
{
    private Model model;
    private StringProperty titleProperty;
    private StringProperty descriptionProperty;
    private DatePicker startDate;
    private DatePicker endDate;
    private IntegerProperty startHour;
    private IntegerProperty startMin;
    private IntegerProperty endHour;
    private IntegerProperty endMin;
    private BooleanProperty isOnline;
    private StringProperty roomProperty;
    private StringProperty platformProperty;
    private StringProperty linkProperty;
    private StringProperty errorProperty;

    public CreateEventViewModel(Model model)
    {
        this.model = model;
        this.titleProperty = new SimpleStringProperty();
        this.descriptionProperty = new SimpleStringProperty();
        this.startDate = new DatePicker(LocalDate.now());
        this.endDate = new DatePicker(LocalDate.now());
        this.startHour = new SimpleIntegerProperty();
        this.startMin = new SimpleIntegerProperty();
        this.endHour = new SimpleIntegerProperty();
        this.endMin = new SimpleIntegerProperty();
        this.isOnline = new SimpleBooleanProperty();
        this.roomProperty = new SimpleStringProperty();
        this.platformProperty = new SimpleStringProperty();
        this.linkProperty = new SimpleStringProperty();
        this.errorProperty = new SimpleStringProperty();
    }

    public void clear(){
        titleProperty.set(null);
        descriptionProperty.set(null);

        startDate = new DatePicker(LocalDate.now());
        endDate = startDate;
        //not for sure ↑
        startHour.setValue(null);
        startMin.setValue(null);
        endHour.setValue(null);
        endMin.setValue(null);
        isOnline.setValue(null);
        roomProperty.set(null);
        platformProperty.set(null);
        linkProperty.set(null);
        errorProperty.set(null);
    }


    // convert the date in datepicker to string

    public void addEvent(){
        String title = titleProperty.get();
        String des = descriptionProperty.get();
    }

    public StringProperty getTitleProperty(){
        return titleProperty;
    }

    public StringProperty getDescriptionProperty(){
        return descriptionProperty;
    }

    public IntegerProperty getStartHour(){
        return startHour;
    }

    public IntegerProperty getStartMin(){
        return startMin;
    }

    public IntegerProperty getEndHour(){
        return endHour;
    }

    public IntegerProperty getEndMin(){
        return endMin;
    }

    public BooleanProperty getIsOnline(){
        return isOnline;
    }

    public StringProperty getPlatformProperty(){
        return platformProperty;
    }

    public StringProperty getRoomProperty(){
        return roomProperty;
    }

    public StringProperty getLinkProperty(){
        return linkProperty;
    }

    public StringProperty getErrorProperty(){
        return errorProperty;
    }

    public void setIsOnline(boolean isOnline){
        model.setOnline(isOnline);
    }
}
