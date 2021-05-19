package client.ViewModel;


import client.Model.Model;


public class ViewModelFactory
{
    private final EventListViewModel eventListViewModel;
    private final CreateEventViewModel createEventViewModel;
    private final CreateRoomViewModel createRoomViewModel;
    private final MainMenuViewModel mainMenuViewModel;

    public ViewModelFactory(Model model)
    {
        eventListViewModel = new EventListViewModel(model);
        createEventViewModel = new CreateEventViewModel(model);
        createRoomViewModel = new CreateRoomViewModel(model);
        mainMenuViewModel = new MainMenuViewModel(model);
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
}
