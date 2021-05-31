package client.View.Event;

import Shared.Room.Room;
import client.Model.Model;
import client.View.ViewHandler;
import client.ViewModel.CreateEventViewModel;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import client.ViewModel.EmployeeViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


public class CreateEventViewController {
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private DatePicker startDate;
    @FXML
    private ChoiceBox<Integer> hourMenuStart;
    @FXML
    private ChoiceBox<Integer> minuteMenuStart;
    @FXML
    private ChoiceBox<Integer> hourMenuEnd;
    @FXML
    private ChoiceBox<Integer> minuteMenuEnd;
    @FXML
    private ChoiceBox<Integer> roomMenu;
    @FXML
    private ChoiceBox<String> platformMenu;
    @FXML
    private TextField linkTextField;
    @FXML
    private TableView<EmployeeViewModel> participantTable;
    @FXML
    private TableColumn<EmployeeViewModel, String> surnameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> nameColumn;
    @FXML
    private TableColumn<EmployeeViewModel, Number> idColumn;
    @FXML
    private TableColumn<EmployeeViewModel, String> roleColumn;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField newParticipantField;



    private int chooseStatus;
    private ObservableList<Integer> roomList;

    private ViewHandler viewHandler;
    private CreateEventViewModel viewModel;
    private Region root;
    private Model model;

    public CreateEventViewController() {
    }

    public void init(ViewHandler viewHandler, CreateEventViewModel viewModel, Region root
            , Model model) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        this.model = model;

        this.titleTextField.textProperty().bindBidirectional(viewModel.getTitleProperty());
        this.descriptionArea.textProperty().bindBidirectional(viewModel.getDescriptionProperty());
        this.errorLabel.setText("Welcome!");
        this.chooseStatus = -1;

        TextFields.bindAutoCompletion(newParticipantField, viewModel.getEmployeeList());

        this.participantTable.setItems(viewModel.update());
        surnameColumn.setCellValueFactory(cellData ->
                cellData.getValue().getSurnameProperty());
        nameColumn.setCellValueFactory(cellData ->
                cellData.getValue().getNameProperty());
        idColumn.setCellValueFactory(cellData ->
                cellData.getValue().getUserIDProperty());
        roleColumn.setCellValueFactory(cellData ->
                cellData.getValue().getRoleProperty());

        this.hourMenuStart.setItems(FXCollections.observableArrayList(9, 10, 11, 12, 13, 14, 15, 16));
//        this.minuteMenuS.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));
        this.hourMenuEnd.setItems(FXCollections.observableArrayList(9, 10, 11, 12, 13, 14, 15, 16));
//        this.minuteMenuE.setItems(FXCollections.observableArrayList("00", "15", "30", "45"));

        roomList = FXCollections.observableArrayList();

        this.roomMenu.setItems(roomList);
        this.platformMenu.setItems(FXCollections.observableArrayList("Discord", "Zoom", "Teams"));

        StringConverter sc = new StringConverter<LocalDate>() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
        model.clearTemporary();

        //adding all rooms to the choiceBox
        roomList.clear();
        try {
            for (Room r :
                    model.getRooms()) {
                roomList.add(r.getRoomID());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void resetPress() {
        this.roomMenu.setVisible(true);
        this.platformMenu.setVisible(true);
        this.linkTextField.setVisible(true);
        this.chooseStatus = -1;
    }

    @FXML
    private void onlinePress() {
        this.roomMenu.setVisible(false);
        viewModel.setIsOnline(true);
        this.chooseStatus = 0;
    }

    @FXML
    private void physicalPress() {
        this.platformMenu.setVisible(false);
        this.linkTextField.setVisible(false);
        viewModel.setIsOnline(false);
        this.chooseStatus = 1;
    }

    @FXML
    private void addEmployeePress() throws SQLException, RemoteException {
        viewHandler.openView("EventEmployee");
    }

    @FXML
    private void removeEmployeePress() {
        if (!(participantTable.getSelectionModel().getSelectedItem() == null)) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Remove this participant?");
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                viewModel.removeParticipant(
                        participantTable.getSelectionModel().getSelectedItem().getUserIDProperty().get()
                );
            }

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Please select a participant first!");
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                a.close();
            }
        }
    }

    @FXML
    private void refreshPress() {
        viewModel.clear();
        participantTable.setItems(viewModel.update());
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
            int monthS = date.getMonthValue();
            int yearS = date.getYear();

            int hourS;
            switch (hourMenuStart.getSelectionModel().getSelectedIndex()) {
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
            switch (hourMenuEnd.getSelectionModel().getSelectedIndex()) {
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
            switch (minuteMenuStart.getSelectionModel().getSelectedIndex()) {
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
            switch (minuteMenuEnd.getSelectionModel().getSelectedIndex()) {
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

            String link;
            if (linkTextField.getText() != null) {
                link = linkTextField.getText();
            } else link = "None";

//TODO there is an error
            //ArrayList<Integer> participantsID = viewModel.getIDs();
            ArrayList<Integer> participantsID = new ArrayList<>();

            String stringDate = date.getDayOfMonth() + "." + date.getMonthValue() + "." + date.getYear();

            long timestamp = parseString(stringDate);

            long startTimestamp = timestamp + (hourMenuStart.getValue().longValue() * 60 * 60 * 1000)
                    + minuteMenuStart.getValue() * 60 * 1000;

            long endTimestamp = timestamp + (hourMenuEnd.getValue().longValue() * 60 * 60 * 1000)
                    + minuteMenuEnd.getValue() * 60 * 1000;

            if (viewModel.isOnline()) {
                if (title != null || yearS != 0 && monthS != 0 && dayS != 0 && hourS != 0 && minuteS != -1 && hourE != 0 && minuteE != -1
                        && platform != null && getChooseStatus() != -1) {
                    viewModel.add(title, des, yearS, monthS, dayS, hourS, minuteS, hourE, minuteE, startTimestamp, endTimestamp,
                            platform, link, model, participantsID);
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Event added." + " Type: online room.");
                    a.setHeaderText("Event has been added.");
                    Optional<ButtonType> result = a.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        a.close();
                    }
                }
            } else {
                int room = roomMenu.getSelectionModel().getSelectedItem();
                if (title != null && yearS != 0 && monthS != 0 && dayS != 0 && hourS != 0 && minuteS != -1
                        && hourE != 0 && minuteE != -1 && room != 0 && getChooseStatus() != -1) {
                    viewModel.add(title, des, yearS, monthS, dayS, hourS, minuteS, hourE, minuteE, startTimestamp, endTimestamp,
                            room, model, participantsID);
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Event added." + " Type: physical room.");
                    a.setHeaderText("Event has been added.");
                    Optional<ButtonType> result = a.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        a.close();
                    }
                }
            }

            if (getChooseStatus() == -1) {
                throw new IllegalArgumentException("Please select a meeting type.");
            }

            reset();

        } catch (Exception e) {
            e.printStackTrace();
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
                e.printStackTrace();
            } else errorLabel.setText("Incomplete information for creating this event!");
        }
    }

    public int getChooseStatus() {
        return chooseStatus;
    }

    @FXML
    private void cancelPress() throws SQLException, RemoteException {
        viewHandler.openView("EventList");
    }

    public Region getRoot() {
        return root;
    }

    public long parseString(String dateString) //TODO maybe delete
    {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date parsedDate = dateFormat.parse(dateString);
            return parsedDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}


