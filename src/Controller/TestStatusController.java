package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

public class TestStatusController implements Initializable {
	
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
    void endTestButton() {

    }

    @FXML
    void detailButton() {
    		loader.loadVBox(box, "/View/TestDetail.fxml");
    }

    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
