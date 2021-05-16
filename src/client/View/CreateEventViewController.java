package client.View;

import client.ViewModel.CreateEventViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;


public class CreateEventViewController
{
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private DatePicker startDate;
    @FXML
    private SplitMenuButton hourMenuS;
    @FXML
    private SplitMenuButton minuteMenuS;
    @FXML
    private DatePicker endDate;
    @FXML
    private SplitMenuButton hourMenuE;
    @FXML
    private SplitMenuButton minuteMenuE;
    @FXML
    private Button onlineButton;
    @FXML
    private Button physicalButton;
    @FXML
    private SplitMenuButton roomMenu;
    @FXML
    private SplitMenuButton platformMenu;
    @FXML
    private TextField linkTextField;
    @FXML
    private Label errorLabel;
    private ViewHandler viewHandler;
    private CreateEventViewModel viewModel;
    private Region root;

    public CreateEventViewController()
    {
    }

    public void init(ViewHandler viewHandler, CreateEventViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

//        this.titleTextField.textProperty().bindBidirectional();
    }
}
