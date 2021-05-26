package client.ViewModel;


import Shared.API;
import client.Model.Model;
import client.RmiClient;
import client.View.Helpers.SelectState;


public class ViewModelFactory
{
    private EventListViewModel eventListViewModel;
    private CreateEventViewModel createEventViewModel;
    private CreateRoomViewModel createRoomViewModel;
    private MainMenuViewModel mainMenuViewModel;
    private RoomListViewModel roomListViewModel;
    private EditEventViewModel editEventViewModel;
    private EmployeeListViewModel employeeListViewModel;
    private EmployeeViewModel employeeViewModel;
    private MessageRoomListViewModel messageRoomListViewModel;
    private RoomViewModel roomViewModel;
    private MessageRoomViewModel messageRoomViewModel;
    private LoginViewModel loginViewModel;
    private EventInfoViewModel eventInfoViewModel;
    
    private SelectState state;
    private final Model model;

    public ViewModelFactory(Model model, SelectState state)
    {
        this.model = model;
        this.state = state;
//        eventListViewModel = new EventListViewModel(model);
//        createEventViewModel = new CreateEventViewModel(model);
//        createRoomViewModel = new CreateRoomViewModel(model);
//        mainMenuViewModel = new MainMenuViewModel(model);
//        roomListViewModel = new RoomListViewModel(model);
//        roomViewModel = new RoomViewModel(model);
//        editEventViewModel = new EditEventViewModel(model, state);
//        employeeListViewModel = new EmployeeListViewModel(model);
//        employeeViewModel = new EmployeeViewModel(model);
//        messageRoomListViewModel = new MessageRoomListViewModel(model);
//        messageRoomViewModel = new MessageRoomViewModel(model);
//        loginViewModel = new LoginViewModel(api);
//        eventInfoViewModel = new EventInfoViewModel(model, state);
    }
    public EventListViewModel getEventListViewModel()
    {
        if (eventListViewModel == null)
            eventListViewModel = new EventListViewModel(model);

        return eventListViewModel;
    }
    
    public CreateEventViewModel getCreateEventViewModel()
    {
        if (createEventViewModel == null)
            createEventViewModel = new CreateEventViewModel(model);
        
        return createEventViewModel;
    }

    public CreateRoomViewModel getCreateRoomViewModel()
    {
        if (createRoomViewModel == null)
            createRoomViewModel = new CreateRoomViewModel(model);

        return createRoomViewModel;
    }
    

    public MainMenuViewModel getMainMenuViewModel()
    {
        if (mainMenuViewModel == null)
            mainMenuViewModel = new MainMenuViewModel(model);

        return mainMenuViewModel;
    }

    public RoomListViewModel getRoomListViewModel()
    {
        if (roomListViewModel == null)
            roomListViewModel = new RoomListViewModel(model);

        return roomListViewModel;
    }

    public RoomViewModel getRoomViewModel()
    {
        if (roomViewModel == null)
            roomViewModel = new RoomViewModel(model);

        return roomViewModel;
    }

    public EditEventViewModel getEditEventViewModel() {
        if (editEventViewModel == null)
            editEventViewModel = new EditEventViewModel(model, state);

        return editEventViewModel;
    }

    public EmployeeListViewModel getEmployeeListViewModel()
    {
        if (employeeListViewModel == null)
            employeeListViewModel = new EmployeeListViewModel(model);

        return employeeListViewModel;
    }

    public EmployeeViewModel getEmployeeViewModel()
    {
        if (employeeViewModel == null)
            employeeViewModel = new EmployeeViewModel(model);

        return employeeViewModel;
    }

    public MessageRoomListViewModel getMessageRoomListViewModel()
    {
        if (messageRoomListViewModel == null)
            messageRoomListViewModel = new MessageRoomListViewModel(model);

        return messageRoomListViewModel;
    }

    public MessageRoomViewModel getMessageRoomViewModel()
    {
        if (messageRoomViewModel == null)
            messageRoomViewModel = new MessageRoomViewModel(model);

        return messageRoomViewModel;
    }

    public LoginViewModel getLoginViewModel() {
        if (loginViewModel == null)
            loginViewModel = new LoginViewModel(model);

        return loginViewModel;
    }

    public EventInfoViewModel getEventInfoViewModel() {
        if (eventInfoViewModel == null)
            eventInfoViewModel = new EventInfoViewModel(model, state);

        return eventInfoViewModel;
    }
}
