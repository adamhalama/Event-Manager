package client.ViewModel;

import Shared.Messages.MessageRoom;
import client.Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;

public class MessageRoomListViewModel
{
    private StringProperty searchBox;
    private StringProperty errorLabel;
    private ObservableList<MessageRoomViewModel> tableView;

    private Model model;

    public MessageRoomListViewModel(Model model)
    {
        this.model = model;

        searchBox = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();
        tableView = FXCollections.observableArrayList();

        reset();
    }

    public void reset()
    {
        searchBox.setValue("");
        errorLabel.setValue("");

        tableView.clear();

        try
        {
            //private rooms
            for (MessageRoom room:
                 model.messageRoomGetPrivate())
            {
                tableView.add(new MessageRoomViewModel(room.getId(), room.getName(),
                        model.getSenderAndBody(room.getLastMessage()),
                        model.getMessageRoomParticipantNames(room)));
            }
            //all other

        } catch (RemoteException e)
        {
            errorLabel.setValue(e.getMessage());
            e.printStackTrace();
        }
    }

    public ObservableList<MessageRoomViewModel> getTableView()
    {
        return tableView;
    }

    public StringProperty getSearchBoxProperty()
    {
        return searchBox;
    }

    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }

    public void search()
    {
        if (searchBox != null)
        {
            tableView.clear();

            try
            {
                for (MessageRoom room:
                     model.getMessageRoomsByAnything(searchBox.get()))
                {
                    tableView.add(new MessageRoomViewModel(room.getId(),
                            room.getName(),
                            model.getSenderAndBody(room.getLastMessage()),
                            model.getMessageRoomParticipantNames(room)));
                }
            } catch (RemoteException e)
            {
                errorLabel.setValue(e.getMessage());
                e.printStackTrace();
            }
        }

        if (searchBox != null && !searchBox.get().equals(""))
            this.reset();
    }

    public void setErrorLabel(String text)
    {
        errorLabel.setValue(text);
    }
}
