package client.View.Event;

import client.View.SelectState;
import client.View.ViewHandler;
import client.ViewModel.CreateEventViewModel;
import client.ViewModel.EditEventViewModel;
import client.ViewModel.EventListViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditEventViewController {
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
    private int id;

    private ViewHandler viewHandler;
    private EditEventViewModel viewModel;
    private SelectState selectState;
    private Region root;

    public EditEventViewController(){}

    public void init(ViewHandler viewHandler, EditEventViewModel viewModel, Region root,
                     SelectState state){
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        this.selectState = state;
        this.chooseStatus = -1;
        this.id = state.getEditSelect();

        this.titleTextField.textProperty().bindBidirectional(viewModel.getTitleProperty());
        this.descriptionArea.textProperty().bindBidirectional(viewModel.getDescriptionProperty());
        this.errorLabel.setText("Welcome!");

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
        startDate.setValue(viewModel.getDate());
        titleTextField.setText(viewModel.getTitle(id));
//        System.out.println(viewModel.getTitleProperty().get());
//        if (!viewModel.getDescriptionProperty().get().equals("None")){
//            descriptionArea.setText(viewModel.getDescriptionProperty().get());
//        }
//        if (!viewModel.getLinkProperty().get().equals("None")){
//            linkTextField.setText(viewModel.getLinkProperty().get());
//        }
//        if (viewModel.isOnline()) {
//            onlinePress();
//        } else physicalPress();
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
    private void cancelPress(){
        viewHandler.openView("EventList");
    }

    @FXML
    private void editPress(){


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
    }

    public Region getRoot() {
        return root;
    }
}
