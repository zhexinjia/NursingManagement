package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Loader {
	public void loadVBox(VBox vbox, String path) {
		BorderPane mainPane = (BorderPane) vbox.getParent();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(path));
			VBox mainView;
			mainView = (VBox) loader.load();
			mainPane.setCenter(mainView);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
