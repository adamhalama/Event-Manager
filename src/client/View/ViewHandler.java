package client.View;


import client.Model.Model;
import client.View.Chat.MessageRoomListViewController;
import client.View.Chat.MessageRoomViewController;
import client.View.Employee.EmployeeListViewController;
import client.View.Employee.EmployeeViewController;
import client.View.Event.*;
import client.View.Login.LoginViewController;
import client.View.MainMenu.MainMenuViewController;
import client.View.Room.CreateRoomViewController;
import client.View.Room.RoomListViewController;
import client.ViewModel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class ViewHandler {
    private Stage primaryStage;
    private Scene currentStage;
    private ViewModelFactory viewModelFactory;
    private CreateEventViewController createEventViewController;
    private Model model;
    private CreateRoomViewController createRoomViewController;
    private EventListViewController eventListViewController;
    private MainMenuViewController mainMenuViewController;
    private RoomListViewController roomListViewController;
    private EditEventViewController editEventViewController;
    private SelectState selectState;
    private EmployeeListViewController employeeListViewController;
    private EmployeeViewController employeeViewController;
    private MessageRoomListViewController messageRoomListViewController;
    private MessageRoomViewController messageRoomViewController;
    private LoginViewController loginViewController;
    private EventInfoViewController eventInfoViewController;
    private EventEmployeeViewController eventEmployeeViewController;

//    private RoomViewController roomViewController;

    private int pickedRoomID;
    private int pickedEmployeeID;
    private int pickedMessageRoomID;


    public ViewHandler(ViewModelFactory viewModelFactory, Model model, SelectState selectState) {
        this.viewModelFactory = viewModelFactory;
        this.model = model;
        this.selectState = selectState;
        currentStage = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("MainMenu");
    }

    public void closeView() {
        primaryStage.close();
    }

    public void openView(String id) {
        Region root = null;
        switch (id) {
            case "MainMenu":
                root = loadMainMenuView("MainMenu/MainMenuView.fxml");
                break;
            case "EventList":
                root = loadEventListView("Event/EventListView.fxml");
                break;
            case "CreateEvent":
                root = loadCreateEventView("Event/CreateEventView.fxml");
                break;
            case "EditEvent":
                root = loadEditEventView("Event/EditEventView.fxml");
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
            case "EmployeeList":
                root = loadEmployeeListView("Employee/EmployeeListView.fxml");
                break;
            case "CreateEmployee":
                root = loadCreateEmployeeView("Employee/EmployeeView.fxml");
                break;
            case "EditEmployee":
                root = loadEditEmployeeView("Employee/EmployeeView.fxml");
                break;
            case "Employee":
                root = loadEmployeeView("Employee/EmployeeView.fxml");
                break;
            case "MessageRoomList":
                root = loadMessageRoomListView("Chat/MessageRoomListView.fxml");
                break;
            case "MessageRoom":
                root = loadMessageRoomView("Chat/MessageRoomView.fxml");
                break;
            case "CreateMessageRoom":
                root = loadMessageRoomListView("Chat/CreateMessageRoomView.fxml");
                break;
            //todo make createMessageRoom
            case "EditMessageRoom":
                root = loadMessageRoomListView("Chat/CreateMessageRoomView.fxml");
                break;
            //todo make editMessageRoom
            case "Login":
                root = loadLoginView("Login/LoginView.fxml");
                break;
            case "EventInfo" :
                root = loadEventInfoView("Event/EventInfoView.fxml");
                break;
            case "MyAccount":
                root = loadMyAccountView("Employee/EmployeeView.fxml");
                break;
            case "EditMyAccount":
                root = loadEditMyAccountView("Employee/EmployeeView.fxml");
                break;
            case "EventEmployee":
                root = loadEventEmployeeViewController("Event/EventEmployeeView.fxml");
                break;
            default:
                System.out.println("Unknown view");
                return;
        }
        currentStage.setRoot(root);

        String title = "Bruh app";
        if (root.getUserData() != null) {
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentStage);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.show();
    }




    private Region loadMainMenuView(String fxmlFile) {
        if (mainMenuViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                mainMenuViewController = loader.getController();
                mainMenuViewController.init(this, viewModelFactory.getMainMenuViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        viewModelFactory.getMainMenuViewModel().reset();
        return mainMenuViewController.getRoot();
    }

    private Region loadLoginView(String fxmlFile) {
        if (loginViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                loginViewController = loader.getController();
                loginViewController.init(this, viewModelFactory.getLoginViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loginViewController.getRoot();
    }

    private Region loadEventInfoView(String fxmlFile) {
        if (eventInfoViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                eventInfoViewController = loader.getController();
                eventInfoViewController.init(this, viewModelFactory.getEventInfoViewModel(), root, selectState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return eventInfoViewController.getRoot();
    }

    private Region loadEventEmployeeViewController(String fxmlFile) {
        if (eventEmployeeViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                eventEmployeeViewController = loader.getController();
                eventEmployeeViewController.init(this, viewModelFactory.getEmployeeListViewModel(),
                        root, selectState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return eventEmployeeViewController.getRoot();
    }

    private Region loadRoomListView(String fxmlFile) {
        if (roomListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                roomListViewController = loader.getController();
                roomListViewController.init(this, viewModelFactory.getRoomListViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        viewModelFactory.getRoomListViewModel().reset();
        return roomListViewController.getRoot();
    }

    private Region loadCreateRoomView(String fxmlFile) {
        if (createRoomViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createRoomViewController = loader.getController();
                createRoomViewController.init(this, viewModelFactory.getCreateRoomViewModel(), root);
            } catch (Exception e) {
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

    private Region loadEditRoomView(String fxmlFile) {
        if (createRoomViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createRoomViewController = loader.getController();
                createRoomViewController.init(this, viewModelFactory.getCreateRoomViewModel(), root);
            } catch (Exception e) {
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

    private Region loadRoomView(String fxmlFile) {
        if (createRoomViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createRoomViewController = loader.getController();
                createRoomViewController.init(this, viewModelFactory.getCreateRoomViewModel(), root);
            } catch (Exception e) {
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


    private Region loadCreateEventView(String fxmlFile) {
        if (createEventViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createEventViewController = loader.getController();
                createEventViewController.init(this, viewModelFactory.getCreateEventViewModel(), root, model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createEventViewController.getRoot();
    }

    private Region loadEventListView(String fxmlFile) {
        if (eventListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                eventListViewController = loader.getController();
                eventListViewController.init(this, viewModelFactory.getEventListViewModel(), root,
                        selectState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return eventListViewController.getRoot();
    }

    private Region loadEditEventView(String fxmlFile) {
        if (editEventViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                editEventViewController = loader.getController();
                editEventViewController.init(this, viewModelFactory.getEditEventViewModel(),
                        root, selectState, model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return editEventViewController.getRoot();
    }

    private Region loadEmployeeListView(String fxmlFile) {
        if (employeeListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                employeeListViewController = loader.getController();
                employeeListViewController.init(this, viewModelFactory.getEmployeeListViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        viewModelFactory.getEmployeeListViewModel().reset();
        return employeeListViewController.getRoot();
    }

    private Region loadCreateEmployeeView(String fxmlFile) {
        if (employeeViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                employeeViewController = loader.getController();
                employeeViewController.init(this, viewModelFactory.getEmployeeViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        employeeViewController.setOpenedFromMenu(false);
        employeeViewController.setEditing(false);
        employeeViewController.setViewing(false);
        viewModelFactory.getEmployeeViewModel().setCurrentEmployeeID(0);
        viewModelFactory.getEmployeeViewModel().setOnlyViewing(false);

        viewModelFactory.getEmployeeViewModel().reset();
        return employeeViewController.getRoot();
    }

    private Region loadEditEmployeeView(String fxmlFile) {
        if (employeeViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                employeeViewController = loader.getController();
                employeeViewController.init(this, viewModelFactory.getEmployeeViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        employeeViewController.setOpenedFromMenu(false);
        employeeViewController.setEditing(true);
        employeeViewController.setViewing(false);
        viewModelFactory.getEmployeeViewModel().setCurrentEmployeeID(pickedEmployeeID);
        viewModelFactory.getEmployeeViewModel().setOnlyViewing(false);

        viewModelFactory.getEmployeeViewModel().reset();
        return employeeViewController.getRoot();
    }

    private Region loadEmployeeView(String fxmlFile) {
        if (employeeViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                employeeViewController = loader.getController();
                employeeViewController.init(this, viewModelFactory.getEmployeeViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        employeeViewController.setOpenedFromMenu(false);
        employeeViewController.setEditing(false);
        employeeViewController.setViewing(true);
        viewModelFactory.getEmployeeViewModel().setCurrentEmployeeID(pickedEmployeeID);
        viewModelFactory.getEmployeeViewModel().setOnlyViewing(true);

        viewModelFactory.getEmployeeViewModel().reset();
        return employeeViewController.getRoot();
    }

    private Region loadMyAccountView(String fxmlFile)
    {
        if (employeeViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                employeeViewController = loader.getController();
                employeeViewController.init(this, viewModelFactory.getEmployeeViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        employeeViewController.setOpenedFromMenu(true);
        employeeViewController.setEditing(false);
        employeeViewController.setViewing(true);
        viewModelFactory.getEmployeeViewModel().setCurrentEmployeeID(model.getLoggedClientID());
        viewModelFactory.getEmployeeViewModel().setOnlyViewing(true);

        viewModelFactory.getEmployeeViewModel().reset();
        return employeeViewController.getRoot();
    }

    private Region loadEditMyAccountView(String fxmlFile) {
        if (employeeViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                employeeViewController = loader.getController();
                employeeViewController.init(this, viewModelFactory.getEmployeeViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        employeeViewController.setOpenedFromMenu(true);
        employeeViewController.setEditing(true);
        employeeViewController.setViewing(false);
        viewModelFactory.getEmployeeViewModel().setCurrentEmployeeID(model.getLoggedClientID());
        viewModelFactory.getEmployeeViewModel().setOnlyViewing(false);

        viewModelFactory.getEmployeeViewModel().reset();
        return employeeViewController.getRoot();
    }


    private Region loadMessageRoomListView(String fxmlFile) {
        if (messageRoomListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                messageRoomListViewController = loader.getController();
                messageRoomListViewController.init(this, viewModelFactory.getMessageRoomListViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        messageRoomListViewController.reset();
        return messageRoomListViewController.getRoot();
    }

    private Region loadMessageRoomView(String fxmlFile) {
        if (messageRoomViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                messageRoomViewController = loader.getController();
                messageRoomViewController.init(this, viewModelFactory.getMessageRoomViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        viewModelFactory.getMessageRoomViewModel().setMessageRoomID(pickedMessageRoomID);
        messageRoomViewController.setPrivate(model.getMessageRoomByID(pickedMessageRoomID).isPrivate());

        messageRoomViewController.reset();
        return messageRoomViewController.getRoot();
    }


    public void setPickedRoomID(int pickedRoomID) {
        this.pickedRoomID = pickedRoomID;
    }

    public int getPickedRoomID() {
        return pickedRoomID;
    }

    public void setPickedEmployeeID(int pickedEmployeeID) {
        this.pickedEmployeeID = pickedEmployeeID;
    }

    public int getPickedEmployeeID() {
        return pickedEmployeeID;
    }

    public void setPickedMessageRoomID(int messageRoomID) {
        pickedMessageRoomID = messageRoomID;
    }

    public int getPickedMessageRoomID() {
        return pickedMessageRoomID;
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
