package client.ViewModel;


import client.Model.Model;


public class ViewModelFactory
{
    private CreateEventViewModel createEventViewModel;
    private CreateRoomViewModel createRoomViewModel;

    public ViewModelFactory(Model model)
    {
        createEventViewModel = new CreateEventViewModel(model);
        createRoomViewModel = new CreateRoomViewModel(model);
    }

    public CreateEventViewModel getCreateEventViewModel()
    {
        return createEventViewModel;
    }

    public CreateRoomViewModel getCreateRoomViewModel()
    {
        return createRoomViewModel;
    }
}
