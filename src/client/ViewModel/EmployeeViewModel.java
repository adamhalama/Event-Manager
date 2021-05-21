package client.ViewModel;

import client.Model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeViewModel
{

    private IntegerProperty userID;

    private StringProperty name, surname, role;
    private StringProperty username, password, repeatPassword;

    private StringProperty topLabel, employeeIDTextLabel, passwordLabel, repeatPasswordLabel;

    private ObservableList<String> eventsList, messageRoomList;
    private StringProperty choiceBox;
    private StringProperty addButton, removeButton;

    private ObservableList<PermissionViewModel> permissionList;

    private StringProperty confirmEditButton;

    private int currentEmployeeID;
    private boolean onlyViewing;

    private Model model;

    public EmployeeViewModel(int userID, String name, String surname, String role)
    {
        this.userID = new SimpleIntegerProperty(userID);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.role = new SimpleStringProperty(role);
    }

    public EmployeeViewModel(Model model)
    {
        this.model = model;

        name = new SimpleStringProperty();
        surname = new SimpleStringProperty();
        role = new SimpleStringProperty();

        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        repeatPassword = new SimpleStringProperty();

        topLabel = new SimpleStringProperty();
        employeeIDTextLabel = new SimpleStringProperty();
        passwordLabel = new SimpleStringProperty();
        repeatPasswordLabel = new SimpleStringProperty();

        eventsList = FXCollections.observableArrayList();
        messageRoomList = FXCollections.observableArrayList();

        choiceBox = new SimpleStringProperty();
        addButton = new SimpleStringProperty();
        removeButton = new SimpleStringProperty();

        permissionList = FXCollections.observableArrayList();

        confirmEditButton = new SimpleStringProperty();

    }

    public IntegerProperty getUserIDProperty()
    {
        return userID;
    }

    public StringProperty getNameProperty()
    {
        return name;
    }

    public StringProperty getSurnameProperty()
    {
        return surname;
    }

    public StringProperty getRoleProperty()
    {
        return role;
    }

    public ObservableList<String> getEventsList()
    {
        return eventsList;
    }

    public ObservableList<String> getMessageRoomList()
    {
        return messageRoomList;
    }

    public ObservableList<PermissionViewModel> getPermissionList()
    {
        return permissionList;
    }

    public StringProperty getEmployeeIDTextLabelProperty()
    {
        return employeeIDTextLabel;
    }

    public StringProperty getUsernameProperty()
    {
        return username;
    }

    public StringProperty getPasswordProperty()
    {
        return password;
    }

    public StringProperty getRepeatPasswordProperty()
    {
        return repeatPassword;
    }

    public StringProperty getTopLabelProperty()
    {
        return topLabel;
    }

    public StringProperty getPasswordLabelProperty()
    {
        return passwordLabel;
    }

    public StringProperty getRepeatPasswordLabelProperty()
    {
        return repeatPasswordLabel;
    }

    public StringProperty getChoiceBoxProperty()
    {
        return choiceBox;
    }

    public StringProperty getAddButtonProperty()
    {
        return addButton;
    }

    public StringProperty getRemoveButtonProperty()
    {
        return removeButton;
    }

    public StringProperty getConfirmEditButtonProperty()
    {
        return confirmEditButton;
    }

    public int getCurrentEmployeeID()
    {
        return currentEmployeeID;
    }

    public void setCurrentEmployeeID(int currentEmployeeID)
    {
        this.currentEmployeeID = currentEmployeeID;
    }

    public void setOnlyViewing(boolean onlyViewing)
    {
        this.onlyViewing = onlyViewing;
    }

    public boolean isOnlyViewing()
    {
        return onlyViewing;
    }
}
