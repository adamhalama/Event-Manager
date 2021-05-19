package client.View;


import client.Model.Model;
import client.View.Event.CreateEventViewController;
import client.View.Event.EventListViewController;
import client.View.MainMenu.MainMenuViewController;
import client.View.Room.CreateRoomViewController;
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
    private CreateEventViewController createEventViewController;
    private Model model;
    private CreateRoomViewController createRoomViewController;
    private EventListViewController eventListViewController;
    private MainMenuViewController mainMenuViewController;

    public ViewHandler(ViewModelFactory viewModelFactory, Model model)
    {
        this.viewModelFactory = viewModelFactory;
        this.model = model;
        currentStage = new Scene(new Region());
    }

    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        openView("MainMenu");
    }

    public void closeView()
    {
        primaryStage.close();
    }

    public void openView(String id)
    {
        Region root = null;
        switch (id)
        {
            case "MainMenu":
                root = loadMainMenuView("MainMenu/MainMenuView.fxml");
                break;
            case "EventList":
                root = loadEventListView("Event/EventListView.fxml");
                break;
            case "CreateEvent":
                root = loadCreateEventView("Event/CreateEventView.fxml");
                break;
            case "CreateRoom":
                root = loadCreateRoomView("Room/CreateRoomView.fxml");
                break;
            case "EditRoom":
                root = loadEditRoomView("Room/CreateRoomView.fxml");
                break;
        }
        currentStage.setRoot(root);

        String title = "Bruh app";
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

    private Region loadMainMenuView(String fxmlFile)
    {
        if (mainMenuViewController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                mainMenuViewController = loader.getController();
                mainMenuViewController.init(this, viewModelFactory.getMainMenuViewModel(), root);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return mainMenuViewController.getRoot();
    }


    private Region loadCreateRoomView(String fxmlFile)
    {
        if (createRoomViewController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createRoomViewController = loader.getController();
                createRoomViewController.init(this, viewModelFactory.getCreateRoomViewModel(), root, false);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return createRoomViewController.getRoot();
    }

    private Region loadEditRoomView(String fxmlFile)
    {
        if (createRoomViewController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createRoomViewController = loader.getController();
                createRoomViewController.init(this, viewModelFactory.getCreateRoomViewModel(), root, true);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return createRoomViewController.getRoot();
    }


    private Region loadCreateEventView(String fxmlFile)
    {
        if (createEventViewController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createEventViewController = loader.getController();
                createEventViewController.init(this, viewModelFactory.getCreateEventViewModel(), root);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return createEventViewController.getRoot();
    }

    private Region loadEventListView(String fxmlFile)
    {
        if (eventListViewController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                eventListViewController = loader.getController();
                eventListViewController.init(this, viewModelFactory.getEventListViewModel(), root);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return eventListViewController.getRoot();
    }


// Commented out code is copied from the assignment 3's viewHandler
//    private Region loadMainView(String fxmlFile)
//    {
//        if (chatViewController == null)
//        {
//            try
//            {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource(fxmlFile));
//                Region root = loader.load();
//                chatViewController = loader.getController();
//                chatViewController.init(this, viewModelFactory.getChatViewModel(), root);
//            } catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return chatViewController.getRoot();
//    }

//    private Region loadSetView(String fxmlFile)
//    {
//        if (setUserNameViewController == null)
//        {
//            try
//            {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource(fxmlFile));
//                Region root = loader.load();
//                setUserNameViewController = loader.getController();
//                setUserNameViewController.init(this, viewModelFactory.getSetUserNameViewModel(), root);
//            } catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return setUserNameViewController.getRoot();
//    }


}
