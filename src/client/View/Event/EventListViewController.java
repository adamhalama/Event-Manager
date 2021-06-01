package client.View.Event;

import client.View.ViewHandler;
import client.ViewModel.EventListViewModel;
import client.ViewModel.EventViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventListViewController
{
    @FXML
    private TextField searchTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<EventViewModel> eventList;
    @FXML
    private TableColumn<EventViewModel, Number> idColumn;
    @FXML
    private TableColumn<EventViewModel, String> titleColumn;
    @FXML
    private TableColumn<EventViewModel, String> dateColumn;
    @FXML
    private TableColumn<EventViewModel, String> platformColumn;
    @FXML
    private TableColumn<EventViewModel, String> roomColumn;
    @FXML
    private TableColumn<EventViewModel, String> creatorColumn;
    @FXML
    private TableColumn<EventViewModel, Number> noOfParticipantsColumn;
    @FXML
    private Label errorLabel;
    @FXML
    private Label dateInfoLabel;
    @FXML
    private Button addButton, editButton, removeButton, backButton, searchButton, openButton;

    private ViewHandler viewHandler;
    private EventListViewModel viewModel;
    private Region root;

    public EventListViewController()
    {
    }

    public void init(ViewHandler viewHandler, EventListViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        this.searchTextField.textProperty().bindBidirectional(
                viewModel.getSearchProperty());
        this.errorLabel.textProperty().bind(
                viewModel.getErrorLabelProperty());

        this.eventList.setItems(viewModel.getEventList());
        idColumn.setCellValueFactory(cellData ->
                cellData.getValue().getIdProperty());
        titleColumn.setCellValueFactory(cellData ->
                cellData.getValue().getTitleProperty());
        dateColumn.setCellValueFactory(cellData ->
                cellData.getValue().getStartDate());
        platformColumn.setCellValueFactory(cellData ->
                cellData.getValue().getPlatformProperty());
        roomColumn.setCellValueFactory(cellData ->
                cellData.getValue().getRoomNameProperty());
        creatorColumn.setCellValueFactory(cellData ->
                cellData.getValue().getCreatorProperty());
        noOfParticipantsColumn.setCellValueFactory(cellData ->
                cellData.getValue().getSizeProperty());

        /* tips when mouse move on the icons */
        tooltips();

        setDateFormat();
    }



    /**
     * sets the date picker and calls the viewModel.reset()
     */
    public void reset()
    {
        datePicker.setValue(LocalDate.now());
        viewModel.reset();
    }


    public Region getRoot()
    {
        return root;
    }


    @FXML
    private void searchPress()
    {
        viewModel.searchButton();
    }
    @FXML
    private void searchByDatePress()
    {
        viewModel.searchByDateButton(datePicker.getValue());
    }

    @FXML
    private void addPress()
    {
        viewHandler.openView("CreateEvent");
    }

    @FXML
    private void editPress()
    {
        try
        {
            viewHandler.setPickedEventID(eventList.getSelectionModel().getSelectedItem().getIdProperty().get());
            viewHandler.openView("EditEvent");
        } catch (Exception e)
        {
            errorLabel.setText("Select event to edit first");
        }

    }

    @FXML
    private void openPress()
    {
        try
        {
            viewHandler.setPickedEventID(eventList.getSelectionModel().getSelectedItem().getIdProperty().get());
            viewHandler.openView("InfoEvent");
        } catch (Exception e)
        {
            errorLabel.setText("Select event to open first");
        }

    }

    @FXML
    private void removePress()
    {
        viewModel.removeButton((eventList.getSelectionModel().getSelectedIndex()), eventList.getSelectionModel().getSelectedItem().getIdProperty().get());
    }

    @FXML
    private void backPress()
    {
        viewHandler.openView("MainMenu");
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

    private void tooltips()
    {
        final Tooltip t2 = new Tooltip();
        t2.setText("Enter the thing you want to search without date.");
        t2.setFont(new Font("Arial", 12));
        Tooltip.install(searchTextField, t2);
        reset();

        final Tooltip t3 = new Tooltip();
        t3.setText("Only allow the number");
        t3.setFont(new Font("Arial", 12));
        Tooltip.install(dateInfoLabel, t3);
        reset();

        final Tooltip t4 = new Tooltip();
        t4.setText("Add an event");
        t4.setFont(new Font("Arial", 12));
        Tooltip.install(addButton, t4);
        reset();

        final Tooltip t5 = new Tooltip();
        t5.setText("Edit the select event");
        t5.setFont(new Font("Arial", 12));
        Tooltip.install(editButton, t5);
        reset();

        final Tooltip t6 = new Tooltip();
        t6.setText("Remove the select event");
        t6.setFont(new Font("Arial", 12));
        Tooltip.install(removeButton, t6);
        reset();

        final Tooltip t7 = new Tooltip();
        t7.setText("Back to the main page");
        t7.setFont(new Font("Arial", 12));
        Tooltip.install(backButton, t7);
        reset();


        final Tooltip t9 = new Tooltip();
        t9.setText("Search the events");
        t9.setFont(new Font("Arial", 12));
        Tooltip.install(searchButton, t9);
        reset();

        final Tooltip t10 = new Tooltip();
        t10.setText("See the event details");
        t10.setFont(new Font("Arial", 12));
        Tooltip.install(openButton, t10);
        reset();
    }
}
