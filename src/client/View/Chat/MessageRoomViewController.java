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
    private Label topLabel, errorLabel;
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

        viewModel.addListener("Scroll", this ::scroll);

        topLabel.textProperty().bind(viewModel.getErrorLabelProperty());
        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());
        message.textProperty().bindBidirectional(viewModel.getMessageProperty());
        listView.setItems(viewModel.getMembersList());
        messageTable.setItems(viewModel.getMessageTable());

        incomingColumn.setCellValueFactory(cellData -> cellData.getValue().getIncomingProperty());
        sentColumn.setCellValueFactory(cellData -> cellData.getValue().getSentProperty());

    }

    private void scroll(PropertyChangeEvent propertyChangeEvent)
    {
        Platform.runLater(() ->
        {
            System.out.println(" scroll listener");
            if ( (int)propertyChangeEvent.getNewValue() == 1) // scroll to bottom when a new message comes or is sent
                this.messageTable.scrollTo(viewModel.getMessageTable().size() - 1);
            else
                this.messageTable.scrollTo(0); //scroll to top

        });
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
    private void loadMoreMessagesButton()
    {
        viewModel.loadMoreMessages();
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
