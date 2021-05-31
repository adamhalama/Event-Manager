package client.ViewModel;

import Shared.Event.Event;
import Shared.Event.EventList;
import client.Model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EventListViewModel {
    private Model model;
    private StringProperty searchProperty;
    private StringProperty yearSProperty;
    private StringProperty monthSProperty;
    private StringProperty daySProperty;
    private ObservableList<EventViewModel> eventList;
    private ObservableList<EventViewModel> resultList;

    public EventListViewModel(Model model) {
        this.model = model;
        this.searchProperty = new SimpleStringProperty();
        this.yearSProperty = new SimpleStringProperty();
        this.monthSProperty = new SimpleStringProperty();
        this.daySProperty = new SimpleStringProperty();
        this.eventList = FXCollections.observableArrayList();
        this.resultList = FXCollections.observableArrayList();
        reset();
    }

    public void reset() {
        searchProperty.set(null);
        this.yearSProperty.set(null);
        this.monthSProperty.set(null);
        this.daySProperty.set(null);
        eventList.clear();
    }

    public void resetSearch() {
        searchProperty.set(null);
        this.yearSProperty.set(null);
        this.monthSProperty.set(null);
        this.daySProperty.set(null);
    }

    public ObservableList<EventViewModel> update() {
        for (int i = 0; i < model.getSize(); i++) {
            eventList.add(i, new EventViewModel(model.getEvents().get(i)));
        }
        return eventList;
    }

    public void removeEvent(int id) {
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getIdProperty().get() == id) {
                try
                {
                    model.removeByEventID(id);
                } catch (RemoteException e)
                {
                    e.printStackTrace();
                }
                reset();
                update();
                break;
            }
        }
    }


    public ObservableList<EventViewModel> searchExceptDate(String searchingContent) {
        resultList.clear();
        ArrayList<Event> resultArrayList = model.getEventExceptDate(searchingContent);
        System.out.println(resultArrayList);
        for (int i = 0; i < resultArrayList.size(); i++) {
            resultList.add(new EventViewModel(resultArrayList.get(i)));
        }
        return resultList;
    }

    public ObservableList<EventViewModel> searchOnlyDate(String searchingDate) {
        resultList.clear();
        ArrayList<Event> resultArrayList = model.getEventOnlyDate(searchingDate);
        System.out.println(resultArrayList);
        for (int i = 0; i < resultArrayList.size(); i++) {
            resultList.add(new EventViewModel(resultArrayList.get(i)));
        }
        return resultList;
    }

    public ObservableList<EventViewModel> search(String searchingContent, String searchingDate) {
        resultList.clear();
        ArrayList<Event> resultArrayList = model.getEventByAnything(searchingContent, searchingDate);
        System.out.println(resultArrayList);
        for (int i = 0; i < resultArrayList.size(); i++) {
            resultList.add(new EventViewModel(resultArrayList.get(i)));
        }
        return resultList;
    }

    public int getResultSize() {
        return resultList.size();
    }

    public ObservableList<EventViewModel> getEventList() {
        for (int i = 0; i < model.getSize(); i++) {
            eventList.add(i, new EventViewModel(model.getEvents().get(i)));
        }
        return eventList;
    }

    public void addEvent(Event e) {
        eventList.add(new EventViewModel(e));
    }

    public int getEventListSize() {
        return model.getSize();
    }

    public StringProperty getSearchProperty() {
        return searchProperty;
    }

    public StringProperty getYearSProperty() {
        return yearSProperty;
    }

    public StringProperty getMonthSProperty() {
        return monthSProperty;
    }

    public StringProperty getDaySProperty() {
        return daySProperty;
    }
}
