package client.View.Chat;

import client.View.ViewHandler;
import client.ViewModel.CreateMessageRoomViewModel;
import client.ViewModel.EmployeeViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import org.controlsfx.control.textfield.TextFields;

public class CreateMessageRoomViewController
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
    private TableColumn<EmployeeViewModel, String> nameColumn, surnameColumn, roleColumn;
    @FXML
    private TableColumn<EmployeeViewModel, Number> idColumn;
    @FXML
    private Label errorLabel;

    private ViewHandler viewHandler;
    private CreateMessageRoomViewModel viewModel;
    private Region root;

    public CreateMessageRoomViewController(){}

    public void init(ViewHandler viewHandler, CreateMessageRoomViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        checkBox.selectedProperty().bindBidirectional(viewModel.getCheckBoxProperty());
        groupChatName.textProperty().bindBidirectional(viewModel.getGroupChatNameProperty());
        newRecipientField.textProperty().bindBidirectional(viewModel.getNewRecipientFieldProperty());
        TextFields.bindAutoCompletion(newRecipientField, viewModel.getEmployeeList());
        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());

        selectedTable.setItems(viewModel.getSelectedTable());


        nameColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getNameProperty());
        surnameColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getSurnameProperty());
        roleColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getRoleProperty());
        idColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getUserIDProperty());

        addButton.disableProperty().bindBidirectional(viewModel.getAddButtonProperty());
        removeButton.disableProperty().bindBidirectional(viewModel.getRemoveButtonProperty());
        saveButton.disableProperty().bindBidirectional(viewModel.getSaveButtonProperty());
        groupChatName.disableProperty().bindBidirectional(viewModel.getGroupChatNameDisableProperty());



    }

    public void reset()
    {
        viewModel.reset();
    }

    @FXML
    private void checkBox()
    {
        viewModel.checkBoxChange();
    }

    @FXML
    private void backButton()
    {
        if (viewModel.backButton())
            viewHandler.openView("MessageRoomList");
    }

    @FXML
    private void addButton()
    {
        viewModel.addButton();
    }

    @FXML
    private void removeButton()
    {
        viewModel.removeButton();
    }

    @FXML
    private void saveButton()
    {
        viewModel.saveButton();
    }

    public Region getRoot()
    {
        return root;
    }
}
