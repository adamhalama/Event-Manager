package client.View.Chat;

import client.View.Helpers.StringIntegerConverter;
import client.View.ViewHandler;
import client.ViewModel.MessageRoomViewModel;
import client.ViewModel.MessageViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class MessageRoomViewController implements PropertyChangeListener
{
    @FXML
    private Label topLabel;
    @FXML
    private ListView<String> listView;
    @FXML
    private TableView<MessageViewModel> messageTable;
    @FXML
    private TableColumn<MessageViewModel, String> incomingColumn, sentColumn;
    @FXML
    private TextField message;


    private ViewHandler viewHandler;
    private MessageRoomViewModel viewModel;
    private Region root;

    private boolean isPrivate;

    public MessageRoomViewController()
    {
    }

    public void init(ViewHandler viewHandler, MessageRoomViewModel viewModel, Region root)
    {
        StringIntegerConverter converter = new StringIntegerConverter(0);

        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        viewModel.addListener("Scroll down", this ::scrollDown);

        topLabel.textProperty().bind(viewModel.getTopLabelProperty());
        message.textProperty().bindBidirectional(viewModel.getMessageProperty());
        listView.setItems(viewModel.getMembersList());
        messageTable.setItems(viewModel.getMessageTable());

        incomingColumn.setCellValueFactory(cellData -> cellData.getValue().getIncomingProperty());
        sentColumn.setCellValueFactory(cellData -> cellData.getValue().getSentProperty());

    }

    private void scrollDown(PropertyChangeEvent propertyChangeEvent)
    {
        Platform.runLater(() ->
        {
            this.messageTable.scrollTo(viewModel.getMessageTable().size() - 1);
        });
    }

    public void reset()
    {
        viewModel.reset();
    }

    @FXML
    private void backButton() throws SQLException, RemoteException {
        viewHandler.openView("MessageRoomList");
    }

    @FXML
    private void sendButton()
    {
        System.out.println("TEST send button");
        viewModel.sendMessage();
    }

    @FXML
    private void loadAllMessagesButton()
    {
        viewModel.loadAllMessages();
    }

    public void setPrivate(boolean aPrivate)
    {
        isPrivate = aPrivate;
        viewModel.setPrivate(isPrivate);
    }


    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {

    }

    public Region getRoot()
    {
        return root;
    }
}
