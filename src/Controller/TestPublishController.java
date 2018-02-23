package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class TestPublishController implements Initializable {	
	@FXML TableView<String[]> tableView;
	@FXML Label countLabel;
	@FXML VBox box;
	@FXML private CustomTextField searchField;
	
	Loader loader = new Loader();
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();	


    @FXML
    void searchButton() {
    		
    }

    @FXML
    void resetButton() {

    }

    @FXML
    void importButton() {

    }

    @FXML
    void exportButton() {

    }



    @FXML
    void newButton() {
    		loader.loadVBox(box, "/View/UserNew.fxml");
    }

    @FXML
    void modifyButton() {
    		loader.loadVBox(box, "/View/UserModify.fxml");
    }

    @FXML
    void deleteButton() {
    }
    void aaa() {
    		System.out.println("hello word");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupTable();
	}
	
	private void setupTable() {
		String[]	fields= {"姓名","住址","科室"};
		ArrayList<String[]> lists = new ArrayList<String[]>();
		lists.add(new String[]{"张三", "哈哈哈哈哈", "心脏外科"});
		lists.add(new String[]{"李四", "自治州", "心脏内科"});
		lists.add(new String[]{"王五", "阿凡达舒服", "小儿科"});
		lists.add(new String[]{"欣欣", "哈史蒂芬森哈哈", "放射科"});
		lists.add(new String[]{"小星星", "哈成都哈哈", "胸外科"});
		lists.add(new String[]{"笨欣", "哈啊啊啊哈哈", "急诊科"});
		lists.add(new String[]{"张三", "哈哈哈哈哈", "心脏外科"});
		lists.add(new String[]{"李四", "自治州", "心脏内科"});
		lists.add(new String[]{"王五", "阿凡达舒服", "小儿科"});
		lists.add(new String[]{"欣欣", "哈史蒂芬森哈哈", "放射科"});
		lists.add(new String[]{"小星星", "哈成都哈哈", "胸外科"});
		lists.add(new String[]{"笨欣", "哈啊啊啊哈哈", "急诊科"});
		lists.add(new String[]{"张三", "哈哈哈哈哈", "心脏外科"});
		lists.add(new String[]{"李四", "自治州", "心脏内科"});
		lists.add(new String[]{"王五", "阿凡达舒服", "小儿科"});
		lists.add(new String[]{"欣欣", "哈史蒂芬森哈哈", "放射科"});
		lists.add(new String[]{"小星星", "哈成都哈哈", "胸外科"});
		lists.add(new String[]{"笨欣", "哈啊啊啊哈哈", "急诊科"});
		
		ObservableList<String[]> data = FXCollections.observableArrayList();
		data.addAll(lists);
		
		//安排好table的显示
		ArrayList<TableColumn<String[], String>> cols = new ArrayList<TableColumn<String[], String>>();
		for (int i = 0; i < 3; i++) {
			TableColumn<String[], String> col = new TableColumn<String[], String>(fields[i]);
			final int index = i;
			col.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>(){

				@Override
				public ObservableValue<String> call(CellDataFeatures<String[], String> param) {
					SimpleStringProperty ret = new SimpleStringProperty();
					ret.setValue(param.getValue()[index]);
					return ret;
				}
			});
			cols.add(col);
		}
		tableView.getColumns().setAll(cols);
		//table设置完毕
		tableView.setItems(data);
		
	}

}
