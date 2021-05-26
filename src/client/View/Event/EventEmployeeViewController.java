package client.View.Event;

import client.View.ViewHandler;
import client.ViewModel.CreateEventViewModel;
import client.ViewModel.EmployeeListViewModel;
import client.ViewModel.EmployeeViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

public class EventEmployeeViewController {
    @FXML
    private TableView<EmployeeViewModel> employeeTable;
    @FXML
    private TableColumn<EmployeeViewModel, Number> IDColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> nameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> usernameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> roleColumn;

    private ViewHandler viewHandler;
    private EmployeeListViewModel viewModel;
    private Region root;

    public EventEmployeeViewController() {
    }

    public void init(ViewHandler viewHandler, EmployeeListViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        employeeTable.setItems(viewModel.getEmployeeList());

        IDColumn.setCellValueFactory
                (cellData -> cellData.getValue().getUserIDProperty());
        usernameColumn.setCellValueFactory
                (cellData -> cellData.getValue().getNameProperty());
        nameColumn.setCellValueFactory
                (cellData -> cellData.getValue().getSurnameProperty());
        roleColumn.setCellValueFactory
                (cellData -> cellData.getValue().getRoleProperty());
    }

    @FXML
    private void addPress() {
        viewModel.addToEvent(employeeTable.getSelectionModel().getSelectedItem().getUserIDProperty().get());
    }

    @FXML
    private void backPress() {
        viewHandler.openView("CreateEvent");
        //TODO 分两个模式，一个给创建一个给编辑
    }

    public Region getRoot() {
        return root;
    }
}
