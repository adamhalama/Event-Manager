package client.View.Event;

import Shared.Event.Event;
import client.View.SelectState;
import client.View.ViewHandler;
import client.ViewModel.EditEventViewModel;
import client.ViewModel.EventViewModel;
import client.ViewModel.EventListViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EventListViewController {
    @FXML
    private TextField searchTextField;
    @FXML
    private TextField ySTextField;
    @FXML
    private TextField mSTextField;
    @FXML
    private TextField dSTextField;
    @FXML
    private TableView<EventViewModel> eventList;
    @FXML
    private TableColumn<EventViewModel, Number> idColumn;
    @FXML
    private TableColumn<EventViewModel, String> titleColumn;
    @FXML
    private TableColumn<EventViewModel, String> descriptionColumn;
    @FXML
    private TableColumn<EventViewModel, String> creatTColumn;
    @FXML
    private TableColumn<EventViewModel, String> dateColumn;
    @FXML
    private TableColumn<EventViewModel, String> endTColumn;
    @FXML
    private TableColumn<EventViewModel, Boolean> isOnlineColumn;
    @FXML
    private TableColumn<EventViewModel, String> platformColumn;
    @FXML
    private TableColumn<EventViewModel, Number> roomColumn;
    @FXML
    private Label errorLabel;
    @FXML
    private Label dateInfoLabel;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button backButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button searchButton;
    private int selected;

    private ViewHandler viewHandler;
    private EventListViewModel viewModel;
    private SelectState state;
    private Region root;

    public EventListViewController() {
    }

    public void init(ViewHandler viewHandler, EventListViewModel viewModel, Region root,
                     SelectState state) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.state = state;
        this.root = root;

        this.selected = -1;
        this.searchTextField.textProperty().bindBidirectional(
                viewModel.getSearchProperty());
        this.ySTextField.textProperty().bindBidirectional(viewModel.getYearSProperty());
        this.mSTextField.textProperty().bindBidirectional(viewModel.getMonthSProperty());
        this.dSTextField.textProperty().bindBidirectional(viewModel.getDaySProperty());

        this.eventList.setItems(viewModel.getEventList());
        idColumn.setCellValueFactory(cellData ->
                cellData.getValue().getIdProperty());
        titleColumn.setCellValueFactory(cellData ->
                cellData.getValue().getTitleProperty());
        descriptionColumn.setCellValueFactory(cellData ->
                cellData.getValue().getDescriptionProperty());
        creatTColumn.setCellValueFactory(cellData ->
                cellData.getValue().getCreateDate());
        dateColumn.setCellValueFactory(cellData ->
                cellData.getValue().getStartDate());
        endTColumn.setCellValueFactory(cellData ->
                cellData.getValue().getEndTime());
        isOnlineColumn.setCellValueFactory(cellData ->
                cellData.getValue().isOnline());
        platformColumn.setCellValueFactory(cellData ->
                cellData.getValue().getPlatformProperty());
        roomColumn.setCellValueFactory(cellData ->
                cellData.getValue().getRoomProperty());

        /* tips when mouse move on the icons */
        final Tooltip t1 = new Tooltip();
        t1.setText("You can only enter numbers!");
        t1.setFont(new Font("Arial", 12));
        Tooltip.install(ySTextField, t1);
        Tooltip.install(mSTextField, t1);
        Tooltip.install(dSTextField, t1);

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

        final Tooltip t8 = new Tooltip();
        t8.setText("Refresh the table");
        t8.setFont(new Font("Arial", 12));
        Tooltip.install(refreshButton, t8);
        reset();

        final Tooltip t9 = new Tooltip();
        t9.setText("Search the events");
        t9.setFont(new Font("Arial", 12));
        Tooltip.install(searchButton, t9);
        reset();
    }

    public void reset() {
        errorLabel.setText("Welcome.");
        viewModel.reset();
    }

    public void resetSearch() {
        errorLabel.setText("");
        viewModel.resetSearch();
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void refreshPress() {
        viewModel.reset();
        eventList.setItems(viewModel.update());
        errorLabel.setText(viewModel.getEventListSize() + " events loaded.");
    }

    @FXML
    private void searchPress() {
        String dateS = "";
        if (searchTextField.getText() != null && !isValidDate()) {
            eventList.setItems(viewModel.searchExceptDate(searchTextField.getText()));
        } else if (searchTextField.getText() == null && isValidDate()) {
            try {
                if (ySTextField.getText().length() == 4) {
                    dateS += ySTextField.getText();
                    dateS += "-";
                } else throw new IllegalArgumentException("The Year should be 4 digits!");
                if (isValidMonth(mSTextField.getText())) {
                    if (mSTextField.getText().length() == 1) {
                        dateS += "0";
                        dateS += mSTextField.getText();
                        dateS += "-";
                    } else {
                        dateS += mSTextField.getText();
                        dateS += "-";
                    }
                } else throw new IllegalArgumentException("Please enter valid month!");
                if (isValidDay(dSTextField.getText())) {
                    if (dSTextField.getText().length() == 1) {
                        dateS += "0";
                        dateS += dSTextField.getText();
                    } else {
                        dateS += dSTextField.getText();
                    }
                } else throw new IllegalArgumentException("Please enter valid day");
                eventList.setItems(viewModel.searchOnlyDate(dateS));
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
        } else if (searchTextField.getText() != null && isValidDate()) {
            try {
                if (ySTextField.getText().length() == 4) {
                    dateS += ySTextField.getText();
                    dateS += "-";
                } else throw new IllegalArgumentException("The Year should be 4 digits!");
                if (isValidMonth(mSTextField.getText())) {
                    if (mSTextField.getText().length() == 1) {
                        dateS += "0";
                        dateS += mSTextField.getText();
                        dateS += "-";
                    } else {
                        dateS += mSTextField.getText();
                        dateS += "-";
                    }
                } else throw new IllegalArgumentException("Please enter valid month!");
                if (isValidDay(dSTextField.getText())) {
                    if (dSTextField.getText().length() == 1) {
                        dateS += "0";
                        dateS += dSTextField.getText();
                    } else {
                        dateS += dSTextField.getText();
                    }
                } else throw new IllegalArgumentException("Please enter valid day");
                eventList.setItems(viewModel.searchOnlyDate(dateS));
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
            viewModel.search(searchTextField.getText(), dateS);
        }
    }

    @FXML
    private void addPress() {
        viewHandler.openView("CreateEvent");
    }

    @FXML
    private void editPress() {
        try {
            if (eventList.getSelectionModel().getSelectedItem() != null) {
                state.setEditSelect(eventList.getSelectionModel().getSelectedItem().getIdProperty().get());
                viewHandler.openView("EditEvent");
            } else throw new IllegalArgumentException("Please select an event to edit!");
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void infoPress() {
        try {
            if (eventList.getSelectionModel().getSelectedItem() != null) {
                state.setEditSelect(eventList.getSelectionModel().getSelectedItem().getIdProperty().get());
                viewHandler.openView("EventInfo");
            } else throw new IllegalArgumentException("Please select an event to see info!");
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void removePress() {
        if (eventList.getSelectionModel().getSelectedItem() != null) {
            selected = eventList.getSelectionModel().getSelectedItem().getIdProperty().get();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Confirm");
            a.setHeaderText("Are you sure to delete this event ?");
            a.setContentText(eventList.getSelectionModel().getSelectedItem().getWholeMessage().get());
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                viewModel.removeEvent(selected);
                Alert b = new Alert(Alert.AlertType.INFORMATION);
                b.setTitle("Success");
                b.setHeaderText("Event removed.");
            }
        } else {
            Alert c = new Alert(Alert.AlertType.INFORMATION);
            c.setTitle("Error");
            c.setHeaderText("Please select an event.");
        }
    }

    @FXML
    private void backPress() {
        viewHandler.openView("MainMenu");
    }

    private boolean isValidMonth(String month) {
        return (month.equals("1") || month.equals("2") || month.equals("3") || month.equals("4") || month.equals("5") || month.equals("6")
                || month.equals("7") || month.equals("8") || month.equals("9") || month.equals("10") || month.equals("11") || month.equals("12")
                || month.equals("01") || month.equals("02") || month.equals("03") || month.equals("04") || month.equals("05") || month.equals("06")
                || month.equals("07") || month.equals("08") || month.equals("09"));
    }

    private boolean isValidDay(String day) {
        return (Integer.valueOf(day) > 0 && (Integer.valueOf(day) <= 31));
    }

    private boolean isValidDate() {
        return ySTextField.getText() != null && mSTextField.getText() != null && dSTextField.getText() != null;
    }
}
