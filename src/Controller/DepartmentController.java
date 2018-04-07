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

public class DepartmentController implements Initializable {
	@FXML TableView<HashMap<String, String>> tableView;
	@FXML Label countLabel;
	@FXML private CustomTextField searchField;
	@FXML VBox box;
	
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(); 
	Loader loader = new Loader();
	DBhelper dbHelper;
	public static HashMap<String, String> selectedDepartment;
	
	@FXML void loadHome() {
		loader.loadVBox(box, "/View/Welcome.fxml");
	}
	
	@FXML void contact() {
		loader.loadWeb();
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
    void newButton() {
    		PopupWindow popUP = new PopupWindow();
    		popUP.confirmButton.setText("创建科室");
    		popUP.confirmButton.setOnAction(e->{
    			if (!popUP.inputField.getText().trim().isEmpty()) {
    				String departmentName = popUP.inputField.getText();
    				HashMap<String, String> map = new HashMap<String, String>();
    				map.put("departmentName", departmentName);
    				if(dbHelper.insert(map, "hospital_department")) {
    					popUP.stage.close();
    					getList();
        				reload();
    				}
    			}else {
    				popUP.alertWindow("新建科室出错", "科室名称不能为空！");
    			}
    		});
    		popUP.inputWindow("输入科室名称", "输入科室名称", false);
    }

    @FXML
    void modifyButton() {
    		selectedDepartment = tableView.getSelectionModel().getSelectedItem();
    		if(loader.selectionCheck(selectedDepartment)) {
    			loader.loadVBox(box, "/View/DepartmentUsers.fxml");
    		}
    }

    @FXML
    void deleteButton() {
    		HashMap<String, String> selected = tableView.getSelectionModel().getSelectedItem();
    		if(loader.selectionCheck(selected)) {
    			PopupWindow popup = new PopupWindow();
    			popup.confirmButton.setOnAction(e->{
    				if(dbHelper.delete(selected, "hospital_department")) {
    	    				getList();
    	    				reload();
    	    			}
    			});
    		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dbHelper = new DBhelper();
		setupTable();
		getList();
		reload();
	}
	private void getList() {
		String tableName = "hospital_department left join user_primary_info on hospital_department.manager_ssn = user_primary_info.ssn";
		String[] columns = {"id", "departmentName", "user_primary_info.name", "manager_ssn"};
		list = dbHelper.getList(tableName, columns);
	}
	private void setupTable() {
		String[] keys = {"departmentName", "name"};
		String[] fields = {"科室名称", "科室管理"};
		loader.setupTable(tableView, keys, fields);
	}
	private void reload() {
		ObservableList<HashMap<String, String>> searchList = loader.search(list, searchField.getText());
		tableView.setItems(searchList);
		countLabel.setText("共 " +searchList.size()+ " 条");
	}

}
