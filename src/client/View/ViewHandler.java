package client.View;


import client.Model.Model;
import client.ViewModel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class ViewHandler
{
    private Stage primaryStage;
    private Scene currentStage;
    private ViewModelFactory viewModelFactory;
    private Model model;

    public ViewHandler(ViewModelFactory viewModelFactory, Model model)
    {
        this.viewModelFactory = viewModelFactory;
        this.model = model;
        currentStage = new Scene(new Region());
    }

    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        openView("Set");
    }

    public void openView(String id)
    {
        Region root = null;
        switch (id)
        {
            case "CreateEvent":
//                root = loadCreateEventView("CreateEventView.fxml");
                break;
        }
        currentStage.setRoot(root);

        String title = "Chat";
        if (root.getUserData() != null)
        {
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentStage);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.show();
    }


// Commented out code is copied from the assignment 3's viewHandler
    /*private Region loadMainView(String fxmlFile)
    {
        if (chatViewController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                chatViewController = loader.getController();
                chatViewController.init(this, viewModelFactory.getChatViewModel(), root);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return chatViewController.getRoot();
    }

    private Region loadSetView(String fxmlFile)
    {
        if (setUserNameViewController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                setUserNameViewController = loader.getController();
                setUserNameViewController.init(this, viewModelFactory.getSetUserNameViewModel(), root);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return setUserNameViewController.getRoot();
    }*/
}
