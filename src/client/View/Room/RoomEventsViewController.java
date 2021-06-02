package client.View.Room;

import client.View.ViewHandler;
import client.ViewModel.EventViewModel;
import client.ViewModel.RoomEventsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

public class RoomEventsViewController
{
    @FXML
    private TableView<EventViewModel> eventList;
    @FXML
    private TableColumn<EventViewModel, Number> idColumn;
    @FXML
    private TableColumn<EventViewModel, String> titleColumn;
    @FXML
    private TableColumn<EventViewModel, String> dateColumn;
//    @FXML
//    private TableColumn<EventViewModel, String> endColumn;
    @FXML
    private TableColumn<EventViewModel, String> creatorColumn;
    @FXML
    private TableColumn<EventViewModel, Number> participantNoColumn;
    @FXML
    private Label errorLabel, topLabel;

    private ViewHandler viewHandler;
    private RoomEventsViewModel viewModel;
    private Region root;

    public RoomEventsViewController(){}


    public void init(ViewHandler viewHandler, RoomEventsViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;


        this.eventList.setItems(viewModel.getEventsOfThisRoom());
        idColumn.setCellValueFactory(cellData ->
                cellData.getValue().getIdProperty());
        titleColumn.setCellValueFactory(cellData ->
                cellData.getValue().getTitleProperty());
        dateColumn.setCellValueFactory(cellData ->
                cellData.getValue().getStartDate());
//        endColumn.setCellValueFactory(cellData ->
//                cellData.getValue().getEndTime());
        creatorColumn.setCellValueFactory(cellData ->
                cellData.getValue().getCreatorProperty());
        participantNoColumn.setCellValueFactory(cellData ->
                cellData.getValue().getSizeProperty());

        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());
        topLabel.textProperty().bind(viewModel.getTopLabelProperty());

    }

    @FXML
    private void backButton()
    {
        viewHandler.openView("Room");
    }

    @FXML
    private void bookEventButton()
    {
        viewHandler.openView("CreateEvent");
    }

    public Region getRoot()
    {
        return root;
    }
}
