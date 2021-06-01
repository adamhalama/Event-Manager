package client.View.Event;

import client.Model.Model;
import client.View.Helpers.ConfirmationButton;
import client.View.Helpers.ConvertTime;
import client.View.ViewHandler;
import client.ViewModel.CreateEventViewModel;
import client.ViewModel.EmployeeViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class CreateEventViewController
{
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<Integer> hourMenuStart, minuteMenuStart, hourMenuEnd, minuteMenuEnd;
    @FXML
    private ChoiceBox<Integer> roomMenu;
    @FXML
    private ChoiceBox<String> platformMenu;
    @FXML
    private TextField linkTextField;
    @FXML
    private TextField newParticipantField;
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

    private ViewHandler viewHandler;
    private CreateEventViewModel viewModel;
    private Region root;
    private Model model;

    public CreateEventViewController()
    {
    }

    public void init(ViewHandler viewHandler, CreateEventViewModel viewModel, Region root, Model model)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        this.model = model;

        titleTextField.textProperty().bindBidirectional(viewModel.getTitleProperty());
        descriptionArea.textProperty().bindBidirectional(viewModel.getDescriptionProperty());
        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());
        linkTextField.textProperty().bindBidirectional(viewModel.getLinkFieldproperty());

        platformMenu.setItems(FXCollections.observableArrayList("Discord", "Zoom", "Teams"));
        roomMenu.setItems(viewModel.getRoomList());

        newParticipantField.textProperty().bindBidirectional(viewModel.getNewParticipantFieldProperty());
        TextFields.bindAutoCompletion(newParticipantField, viewModel.getAllEmployeesForAutocomplete());

        ArrayList<Integer> hours = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23));
        ArrayList<Integer> minutes = new ArrayList<>(Arrays.asList(0, 15, 30, 45));

        hourMenuStart.setItems(FXCollections.observableArrayList(hours));
        minuteMenuStart.setItems(FXCollections.observableArrayList(minutes));
        hourMenuEnd.setItems(FXCollections.observableArrayList(hours));
        minuteMenuEnd.setItems((FXCollections.observableArrayList(minutes)));

        this.participantTable.setItems(viewModel.getEmployeeList());
        idColumn.setCellValueFactory(cellData ->
                cellData.getValue().getUserIDProperty());
        nameColumn.setCellValueFactory(cellData ->
                cellData.getValue().getNameProperty());
        surnameColumn.setCellValueFactory(cellData ->
                cellData.getValue().getSurnameProperty());
        roleColumn.setCellValueFactory(cellData ->
                cellData.getValue().getRoleProperty());


        setDateFormat();

        //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>()
                {
                    @Override
                    public DateCell call(DatePicker datePicker)
                    {
                        return new DateCell()
                        {
                            public void updateItem(LocalDate item, boolean empty)
                            {
                                super.updateItem(item, empty);
                                if (item.isBefore(LocalDate.now()))
                                {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #FFD8ADAD");
                                }
                            }
                        };
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
        //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑this setting is for disable the date before today
        //you cannot set the event before today
    }

    private void setDateFormat()
    {
        StringConverter<LocalDate> sc = new StringConverter<>()
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public String toString(LocalDate date)
            {
                if (date != null)
                {
                    return dtf.format(date);
                } else
                {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string)
            {
                if (string != null && !string.isEmpty())
                {
                    return LocalDate.parse(string, dtf);
                } else
                {
                    return null;
                }
            }
        };
        datePicker.setConverter(sc);
    }

    public void reset()
    {
        TextFields.bindAutoCompletion(newParticipantField, viewModel.getAllEmployeesForAutocomplete());
        datePicker.setValue(LocalDate.now());

        hourMenuStart.setValue(null);
        minuteMenuStart.setValue(null);
        hourMenuEnd.setValue(null);
        minuteMenuEnd.setValue(null);

        platformMenu.setValue(null);
        roomMenu.setValue(null);
    }


    @FXML
    private void addEmployeePress()
    {
        viewModel.addParticipant();
    }

    @FXML
    private void removeEmployeePress()
    {
        viewModel.removeParticipant(participantTable.getSelectionModel().getSelectedIndex());
    }


    @FXML
    private void createPress()
    {
        if (datePicker.getValue() == null)
        {
            errorLabel.setText("Pick a date");
            return;
        } else if (hourMenuStart.getValue() == null || minuteMenuStart.getValue() == null || hourMenuEnd.getValue() == null || minuteMenuEnd.getValue() == null)
        {
            errorLabel.setText("Pick a complete time, start and end.");
            return;
        }


        String platform = platformMenu.getValue();
        int room = roomMenu.getValue() == null || roomMenu.getValue() == 0 ? -1 : roomMenu.getValue();

        long start = ConvertTime.parseStringToTimestamp(datePicker.toString()) + (hourMenuStart.getValue().longValue() * 60 * 60 * 1000) + (minuteMenuStart.getValue().longValue() * 60 * 1000);
        long end = ConvertTime.parseStringToTimestamp(datePicker.toString()) + (hourMenuEnd.getValue().longValue() * 60 * 60 * 1000) + (minuteMenuEnd.getValue().longValue() * 60 * 1000);


        if (ConfirmationButton.confirmationView("Do you want to save the changes?"))
            if (viewModel.createButton(platform, start, end, room))
                viewHandler.openView("EventList");
    }

    @FXML
    private void cancelPress()
    {
        if (ConfirmationButton.confirmationView("Do you want to exit and discard changes?"))
        {
            viewHandler.openView("EventList");
        }
    }

    public Region getRoot()
    {
        return root;
    }



}


