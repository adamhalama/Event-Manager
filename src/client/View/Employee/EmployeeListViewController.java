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

public class EmployeeListViewController
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
    private TableColumn<EmployeeViewModel, String> surnameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> roleColumn;

    @FXML
    private Label errorLabel;

    private ViewHandler viewHandler;
    private EmployeeListViewModel viewModel;
    private Region root;

    public EmployeeListViewController(){}

    public void init(ViewHandler viewHandler, EmployeeListViewModel viewModel, Region root)
    {
        StringIntegerConverter converter = new StringIntegerConverter(0);

        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        searchBox.textProperty().bindBidirectional(viewModel.getSearchBoxProperty());
        employeeTable.setItems(viewModel.getEmployeeList());

        userIDColumn.setCellValueFactory
                (cellData -> cellData.getValue().getUserIDProperty());
        nameColumn.setCellValueFactory
                (cellData -> cellData.getValue().getNameProperty());
        surnameColumn.setCellValueFactory
                (cellData -> cellData.getValue().getSurnameProperty());
        roleColumn.setCellValueFactory
                (cellData -> cellData.getValue().getRoleProperty());

        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());
    }

    private void setErrorLabel(String text)
    {
        viewModel.setErrorLabel(text);
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
        try
        {
            viewHandler.setPickedEmployeeID(employeeTable.getSelectionModel().getSelectedItem().getUserIDProperty().get());
            viewHandler.openView("Employee");
        } catch (Exception e)
        {
            this.setErrorLabel("Select an employee to open first");
        }
    }

    @FXML
    private void editButton()
    {
        try
        {
            viewHandler.setPickedEmployeeID(employeeTable.getSelectionModel().getSelectedItem().getUserIDProperty().get());
            viewHandler.openView("EditEmployee");
        } catch (Exception e)
        {
            this.setErrorLabel("Select an employee to edit first");
        }
    }

    @FXML
    private void removeButton()
    {
        try
        {
            viewModel.removeEmployee(employeeTable.getSelectionModel().getSelectedIndex(), employeeTable.getSelectionModel().getSelectedItem().getCurrentEmployeeID());
        } catch (Exception e)
        {
            this.setErrorLabel("Select an employee to remove first");
        }
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
