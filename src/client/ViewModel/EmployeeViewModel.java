package client.ViewModel;

import Shared.Employee.Employee;
import client.Model.Model;
import client.View.Helpers.ConfirmationButton;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
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

    private ObservableList<PermissionViewModel> permissionTable;

    private StringProperty confirmEditButton, errorLabel;

    private ArrayList<String> permissions;

    private int currentEmployeeID;
    private boolean onlyViewing;

    private Model model;

    public EmployeeViewModel(int userID, String name, String surname, String role) // for the data in the EmployeeListView, and CreateMessageRoomView
    {
        this.userID = new SimpleIntegerProperty(userID);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.role = new SimpleStringProperty(role);
    }


    public EmployeeViewModel(Model model) // viewModel for the EmpViewController
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

        permissionTable = FXCollections.observableArrayList();

        confirmEditButton = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();

    }

    public void reset()
    {
        eventsList.clear();
        permissionTable.clear();
        messageRoomList.clear();


        passwordLabel.setValue("Password: ");
        repeatPasswordLabel.setValue("Repeat password: ");

        addButton.setValue("Add");
        removeButton.setValue("Remove selected");
        choiceBox.setValue("");

        errorLabel.setValue("");

        if (currentEmployeeID == 0) // creating
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

        } else  // editing or viewing
        {
            Employee currentEmp = null;
            try
            {
                currentEmp = model.getEmployeeByID(currentEmployeeID);
                topLabel.setValue(model.getEmployeeByID(currentEmployeeID).getName() + " " + model.getEmployeeByID(currentEmployeeID).getSurname());
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
                errorLabel.setValue(throwables.getMessage());
            } catch (RemoteException e)
            {
                e.printStackTrace();
                errorLabel.setValue("Error communicating with the server");
            }


            employeeIDTextLabel.setValue("Employee ID: " + currentEmployeeID);

            username.setValue(currentEmp.getSurname());
            password.setValue("");
            repeatPassword.setValue("");
            name.setValue(currentEmp.getName());
            surname.setValue(currentEmp.getSurname());
            role.setValue(currentEmp.getRole());

            for (Integer eventID :
                    currentEmp.getEvents())
            {
                eventsList.add(model.getEventByID(eventID).getTitleTimeString());
            }

            for (Integer messageRoomID :
                    currentEmp.getMessageRooms())
            {
                try
                {
                    if (!model.getMessageRoomByID(messageRoomID).isPrivate())
                        messageRoomList.add(model.getMessageRoomByID(messageRoomID).getName());
                } catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                    errorLabel.setValue(throwables.getMessage());
                } catch (RemoteException e)
                {
                    e.printStackTrace();
                    errorLabel.setValue("Error communicating with the server");
                }
            }

            for (String permission :
                    currentEmp.getPermissions())
            {
                permissionTable.add(new PermissionViewModel(permission));
            }

            if (onlyViewing)
            {

                //todo make setDisable
                // mabybe already done in the controller
            }

        }
    }


    public void passwordTyped()
    {
        //TODO some real time validation could be nice, if the password is sufficient
    }


    public void addButton(String permission)
    {
        for (PermissionViewModel perm :
                permissionTable)
        {
            if (perm.getPermissionProperty().get().equals(permission))
                return;
        }
        permissionTable.add(new PermissionViewModel(permission));
    }

    public void removeSelectedButton(int selectedItem)
    {
        permissionTable.remove(selectedItem);
    }

    public boolean confirmButton()
    {
        if (username == null || password == null || repeatPassword == null || name == null || surname == null || role == null)
        {
            errorLabel.set("You have to fill all the forms");
            return false;
        }

        if (!ConfirmationButton.confirmationView("Do you want to save the changes?"))
            return false;

        if (currentEmployeeID == 0) // Creating an employee
        {
            if (!password.get().equals(repeatPassword.get()))
            {
                errorLabel.set("The passwords have to match");
                return false;
            }

            if (permissionTable.isEmpty())
            {
                try
                {
                    model.addEmployee(username.get(), password.get(), name.get(), surname.get(), role.get());
                } catch (SQLException | GeneralSecurityException | IOException throwables)
                {
                    errorLabel.set(throwables.getMessage());
                    return false;
                }
            } //TODO add diferent things with different exceptions
            else
            {
                getPermissionsFromList();

                try
                {
                    model.addEmployee(username.get(), password.get(), name.get(), surname.get(), role.get(), permissions);
                } catch (SQLException | GeneralSecurityException | IOException throwables)
                {
                    errorLabel.set(throwables.getMessage());
                    throwables.printStackTrace();
                    return false;
                }
                //TODO Creating employees with permissions
            }
        } else //editing - viewing
        {
            try
            {
                Employee e = model.getEmployeeByID(currentEmployeeID);
                e.setName(name.get());
                e.setSurname(surname.get());
                e.setUsername(username.get());
                e.setRole(role.get());

                model.employeeSetName(currentEmployeeID, name.get());
                model.employeeSetSurname(currentEmployeeID, surname.get());
                model.employeeSetRole(currentEmployeeID, role.get());

                //todo make password change

                getPermissionsFromList();
                if (!e.getPermissions().equals(permissions))
                {
                    e.setPermissions(permissions);
                }
            } catch (SQLException | RemoteException throwables)
            {
                errorLabel.set(throwables.getMessage());
                throwables.printStackTrace();
                return false;
            }

        }
        return true;
    }

    private void getPermissionsFromList()
    {
        permissions = new ArrayList<>();
        for (PermissionViewModel pers :
                permissionTable)
        {
            if (pers.getPermissionProperty().equals("Event join"))
                permissions.add("event_join");
            if (pers.getPermissionProperty().equals("Event create"))
                permissions.add("event_create");
            if (pers.getPermissionProperty().equals("Event edit"))
                permissions.add("event_edit");
            /*if (pers.getPermissionProperty().equals("Event invite"))
                permissions.add("event_invite");*/
            if (pers.getPermissionProperty().equals("Room create/edit"))
                permissions.add("room_create_edit");
            if (pers.getPermissionProperty().equals("Manage employees"))
                permissions.add("employees_create_edit");
            if (pers.getPermissionProperty().equals("Manage chat rooms"))
                permissions.add("chat_rooms_create_edit");
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

    public ObservableList<PermissionViewModel> getPermissionTable()
    {
        return permissionTable;
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

    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
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
