package client.View.Employee;

import client.View.StringIntegerConverter;
import client.View.ViewHandler;
import client.ViewModel.EmployeeViewModel;
import client.ViewModel.PermissionViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;


public class EmployeeViewController
{
    @FXML
    private Label topLabel, employeeIDTextLabel;
    @FXML
    private Label passwordLabel, repeatPasswordLabel;
    @FXML
    private TextField username, password, repeatPassword, name, surname, role;
    @FXML
    private ListView<String> eventsList, messageRoomList;

    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Button addButton, removeButton;
    @FXML
    private TableView<PermissionViewModel> permissionTable;
    @FXML
    private TableColumn<PermissionViewModel, String> permissionColumn;

    @FXML
    private Button confirmEditButton;


    private ViewHandler viewHandler;
    private EmployeeViewModel viewModel;
    private Region root;

    private boolean editing;
    private boolean viewing;
    private int currentEmployeeID;


    public void init(ViewHandler viewHandler, EmployeeViewModel viewModel, Region root)
    {
        StringIntegerConverter converter = new StringIntegerConverter(0);

        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        name.textProperty().bindBidirectional(this.viewModel.getNameProperty());
        surname.textProperty().bindBidirectional(this.viewModel.getSurnameProperty());
        role.textProperty().bindBidirectional(this.viewModel.getRoleProperty());

        username.textProperty().bindBidirectional(this.viewModel.getUsernameProperty());
        password.textProperty().bindBidirectional(this.viewModel.getPasswordProperty());
        repeatPassword.textProperty().bindBidirectional(this.viewModel.getRepeatPasswordProperty());

        topLabel.textProperty().bindBidirectional(this.viewModel.getTopLabelProperty());
        employeeIDTextLabel.textProperty().bindBidirectional(this.viewModel.getEmployeeIDTextLabelProperty());
        passwordLabel.textProperty().bindBidirectional(this.viewModel.getPasswordLabelProperty());
        repeatPasswordLabel.textProperty().bindBidirectional(this.viewModel.getRepeatPasswordLabelProperty());

        eventsList.setItems(this.viewModel.getEventsList());
        messageRoomList.setItems(this.viewModel.getMessageRoomList());

        choiceBox.setItems(FXCollections.observableArrayList("Event join", "Event create",
                "Event edit", "Event invite",
                "Room create/edit", "Manage employees",
                "Manage chat rooms"));

        addButton.textProperty().bindBidirectional(viewModel.getAddButtonProperty());
        removeButton.textProperty().bindBidirectional(viewModel.getRemoveButtonProperty());

        permissionTable.setItems(viewModel.getPermissionTable());
        permissionColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getPermissionProperty());

        confirmEditButton.textProperty().bindBidirectional(viewModel.getConfirmEditButtonProperty());
    }


    @FXML
    private void passwordTyped()
    {
        viewModel.passwordTyped();
    }

    @FXML
    private void repeatPasswordTyped()
    {
        // cant call a viewModel func, that would be overcomplicated
        if (password.textProperty().get().equals(repeatPassword.textProperty().get()))
            repeatPassword.setStyle("-fx-text-fill:GREEN");
        else
            repeatPassword.setStyle("-fx-text-fill:RED");

    }

    @FXML
    private void addButton()
    {
        viewModel.addButton(choiceBox.getValue());
    }

    @FXML
    private void removeSelectedButton()
    {
        viewModel.removeSelectedButton(permissionTable.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void confirmButton()
    {
        if (viewing)
            viewHandler.openView("EditEmployee");
        else
        {
            viewModel.confirmButton();
            viewHandler.openView("EmployeeList");
        }
    }

    @FXML
    private void backButton()
    {
        viewHandler.openView("EmployeeList");
    }

    public void setEditing(boolean editing)
    {
        this.editing = editing;
    }

    public void setViewing(boolean viewing)
    {
        this.viewing = viewing;
    }

    public void setRepeatPasswordColor(boolean isRed)
    {

    }


    public Region getRoot()
    {
        return root;
    }

    public Label getEmployeeIDTextLabel()
    {
        return employeeIDTextLabel;
    }

    public void setCurrentEmployeeID(int currentEmployeeID)
    {
        this.currentEmployeeID = currentEmployeeID;
    }
}
