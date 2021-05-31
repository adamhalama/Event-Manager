package client.ViewModel;

import Shared.Event.Event;
import Shared.Room.Room;
import client.Model.Model;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class CreateEventViewModel {
    private Model model;
    private IntegerProperty idProperty;
    private StringProperty titleProperty;
    private StringProperty descriptionProperty;
    private IntegerProperty startHour;
    private IntegerProperty startMin;
    private IntegerProperty endHour;
    private IntegerProperty endMin;
    private BooleanProperty isOnline;
    private IntegerProperty roomProperty;
    private StringProperty platformProperty;
    private StringProperty linkProperty;
    private StringProperty usernameProperty;
    private StringProperty nameProperty;
    private StringProperty employeeIdProperty;
    private StringProperty roleProperty;
    private ObservableList<EmployeeViewModel> employeeList;
    private StringProperty errorProperty;

    public CreateEventViewModel(Model model) {
        this.model = model;
        this.idProperty = new SimpleIntegerProperty();
        this.titleProperty = new SimpleStringProperty();
        this.descriptionProperty = new SimpleStringProperty();
        this.startHour = new SimpleIntegerProperty();
        this.startMin = new SimpleIntegerProperty();
        this.endHour = new SimpleIntegerProperty();
        this.endMin = new SimpleIntegerProperty();
        this.isOnline = new SimpleBooleanProperty();
        this.roomProperty = new SimpleIntegerProperty();
        this.platformProperty = new SimpleStringProperty();
        this.linkProperty = new SimpleStringProperty();
        this.errorProperty = new SimpleStringProperty();

        this.usernameProperty = new SimpleStringProperty();
        this.nameProperty = new SimpleStringProperty();
        this.employeeIdProperty = new SimpleStringProperty();
        this.roleProperty = new SimpleStringProperty();
        this.employeeList = FXCollections.observableArrayList();
    }

    public void clear() {
        employeeList.clear();
    }

    public ObservableList<EmployeeViewModel> update(){
        for (int i = 0; i < model.getParticipantsIDT().size(); i++){
            int id = model.getParticipantsT().get(i).getId();
            String username = model.getParticipantsT().get(i).getName();
            String name = model.getParticipantsT().get(i).getFullName();
            String role = model.getParticipantsT().get(i).getRole();
            employeeList.add(i, new EmployeeViewModel(id, username, name, role));
        }
        return employeeList;
    }

    public void removeParticipant(int id){
        for (int i = 0; i < employeeList.size(); i++){
            if (employeeList.get(i).getUserIDProperty().get() == id){
                employeeList.remove(i);
                model.removeEmployeeT(id);
            }
        }
    }

    public void addEvent(Event e) {
        model.add(e);
    }

    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    public BooleanProperty getIsOnline() {
        return isOnline;
    }

    public boolean isOnline() {
        return model.isOnline();
    }
    public void setIsOnline(boolean isOnline) {
        model.setOnline( isOnline);
    }

    public void setErrorProperty(String errorProperty) {
        this.errorProperty.set(errorProperty);
    }

    public void setIdProperty(int idProperty) {
        this.idProperty.set(idProperty);
    }

    public int getID() {
        return model.getEvent_id();
    }

    public void add(String title, String des, int year, int month, int day, int hourS, int minuteS,
                    int hourE, int minuteE, long startTime, long endTime, String platform, String link,
                    Model model, ArrayList<Integer> paticipants){
        Event e1 = new Event(title, des, year, month, day, hourS, minuteS, hourE, minuteE,
                true, platform, link, model, paticipants); // for local constructor
        addEvent(e1);
        setIdProperty(e1.getEvent_id());
    }

    public void add(String title, String des, int year, int month, int day, int hourS, int minuteS,
                    int hourE, int minuteE, long startTime, long endTime, int id,
                    Model model, ArrayList<Integer> paticipants){
        Event e1 = new Event(title, des, year, month, day, hourS, minuteS, hourE, minuteE,
                false, id, model, paticipants); // for local constructor
        addEvent(e1);
        setIdProperty(e1.getEvent_id());
    }
}
