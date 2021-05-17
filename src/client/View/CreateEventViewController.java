package client.View;

import client.ViewModel.CreateEventViewModel;
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
    private MenuButton hourMenuS;
    @FXML
    private MenuButton minuteMenuS;
    @FXML
    private MenuButton hourMenuE;
    @FXML
    private MenuButton minuteMenuE;
    @FXML
    private Button onlineButton;
    @FXML
    private Button physicalButton;
    @FXML
    private MenuButton roomMenu;
    @FXML
    private MenuButton platformMenu;
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

        this.hourMenuS = new MenuButton();

        this.minuteMenuS = new MenuButton();
        MenuItem item9S = new MenuItem("00");
        MenuItem item10S = new MenuItem("15");
        MenuItem item11S = new MenuItem("30");
        MenuItem item12S = new MenuItem("45");
        minuteMenuS.getItems().addAll(item9S, item10S, item11S, item12S);

        this.hourMenuE = new MenuButton();
        MenuItem item1E = new MenuItem("9");
        MenuItem item2E = new MenuItem("10");
        MenuItem item3E = new MenuItem("11");
        MenuItem item4E = new MenuItem("12");
        MenuItem item5E = new MenuItem("13");
        MenuItem item6E = new MenuItem("14");
        MenuItem item7E = new MenuItem("15");
        MenuItem item8E = new MenuItem("16");
        hourMenuE.getItems().addAll(item1E, item2E, item3E, item4E, item5E, item6E, item7E, item8E);
        this.minuteMenuE = new MenuButton();
        MenuItem item9E = new MenuItem("00");
        MenuItem item10E = new MenuItem("15");
        MenuItem item11E = new MenuItem("30");
        MenuItem item12E = new MenuItem("45");
        minuteMenuE.getItems().addAll(item9E, item10E, item11E, item12E);

        this.platformMenu = new MenuButton();
        MenuItem item1P = new MenuItem("Discord");
        MenuItem item2P = new MenuItem("Zoom");
        MenuItem item3P = new MenuItem("Teams");
        platformMenu.getItems().addAll(item1P, item2P, item3P);

        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                hourMenuS.setText(((MenuItem) e.getSource()).getText());
                hourMenuE.setText(((MenuItem) e.getSource()).getText());
                minuteMenuS.setText(((MenuItem) e.getSource()).getText());
                minuteMenuE.setText(((MenuItem) e.getSource()).getText());
                roomMenu.setText(((MenuItem) e.getSource()).getText());
                platformMenu.setText(((MenuItem) e.getSource()).getText());
            }
        };

        item9S.setOnAction(event1);
        item10S.setOnAction(event1);
        item11S.setOnAction(event1);
        item12S.setOnAction(event1);
        item1E.setOnAction(event1);
        item2E.setOnAction(event1);
        item3E.setOnAction(event1);
        item4E.setOnAction(event1);
        item5E.setOnAction(event1);
        item6E.setOnAction(event1);
        item7E.setOnAction(event1);
        item8E.setOnAction(event1);
        item9E.setOnAction(event1);
        item10E.setOnAction(event1);
        item11E.setOnAction(event1);
        item12E.setOnAction(event1);
        item1P.setOnAction(event1);
        item2P.setOnAction(event1);
        item3P.setOnAction(event1);

        this.startDate = new DatePicker();
        startDate.setShowWeekNumbers(true);
        startDate.setValue(LocalDate.now());

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

    @FXML private void hourSSelect() {
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                hourMenuS.setText(((MenuItem) e.getSource()).getText());
                //TODO need to fix
            }
        };
        MenuItem item1S = new MenuItem("9");
        MenuItem item2S = new MenuItem("10");
        MenuItem item3S = new MenuItem("11");
        MenuItem item4S = new MenuItem("12");
        MenuItem item5S = new MenuItem("13");
        MenuItem item6S = new MenuItem("14");
        MenuItem item7S = new MenuItem("15");
        MenuItem item8S = new MenuItem("16");
        hourMenuS.getItems().addAll(item1S, item2S, item3S, item4S, item5S, item6S, item7S, item8S);
        item1S.setOnAction(event1);
        item2S.setOnAction(event1);
        item3S.setOnAction(event1);
        item4S.setOnAction(event1);
        item5S.setOnAction(event1);
        item6S.setOnAction(event1);
        item7S.setOnAction(event1);
        item8S.setOnAction(event1);
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
