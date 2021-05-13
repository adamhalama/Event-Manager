package client.ViewModel;

import javafx.beans.property.*;
import javafx.scene.control.DatePicker;

public class CreateEventViewModel
{
    //private Model model;
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

    public CreateEventViewModel(/*Model model*/)
    {
        //this.model = model;
        this.titleProperty = new SimpleStringProperty();
        this.descriptionProperty = new SimpleStringProperty();
        this.startDate = new DatePicker();
        this.endDate = new DatePicker();
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


}
