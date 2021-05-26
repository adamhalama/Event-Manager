package client.ViewModel;

import Shared.Employee.Employee;
import Shared.Room.Room;
import client.Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeListViewModel {
    private StringProperty searchBox;
    private ObservableList<EmployeeViewModel> employeeList;
    private StringProperty errorLabel;
    private Model model;


    public EmployeeListViewModel(Model model) {
        this.model = model;

        searchBox = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();

        employeeList = FXCollections.observableArrayList();

        reset();
    }

    public void reset() {
        searchBox.setValue(null);
        errorLabel.setValue(null);

        employeeList.clear();

        for (Employee e : model.getEmployees()) {
            employeeList.add(new EmployeeViewModel(e.getId(), e.getName(), e.getSurname(), e.getRole()));
        }
    }

    public void setErrorLabel(String text) {
        errorLabel.setValue(text);
    }

    public ObservableList<EmployeeViewModel> getEmployeeList() {
        return employeeList;
    }

    public StringProperty getSearchBoxProperty() {
        return searchBox;
    }

    public StringProperty getErrorLabelProperty() {
        return errorLabel;
    }

    public void search() {
        if (searchBox == null)
            return;

        if (searchBox.get().equals("")) {
            reset();
            return;
        } else
            employeeList.clear();

        for (Employee e :
                model.getEmployeesByAnything(searchBox.get())) {
            employeeList.add(new EmployeeViewModel(e.getId(), e.getName(), e.getSurname(), e.getRole()));
        }
    }

    public void removeEmployee(int selectedIndex, int employeeID) {
        employeeList.remove(selectedIndex);
        model.removeEmployee(employeeID);
    }

    public void addToEvent(int id) {
        model.addParticipants(id);
    }
}
