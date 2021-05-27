package client;

import Shared.API;
import client.Model.Model;
import client.Model.ModelManager;
import client.View.Helpers.SelectState;
import client.View.ViewHandler;
import client.ViewModel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class MyApplication extends Application
{
    public void start(Stage primaryStage)
    {
        RmiClient client = new RmiClient();
        Model model = new ModelManager();
        SelectState state = new SelectState();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model, state, client);
        ViewHandler view = new ViewHandler(viewModelFactory, model, state);
        view.start(primaryStage);

        //todo start the connection with server
    }
}
