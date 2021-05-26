package client.ViewModel;


import Shared.API;
import client.Model.Model;
import client.View.Helpers.SelectState;


public class ViewModelFactory
{
    private final EventListViewModel eventListViewModel;
    private final CreateEventViewModel createEventViewModel;
    private final CreateRoomViewModel createRoomViewModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final RoomListViewModel roomListViewModel;
    private final EditEventViewModel editEventViewModel;
    private final EmployeeListViewModel employeeListViewModel;
    private final EmployeeViewModel employeeViewModel;
    private final MessageRoomListViewModel messageRoomListViewModel;
    private SelectState state;
    private final RoomViewModel roomViewModel;
    private final MessageRoomViewModel messageRoomViewModel;
    private final LoginViewModel loginViewModel;
    private final EventInfoViewModel eventInfoViewModel;


    public ViewModelFactory(Model model, SelectState state, API api)
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
        employeeViewModel = new EmployeeViewModel(model);
        messageRoomListViewModel = new MessageRoomListViewModel(model);
        messageRoomViewModel = new MessageRoomViewModel(model);
        loginViewModel = new LoginViewModel(api);
        eventInfoViewModel = new EventInfoViewModel(model, state);
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

    public EmployeeViewModel getEmployeeViewModel()
    {
        return employeeViewModel;
    }

    public MessageRoomListViewModel getMessageRoomListViewModel()
    {
        return messageRoomListViewModel;
    }

    public MessageRoomViewModel getMessageRoomViewModel()
    {
        return messageRoomViewModel;
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public EventInfoViewModel getEventInfoViewModel() {
        return eventInfoViewModel;
    }
}
