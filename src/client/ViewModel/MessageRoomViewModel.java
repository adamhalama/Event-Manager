package client.ViewModel;

import Shared.Messages.Message;
import client.Model.Model;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.observer.subject.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageRoomViewModel implements NamedPropertyChangeSubject, PropertyChangeListener
{
    private IntegerProperty messageRoomIDProperty;
    private StringProperty nameColumn;
    private StringProperty lastMessageColumn;
    private StringProperty participantsColumn;

    private StringProperty errorLabel;
    private StringProperty topLabel, message;
    private ObservableList<String> membersList;

    private ObservableList<MessageViewModel> messageTable;

    private Model model;

    private boolean isPrivate;
    private int messageRoomID;
    private int loggedEmployeeID;

    private int messageRoomOffset;

    private PropertyChangeSupport property;


    public MessageRoomViewModel(int messageRoomID, String name, String lastMessage, ArrayList<String> participants) //for messageListView
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

        model.addListener("message", this::newMessage);

        this.model = model;
        topLabel = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();
        message = new SimpleStringProperty();

        membersList = FXCollections.observableArrayList();
        messageTable = FXCollections.observableArrayList();
    }

    private void newMessage(PropertyChangeEvent propertyChangeEvent)
    {
        Platform.runLater(() -> {

            System.out.println("listener hears");
            Message message = (Message) propertyChangeEvent.getNewValue();


            String firstCol;
            String secondCol;

            if (message.getUserID() == loggedEmployeeID)
            {
                firstCol = "";
                secondCol = message.getMessage();
            } else
            {
                firstCol = model.getSenderAndBody(message);
                secondCol = "";

            }


            messageTable.add(messageTable.size(), new MessageViewModel(firstCol, secondCol));
            property.firePropertyChange("Scroll down", null, 1);
            // newValue 1 scroll to bottom when a new message comes or is sent


        });
    }

    public void reset()
    {
        loggedEmployeeID = model.getLoggedEmployeeID();
        messageRoomOffset = 0;
        messageTable.clear();
        membersList.clear();
        errorLabel.set("");

        model.messageRoomFollow(messageRoomID);
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
            errorLabel.set(throwables.getMessage());
        } catch (RemoteException e)
        {
            errorLabel.set(e.getMessage());
            e.printStackTrace();
        }

        try
        {
            for (Message message :
                    model.messagesGet(messageRoomID, messageRoomOffset))
            {

                String firstCol;
                String secondCol;
                if (message.getUserID() == loggedEmployeeID)
                {
                    firstCol = "";
                    secondCol = message.getMessage();
                } else
                {
                    firstCol = model.getSenderAndBody(message);
                    secondCol = "";

                }

//                messageTable.add(new MessageViewModel(firstCol, secondCol));
                messageTable.add(0, new MessageViewModel(firstCol, secondCol));
                property.firePropertyChange("Scroll", null, 1);
                // newValue 1 scroll to bottom when a new message comes or is sent
            }
        } catch (RemoteException e)
        {
            errorLabel.set(e.getMessage());
            e.printStackTrace();
        }

    }

    public void loadMoreMessages()
    {
        messageRoomOffset += 40;
        try
        {
            for (Message message :
                    model.messagesGet(messageRoomID, messageRoomOffset))
            {

                String firstCol;
                String secondCol;
                if (message.getUserID() == loggedEmployeeID)
                {
                    firstCol = "";
                    secondCol = message.getMessage();
                } else
                {
                    firstCol = model.getSenderAndBody(message);
                    secondCol = "";

                }

//                messageTable.add(new MessageViewModel(firstCol, secondCol));
                messageTable.add(0, new MessageViewModel(firstCol, secondCol));
                property.firePropertyChange("Scroll", null, 2); //scroll to top
            }
        } catch (RemoteException e)
        {
            errorLabel.set(e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage()
    {
        if (!message.get().trim().equals(""))
        {
            try
            {
                model.sendMessage(messageRoomID, message.get().trim());
//                messageTable.add(messageTable.size(), new MessageViewModel("", message.get().trim()));
                message.setValue("");
//                property.firePropertyChange("Scroll", null, 1);
            } catch (SQLException throwables)
            {
                throwables.printStackTrace();
                errorLabel.set(throwables.getMessage());
            } catch (RemoteException e)
            {
                errorLabel.set(e.getMessage());
                e.printStackTrace();
            }
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

    public boolean isPrivate()
    {
        return isPrivate;
    }

    public void setPrivate(boolean Private)
    {
        isPrivate = Private;
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


    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
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

    public void unfollowMessageRoom(int pickedID)
    {
        if (pickedID == -1)
            model.messageRoomUnfollow(messageRoomID);
        else
            model.messageRoomUnfollow(pickedID);
    }
}
