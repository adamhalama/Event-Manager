package client.ViewModel;

import client.Model.Model;
import client.View.SelectState;
import javafx.beans.property.*;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class EditEventViewModel {
    private Model model;
    private IntegerProperty idProperty;
    private StringProperty titleProperty;
    private StringProperty descriptionProperty;
    private DatePicker startDate;
    private IntegerProperty startHour;
    private IntegerProperty startMin;
    private IntegerProperty endHour;
    private IntegerProperty endMin;
    private BooleanProperty isOnline;
    private IntegerProperty roomProperty;
    private StringProperty platformProperty;
    private StringProperty linkProperty;
    private StringProperty errorProperty;
    private int id;
    private SelectState state;

    public EditEventViewModel(Model model, SelectState state){
        this.state = state;
        this.id = state.getEditSelect();
        this.model = model;
        this.errorProperty = new SimpleStringProperty();
        if (id < 0) {
            this.idProperty = new SimpleIntegerProperty();
            this.titleProperty = new SimpleStringProperty();
            this.descriptionProperty = new SimpleStringProperty();
            this.startDate = new DatePicker(LocalDate.now());
            this.startHour = new SimpleIntegerProperty();
            this.startMin = new SimpleIntegerProperty();
            this.endHour = new SimpleIntegerProperty();
            this.endMin = new SimpleIntegerProperty();
            this.isOnline = new SimpleBooleanProperty();
            this.roomProperty = new SimpleIntegerProperty();
            this.platformProperty = new SimpleStringProperty();
            this.linkProperty = new SimpleStringProperty();
        } else {
            this.idProperty = new SimpleIntegerProperty(model.getEventByID(id).getEvent_id());
            this.titleProperty = new SimpleStringProperty(model.getEventByID(id).getTitle());
            this.descriptionProperty = new SimpleStringProperty(model.getEventByID(id).getDescription());
            this.startDate = new DatePicker(model.getEventByID(id).getDateString());
            this.startHour = new SimpleIntegerProperty(model.getEventByID(id).getHourS());
            this.startMin = new SimpleIntegerProperty(model.getEventByID(id).getMinuteS());
            this.endHour = new SimpleIntegerProperty(model.getEventByID(id).getHourE());
            this.endMin = new SimpleIntegerProperty(model.getEventByID(id).getMinuteE());
            if (model.getEventByID(id).isOnline()) {
                this.isOnline = new SimpleBooleanProperty(model.getEventByID(id).isOnline());
                this.roomProperty = new SimpleIntegerProperty();
                this.platformProperty = new SimpleStringProperty(model.getEventByID(id).getPlatform());
                this.linkProperty = new SimpleStringProperty(model.getEventByID(id).getOnlineLink());
            } else {
                this.isOnline = new SimpleBooleanProperty(model.getEventByID(id).isOnline());
                this.roomProperty = new SimpleIntegerProperty(model.getEventByID(id).getRoomID());
                this.platformProperty = new SimpleStringProperty();
                this.linkProperty = new SimpleStringProperty();
            }
        }
    }

    public void clear() {
        titleProperty.set(null);
        descriptionProperty.set(null);

        startDate = new DatePicker(LocalDate.now());
        //not for sure ↑
        startHour.setValue(null);
        startMin.setValue(null);
        endHour.setValue(null);
        endMin.setValue(null);
        isOnline.setValue(null);
        roomProperty.setValue(null);
        platformProperty.set(null);
        linkProperty.set(null);
        errorProperty.set(null);
    }

    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    public String getTitle(int id){
        return model.getEventByID(id).getTitle();
    }

    public String getDes(int id){
        return model.getEventByID(id).getDescription();
    }

    public String getLink(int id){
        return model.getEventByID(id).getOnlineLink();
    }

    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    public IntegerProperty getStartHour() {
        return startHour;
    }

    public IntegerProperty getIdProperty(){
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

    public LocalDate getDate(){
        this.id = state.getEditSelect();
        return model.getEventByID(id).getDateString();
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

    public void setDescriptionProperty(String descriptionProperty) {
        this.descriptionProperty.set(descriptionProperty);
    }

    public void setEndHour(int endHour) {
        this.endHour.set(endHour);
    }

    public void setEndMin(int endMin) {
        this.endMin.set(endMin);
    }

    public void setLinkProperty(String linkProperty) {
        this.linkProperty.set(linkProperty);
    }

    public void setPlatformProperty(String platformProperty) {
        this.platformProperty.set(platformProperty);
    }

    public void setRoomProperty(int roomProperty) {
        this.roomProperty.set(roomProperty);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title, int id){
        model.getEventByID(id).setTitle(title);
    }

    public void setDes(String des, int id){
        model.getEventByID(id).setDateString(des);
    }

    public void setDate(int y, int m, int d, int id){
        model.getEventByID(id).setDate(y, m, d);
    }

    public void setStartHour(int h, int m, int id){
        model.getEventByID(id).setStartTime(h, m);
    }

    public void setEndHour(int h, int m, int id){
        model.getEventByID(id).setEndTime(h, m);
    }

    public void setTime(int hS, int mS, int hE, int mE, int id){
        model.getEventByID(id).setTime(hS, mS, hE, mE);
    }

    public void setStartHour(int startHour) {
        this.startHour.set(startHour);
    }

    public void setStartMin(int startMin) {
        this.startMin.set(startMin);
    }

    public int getYear(int id){
        return model.getEventByID(id).getYearS();
    }

    public int getMonth(int id){
        return model.getEventByID(id).getMonthS();
    }

    public int getDay(int id){
        return model.getEventByID(id).getDayS();
    }

    public void setPlatform(String platform, int id){
        model.getEventByID(id).setPlatform(platform);
    }

    public void setRoom(int roomID, int id){
        model.getEventByID(id).setRoom(roomID);
    }

    public String getStartTime(int id){
        return model.getEventByID(id).getTime_start();
    }

    public String getEndTime(int id){
        return model.getEventByID(id).getTime_end();
    }

    public void setTitleProperty(String titleProperty) {
        this.titleProperty.set(titleProperty);
    }

    public int getID(){
        return model.getEvent_id();
    }

    public String getWholeMessage() {
        return model.getEventByIndex(0).toString();
    }
}
