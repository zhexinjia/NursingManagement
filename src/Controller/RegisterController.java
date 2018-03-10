package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {
	@FXML TextField userNameField;
	@FXML PasswordField passwordField;
	@FXML PasswordField passwordField2;
	@FXML TextField hospitalNameField;
	@FXML TextField codeField;
	
    @FXML private AnchorPane box;
    
    String userName;
    String hospitalName;
    public static String hospitalID;
    
    @FXML void contactButton(){
    		//FIXME:navigate to website
    }
    
    @FXML void loginButton() {
    		Stage currentStage = (Stage)box.getScene().getWindow();
    		currentStage.close();
    }
    
    @FXML void registerButton() {
    		register();
    }
    
    //TODO: register return string from server, display message
    private void register() {
    		System.out.println("register");
    }

}
