package Controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import Model.DBhelper;
import Model.Loader;
import Model.PopupWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class MeetingNewController implements Initializable{

    @FXML private VBox box;
    @FXML private JFXTextField nameField;
    @FXML private JFXComboBox<String> if_countPicker;
    @FXML private JFXComboBox<Integer> pointPicker;
    @FXML private JFXDatePicker datePicker;
    @FXML private JFXTimePicker timePicker;
    
    private Random random = new Random();
    Loader loader = new Loader();
    private String loginCode;
    private String logoutCode;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    		setupChoiceBox();
    		setupDatePicker();
	}

    @FXML
    void contactButton() {

    }

    @FXML
    void generateLogin() {
    		String login = generateCode();
    		if(loader.printBarCode(login)) {
    			loginCode = login;
    		}else {
    			loginCode = null;
    		}
    }

    @FXML
    void generateLogout() {
    		String logout = generateCode();
    		if(loader.printBarCode(logout)) {
    			logoutCode = logout;
    		}else {
    			logoutCode = null;
    		}
    }

    @FXML
    void saveButton() {
    		PopupWindow pop = new PopupWindow();
    		if(validate()) {
    			HashMap<String, String> map = new HashMap<String, String>();
    			map.put("name", nameField.getText());
    			map.put("date", datePicker.getValue().format(dateFormat));
    			map.put("time", timePicker.getValue().format(timeFormat));
    			map.put("checkinCode", loginCode);
    			map.put("checkOutCode", logoutCode);
    			map.put("if_count", if_countPicker.getValue().toString());
    			if(if_countPicker.getValue().toString().equals("是")) {
    				map.put("point", pointPicker.getValue().toString());
    			}
    			DBhelper dbHelper = new DBhelper();
    			if (dbHelper.insert(map, "meeting_list")) {
    				pop.confirmWindow("新建成功", "会议创建成功！");
    				pop.confirmButton.setOnAction(e->{
    					loader.loadVBox(box, "/View/MeetingList.fxml");
    				});
    			}else {
    				pop.errorWindow();
    			}
    		}else {
    			pop.alertWindow("保存失败", "选项不能为空。");
    		}
    }
    
    private void setupChoiceBox() {
    		String[] list = {"是","否"};
    		if_countPicker.getItems().setAll(list);
    		if_countPicker.setOnAction(e->{
    			if(if_countPicker.getSelectionModel().getSelectedItem().equals("是")) {
    				pointPicker.setDisable(false);
    			}else if(if_countPicker.getSelectionModel().getSelectedItem().equals("否")){
    				pointPicker.setValue(null);
    				pointPicker.setDisable(true);
    			}
    		});
    		Integer[] array = new Integer[100];
    		Arrays.setAll(array, i -> i + 1);
    		pointPicker.getItems().setAll(array);
    }
    private void setupDatePicker() {
    		loader.setupDatePicker(datePicker);
    }
    
    private String generateCode() {
    		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		String code = dtf.format(now);
		int randomInt = random.nextInt(100);
		code += Integer.toString(randomInt);
		return code;
    }
    
    private boolean validate() {
    		if (nameField.getText().trim().isEmpty()) {
    			return false;
    		}
    		if(if_countPicker.getSelectionModel().getSelectedItem() == null) {
    			return false;
    		}else if(if_countPicker.getSelectionModel().getSelectedItem().equals("是")) {
    			if(pointPicker.getSelectionModel().getSelectedItem()==null) {
    				return false;
    			}
    		}
    		if(datePicker.getValue()==null) {
    			return false;
    		}
    		if(timePicker.getValue()==null) {
    			return false;
    		}
    		if(loginCode == null||logoutCode == null) {
    			return false;
    		}
    		return true;
    }

	

}
