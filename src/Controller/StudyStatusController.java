package Controller;

import Model.Loader;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class StudyStatusController {
	@FXML VBox box;
	Loader loader = new Loader();

    @FXML
    void contactButton() {

    }
    @FXML
    void studyDetailButton() {
    		loader.loadVBox(box, "/View/StudyDetail.fxml");
    }
    @FXML
    void trainningDetailButton() {
    		loader.loadVBox(box, "/View/TrainningDetail.fxml");
    }

}

