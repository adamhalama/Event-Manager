package client.ViewModel;

import Shared.Employee.Employee;
import Shared.Messages.MessageRoom;
import client.Model.Model;
import client.View.Helpers.ConfirmationButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateMessageRoomViewModel
{

    private StringProperty groupChatName, newRecipientField, errorLabel;
    private ObservableList<EmployeeViewModel> selectedTable;
    private BooleanProperty checkBox, addButtonDisable, removeButtonDisable, saveButtonDisable, groupChatNameDisable, chechBoxDisable;

    private Model model;
    private int selectedMessageRoomID;

    public CreateMessageRoomViewModel(Model model)
    {
        this.model = model;

        groupChatName = new SimpleStringProperty();
        newRecipientField = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();

        selectedTable = FXCollections.observableArrayList();
        checkBox = new SimpleBooleanProperty();
        addButtonDisable = new SimpleBooleanProperty();
        removeButtonDisable = new SimpleBooleanProperty();
        saveButtonDisable = new SimpleBooleanProperty();
        groupChatNameDisable = new SimpleBooleanProperty();
        chechBoxDisable = new SimpleBooleanProperty();
    }

    public void reset()
    {
        errorLabel.setValue("");
        selectedTable.clear();

        if (selectedMessageRoomID == 0) //creating a room
        {
            checkBox.setValue(false);
            groupChatNameDisable.setValue(true);
            groupChatName.setValue("");
            newRecipientField.setValue("");
            addButtonDisable.setValue(false);
            chechBoxDisable.setValue(false);
        } else // editing a chat room
        {

            MessageRoom messageRoom;
            try
            {
                messageRoom = model.getMessageRoomByID(selectedMessageRoomID);
            } catch (SQLException throwables)
            {
                errorLabel.set(throwables.getMessage());
                return;
            } catch (RemoteException throwables)
            {
                errorLabel.set("Error connecting to the server");
                throwables.printStackTrace();
                return;
            }

            newRecipientField.setValue("");
            chechBoxDisable.setValue(true);

            if (messageRoom.isPrivate()) // private chat
            {
                groupChatName.setValue("");
                checkBox.set(false);
                groupChatNameDisable.set(true);
                addButtonDisable.setValue(true);
                errorLabel.setValue("You cant edit a private room");
            } else // not private - group chat
            {
                groupChatName.setValue(messageRoom.getName());
                checkBox.set(true);
                groupChatNameDisable.set(false);
                addButtonDisable.setValue(false);

                for (int id :
                        messageRoom.getUsersIDs())
                {
                    try
                    {
                        Employee employee = model.getEmployeeByID(id);
                        selectedTable.add(new EmployeeViewModel(id, employee.getName(), employee.getSurname(), employee.getRole()));
                    } catch (SQLException | RemoteException throwables)
                    {
                        throwables.printStackTrace();
                        errorLabel.setValue("Failed to load employees");
                    }
                }


            }

        }
    }


    public ArrayList<String> getEmployeeList()
    {
        ArrayList<Employee> employees = null;
        try
        {
            employees = model.getEmployees();
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
        ArrayList<String> returnStrings = new ArrayList<>();

        for (Employee employee :
                employees)
        {
            returnStrings.add(employee.getName() + " " + employee.getSurname() + "(id " + employee.getId() + ")");
        }
        return returnStrings;
    }

    public void addButton()
    {
        String employeeID = newRecipientField.getValue().contains("(") ? newRecipientField.getValue().split("[()]")[1] : "no id";
        String id = employeeID.equals("no id") ? employeeID : employeeID.split(" ")[1];
        //getting just the id number from the whole string

        if (id.equals("no id"))
        {
            errorLabel.setValue("Pick an employee from the list");
            return;
        }

        Employee employee = null;
        try
        {
            employee = model.getEmployeeByID(Integer.parseInt(id));
        } catch (SQLException | RemoteException throwables)
        {
            throwables.printStackTrace();
            errorLabel.setValue("Failed to add an employee");
            return;
        }

        for (int i = 0; i < selectedTable.size(); i++) // check if the employee is already in the list
        {
            if (selectedTable.get(i).getUserIDProperty().get() == Integer.parseInt(id))
            {
                errorLabel.setValue("The employee is already added");
                return;
            }
        }



        if (checkBox.get()) // groupChat - true
        {
            selectedTable.add(new EmployeeViewModel(Integer.parseInt(id), employee.getName(), employee.getSurname(), employee.getRole()));
            addButtonDisable.set(false);
            newRecipientField.set("");
        } else // groupChat - false - PRIVATE room
        {
            if ((selectedTable.size() < 1))
            {
                selectedTable.add(new EmployeeViewModel(Integer.parseInt(id), employee.getName(), employee.getSurname(), employee.getRole()));
                addButtonDisable.set(true);
                newRecipientField.set("");
            }
        }

    }

    public void removeButton(int selectedIndex)
    {
        selectedTable.remove(selectedIndex);
        addButtonDisable.set(false);
    }

    public int saveButton()
    {
        if (checkBox.get()) // groupChat true
        {
            if (groupChatName == null || groupChatName.get().equals(""))
            {
                errorLabel.set("The name must not be empty");
                return -1;
            }
            try
            {
                int[] emps = new int[selectedTable.size()];
                for (int i = 0, selectedTableSize = selectedTable.size(); i < selectedTableSize; i++)
                {
                    emps[i] = selectedTable.get(i).getUserIDProperty().get();
                }


                MessageRoom room = model.messageRoomCreateGroup(emps, groupChatName.get());
                return room.getId();
            }
            catch (IndexOutOfBoundsException e)
            {
                errorLabel.setValue("Pick an employee");
                return -1;
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
                errorLabel.setValue(throwables.getMessage());
                return -1;
            } catch (RemoteException throwables)
            {
                throwables.printStackTrace();
                errorLabel.setValue("Failed to save");
                return -1;
            }
        } else
        {
            try
            {
                MessageRoom room = model.messageRoomCreatePrivate(selectedTable.get(0).getUserIDProperty().get());
                return room.getId();
            }
            catch (IndexOutOfBoundsException e)
            {
                errorLabel.setValue("Pick an employee");
                return -1;
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
                errorLabel.setValue(throwables.getMessage());
                return -1;
            } catch (RemoteException throwables)
            {
                throwables.printStackTrace();
                errorLabel.setValue("Failed to save");
                return -1;
            }
        }
    }

    public void setMessageRoomID(int i)
    {
        this.selectedMessageRoomID = i;
    }

    public boolean backButton()
    {
        return ConfirmationButton.confirmationView("Leaving now will not save the changes \ndo you want to exit?");
    }

    public void checkBoxChange()
    {
        if (checkBox.get()) // groupChat true
        {
            groupChatNameDisable.set(false);
            addButtonDisable.set(false);
        }
        else if (selectedTable.size() > 1) //if false
        {
            errorLabel.setValue("In order to change to a private chat, the list has to have just one person in it");
            checkBox.set(true);
        }
        else //if false
        {
            groupChatNameDisable.set(true);
            groupChatName.setValue("");
        }
    }


    public ObservableList<EmployeeViewModel> getSelectedTable()
    {
        return selectedTable;
    }

    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }

    public StringProperty getGroupChatNameProperty()
    {
        return groupChatName;
    }

    public StringProperty getNewRecipientFieldProperty()
    {
        return newRecipientField;
    }

    public BooleanProperty getCheckBoxProperty()
    {
        return checkBox;
    }

    public BooleanProperty getAddButtonProperty()
    {
        return addButtonDisable;
    }

    public BooleanProperty getRemoveButtonProperty()
    {
        return removeButtonDisable;
    }

    public BooleanProperty getSaveButtonProperty()
    {
        return saveButtonDisable;
    }

    public BooleanProperty getGroupChatNameDisableProperty()
    {
        return groupChatNameDisable;
    }

    public BooleanProperty getCheckBoxDisableProperty()
    {
        return chechBoxDisable;
    }
}