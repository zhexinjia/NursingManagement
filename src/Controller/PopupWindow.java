package Controller;


import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupWindow {
	//used for all window
	JFXButton confirmButton = new JFXButton("确认");
	
	//used for inputWindow
	TextField inputField = new TextField();
	Stage stage;
	
	public void errorWindow() {
		Stage errorStage = new Stage(StageStyle.UNDECORATED);
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/ErrorMessage.fxml"));
			AnchorPane anchorPane = (AnchorPane)loader.load();
			Scene scene = new Scene(anchorPane);			
			errorStage.setScene(scene);
			//primaryStage.setFullScreen(true);
			//primaryStage.setResizable(false);
			errorStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("出错啦！");
		alert.setHeaderText(null);
		alert.setContentText("抱歉！出错啦，请联系技术人员。。。");
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add("/View/dialog.css");
		dialogPane.getStyleClass().add("my-dialog");
		ImageView error = new ImageView(this.getClass().getResource("/com/sun/javafx/scene/control/skin/modena/dialog-error.png").toString());
		alert.setGraphic(error);
		alert.showAndWait();
		*/
	}
	public void inputWindow(String title, String prompText) {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setWidth(400);
		stage.setHeight(180);
		stage.setTitle(title);
		VBox vbox = new VBox(2);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(0, 20, 0, 20));
		inputField.setPromptText(prompText);
		inputField.setPrefWidth(250);
		confirmButton.setPrefWidth(250);
		confirmButton.setStyle("-fx-background-color: #62baf0");
		VBox.setMargin(inputField, new Insets(0, 20, 20, 20));
		VBox.setMargin(confirmButton, new Insets(0, 20, 20, 20));
		vbox.getChildren().addAll(inputField, confirmButton);
		vbox.setStyle("-fx-background-color: #393f4f");
		
		Scene scene = new Scene(vbox);
		scene.getStylesheets().add("/View/application.css");
		stage.setScene(scene);
		stage.setResizable(true);
		stage.showAndWait();
	}
	
	
	public void confirmWindow(String header, String info) {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setWidth(400);
		stage.setHeight(180);
				
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(0, 20, 0, 20));
		Label title = new Label(header);
		title.setAlignment(Pos.CENTER_LEFT);
		title.setStyle("-fx-font-size:24;-fx-text-fill:white");
		Separator separator = new Separator();
		separator.setStyle("-fx-border-width:1");
		Label body = new Label(info);
		body.setAlignment(Pos.CENTER_LEFT);
		body.setStyle("-fx-font-size:18;-fx-text-fill:white");
		HBox hbox = new HBox();
		JFXButton cancelButton = new JFXButton("取消");
		//aaa.getStyleClass().add("jfxbutton-circle");
		//aaa.setStyle("-fx-background-color:black");
		hbox.setPadding(new Insets(0, 10, 10, 0));
		hbox.setAlignment(Pos.CENTER_RIGHT);
		HBox.setMargin(confirmButton, new Insets(10, 10, 10, 10));
		HBox.setMargin(cancelButton, new Insets(10, 0, 10, 10));
		
		
		
		confirmButton.setStyle("-fx-background-color: #62baf0");
		cancelButton.setStyle("-fx-background-color:#e4e4e4");
		confirmButton.setPrefWidth(100);
		cancelButton.setPrefWidth(100);
		
		
		
		
		
		cancelButton.setOnAction(e->stage.close());
		hbox.getChildren().addAll(confirmButton, cancelButton);
		
		
		vbox.getChildren().addAll(title, separator, body, hbox);
		vbox.setAlignment(Pos.CENTER_LEFT);
		
		
		vbox.setStyle("-fx-background-color: #393f4f");
		Scene scene = new Scene(vbox);
		scene.getStylesheets().add("/View/application.css");
		
		stage.setScene(scene);
		stage.setResizable(true);
		stage.showAndWait();
		
		
	}
	

}
