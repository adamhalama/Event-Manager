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
                return;
            } catch (RemoteException e)
            {
                e.printStackTrace();
                errorLabel.setValue("Error communicating with the server");
                return;
            }


            employeeIDTextLabel.setValue("Employee ID: " + currentEmployeeID);

            username.setValue(currentEmp.getUsername());
            password.setValue("");
            repeatPassword.setValue("");
            name.setValue(currentEmp.getName());
            surname.setValue(currentEmp.getSurname());
            role.setValue(currentEmp.getRole());


            for (Integer eventID :
                    currentEmp.getEvents())
            {
                //todo make the method to show title and time
//                eventsList.add(model.getEventByID(eventID).getTitleTimeString());
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


            //todo add user readable permissions
            for (String permission :
                    getReadablePermissions(currentEmp.getPermissions()))
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
        if (!permissionTable.isEmpty())
        {
            for (PermissionViewModel perm :
                    permissionTable)
            {
                if (perm.getPermissionProperty().get().equals(permission))
                    return;
            }
        }

        permissionTable.add(new PermissionViewModel(permission));
    }

    public void removeSelectedButton(int selectedIndex)
    {
        permissionTable.remove(selectedIndex);
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
                model.employeeSetName(currentEmployeeID, name.get());
                model.employeeSetSurname(currentEmployeeID, surname.get());
                model.employeeSetRole(currentEmployeeID, role.get());
                model.employeeSetUsername(currentEmployeeID, username.get());

                if (password != null && !password.get().equals(""))
                {
                    if (!password.get().equals(repeatPassword.get()))
                    {
                        errorLabel.set("The passwords have to match");
                        return false;
                    }
                    model.employeeSetPassword(currentEmployeeID,password.get());
                }

                getPermissionsFromList();
                if (!model.getEmployeeByID(currentEmployeeID).getPermissions()
                        .equals(permissions))
                {
                    model.setPermissions(currentEmployeeID, permissions.toArray(new String[0]));
                }
            }

            catch (SQLException | RemoteException throwables)
            {
                errorLabel.set(throwables.getMessage());
                throwables.printStackTrace();
                return false;
            } catch (GeneralSecurityException | IOException e) //password specific
            {
                e.printStackTrace();
                errorLabel.set(e.getMessage());
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
            if (pers.getPermissionProperty().get().equals("Event join"))
                permissions.add("event_join");
            if (pers.getPermissionProperty().get().equals("Event create"))
                permissions.add("event_create");
            if (pers.getPermissionProperty().get().equals("Event edit"))
                permissions.add("event_edit");
            /*if (pers.getPermissionProperty().get().equals("Event invite"))
                permissions.add("event_invite");*/
            if (pers.getPermissionProperty().get().equals("Room create/edit"))
                permissions.add("room_create_edit");
            if (pers.getPermissionProperty().get().equals("Manage employees"))
                permissions.add("employees_create_edit");
            if (pers.getPermissionProperty().get().equals("Manage chat rooms"))
                permissions.add("chat_rooms_create_edit");
        }
    }

    private ArrayList<String> getReadablePermissions(ArrayList<String> sqlPermissions)
    {
        ArrayList<String> readablePermissions = new ArrayList<>();
        for (String perm:
             sqlPermissions)
        {
            if (perm.equals("event_join"))
                readablePermissions.add("Event join");

            if (perm.equals("event_create"))
                readablePermissions.add("Event create");

            if (perm.equals("event_edit"))
                readablePermissions.add("Event edit");

            if (perm.equals("room_create_edit"))
                readablePermissions.add("Room create/edit");

            if (perm.equals("employees_create_edit"))
                readablePermissions.add("Manage employees");

            if (perm.equals("chat_rooms_create_edit"))
                readablePermissions.add("Manage chat rooms");
        }

        return readablePermissions;
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
