package client.ViewModel;

import Shared.Event.Event;
import client.Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

public class EventListViewModel {
    private Model model;
    private StringProperty searchProperty;
    private DatePicker dateSearch;
    private ObservableList<EventViewModel> eventList;

    public EventListViewModel(Model model) {
        this.model = model;
        this.searchProperty = new SimpleStringProperty();
        this.dateSearch = new DatePicker();
        this.eventList = FXCollections.observableArrayList();
        reset();
    }

    public void reset() {
        searchProperty.set(null);
        dateSearch.setValue(null);

        for (int i = 0; i < model.getSize(); i++) {
            eventList.add(i, new EventViewModel(model.getEventByIndex(i)));
        }
    }

    public void removeEvent(Event event) {
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getIdProperty().get() == event.getEvent_id()) {
                model.removeByEventID(event.getEvent_id());
                break;
            }
        }
    }

    public ObservableList<EventViewModel> getEventList() {
        return eventList;
    }

    public void addEvent(Event e) {
        eventList.add(new EventViewModel(e));
    }

    public StringProperty getSearchProperty(){
        return searchProperty;
    }
}
