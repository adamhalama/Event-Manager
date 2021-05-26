package client.View.Event;

import client.Model.Model;
import client.View.SelectState;
import client.View.ViewHandler;
import client.ViewModel.CreateEventViewModel;
import client.ViewModel.EditEventViewModel;
import client.ViewModel.EmployeeViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

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
    private Button resetButton;
    @FXML
    private ChoiceBox<Integer> roomMenu;
    @FXML
    private ChoiceBox<String> platformMenu;
    @FXML
    private TextField linkTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label buttonLabel;
    @FXML
    private Label roomInfoLabel;
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

    private int chooseStatus;
    private int id;

    private ViewHandler viewHandler;
    private EditEventViewModel viewModel;
    private SelectState selectState;
    private Region root;
    private Model model;

    public EditEventViewController() {
    }

    public void init(ViewHandler viewHandler, EditEventViewModel viewModel, Region root,
                     SelectState state, Model model) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        this.selectState = state;
        this.chooseStatus = -1;
        this.model = model;
        this.id = state.getEditSelect();

        this.titleTextField.textProperty().bindBidirectional(viewModel.getTitleProperty());
        this.descriptionArea.textProperty().bindBidirectional(viewModel.getDescriptionProperty());
        this.errorLabel.setText("Welcome!");

//        this.participantTable.setItems(viewModel.getEmployeeList());
//        surnameColumn.setCellValueFactory(cellData ->
//                cellData.getValue().getSurnameProperty());
//        nameColumn.setCellValueFactory(cellData ->
//                cellData.getValue().getNameProperty());
//        idColumn.setCellValueFactory(cellData ->
//                cellData.getValue().getUserIDProperty());
//        roleColumn.setCellValueFactory(cellData ->
//                cellData.getValue().getRoleProperty());

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
        if (!viewModel.getDes(id).equals("None")) {
            descriptionArea.setText(viewModel.getDes(id));
        }
        if (!viewModel.getLink(id).equals("None")) {
            linkTextField.setText(viewModel.getLink(id));
        }
        if (viewModel.isOnline()) {
            onlinePress();
        } else physicalPress();

        this.physicalButton.setVisible(false);
        this.onlineButton.setVisible(false);
        this.resetButton.setVisible(false);
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
    private void cancelPress() {
        viewHandler.openView("EventList");
    }

    @FXML
    private void editPress() {
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
                platform = "null";
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

        viewModel.setTitle(titleTextField.getText(), id);
        viewModel.setDes(descriptionArea.getText(), id);
        if (!isDateEqual(yearS, monthS, dayS, id)) {
            viewModel.setDate(yearS, monthS, dayS, id);
        }

        ArrayList<Integer> participantsIDs = new ArrayList<>();

        for (EmployeeViewModel employee:
                participantTable.getItems())
        {
            participantsIDs.add(employee.getUserIDProperty().get());
        }

        try {
            if (hourS != 0 && minuteS != -1 && hourE != 0 && minuteE != -1) {
                viewModel.setTime(hourS, minuteS, hourE, minuteE, id);
            } else if (hourS != 0 && minuteS != -1 && hourE == 0 && minuteE == -1) {
                viewModel.setStartHour(hourS, minuteS, id);
            } else if (hourS == 0 && minuteS == -1 && hourE != 0 && minuteE != -1) {
                viewModel.setEndHour(hourE, minuteE, id);
            } else if (hourS == 0 && minuteS == -1 && hourE == 0 && minuteE == -1) {
                // nothing need to change
            } else throw new IllegalArgumentException("Invalid selection!");
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }

        if (!platform.equals("null")) {
            viewModel.setPlatform(platform, id);
        }

        if (room != 0) {
            viewModel.setRoom(room, id);
        }

        selectState.setAdd(true);

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("edit");
        a.setHeaderText("Edit success!");
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            a.close();
        }
    }

    @FXML
    private void startTimeInfo() {
        final Tooltip t1 = new Tooltip();
        t1.setText(
                "The original start time is " + viewModel.getStartTime(id) + '\n' +
                        "Please leave it empty if you don't want to change the time and date."
        );
        t1.setFont(new Font("Arial", 14));
        Tooltip.install(startTimeLabel, t1);
    }

    @FXML
    private void endTimeInfo() {
        final Tooltip t2 = new Tooltip();
        t2.setText(
                "The original end time is " + viewModel.getEndTime(id) + '\n' +
                        "Please leave it empty if you don't want to change the time and date."
        );
        t2.setFont(new Font("Arial", 14));
        Tooltip.install(endTimeLabel, t2);
    }

    @FXML
    private void buttonInfo() {
        final Tooltip t3 = new Tooltip();
        t3.setText(
                "You cannot reset the meeting type here."
        );
        t3.setFont(new Font("Arial", 14));
        Tooltip.install(buttonLabel, t3);
    }

    @FXML
    private void roomInfo() {
        final Tooltip t4 = new Tooltip();
        t4.setText(
                "The original room is " + viewModel.getRoom(id) + '\n' +
                        "Please leave it empty if you don't want to change the room."
        );
        t4.setFont(new Font("Arial", 14));
        Tooltip.install(roomInfoLabel, t4);
    }

    @FXML private void addEmployeePress(){
        selectState.setAdd(false);
        viewHandler.openView("EventEmployee");
    }
    @FXML private void removeEmployeePress(){
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
    @FXML private void refreshPress(){
    }

    public Region getRoot() {
        return root;
    }

    private boolean isDateEqual(int y, int m, int d, int id) {
        return viewModel.getYear(id) == y && viewModel.getMonth(id) == m
                && viewModel.getDay(id) == d;
    }

    public int getChooseStatus() {
        return chooseStatus;
    }
}
