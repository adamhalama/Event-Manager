package client.ViewModel;

import Shared.Employee.Employee;
import client.Model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class AddMessageRoomViewModel
{
    /*@FXML
    private CheckBox checkBox;
    @FXML
    private TextField groupChatName;
    @FXML
    private TextField newRecipientField;
    @FXML
    private Button addButton, removeButton, saveButton;
    @FXML
    private TableView<EmployeeViewModel> selectedTable;
    @FXML
    private TableColumn<EmployeeViewModel, String> usernameColumn, nameColumn, surnameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, Number> idColumn;
    @FXML
    private Label errorLabel;*/

    private StringProperty groupChatName, newRecipientField, errorLabel;
    private ObservableList<EmployeeViewModel> selectedTable;
    private BooleanProperty checkBox, addButtonDisable, removeButtonDisable, saveButtonDisable, groupChatNameDisable;

    private Model model;

    public AddMessageRoomViewModel(Model model)
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
    }

    public void reset()
    {
        if (true) //TODO add if is any message room picked
        {
            checkBox.setValue(false);
            groupChatNameDisable.setValue(true);
            groupChatName.setValue("");
            newRecipientField.setValue("");
            addButtonDisable.setValue(false);
            removeButtonDisable.setValue(true);
        }
        else
        {
            //TODO add editing
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

    public ArrayList<String> getEmployeeList()
    {
        ArrayList<Employee> employees = null;
        try
        {
            employees = model.getEmployees();
        } catch (RemoteException e)
        {
            errorLabel.set("The employee list couldn't be loaded");
        }
        ArrayList<String> returnStrings  = new ArrayList<>();

        for (Employee employee:
             employees)
        {
            returnStrings.add(employee.getName() + " " + employee.getSurname() + "(" + employee.getId() + ")");
        }
        return returnStrings;
    }
}
