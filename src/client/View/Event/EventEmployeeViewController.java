package client.View.Event;

import client.View.Helpers.SelectState;
import client.View.ViewHandler;
import client.ViewModel.EmployeeListViewModel;
import client.ViewModel.EmployeeViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import java.util.Optional;

public class EventEmployeeViewController {
    @FXML
    private TableView<EmployeeViewModel> employeeTable;
    @FXML
    private TableColumn<EmployeeViewModel, Number> IDColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> nameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> surnameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> roleColumn;

    private ViewHandler viewHandler;
    private EmployeeListViewModel viewModel;
    private Region root;
    private SelectState state;

    public EventEmployeeViewController() {
    }

    public void init(ViewHandler viewHandler, EmployeeListViewModel viewModel, Region root, SelectState selectState) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        this.state = selectState;

        employeeTable.setItems(viewModel.getEmployeeList());

        IDColumn.setCellValueFactory
                (cellData -> cellData.getValue().getUserIDProperty());
        surnameColumn.setCellValueFactory
                (cellData -> cellData.getValue().getNameProperty());
        nameColumn.setCellValueFactory
                (cellData -> cellData.getValue().getSurnameProperty());
        roleColumn.setCellValueFactory
                (cellData -> cellData.getValue().getRoleProperty());
    }

    @FXML
    private void addPress() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Are you sure to add this employee as participant?");
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            viewModel.addToEvent(employeeTable.getSelectionModel().getSelectedItem().
                    getUserIDProperty().get());
        }
    }

    @FXML
    private void backPress() {
        if (state.isAdd()) {
            viewHandler.openView("CreateEvent");
        } else viewHandler.openView("EditEvent");
    }

    public Region getRoot() {
        return root;
    }
}
