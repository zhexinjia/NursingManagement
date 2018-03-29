package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;

import Model.DBhelper;
import Model.PopupWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UserModifyController implements Initializable {
    @FXML
    private VBox box;
    
    @FXML
    private JFXTabPane tabPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ssnField;

    @FXML
    private ChoiceBox<String> departmentChoiceBox;

    @FXML
    private ChoiceBox<String> titleChoiceBox;

    @FXML
    private ChoiceBox<String> positionChoiceBox;

    @FXML
    private ChoiceBox<String> levelChoiceBox;

    @FXML
    private ChoiceBox<String> sexChoiceBox;

    @FXML
    private DatePicker birthPicker;

    @FXML
    private TextField regionField;

    @FXML
    private DatePicker dateJoinPartyPicker;

    @FXML
    private DatePicker timeStartWorkPicker;

    @FXML
    private DatePicker certifactionDatePicker;

    @FXML
    private TextField certifactionNumField;

    @FXML
    private TextField technicalPositionField;

    @FXML
    private DatePicker dateReceivedTPPicker;

    @FXML
    private DatePicker hireDatePicker;

    @FXML
    private DatePicker N0Picker;

    @FXML
    private DatePicker N1Picker;

    @FXML
    private DatePicker N2Picker;

    @FXML
    private DatePicker N3Picker;

    @FXML
    private DatePicker N4Picker;

    @FXML
    private DatePicker N5Picker;

    @FXML
    private TextField primaryEdField;

    @FXML
    private DatePicker primaryEdTimePicker;

    @FXML
    private TextField primaryEdSchoolField;

    @FXML
    private TextField highestEdField;

    @FXML
    private DatePicker highestEdTimePicker;

    @FXML
    private TextField highestEdSchoolField;
    
    HashMap<String, String> userPrimary;
    HashMap<String, String> userSub;
    DBhelper dbHelper;
    ArrayList<String> departmentList = new ArrayList<String>();
    ArrayList<HashMap<String, String>> departmentHashMaplist;
    String ssn;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    		dbHelper = new DBhelper();
		setupChoiceBox();
		userPrimary = UserlistController.selectedUser;
		ssn = userPrimary.get("ssn");
		String[] searchColumn = {"ssn"};
		String[] values = {ssn};
		userSub = dbHelper.getEntireList(searchColumn, values, "user_sub_info").get(0);
		loadPage();
		
	}

    //update modified user
    @FXML
    void addButton() {
    		if(validate()) {
    			HashMap<String, String> map = getHashMap();
    			dbHelper.updateUser(map);
    		}else {
    			PopupWindow popUP = new PopupWindow();
    			popUP.alertWindow("出错啦。。。", "姓名和工号不能为空");
    		}
    }

    //navigate to website
    @FXML
    void contactButton() {
    		
    }

    //navigate to next tabPane page
    @FXML
    void nextPane() {
    		tabPane.getSelectionModel().selectNext();
    }
    
    //setup all choiceBox();
    private void setupChoiceBox() {
    		//setup department ChoiceBox
    		getDepartment();
    		departmentChoiceBox.getItems().addAll(departmentList);
    		
    		//setup title ChoiceBox
    		String[] titleList = {"初级职称", "中级职称", "正高级职称", "高级职称"};
    		titleChoiceBox.getItems().addAll(titleList);
    		
    		//setup position ChoiceBox
    		String[] positionList = {"无", "副护士长", "护士长", "科护长", "护理部副主任", "护理部主任"};
    		positionChoiceBox.getItems().addAll(positionList);
    		
    		//setup level ChoiceBox
    		String[] levelList = {"N0", "N1", "N2", "N3", "N4", "N5"};
    		levelChoiceBox.getItems().addAll(levelList);
    		
    		//setup Sex ChoiceBox
    		String[] sexList = {"男", "女", "其他"};
    		sexChoiceBox.getItems().addAll(sexList);
    }
    
    //modify the HashMap, return modified HashMap
    /*
    private HashMap<String, String> getHashMap() {
    		HashMap<String, String> output = new HashMap<String, String>();
    		output.put("ssn", ssnField.getText());
    		output.put("name", nameField.getText());
    		//FIXME: if not using deaprtment_id in future, change it
    		output.put("department", departmentChoiceBox.getSelectionModel().getSelectedItem());
    		
    		output.put("position", positionChoiceBox.getSelectionModel().getSelectedItem());
    		output.put("title", titleChoiceBox.getSelectionModel().getSelectedItem());
    		output.put("level", levelChoiceBox.getSelectionModel().getSelectedItem());
    		if(birthPicker.getValue()!=null) {
    			output.put("birth", birthPicker.getValue().toString());
    		}    		
    		output.put("sex", sexChoiceBox.getSelectionModel().getSelectedItem());
    		output.put("region", regionField.getText());
    		if(dateJoinPartyPicker.getValue()!=null) {
        		output.put("dateJoinParty", dateJoinPartyPicker.getValue().toString());
    		}    		
    		if(timeStartWorkPicker.getValue()!=null) {
        		output.put("timeStartWork", timeStartWorkPicker.getValue().toString());
    		}
    		if(certifactionDatePicker.getValue()!=null) {
        		output.put("certifactionDate", certifactionDatePicker.getValue().toString());
    		}
    		output.put("certifactionNum", certifactionNumField.getText());
    		output.put("technicalPosition", technicalPositionField.getText());
    		if(dateReceivedTPPicker.getValue()!=null) {
        		output.put("dateReceivedTP", dateReceivedTPPicker.getValue().toString());
    		}
    		if(hireDatePicker.getValue()!=null) {
    			output.put("hireDate", hireDatePicker.getValue().toString());
    		}
    		if(N0Picker.getValue()!=null) {
    			output.put("N0", N0Picker.getValue().toString());
    		}
    		if(N1Picker.getValue()!=null) {
    			output.put("N1", N1Picker.getValue().toString());
    		}
    		if(N2Picker.getValue()!=null) {
    			output.put("N2", N2Picker.getValue().toString());
    		}
    		if(N3Picker.getValue()!=null) {
    			output.put("N3", N3Picker.getValue().toString());
    		}
    		if(N4Picker.getValue()!=null) {
    			output.put("N4", N4Picker.getValue().toString());
    		}
    		if(N5Picker.getValue()!=null) {
    			output.put("N5", N5Picker.getValue().toString());
    		}
    		output.put("primaryEd", primaryEdField.getText());
    		if(primaryEdTimePicker.getValue()!=null) {
        		output.put("primaryEdTime", primaryEdTimePicker.getValue().toString());
    		}
    		output.put("primaryEdSchool", primaryEdSchoolField.getText());
    		output.put("highestEd", highestEdField.getText());
    		if(highestEdTimePicker.getValue()!=null) {
    			output.put("highestEdTime", highestEdTimePicker.getValue().toString());
    		}
    		output.put("highestEdSchool", highestEdSchoolField.getText());
    		return output;
    }
    */
    private HashMap<String, String> getHashMap() {
		HashMap<String, String> output = new HashMap<String, String>();
		output.put("ssn", ssnField.getText());
		output.put("name", nameField.getText());
		if(departmentChoiceBox.getSelectionModel().getSelectedItem()!=null) {
			output.put("department", departmentChoiceBox.getSelectionModel().getSelectedItem());
		}
		//output.put("department", departmentChoiceBox.getSelectionModel().getSelectedItem());
		if(positionChoiceBox.getSelectionModel().getSelectedItem() != null) {
			output.put("position", positionChoiceBox.getSelectionModel().getSelectedItem());
		}
		//output.put("position", positionChoiceBox.getSelectionModel().getSelectedItem());
		if(titleChoiceBox.getSelectionModel().getSelectedItem()!=null) {
			output.put("title", titleChoiceBox.getSelectionModel().getSelectedItem());
		}
		//output.put("title", titleChoiceBox.getSelectionModel().getSelectedItem());
		if(levelChoiceBox.getSelectionModel().getSelectedItem()!=null) {
			output.put("level", levelChoiceBox.getSelectionModel().getSelectedItem());
		}
		//output.put("level", levelChoiceBox.getSelectionModel().getSelectedItem());
		if(birthPicker.getValue()!=null) {
			output.put("birth", birthPicker.getValue().toString());
		}    		
		if(sexChoiceBox.getSelectionModel().getSelectedItem()!=null) {
			output.put("sex", sexChoiceBox.getSelectionModel().getSelectedItem());
		}
		//output.put("sex", sexChoiceBox.getSelectionModel().getSelectedItem());
		if(regionField.getText() != null) {
			output.put("region", regionField.getText());
		}
		//output.put("region", regionField.getText());
		if(dateJoinPartyPicker.getValue()!=null) {
    		output.put("dateJoinParty", dateJoinPartyPicker.getValue().toString());
		}    		
		if(timeStartWorkPicker.getValue()!=null) {
    		output.put("timeStartWork", timeStartWorkPicker.getValue().toString());
		}
		if(certifactionDatePicker.getValue()!=null) {
    		output.put("certifactionDate", certifactionDatePicker.getValue().toString());
		}
		if(certifactionNumField.getText()!=null) {
			output.put("certifactionNum", certifactionNumField.getText());
		}
		//output.put("certifactionNum", certifactionNumField.getText());
		output.put("technicalPosition", technicalPositionField.getText());
		if(dateReceivedTPPicker.getValue()!=null) {
    		output.put("dateReceivedTP", dateReceivedTPPicker.getValue().toString());
		}
		if(hireDatePicker.getValue()!=null) {
			output.put("hireDate", hireDatePicker.getValue().toString());
		}
		if(N0Picker.getValue()!=null) {
			output.put("N0", N0Picker.getValue().toString());
		}
		if(N1Picker.getValue()!=null) {
			output.put("N1", N1Picker.getValue().toString());
		}
		if(N2Picker.getValue()!=null) {
			output.put("N2", N2Picker.getValue().toString());
		}
		if(N3Picker.getValue()!=null) {
			output.put("N3", N3Picker.getValue().toString());
		}
		if(N4Picker.getValue()!=null) {
			output.put("N4", N4Picker.getValue().toString());
		}
		if(N5Picker.getValue()!=null) {
			output.put("N5", N5Picker.getValue().toString());
		}
		output.put("primaryEd", primaryEdField.getText());
		if(primaryEdTimePicker.getValue()!=null) {
    		output.put("primaryEdTime", primaryEdTimePicker.getValue().toString());
		}
		output.put("primaryEdSchool", primaryEdSchoolField.getText());
		output.put("highestEd", highestEdField.getText());
		if(highestEdTimePicker.getValue()!=null) {
			output.put("highestEdTime", highestEdTimePicker.getValue().toString());
		}
		output.put("highestEdSchool", highestEdSchoolField.getText());
		return output;
}
    //get list of department
    private void getDepartment() {
    		String[] columns = {"id", "departmentName"};
    		departmentHashMaplist = dbHelper.getList("hospital_department", columns);  
    		departmentList.add("无");
    		for(int i = 0; i< departmentHashMaplist.size(); i++) {
    			departmentList.add(departmentHashMaplist.get(i).get("departmentName"));
    		}
    }
    
    //check if name and ssn are entered
    private boolean validate() {
    		if(nameField.getText().trim().isEmpty() || ssnField.getText().trim().isEmpty()) {
    			return false;
    		}
    		return true;
    }
    
    //write message from HashMap to fields
    private void loadPage() {
    		//primary
    		nameField.setText(userPrimary.get("name"));
    		ssnField.setText(userPrimary.get("ssn"));
    		departmentChoiceBox.getSelectionModel().select(userPrimary.get("department"));
    		
    		titleChoiceBox.getSelectionModel().select(userPrimary.get("title"));
    		positionChoiceBox.getSelectionModel().select(userPrimary.get("position"));
    		levelChoiceBox.getSelectionModel().select(userPrimary.get("level"));
    		
    		//sub
    		sexChoiceBox.getSelectionModel().select(userSub.get("sex"));
    		birthPicker.setValue(date(userSub.get("birth")));
    		regionField.setText(userSub.get("region"));
    		dateJoinPartyPicker.setValue(date(userSub.get("dateJoinParty")));
    		timeStartWorkPicker.setValue(date(userSub.get("timeStartWork")));
    		certifactionDatePicker.setValue(date(userSub.get("certifactionDate")));
    		certifactionNumField.setText(userSub.get("certifactionNum"));
    		technicalPositionField.setText(userSub.get("technicalPosition"));
    		dateReceivedTPPicker.setValue(date(userSub.get("dateReceivedTP")));
    		hireDatePicker.setValue(this.date(userSub.get("dateReceivedTP")));
    		N0Picker.setValue(date(userSub.get("N0")));
    		N1Picker.setValue(date(userSub.get("N1")));
    		N2Picker.setValue(date(userSub.get("N2")));
    		N3Picker.setValue(date(userSub.get("N3")));
    		N4Picker.setValue(date(userSub.get("N4")));
    		N5Picker.setValue(date(userSub.get("N5")));
    		primaryEdField.setText(userSub.get("primaryEd"));
    		primaryEdTimePicker.setValue(date(userSub.get("primaryEdTime")));
    		primaryEdSchoolField.setText(userSub.get("primaryEdSchool"));
    		highestEdField.setText(userSub.get("highestEd"));
    		highestEdTimePicker.setValue(date(userSub.get("highestEdTime")));
    		highestEdSchoolField.setText(userSub.get("highestEdSchool"));
    }
    private LocalDate date(String dateString) {
    		if (dateString!=null) {
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        		LocalDate localDate = LocalDate.parse(dateString, formatter);
        		return localDate;
    		}
    		return null;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	

}


