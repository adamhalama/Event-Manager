package client.View.Event;

import client.View.SelectState;
import client.View.ViewHandler;
import client.ViewModel.EventInfoViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class EventInfoViewController {
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea desArea;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label platformLabel;
    @FXML
    private Label linkLabel;
    @FXML
    private Label roomLabel;
    @FXML
    private TextArea employeeInfoArea;
    @FXML
    private Label createTimeLabel;

    private int id;
    private EventInfoViewModel viewModel;
    private ViewHandler viewHandler;
    private Region root;
    private SelectState selectState;

    public EventInfoViewController() {
    }

    public void init(ViewHandler viewHandler, EventInfoViewModel viewModel, Region root,
                     SelectState state) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        this.selectState = state;
        this.id = state.getEditSelect();

        this.titleTextField.setText(viewModel.getTitle(id));
        this.desArea.setText(viewModel.getDes(id));
        this.startTimeLabel.setText(viewModel.getStart(id));
        this.endTimeLabel.setText(viewModel.getEnd(id));
        if (viewModel.getTYpe(id)){
            this.typeLabel.setText("Online");
            this.platformLabel.setText(viewModel.getPlatform(id));
            this.linkLabel.setText(viewModel.getLink(id));
            this.roomLabel.setText("");
        } else {
            this.typeLabel.setText("Physical");
            this.platformLabel.setText("");
            this.linkLabel.setText("");
            this.roomLabel.setText(String.valueOf(viewModel.getRoom(id)));
        }
        this.employeeInfoArea.setText(viewModel.getParticipantCreatorInfo(id));
        this.createTimeLabel.setText(viewModel.getCreate(id));
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void backPress() {
        viewHandler.openView("EventList");
    }
}
