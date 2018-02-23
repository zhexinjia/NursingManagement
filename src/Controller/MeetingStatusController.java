package Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MeetingStatusController {
	@FXML VBox box;
	Loader loader = new Loader();

	@FXML
	void searchButton() {
		
	}
	
	@FXML
	void resetButton() {
		
	}
	
    @FXML
    void contactButton() {

    }
    
    @FXML
    void detailButton() {
    		loader.loadVBox(box, "/View/MeetingDetail.fxml");
    }

}

