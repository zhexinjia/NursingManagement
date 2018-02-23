package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class TestListController implements Initializable {
	Loader loader = new Loader();
	@FXML
	private VBox box;

    @FXML
    private CustomTextField searchField;

    @FXML
    private TableView<?> tableView;

    @FXML
    private Label countLabel;

    @FXML
    void searchButton() {

    }

    @FXML
    void resetButton() {

    }

    @FXML
    void publishButton() {
    		loader.loadVBox(box, "/View/TestPublish.fxml");
    }


    @FXML
    void newButton() {

    }

    @FXML
    void modifyButton() {
    		loader.loadVBox(box, "/View/TestModify.fxml");
    }

    @FXML
    void deleteButton() {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

