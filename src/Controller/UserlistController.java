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

public class UserlistController implements Initializable {	
	@FXML TableView<HashMap<String,String>> tableView;
	@FXML Label countLabel;
	@FXML VBox box;
	@FXML private CustomTextField searchField;
	Loader loader = new Loader();
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();	
	String[] keys = {"name", "ssn", "department", "title", "position", "level"};
	String[] fields = {"姓名", "工号", "科室", "职称", "职务", "层级"};
	public static HashMap<String, String> selectedUser;
	PopupWindow popUP = new PopupWindow();
	DBhelper dbHelper = new DBhelper();
	
	private String hospitalID;
	
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
    void importButton() {
    		//FIXME: wrong keys and fields, import both primary and sub
    		ArrayList<HashMap<String, String>> importlist = loader.importExcel(keys, fields);
    		if(importlist!=null) {
    			dbHelper.insertUserList(importlist);
    		}else {
    			PopupWindow pop = new PopupWindow();
    			pop.errorWindow();
    		}
    }

    @FXML
    void exportButton() {
    		loader.exportExcel(list, fields, keys);
    }



    @FXML
    void newButton() {
    		loader.loadVBox(box, "/View/UserNew.fxml");
    }

    @FXML
    void modifyButton() {
    		selectedUser = tableView.getSelectionModel().getSelectedItem();
    		if(selectedUser == null) {
    			popUP.alertWindow("没有选中目标","请选择要编辑的用户");
    		}else {
    			loader.loadVBox(box, "/View/UserModify.fxml");
    		}
    }

    @FXML
    void deleteButton() {
    		HashMap<String, String> selected = tableView.getSelectionModel().getSelectedItem();
    		if(selected == null) {
    			popUP.alertWindow("没有选中目标","请选择要编辑的用户");
    		}else {
    			//FIXME:delete all informations from different tables using one SQL statement
    			//FIXME: delete 3 tables
    			if (dbHelper.deleteUser(selected)) {
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
		list = dbHelper.getEntireList("user_primary_info");
	}
	
	private void reload() {
		ObservableList<HashMap<String, String>> searchList = loader.search(list, searchField.getText());
		tableView.setItems(searchList);
		countLabel.setText("共 " +searchList.size()+ " 条");
	}

}
