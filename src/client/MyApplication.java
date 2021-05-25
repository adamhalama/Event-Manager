package client;

import Shared.API;
import client.Model.Model;
import client.Model.ModelManager;
import client.View.SelectState;
import client.View.ViewHandler;
import client.ViewModel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import server.RmiServer;

import java.rmi.RemoteException;
import java.util.Scanner;

public class MyApplication extends Application
{
    public void start(Stage primaryStage)
    {
        RmiClient client = new RmiClient();


        Model model = new ModelManager();
        SelectState state = new SelectState();
        API api = new RmiServer();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model, state, api);
        ViewHandler view = new ViewHandler(viewModelFactory, model, state);
        view.start(primaryStage);

        //todo start the connection with server
        /*try
        {
            RmiCaseClient client = new RmiCaseClient();
            Scanner input = new Scanner(System.in);
            System.out.print("Enter a string to convert to uppercase: ");
            String line = input.nextLine();
            String convertedLine = client.convert(line, true);
            System.out.println("Uppercase version: " + convertedLine);
            System.out.print("Enter a string to capitalize first letter: ");
            line = input.nextLine();
            convertedLine = client.convert(line, false);
            System.out.println("Capitalized version: " + convertedLine);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }*/
    }
}
