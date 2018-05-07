package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class WelcomeController implements Initializable {

    @FXML
    private VBox box;
    
    //Branch page start
    @FXML private JFXComboBox<String> branchPicker;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		if(LoginController.branch.equals("0")) {
			System.out.println("0");
		}
	}
    
    @FXML void changePW() {
    		
    }
    @FXML void newBranch() {
    	
    }
    //Branch page end

    @FXML
    void downloadMultiTemplate() {

    }

    @FXML
    void downloadSingleTemplate() {

    }

    @FXML
    void downloadTfTemplate() {

    }

    @FXML
    void downloadTrainingTemplate() {

    }

    @FXML
    void downloadUserGuide() {

    }

    @FXML
    void downloadUserTemplate() {

    }
	

}
