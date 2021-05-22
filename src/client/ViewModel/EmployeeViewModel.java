package client.ViewModel;

import Shared.Employee.Employee;
import client.Model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

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

    public void reset()
    {
        eventsList.clear();
        permissionList.clear();
        messageRoomList.clear();
//        TODO
//        REMOVE THIS WHEN MESSAGEROOM LIST IS COMPLETE
        messageRoomList.add("Add messageRoomList, needed for this to work with chat names and not ID's");

        passwordLabel.setValue("Password: ");
        repeatPasswordLabel.setValue("Repeat password: ");

        addButton.setValue("Add");
        removeButton.setValue("Remove selected");
        choiceBox.setValue("");

        if(currentEmployeeID == 0) // creating
        {
            topLabel.setValue("Create Employee");
            employeeIDTextLabel.setValue("");

            confirmEditButton.setValue("Confirm");

            username.setValue("");
            password.setValue("");
            repeatPassword.setValue("");
            name.setValue("");
            surname.setValue("");
            role.setValue("");

        }
        else if (!onlyViewing) // editing
        {
            Employee currentEmp = model.getEmployeeByID(currentEmployeeID);

            topLabel.setValue(model.getEmployeeByID(currentEmployeeID).getName() + " " + model.getEmployeeByID(currentEmployeeID).getSurname());
            employeeIDTextLabel.setValue("Employee ID: " + currentEmployeeID);

            username.setValue(currentEmp.getSurname());
            password.setValue("");
            name.setValue(currentEmp.getName());
            surname.setValue(currentEmp.getSurname());
            role.setValue(currentEmp.getRole());

            for (Integer eventID:
                 currentEmp.getEvents())
            {
                eventsList.add(model.getEventByID(eventID).getTitle());
            }

            for (Integer messageRoomID:
                 currentEmp.getMessageRooms())
            {
                //TODO currently not doable, the chatroomList is missing
                break;
            }
            messageRoomList.add("Add messageRoomList, needed for this to work with chat names and not ID's");

            for (String permission:
                 currentEmp.getPermissions())
            {
                permissionList.add(new PermissionViewModel(permission));
            }

        }
        else if (onlyViewing) // only viewing
        {

        }
    }




    public void passwordTyped()
    {
        //TODO some real time validation could be nice, if the password is sufficient
    }


    public void addButton(String permission)
    {
        for (PermissionViewModel perm:
             permissionList)
        {
            if(perm.getPermissionProperty().get().equals(permission))
                return;
        }
        permissionList.add(new PermissionViewModel(permission));
    }

    public void removeSelectedButton(int selectedItem)
    {
        permissionList.remove(selectedItem);
    }

    public void confirmButton()
    {
        //TODO pass the password to the database
        if (permissionList.isEmpty())
            model.addEmployee(username.get(), name.get(), surname.get(), role.get());
        else
        {
            ArrayList<String> permissions = new ArrayList<>();
            for (PermissionViewModel pers:
                 permissionList)
            {

                if (pers.getPermissionProperty().equals("Event join"))
                    permissions.add("event_join");
                if (pers.getPermissionProperty().equals("Event create"))
                    permissions.add("event_create");
                if (pers.getPermissionProperty().equals("Event edit"))
                    permissions.add("event_edit");
                if (pers.getPermissionProperty().equals("Event invite"))
                    permissions.add("event_invite");
                if (pers.getPermissionProperty().equals("Room create/edit"))
                    permissions.add("room_create_edit");
                if (pers.getPermissionProperty().equals("Manage employees"))
                    permissions.add("manage_employees");
                if (pers.getPermissionProperty().equals("Manage chat rooms"))
                    permissions.add("manage_chat_rooms");
            }

            model.addEmployee(username.get(), name.get(), surname.get(), new ArrayList<>(), new ArrayList<>(), role.get(), permissions);
        }
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
