package client.View.Event;

import client.View.ViewHandler;
import client.ViewModel.EmployeeViewModel;
import client.ViewModel.EventInfoViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Region;

public class EventInfoViewController
{
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label platformLabel;
    @FXML
    private TextField linkTextField;
    @FXML
    private Label roomLabel, creatorLabel, errorLabel;

    @FXML
    private TableView<EmployeeViewModel> employeeTable;
    @FXML
    private TableColumn<EmployeeViewModel, Number> idColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> nameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> surnameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> roleColumn;

    private EventInfoViewModel viewModel;
    private ViewHandler viewHandler;
    private Region root;

    public EventInfoViewController()
    {
    }

    public void init(ViewHandler viewHandler, EventInfoViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;


        titleTextField.textProperty().bind(viewModel.getTitleProperty());
        descriptionField.textProperty().bind(viewModel.getDescriptionProperty());
        startTimeLabel.textProperty().bind(viewModel.getStartTimeProperty());
        endTimeLabel.textProperty().bind(viewModel.getEndTimeProperty());
        platformLabel.textProperty().bind(viewModel.getPlatformProperty());
        linkTextField.textProperty().bind(viewModel.getLinkProperty());
        roomLabel.textProperty().bind(viewModel.getRoomProperty());
        creatorLabel.textProperty().bind(viewModel.getCreatorProperty());

        employeeTable.setItems(viewModel.getEmployeeList());

        idColumn.setCellValueFactory
                (cellData -> cellData.getValue().getUserIDProperty());
        nameColumn.setCellValueFactory
                (cellData -> cellData.getValue().getNameProperty());
        surnameColumn.setCellValueFactory
                (cellData -> cellData.getValue().getSurnameProperty());
        roleColumn.setCellValueFactory
                (cellData -> cellData.getValue().getRoleProperty());

        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());
    }


    public Region getRoot()
    {
        return root;
    }


    @FXML
    private void backPress()
    {
        viewHandler.openView("EventList");
    }

    @FXML
    private void copyPressed()
    {
        String link = viewModel.copyButton();
        if (link != null && !link.equals(""))
        {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(link);
            clipboard.setContent(content);
        }
    }
}
