package client.View.Chat;

import client.View.ViewHandler;
import client.ViewModel.AddMessageRoomViewModel;
import client.ViewModel.EmployeeViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import org.controlsfx.control.textfield.TextFields;

public class AddMessageRoomViewController
{
    @FXML
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
    private Label errorLabel;

    private ViewHandler viewHandler;
    private AddMessageRoomViewModel viewModel;
    private Region root;

    public AddMessageRoomViewController(){}

    public void init(ViewHandler viewHandler, AddMessageRoomViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        checkBox.selectedProperty().bindBidirectional(viewModel.getCheckBoxProperty());
        groupChatName.textProperty().bindBidirectional(viewModel.getGroupChatNameProperty());
        newRecipientField.textProperty().bindBidirectional(viewModel.getNewRecipientFieldProperty());
        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());

        selectedTable.setItems(viewModel.getSelectedTable());

        usernameColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getUsernameProperty());
        nameColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getNameProperty());
        surnameColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getSurnameProperty());
        idColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getUserIDProperty());

        addButton.disableProperty().bindBidirectional(viewModel.getAddButtonProperty());
        removeButton.disableProperty().bindBidirectional(viewModel.getRemoveButtonProperty());
        saveButton.disableProperty().bindBidirectional(viewModel.getSaveButtonProperty());
        groupChatName.disableProperty().bindBidirectional(viewModel.getGroupChatNameDisableProperty());

        TextFields.bindAutoCompletion(newRecipientField, viewModel.getEmployeeList());

    }

    public Region getRoot()
    {
        return root;
    }
}
