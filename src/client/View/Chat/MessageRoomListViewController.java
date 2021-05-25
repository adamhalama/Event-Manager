package client.View.Chat;

import client.View.ViewHandler;
import client.ViewModel.MessageRoomListViewModel;
import client.ViewModel.MessageRoomViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class MessageRoomListViewController
{
    @FXML
    private TextField searchBox;
    @FXML
    private TableView<MessageRoomViewModel> tableView;
    @FXML
    private TableColumn<MessageRoomViewModel, String> nameColumn, lastMessageColumn, participantsColumn;
    @FXML
    private Label errorLabel;

    private ViewHandler viewHandler;
    private MessageRoomListViewModel viewModel;
    private Region root;

    public MessageRoomListViewController(){}

    public void init(ViewHandler viewHandler, MessageRoomListViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;


        searchBox.textProperty().bindBidirectional(viewModel.getSearchBoxProperty());
        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());

        tableView.setItems(viewModel.getTableView());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameColumnProperty());
//        lastMessageColumn.setCellValueFactory(cellData -> cellData.getValue().getLastMessageColumnProperty());
//        i merged the last message column and the name column together
        participantsColumn.setCellValueFactory(cellData -> cellData.getValue().getParticipantsColumnProperty());

        viewModel.reset();

    }

    private void setErrorLabel(String text)
    {
        viewModel.setErrorLabel(text);
    }

    @FXML
    private void searchButton()
    {
        viewModel.search();
    }

    @FXML
    private void newButton()
    {
        viewHandler.openView("CreateMessageRoom");
    }

    @FXML
    private void backButton()
    {
        viewHandler.openView("MainMenu");
    }

    @FXML
    private void openButton()
    {
        try
        {
            viewHandler.setPickedMessageRoomID(tableView.getSelectionModel().getSelectedItem().getMessageRoomIDProperty().get());
            viewHandler.openView("MessageRoom");
        } catch (Exception e)
        {
            e.printStackTrace();
            setErrorLabel("Select a chat room to open first");
        }
    }


    public void reset()
    {
        viewModel.reset();
    }

    public Region getRoot()
    {
        return root;
    }
}
