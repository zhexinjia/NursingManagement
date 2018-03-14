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

public class TestListController implements Initializable {
	
	@FXML
	private VBox box;

    @FXML
    private CustomTextField searchField;

    @FXML
    private TableView<HashMap<String, String>> tableView;

    @FXML
    private Label countLabel;
    
    Loader loader = new Loader();
    DBhelper dbHelper;
    private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    String[] keys = {"examName", "publish_status", "user_count", "if_count"};
    String[] fields = {"考试名称", "发布状态", "参加考试人数","是否记分"};
    public static HashMap<String, String> selectedTest;
    
    @Override
 	public void initialize(URL location, ResourceBundle resources) {
    		dbHelper = new DBhelper();
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
    void publishButton() {
    		//未发布，已发布，已截止
    		selectedTest = tableView.getSelectionModel().getSelectedItem();
    		if(selectedTest==null) {
    			PopupWindow pop = new PopupWindow();
    			pop.alertWindow("发布失败", "请选中需要发布的考试。");
    		}else if (selectedTest.get("publish_status").equals("未发布")) {
    			loader.loadVBox(box, "/View/TestPublish.fxml");
    		}else {
    			PopupWindow popUP = new PopupWindow();
    			popUP.alertWindow("发布试卷失败", "试卷已经发布，无法重复发布。");
    		}
    }


    @FXML
    void newButton() {
    		//TODO: modify the box including a check box "记分"
    		PopupWindow popUP = new PopupWindow();
		popUP.confirmButton.setText("创建试卷");
		popUP.confirmButton.setOnAction(e->{
			if (!popUP.inputField.getText().trim().isEmpty()) {
				//modify Below
				String examName = popUP.inputField.getText();
				String ifCount = popUP.checkBox.isSelected()? "是":"否";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("examName", examName);
				map.put("if_count", ifCount);
				if(dbHelper.insert(map, "exam_list")) {
					//modify Above
					popUP.stage.close();
				}else {
					popUP.errorWindow();
				}
			}else {
				popUP.alertWindow("新建试卷出错", "试卷名称不能为空！");
			}
		});
		popUP.inputWindow("输入试卷名称", "输入试卷名称", true);
    }

    @FXML
    void modifyButton() {
    		selectedTest = tableView.getSelectionModel().getSelectedItem();
    		if(selectedTest!=null) {
    			loader.loadVBox(box, "/View/TestModify.fxml");
    		}else {
    			PopupWindow pop = new PopupWindow();
    			pop.alertWindow("编辑失败", "请选中需要编辑的考试。");
    		}
    }

    @FXML
    void deleteButton() {
    		HashMap<String, String> selected = tableView.getSelectionModel().getSelectedItem();
    		if (selected!=null) {
    			deleteFunction(selected);
    		}else {
    			PopupWindow pop = new PopupWindow();
    			pop.alertWindow("删除失败！", "请选中需要删除的考试。");
    		}
    }

	private void setupTable() {
		loader.setupTable(tableView, keys, fields);
	}
	private void getList() {
		//TODO: how to count total point??? should we remove it?
		String[] columns = {"id", "examName", "publish_status", "single_point", "multi_point", "tf_point", "if_count"};
		list = dbHelper.getList("exam_list", columns);
	}
	private void reload() {
		ObservableList<HashMap<String, String>> searchList = loader.search(list, searchField.getText());
		tableView.setItems(searchList);
		countLabel.setText("共 " +searchList.size()+ " 条");
	}
	
	private void deleteFunction(HashMap<String, String> map) {		
		PopupWindow popUP = new PopupWindow();
		popUP.confirmButton.setOnAction(e->{
			if(dbHelper.deleteExam(map)) {
				getList();
				reload();
			}
		});
		popUP.confirmWindow("确认要删除考卷吗？", "删除考卷将删除所有与本考卷相关信息");
	}

}

