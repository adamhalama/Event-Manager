package client.ViewModel;

import client.Model.Model;
import client.View.SelectState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class EventInfoViewModel {
    private StringProperty title;
    private StringProperty description;
    private StringProperty startTime;
    private StringProperty endTime;
    private StringProperty type;
    private StringProperty platform;
    private StringProperty link;
    private IntegerProperty room;
    private StringProperty participant;
    private StringProperty creator;
    private StringProperty employeeInfo;
    private StringProperty createTime;
    private Model model;
    private SelectState selectState;
    private int id;

    public EventInfoViewModel(Model model, SelectState state) {
        this.model = model;
        this.selectState = state;
        this.id = state.getEditSelect();
        if (id < 0) {
            this.title = new SimpleStringProperty();
            this.description = new SimpleStringProperty();
            this.startTime = new SimpleStringProperty();
            this.endTime = new SimpleStringProperty();
            this.type = new SimpleStringProperty();
            this.platform = new SimpleStringProperty();
            this.link = new SimpleStringProperty();
            this.room = new SimpleIntegerProperty();
            this.participant = new SimpleStringProperty();
            this.creator = new SimpleStringProperty();
            this.createTime = new SimpleStringProperty();
        } else {
            this.title = new SimpleStringProperty(model.getEventByID(id).getTitle());
            this.description = new SimpleStringProperty(model.getEventByID(id).getDescription());
            this.startTime = new SimpleStringProperty(model.getEventByID(id).getTime_start());
            this.endTime = new SimpleStringProperty(model.getEventByID(id).getTime_end());
            if (model.getEventByID(id).isOnline()) {
                this.type = new SimpleStringProperty("Online");
                this.platform = new SimpleStringProperty(model.getEventByID(id).getPlatform());
                this.link = new SimpleStringProperty(model.getEventByID(id).getOnlineLink());
                this.room = new SimpleIntegerProperty(-1);
            } else {
                this.type = new SimpleStringProperty("Physical");
                this.room = new SimpleIntegerProperty(model.getEventByID(id).getRoomID());
            }
            this.participant = new SimpleStringProperty();
            this.creator = new SimpleStringProperty();
            this.participant = new SimpleStringProperty(model.getEventByID(id).participantString());
            this.creator = new SimpleStringProperty(model.getEventByID(id).getCreator());
            this.employeeInfo = new SimpleStringProperty(model.getEventByID(id).participantString());
            this.createTime = new SimpleStringProperty(model.getEventByID(id).getTime_create());
        }
    }

    public String getTitle(int id) {
        return model.getEventByID(id).getTitle();
    }

    public String getDes(int id) {
        return model.getEventByID(id).getDescription();
    }

    public String getStart(int id) {
        return model.getEventByID(id).getTime_start();
    }

    public String getEnd(int id) {
        return model.getEventByID(id).getTime_end();
    }

    public String getCreate(int id) {
        return model.getEventByID(id).getTime_create();
    }

    public String getLink(int id) {
        return model.getEventByID(id).getOnlineLink();
    }

    public String getPlatform(int id) {
        return model.getEventByID(id).getPlatform();
    }

    public String getParticipantCreatorInfo(int id){
        return model.getEventByID(id).creatorParticipantString();
    }

    public String getParticipant(int id) {
        return model.getEventByID(id).participantString();
    }

    public String getCreator(int id) {
        return model.getEventByID(id).getCreator();
    }

    public boolean getTYpe(int id) {
        return model.getEventByID(id).isOnline();
    }

    public int getRoom(int id) {
        return model.getEventByID(id).getRoomID();
    }
}
