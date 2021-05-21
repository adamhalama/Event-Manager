package client.ViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PermissionViewModel
{
    private StringProperty permission;

    public PermissionViewModel(String permission)
    {
        this.permission = new SimpleStringProperty(permission);
    }

    public StringProperty getPermissionProperty()
    {
        return permission;
    }
}
