package client.View.Employee;

import client.View.Helpers.StringIntegerConverter;
import client.View.ViewHandler;
import client.ViewModel.EmployeeViewModel;
import client.ViewModel.PermissionViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.rmi.RemoteException;
import java.sql.SQLException;


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
    @FXML
    private Label errorLabel;


    private ViewHandler viewHandler;
    private EmployeeViewModel viewModel;
    private Region root;

    private boolean editing;
    private boolean viewing;
    private int currentEmployeeID;
    private boolean openedFromMenu;


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
        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());

        //TODO make it so you can view and edit MYACcount even if you dont have the permission to edit
        //  OPTIONAL

        //TODO hide passwords if only viewing.
        // TODO disable edit  if you dont have permission
    }

    private void enableEditing()
    {
        username.setDisable(false);
        password.setDisable(false);
        repeatPassword.setDisable(false);
        name.setDisable(false);
        surname.setDisable(false);
        role.setDisable(false);
        choiceBox.setDisable(false);
        addButton.setDisable(false);
        removeButton.setDisable(false);

        confirmEditButton.setText("Confirm");
    }

    private void disableEditing()
    {
        username.setDisable(true);
        password.setDisable(true);
        repeatPassword.setDisable(true);
        name.setDisable(true);
        surname.setDisable(true);
        role.setDisable(true);
        choiceBox.setDisable(true);
        addButton.setDisable(true);
        removeButton.setDisable(true);

        confirmEditButton.setText("Edit");
    }

    public void setViewing(boolean isViewing)
    {
        this.viewing = isViewing;

        if (viewing)
            disableEditing();
        else
            enableEditing();
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
            repeatPassword.setStyle("-fx-text-fill:#02722a");
        else
            repeatPassword.setStyle("-fx-text-fill:RED");

    }

    @FXML
    private void addButton()
    {
        viewModel.addButton(choiceBox.getValue());
        //TODO FIX picture on addButton
    }

    @FXML
    private void removeSelectedButton()
    {
        viewModel.removeSelectedButton(permissionTable.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void confirmButton() throws SQLException, RemoteException {

        if (viewing)
        {
            if (openedFromMenu)
                viewHandler.openView("EditMyAccount");
            else
                viewHandler.openView("EditEmployee");
        }
        else  // creating or editing
        {
            viewModel.confirmButton();
            viewModel.reset();
            if (openedFromMenu) //confirm editing and opening myAccount once again
                viewHandler.openView("MyAccount");
            else
            viewHandler.openView("EmployeeList");
        }
    }

    @FXML
    private void backButton() throws SQLException, RemoteException {
        if (openedFromMenu)
            viewHandler.openView("MainMenu");
        else
            viewHandler.openView("EmployeeList");
    }

    public void setEditing(boolean editing)
    {
        this.editing = editing;
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

    public void setOpenedFromMenu(boolean openedFromMenu)
    {
        this.openedFromMenu = openedFromMenu;
    }
}
