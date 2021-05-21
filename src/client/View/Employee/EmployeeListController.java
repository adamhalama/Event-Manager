package client.View.Employee;

import client.View.StringIntegerConverter;
import client.View.ViewHandler;
import client.ViewModel.EmployeeListViewModel;
import client.ViewModel.EmployeeViewModel;
import client.ViewModel.RoomListViewModel;
import client.ViewModel.RoomViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class EmployeeListController
{
    @FXML
    private TextField searchBox;
    @FXML
    private TableView<EmployeeViewModel> employeeTable;
    @FXML
    private TableColumn<EmployeeViewModel, Number> userIDColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> nameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, Number> surnameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, Number> roleColumn;

    @FXML
    private Label errorLabel;

    private ViewHandler viewHandler;
    private EmployeeListViewModel viewModel;
    private Region root;

    public EmployeeListController(){}

    public void init(ViewHandler viewHandler, EmployeeListViewModel viewModel, Region root)
    {
        StringIntegerConverter converter = new StringIntegerConverter(0);

        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    @FXML
    private void searchBoxKeyTyped()
    {

        viewModel.search();
    }

    @FXML
    private void searchButton()
    {
        viewModel.search();
    }

    @FXML
    private void openButton()
    {
        viewHandler.setPickedEmployeeID(employeeTable.getSelectionModel().getSelectedItem().getUserIDProperty().get());
        viewHandler.openView("Employee");
    }

    @FXML
    private void editButton()
    {
        viewHandler.setPickedEmployeeID(employeeTable.getSelectionModel().getSelectedItem().getUserIDProperty().get());
        viewHandler.openView("EditEmployee");
    }

    @FXML
    private void removeButton()
    {

    }

    @FXML
    private void backButton()
    {
        viewHandler.openView("MainMenu");
    }

    @FXML
    private void addButton()
    {
        viewHandler.openView("CreateEmployee");
    }


    public Region getRoot()
    {
        return root;
    }

}
