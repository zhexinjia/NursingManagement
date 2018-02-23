package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class TestModifyController implements Initializable {
	Loader loader = new Loader();
	@FXML TableView<String[]> tableView;
	@FXML Label countLabel;
	@FXML VBox box;
	
	@FXML private CustomTextField searchField;

	@FXML
	void homeButton() {
		
	}
	
	@FXML
	void leveloneButton() {
		
	}
	
	@FXML
	void leveltwoButton() {
		
	}
	
	@FXML
	void contactButton() {
		
	}
	
    @FXML
    void searchButton() {
    		
    }

    @FXML
    void resetButton() {

    }

    @FXML
    void importButton() {

    }

    @FXML
    void exportButton() {

    }



    @FXML
    void newButton() {
    		loader.loadVBox(box, "/View/UserNew.fxml");
    }

    @FXML
    void modifyButton() {
    		loader.loadVBox(box, "/View/UserModify.fxml");
    }

    @FXML
    void deleteButton() {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	private void setupTable() {
		
	}

}