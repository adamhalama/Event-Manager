package client.ViewModel;

import Shared.Event.Event;
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
            String username = model.getParticipantsT().get(i).getName(); //should be username
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

    public IntegerProperty getStartHour() {
        return startHour;
    }

    public IntegerProperty getIdProperty() {
        return idProperty;
    }

    public IntegerProperty getStartMin() {
        return startMin;
    }

    public IntegerProperty getEndHour() {
        return endHour;
    }

    public IntegerProperty getEndMin() {
        return endMin;
    }

    public BooleanProperty getIsOnline() {
        return isOnline;
    }

    public boolean isOnline() {
        return model.isOnline();
    }

    public StringProperty getPlatformProperty() {
        return platformProperty;
    }

    public IntegerProperty getRoomProperty() {
        return roomProperty;
    }

    public StringProperty getLinkProperty() {
        return linkProperty;
    }

    public StringProperty getErrorProperty() {
        return errorProperty;
    }

    public void setIsOnline(boolean isOnline) {
        model.setOnline(isOnline);
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

    public String getWholeMessage() {
        return model.getEventByIndex(0).toString();
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public StringProperty getNameProperty() {
        return nameProperty;
    }

    public StringProperty getEmployeeIdProperty() {
        return employeeIdProperty;
    }

    public StringProperty getRoleProperty() {
        return roleProperty;
    }

    public ArrayList<Integer> getIDs(){
        return model.getParticipantsIDT();
    }
}
