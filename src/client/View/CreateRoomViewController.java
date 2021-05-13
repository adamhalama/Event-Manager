package client.View;


import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;


public class CreateRoomViewController
{
    @FXML
    private TextField roomCode;
    @FXML
    private TextField floor;
    @FXML
    private TextField address;
    @FXML
    private TextField numberOfSeats;
    @FXML
    private TextField equipmentToAdd;
    @FXML
    private ListView<String> equipmentList;

    private ViewHandler viewHandler;
    private CreateRoomViewModel viewModel;
    private Region root;

    public CreateRoomViewController()
    {
    }

    public void init(ViewHandler viewHandler, CreateRoomViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        equipmentList.setItems(null);

    }

    @FXML
    private void addButton()
    {

    }

    @FXML
    private void removeSelectedButton()
    {

    }
}
