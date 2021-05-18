package client.View;

import client.ViewModel.CreateEventViewModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CreateEventViewController
{
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private DatePicker startDate;
    @FXML
    private ChoiceBox<Integer> hourMenuS;
    @FXML
    private ChoiceBox<Integer> minuteMenuS;
    @FXML
    private ChoiceBox<Integer> hourMenuE;
    @FXML
    private ChoiceBox<Integer> minuteMenuE;
    @FXML
    private Button onlineButton;
    @FXML
    private Button physicalButton;
    @FXML
    private ChoiceBox<Integer> roomMenu;
    @FXML
    private ChoiceBox<String> platformMenu;
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

        this.titleTextField.textProperty().bindBidirectional(viewModel.getTitleProperty());
        this.descriptionArea.textProperty().bindBidirectional(viewModel.getDescriptionProperty());
        this.errorLabel.textProperty().bind(viewModel.getErrorProperty());

        this.hourMenuS.setItems(FXCollections.observableArrayList(9, 10, 11, 12, 13, 14, 15, 16));
        this.minuteMenuS.setItems(FXCollections.observableArrayList(00, 15, 30, 45));
        this.hourMenuE.setItems(FXCollections.observableArrayList(9, 10, 11, 12, 13, 14, 15, 16));
        this.minuteMenuE.setItems(FXCollections.observableArrayList(00, 15, 30, 45));
        this.roomMenu.setItems(FXCollections.observableArrayList(1, 2, 3));
        this.platformMenu.setItems(FXCollections.observableArrayList("Discord", "Zoom", "Teams"));

        StringConverter sc = new StringConverter<LocalDate>() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dtf.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dtf);
                } else {
                    return null;
                }
            }
        };


    }


    @FXML private void resetPress(){
        this.physicalButton.setVisible(true);
        this.roomMenu.setVisible(true);
        this.onlineButton.setVisible(true);
        this.platformMenu.setVisible(true);
        this.linkTextField.setVisible(true);
    }

    @FXML private void onlinePress(){
        this.physicalButton.setVisible(false);
        this.roomMenu.setVisible(false);
        viewModel.setIsOnline(true);
    }

    @FXML private void physicalPress(){
        this.onlineButton.setVisible(false);
        this.platformMenu.setVisible(false);
        this.linkTextField.setVisible(false);
        viewModel.setIsOnline(false);
    }

    public Region getRoot() {
        return root;
    }
}
