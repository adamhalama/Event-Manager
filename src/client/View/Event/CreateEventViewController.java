package client.View.Event;

import Shared.Event.Event;
import client.View.ViewHandler;
import client.ViewModel.CreateEventViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.Optional;


public class CreateEventViewController {
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private DatePicker startDate;
    @FXML
    private ChoiceBox<Integer> hourMenuS;
    @FXML
    private ChoiceBox<String> minuteMenuS;
    @FXML
    private ChoiceBox<Integer> hourMenuE;
    @FXML
    private ChoiceBox<String> minuteMenuE;
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
    private int chooseStatus;

    private ViewHandler viewHandler;
    private CreateEventViewModel viewModel;
    private Region root;

    public CreateEventViewController() {
    }

    public void init(ViewHandler viewHandler, CreateEventViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        this.titleTextField.textProperty().bindBidirectional(viewModel.getTitleProperty());
        this.descriptionArea.textProperty().bindBidirectional(viewModel.getDescriptionProperty());
        this.errorLabel.setText("Welcome!");
        this.chooseStatus = -1;

        this.hourMenuS.setItems(FXCollections.observableArrayList(9, 10, 11, 12, 13, 14, 15, 16));
        this.minuteMenuS.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
        this.hourMenuE.setItems(FXCollections.observableArrayList(9, 10, 11, 12, 13, 14, 15, 16));
        this.minuteMenuE.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
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
        startDate.setConverter(sc);

        //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(DatePicker datePicker) {
                        return new DateCell() {
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(LocalDate.now())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #FFD8ADAD");
                                }
                            }
                        };
                    }
                };
        startDate.setDayCellFactory(dayCellFactory);
        //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑this setting is for disable the date before today
        //you cannot set the event before today
    }

    public void reset() {
        titleTextField.setText(null);
        descriptionArea.setText(null);
        startDate.setValue(null);
        linkTextField.setText(null);
        resetPress();
    }

    @FXML
    private void resetPress() {
        this.physicalButton.setVisible(true);
        this.roomMenu.setVisible(true);
        this.onlineButton.setVisible(true);
        this.platformMenu.setVisible(true);
        this.linkTextField.setVisible(true);
        this.chooseStatus = -1;
    }

    @FXML
    private void onlinePress() {
        this.physicalButton.setVisible(false);
        this.roomMenu.setVisible(false);
        viewModel.setIsOnline(true);
        this.chooseStatus = 0;
    }

    @FXML
    private void physicalPress() {
        this.onlineButton.setVisible(false);
        this.platformMenu.setVisible(false);
        this.linkTextField.setVisible(false);
        viewModel.setIsOnline(false);
        this.chooseStatus = 1;
    }

    @FXML
    private void createPress() {
        try {
            String title = titleTextField.getText();
            String des;
            if (descriptionArea.getText() != null) {
                des = descriptionArea.getText();
            } else des = "None";

            LocalDate date = this.startDate.getValue();
            int dayS = date.getDayOfMonth();
            int dayE = dayS;
            int monthS = date.getMonthValue();
            int monthE = monthS;
            int yearS = date.getYear();
            int yearE = yearS;

            int hourS;
            switch (hourMenuS.getSelectionModel().getSelectedIndex()) {
                case 0:
                    hourS = 9;
                    break;
                case 1:
                    hourS = 10;
                    break;
                case 2:
                    hourS = 11;
                    break;
                case 3:
                    hourS = 12;
                    break;
                case 4:
                    hourS = 13;
                    break;
                case 5:
                    hourS = 14;
                    break;
                case 6:
                    hourS = 15;
                    break;
                case 7:
                    hourS = 16;
                    break;
                default:
                    hourS = 0;
            }
            int hourE;
            switch (hourMenuE.getSelectionModel().getSelectedIndex()) {
                case 0:
                    hourE = 9;
                    break;
                case 1:
                    hourE = 10;
                    break;
                case 2:
                    hourE = 11;
                    break;
                case 3:
                    hourE = 12;
                    break;
                case 4:
                    hourE = 13;
                    break;
                case 5:
                    hourE = 14;
                    break;
                case 6:
                    hourE = 15;
                    break;
                case 7:
                    hourE = 16;
                    break;
                default:
                    hourE = 0;
            }
            int minuteS;
            switch (minuteMenuS.getSelectionModel().getSelectedIndex()) {
                case 0:
                    minuteS = 00;
                    break;
                case 1:
                    minuteS = 15;
                    break;
                case 2:
                    minuteS = 30;
                    break;
                case 3:
                    minuteS = 45;
                    break;
                default:
                    minuteS = -1;
            }
            int minuteE;
            switch (minuteMenuE.getSelectionModel().getSelectedIndex()) {
                case 0:
                    minuteE = 00;
                    break;
                case 1:
                    minuteE = 15;
                    break;
                case 2:
                    minuteE = 30;
                    break;
                case 3:
                    minuteE = 45;
                    break;
                default:
                    minuteE = -1;
            }

            String platform;
            switch (platformMenu.getSelectionModel().getSelectedIndex()) {
                case 0:
                    platform = "Discord";
                    break;
                case 1:
                    platform = "Zoom";
                    break;
                case 2:
                    platform = "Teams";
                    break;
                default:
                    platform = null;
            }

            int room;
            switch (roomMenu.getSelectionModel().getSelectedIndex()) {
                case 0:
                    room = 1;
                    break;
                case 1:
                    room = 2;
                    break;
                case 2:
                    room = 3;
                    break;
                default:
                    room = 0;
            }

            String link;
            if (linkTextField.getText() != null) {
                link = linkTextField.getText();
            } else link = "None";

            /*if (viewModel.isOnline()) {
                if (title != null || yearS != 0 && monthS != 0 && dayS != 0 && hourS != 0 && minuteS != -1 && hourE != 0 && minuteE != -1
                        && platform != null && getChooseStatus() != -1) {
                    Event e = new Event(title, des, yearS, monthS, dayS, hourS, minuteS, hourE, minuteE,
                            true, platform, link);
                    viewModel.addEvent(e);
                    viewModel.setIdProperty(e.getEvent_id());
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Event added." + " Type: online room.");
                    a.setHeaderText("Event has been added.");
                    a.setContentText(e.toString());
                    Optional<ButtonType> result = a.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        a.close();
                    }
                }
            } else {
                if (title != null && yearS != 0 && monthS != 0 && dayS != 0 && hourS != 0 && minuteS != -1
                        && hourE != 0 && minuteE != -1 && room != 0 && getChooseStatus() != -1) {
                    Event e = new Event(title, des, yearS, monthS, dayS, hourS, minuteS, hourE, minuteE,
                            false, room);
                    viewModel.addEvent(e);
                    viewModel.setIdProperty(e.getEvent_id());
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Event added." + " Type: physical room.");
                    a.setHeaderText("Event has been added.");
                    a.setContentText(e.toString());
                    Optional<ButtonType> result = a.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        a.close();
                    }
                }
            }*/

            if (getChooseStatus() == -1) {
                throw new IllegalArgumentException("Please select a meeting type.");
            }

            reset();

        } catch (Exception e) {
            viewModel.setErrorProperty(e.getMessage());
            if (e.getMessage() != null) {
                Alert b = new Alert(Alert.AlertType.CONFIRMATION);
                b.setTitle("Error");
                b.setHeaderText(e.getMessage());
                Optional<ButtonType> result = b.showAndWait();
                if (result.get() == ButtonType.OK) {
                    b.close();
                }
                errorLabel.setText(e.getMessage());
            } else errorLabel.setText("You are missing something!");
//            error :  Label.text : A bound value cannot be set.
        }
    }

    public int getChooseStatus() {
        return chooseStatus;
    }

    @FXML
    private void cancelPress() {
        viewHandler.openView("EventList");
    }

    public Region getRoot() {
        return root;
    }
}
