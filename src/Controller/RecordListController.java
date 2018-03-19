package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import Model.DBhelper;
import Model.Loader;
import Model.PopupWindow;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class RecordListController implements Initializable {
	Loader loader = new Loader();
	@FXML TableView<HashMap<String, String>> tableView;
	@FXML Label countLabel;
	@FXML VBox box;
	
	@FXML private CustomTextField searchField;
	DBhelper dbHelper = new DBhelper();
	private ArrayList<HashMap<String, String>> list;
	public static HashMap<String, String> selectedUser;
	
	String[] keys = {"name", "department", "position", "title", "level", "currentScore", "totalScore", "percent"};
	String[] fields = {"姓名", "科室", "职务", "职称", "层级", "得分", "总分", "百分比"};
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupTable();
		getList();
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
    void exportButton() {
    		//TODO
    }


    @FXML
    void modifyButton() {
    		//TODO
    }

    @FXML
    void detailButton() {
    		selectedUser = tableView.getSelectionModel().getSelectedItem();
    		if(selectedUser!=null) {
    			loader.loadVBox(box, "/View/RecordDetail.fxml");
    		}else {
    			PopupWindow pop = new PopupWindow();
    			pop.alertWindow("操作失败", "请选中一个用户。");
    		}
    }
	
	private void setupTable() {
		loader.setupTable(tableView, keys, fields);
	}
	
	private void getList() {
		String tableName = "user_primary_info inner join user_score on user_primary_info.ssn = user_score.ssn";
		String[] columns = {"user_primary_info.department", "user_primary_info.name", "user_primary_info.position", "user_primary_info.title", 
				"user_primary_info.level", "user_score.totalScore	", "user_score.currentScore", "user_primary_info.ssn", "user_score.comment"};
		list = dbHelper.getList(tableName, columns);
		for(HashMap<String, String> map:list) {
			String current = map.get("currentScore");
			String total = map.get("totalScore");
			if(current!=null && total != null) {
				double currentScore = Double.parseDouble(current);
				double totalScore = Double.parseDouble(total);
				double percentScore = 100*currentScore/totalScore;
				map.put("percent", Double.toString(percentScore)+"%");
			}
		}
}
	
	private void reload() {
		ObservableList<HashMap<String, String>> searchList = loader.search(list, searchField.getText());
		tableView.setItems(searchList);
		countLabel.setText("共 " +searchList.size()+ " 条");
	}

}
