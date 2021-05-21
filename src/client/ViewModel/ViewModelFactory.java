package client.ViewModel;


import client.Model.Model;
import client.View.SelectState;


public class ViewModelFactory
{
    private final EventListViewModel eventListViewModel;
    private final CreateEventViewModel createEventViewModel;
    private final CreateRoomViewModel createRoomViewModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final RoomListViewModel roomListViewModel;
    private final EditEventViewModel editEventViewModel;
    private final EmployeeListViewModel employeeListViewModel;
    private SelectState state;
    private final RoomViewModel roomViewModel;

    public ViewModelFactory(Model model, SelectState state)
    {
        this.state = state;
        eventListViewModel = new EventListViewModel(model);
        createEventViewModel = new CreateEventViewModel(model);
        createRoomViewModel = new CreateRoomViewModel(model);
        mainMenuViewModel = new MainMenuViewModel(model);
        roomListViewModel = new RoomListViewModel(model);
        roomViewModel = new RoomViewModel(model);
        editEventViewModel = new EditEventViewModel(model, state);
        employeeListViewModel = new EmployeeListViewModel(model);
    }

    public CreateEventViewModel getCreateEventViewModel()
    {
        return createEventViewModel;
    }

    public CreateRoomViewModel getCreateRoomViewModel()
    {
        return createRoomViewModel;
    }

    public EventListViewModel getEventListViewModel()
    {
        return eventListViewModel;
    }

    public MainMenuViewModel getMainMenuViewModel()
    {
        return mainMenuViewModel;
    }

    public RoomListViewModel getRoomListViewModel()
    {
        return roomListViewModel;
    }

    public RoomViewModel getRoomViewModel()
    {
        return roomViewModel;
    }

    public EditEventViewModel getEditEventViewModel() {
        return editEventViewModel;
    }

    public EmployeeListViewModel getEmployeeListViewModel()
    {
        return employeeListViewModel;
    }
}
