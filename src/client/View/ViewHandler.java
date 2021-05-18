package client.View;


import client.Model.Model;
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

    public ViewHandler(ViewModelFactory viewModelFactory, Model model) {
        this.viewModelFactory = viewModelFactory;
        this.model = model;
        currentStage = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("CreateRoom");
    }

    public void openView(String id) {
        Region root = null;
        switch (id) {
            case "CreateEvent":
                root = loadCreateEventView("CreateEventView.fxml");
                break;
            case "CreateRoom":
                root = loadCreateRoomView("CreateRoomView.fxml");
        }
        currentStage.setRoot(root);

        String title = "Chat";
        if (root.getUserData() != null) {
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentStage);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.show();
    }

    private Region loadCreateRoomView(String fxmlFile) {
        if (createRoomViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createRoomViewController = loader.getController();
                createRoomViewController.init(this, viewModelFactory.getCreateRoomViewModel(), root, false);
                // todo change the hardcoded FALSE
                // or make loadEditRoomView
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createRoomViewController.getRoot();
    }

    public void closeView() {
        primaryStage.close();
    }

    private Region loadCreateEventView(String fxmlFile) {
        if (createEventViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createEventViewController = loader.getController();
                createEventViewController.init(this, viewModelFactory.getCreateEventViewModel(), root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createEventViewController.getRoot();
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
