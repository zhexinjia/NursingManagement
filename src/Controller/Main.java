package Controller;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;

public class Main extends Application {
	
	private SplitPane splitPane;
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/Main.fxml"));
			splitPane = (SplitPane)loader.load();
			Scene scene = new Scene(splitPane);
			//remove for later
			primaryStage.setMaximized(true);
			primaryStage.setScene(scene);
			//primaryStage.setFullScreen(true);
			//primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
