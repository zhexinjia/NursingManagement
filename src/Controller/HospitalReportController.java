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

public class HospitalReportController implements Initializable {	
	@FXML TableView<HashMap<String,String>> tableView;
	@FXML Label countLabel;
	@FXML VBox box;
	@FXML private CustomTextField searchField;
	Loader loader = new Loader();
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();	
	String[] keys = {"name", "department", "date", "title","status"};
	String[] fields = {"上报员工", "所属科室", "上报时间", "上报事件", "处理情况"};
	PopupWindow popUP = new PopupWindow();
	DBhelper dbHelper = new DBhelper();
	
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
    void modifyButton() {
    		
    }

    @FXML
    void deleteButton() {
    		HashMap<String, String> selected = tableView.getSelectionModel().getSelectedItem();
    		if(selected == null) {
    			popUP.alertWindow("没有选中目标","请选择要编辑的用户");
    		}else {
    			if (dbHelper.delete(selected, "report_list")) {
    				getList();
    				reload();
    			}else {
    				popUP.alertWindow("操作失败", "删除用户失败");
    			}
    		}
    }
	
	private void setupTable() {
		loader.setupTable(tableView, keys, fields);
	}
	
	private void getList() {
		//String[] searchColumn = {};
		//String[] values;
		String tableName = "user_primary_info right join report_list on user_primary_info.ssn = report_list.ssn";
		String[] columns = {"report_list.id","report_list.date", "report_list.title", "report_list.level", "user_primary_info.name", "user_primary_info.department"};
		list = dbHelper.getList(tableName, columns );
		for(HashMap<String, String> map:list) {
			if(map.get("level").equals("1")) {
				map.put("status", "上报至科室");
			}
			if(map.get("level").equals("2")) {
				map.put("status", "科室已讨论/上报医院");
			}
			if(map.get("level").equals("3")) {
				map.put("status", "医院已处理");
			}
		}
	}
	
	private void reload() {
		ObservableList<HashMap<String, String>> searchList = loader.search(list, searchField.getText());
		tableView.setItems(searchList);
		countLabel.setText("共 " +searchList.size()+ " 条");
	}

}