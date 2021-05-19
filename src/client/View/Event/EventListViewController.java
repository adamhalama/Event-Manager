package client.View.Event;

import client.View.ViewHandler;
import client.ViewModel.EventViewModel;
import client.ViewModel.EventListViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventListViewController {
    @FXML private TextField searchTextField;
    @FXML private DatePicker searchDate;
    @FXML private TableView<EventViewModel> CreateEventViewModelList;
    @FXML private TableColumn<EventViewModel, Number> idColumn;
    @FXML private TableColumn<EventViewModel, String> titleColumn;
    @FXML private TableColumn<EventViewModel, String> descriptionColumn;
    @FXML private TableColumn<EventViewModel, String> creatTColumn;
    @FXML private TableColumn<EventViewModel, String> dateColumn;
    @FXML private TableColumn<EventViewModel, String> endTColumn;
    @FXML private TableColumn<EventViewModel, Boolean> isOnlineColumn;
    @FXML private TableColumn<EventViewModel, String> platformColumn;
    @FXML private TableColumn<EventViewModel, Number> roomColumn;
    @FXML private Label errorLabel;

    private ViewHandler viewHandler;
    private EventListViewModel viewModel;
    private Region root;

    public EventListViewController(){}

    public void init(ViewHandler viewHandler, EventListViewModel viewModel, Region root){
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        this.searchTextField.textProperty().bindBidirectional(
                viewModel.getSearchProperty());
        this.CreateEventViewModelList.setItems(viewModel.getEventList());
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
        searchDate.setConverter(sc);

        reset();
    }

    public void reset(){
        errorLabel.setText("Welcome.");
        viewModel.reset();
    }

    public Region getRoot()
    {
        return root;
    }

    /*
    For Jerry only:
    未完成的功能：
    刷新按钮
    搜索功能 （按搜索框搜索 + 按日期搜索）
    添加按钮        编辑按钮        删除按钮
     */
}
