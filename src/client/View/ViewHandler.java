package client.View;


import client.Model.Model;
import client.View.Event.CreateEventViewController;
import client.View.Event.EditEventViewController;
import client.View.Event.EventListViewController;
import client.View.MainMenu.MainMenuViewController;
import client.View.Room.CreateRoomViewController;
import client.View.Room.RoomListViewController;
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

    private CreateEventViewController createEventViewController;
    private CreateRoomViewController createRoomViewController;
    private EventListViewController eventListViewController;
    private MainMenuViewController mainMenuViewController;
    private RoomListViewController roomListViewController;
    private EditEventViewController editEventViewController;
    private SelectState selectState;

//    private RoomViewController roomViewController;

    private int pickedRoomID;

    public ViewHandler(ViewModelFactory viewModelFactory, Model model, SelectState selectState)
    {
        this.viewModelFactory = viewModelFactory;
        this.model = model;
        this.selectState = selectState;
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
            case "RoomList":
                root = loadRoomListView("Room/RoomListView.fxml");
                break;
            case "CreateRoom":
                root = loadCreateRoomView("Room/CreateRoomView.fxml");
                break;
            case "EditRoom":
                root = loadEditRoomView("Room/CreateRoomView.fxml");
                break;
            case "Room":
                root = loadRoomView("Room/CreateRoomView.fxml");
                break;
            case "EditEvent":
                root = loadEditEventViewController("Event/EditEventView.fxml");
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

    private Region loadRoomListView(String fxmlFile)
    {
        if (roomListViewController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                roomListViewController = loader.getController();
                roomListViewController.init(this, viewModelFactory.getRoomListViewModel(), root);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        viewModelFactory.getRoomListViewModel().reset();
        return roomListViewController.getRoot();
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
                createRoomViewController.init(this, viewModelFactory.getCreateRoomViewModel(), root);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        createRoomViewController.setEditing(false);
        createRoomViewController.setViewing(false);
        viewModelFactory.getCreateRoomViewModel().setCurrentRoomID(0);
        viewModelFactory.getCreateRoomViewModel().setOnlyViewing(false);

        viewModelFactory.getCreateRoomViewModel().reset();
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
                createRoomViewController.init(this, viewModelFactory.getCreateRoomViewModel(), root);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        createRoomViewController.setEditing(true);
        createRoomViewController.setViewing(false);
        viewModelFactory.getCreateRoomViewModel().setCurrentRoomID(pickedRoomID);
        viewModelFactory.getCreateRoomViewModel().setOnlyViewing(false);

        viewModelFactory.getCreateRoomViewModel().reset();
        return createRoomViewController.getRoot();
    }

    private Region loadRoomView(String fxmlFile)
    {
        if (createRoomViewController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createRoomViewController = loader.getController();
                createRoomViewController.init(this, viewModelFactory.getCreateRoomViewModel(), root);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        createRoomViewController.setEditing(false);
        createRoomViewController.setViewing(true);
        viewModelFactory.getCreateRoomViewModel().setCurrentRoomID(pickedRoomID);
        viewModelFactory.getCreateRoomViewModel().setOnlyViewing(true);

        viewModelFactory.getCreateRoomViewModel().reset();
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
                eventListViewController.init(this, viewModelFactory.getEventListViewModel(), root,
                        selectState);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return eventListViewController.getRoot();
    }

    private Region loadEditEventViewController(String fxmlFile)
    {
        if (editEventViewController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                editEventViewController = loader.getController();
                editEventViewController.init(this, viewModelFactory.getEditEventViewModel(), root, selectState);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return editEventViewController.getRoot();
    }

    public void setPickedRoomID(int pickedRoomID)
    {
        this.pickedRoomID = pickedRoomID;
    }

    public int getPickedRoomID()
    {
        return pickedRoomID;
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
