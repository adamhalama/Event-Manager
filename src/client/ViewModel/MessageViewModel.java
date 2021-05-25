package client.ViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MessageViewModel
{
    private StringProperty incoming, sent;

    public MessageViewModel(String incoming, String sent)
    {
        this.incoming = new SimpleStringProperty(incoming);
        this.sent = new SimpleStringProperty(sent);
    }

    public StringProperty getIncomingProperty()
    {
        return incoming;
    }

    public StringProperty getSentProperty()
    {
        return sent;
    }
}
