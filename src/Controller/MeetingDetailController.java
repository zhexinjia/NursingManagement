package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import Model.Loader;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class MeetingDetailController implements Initializable {
	@FXML VBox box;
	Loader loader = new Loader();
	@FXML TableView<HashMap<String, String>> tableView;
	@FXML CustomTextField searchField;
	@FXML Label countLabel;
	
	ArrayList<HashMap<String, String>> list;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = MeetingStatusController.passedMeetingHistory;
		setupTable();
		reload();
	}

	@FXML
	void searchButton() {
		reload();
	}
	
	@FXML
	void resetButton() {
		searchField.setText("");
		reload();
	}
	
    @FXML
    void contactButton() {
    		loader.loadWeb();
    }
    private void setupTable() {
    		String[] keys = {"name", "department_id", "position", "title", "level", "checkin", "checkout"};
    		String[] fields = {"姓名", "科室", "职位", "职称", "层级", "签到", "签出"};
    		loader.setupTable(tableView, keys, fields);
    }
    
    private void reload() {
    		ObservableList<HashMap<String, String>> searchList = loader.search(list, searchField.getText());
		tableView.setItems(searchList);
		countLabel.setText("共 " +searchList.size()+ " 条");
    }

	
    

}

