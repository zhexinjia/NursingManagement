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

public class DepartmentController implements Initializable {
	@FXML TableView<HashMap<String, String>> tableView;
	@FXML Label countLabel;
	@FXML private CustomTextField searchField;
	
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(); 
	String[] keys = {"departmentName", "manager"};
	String[] fields = {"科室名称", "科室管理"};
	Loader loader = new Loader();
	DBhelper dbHelper;
	String hospitalID;

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
    				map.put("hospital_id", hospitalID);
    				if(dbHelper.insert(map, "hospital_department")) {
    					popUP.stage.close();
    				}else {
    					popUP.errorWindow();
    				}
    			}else {
    				popUP.alertWindow("新建科室出错", "科室名称不能为空！");
    			}
    		});
    		popUP.inputWindow("输入科室名称", "输入科室名称", false);
    }

    @FXML
    void modifyButton() {
    		
    }

    @FXML
    void deleteButton() {
    		
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dbHelper = new DBhelper();
		hospitalID = LoginController.hospitalID;
		setupTable();
		getList();
		reload();
	}
	private void getList() {
		String[] columns = {"id", "departmentName", "manager"};
		String[] searchColumn = {"hospitalID"};
		String[] value = {hospitalID};
		list = dbHelper.getList(searchColumn, value, "hospital_department", columns);
	}
	private void setupTable() {
		loader.setupTable(tableView, keys, fields);
	}
	private void reload() {
		ObservableList<HashMap<String, String>> searchList = loader.search(list, searchField.getText());
		tableView.setItems(searchList);
		countLabel.setText("共 " +searchList.size()+ " 条");
	}

}
