package client.ViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EquipmentViewModel
{
    private StringProperty equipment;

    public EquipmentViewModel(String equipment)
    {
        this.equipment = new SimpleStringProperty(equipment);
    }

    public StringProperty getEquipmentProperty()
    {
        return equipment;
    }
}
