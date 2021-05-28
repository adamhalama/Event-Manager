package client.ViewModel;

import Shared.Messages.Message;
import client.Model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.observer.subject.NamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageRoomViewModel implements NamedPropertyChangeSubject
{
    private IntegerProperty messageRoomIDProperty;
    private StringProperty nameColumn;
    private StringProperty lastMessageColumn;
    private StringProperty participantsColumn;

    private StringProperty topLabel, message;
    private ObservableList<String> membersList;

    private ObservableList<MessageViewModel> messageTable;

    private Model model;

    private boolean isPrivate;
    private int messageRoomID;

    private PropertyChangeSupport property;


    public MessageRoomViewModel(int messageRoomID, String name, String lastMessage, ArrayList<String> participants)
    {
        this.messageRoomIDProperty = new SimpleIntegerProperty(messageRoomID);
        nameColumn = new SimpleStringProperty(name + "\n" + lastMessage);
        lastMessageColumn = new SimpleStringProperty(lastMessage);

        StringBuilder s = new StringBuilder();
        for (String str : participants)
            s.append(str).append(", ");
        s.deleteCharAt(s.lastIndexOf(", "));

        participantsColumn = new SimpleStringProperty(s.toString());
    }

    public MessageRoomViewModel(Model model)
    {
        property = new PropertyChangeSupport(this);

        this.model = model;
        topLabel = new SimpleStringProperty();
        message = new SimpleStringProperty();

        membersList = FXCollections.observableArrayList();
        messageTable = FXCollections.observableArrayList();
    }

    public void reset()
    {
        messageTable.clear();
        membersList.clear();
        if (isPrivate)
        {
            topLabel.setValue("Private chat between:");

        } else
        {
            topLabel.setValue("Group chat members:");

        }
        try
        {
            membersList.addAll(model.getMessageRoomParticipantNames(model.getMessageRoomByID(messageRoomID)));
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }//TODO add different errorLabel statements

        try
        {
            for (Message message :
                    model.getMessageRoomByID(messageRoomID).getMessages())
            {

                String firstCol;
                String secondCol;
                if(message.getUserID() == model.getLoggedClientID())
                {
                    firstCol = "";
                    secondCol = message.getMessage();
                }
                else
                {
                    firstCol = model.getSenderAndBody(message);
                    secondCol = "";

                }

                messageTable.add(new MessageViewModel(firstCol, secondCol));
                property.firePropertyChange("Scroll down", null, 1);
            }
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }//TODO add different errorLabel statements

    }

    public void loadAllMessages()
    {
        //todo potentially load all messages if the first load will load just a few more
    }

    public void sendMessage()
    {
        if (!message.get().trim().equals(""))
        {
            try
            {
                model.getMessageRoomByID(messageRoomID).addMessage(new Message(model.getLoggedClientID(), System.currentTimeMillis(), message.get().trim()));
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
            } catch (RemoteException e)
            {
                e.printStackTrace();
            }//TODO add different errorLabel statements
            messageTable.add(new MessageViewModel("", message.get().trim()));
            message.setValue("");
            property.firePropertyChange("Scroll down", null, 1);
        }
    }

    public StringProperty getNameColumnProperty()
    {
        return nameColumn;
    }

    public StringProperty getLastMessageColumnProperty()
    {
        return lastMessageColumn;
    }

    public StringProperty getParticipantsColumnProperty()
    {
        return participantsColumn;
    }

    public IntegerProperty getMessageRoomIDProperty()
    {
        return messageRoomIDProperty;
    }

    public StringProperty getTopLabelProperty()
    {
        return topLabel;
    }

    public StringProperty getMessageProperty()
    {
        return message;
    }

    public ObservableList<MessageViewModel> getMessageTable()
    {
        return messageTable;
    }

    public ObservableList<String> getMembersList()
    {
        return membersList;
    }

    public void setPrivate(boolean Private)
    {
        isPrivate = Private;
    }

    public boolean isPrivate()
    {
        return isPrivate;
    }

    public void setMessageRoomID(int messageRoomID)
    {
        this.messageRoomID = messageRoomID;
    }

    @Override
    public void addListener(String propertyName, PropertyChangeListener listener)
    {
        property.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener)
    {
        property.removePropertyChangeListener(propertyName, listener);
    }


}
