package Controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import Model.DBhelper;
import Model.Loader;
import Model.PopupWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ReportModifyController implements Initializable{
	@FXML Label title;
	@FXML JFXTextArea personArea;
	@FXML JFXTextArea departmentArea;
	@FXML JFXTextArea hospitalArea;
	@FXML VBox box;
	
	private HashMap<String, String> report;
	Loader loader = new Loader();
	DBhelper dbHelper = new DBhelper();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		report = HospitalReportController.selectedReport;
		//setupPage();
	}
	
	@FXML void loadHome() {
		loader.loadVBox(box, "/View/Welcome.fxml");
	}
	
	@FXML void uploadButton() {
		System.out.println(report.get("level"));
		if(!report.get("level").equals("1")) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", report.get("id"));
			map.put("comment2", hospitalArea.getText());
			map.put("level", "3");
			if(dbHelper.update(map, "report_list")) {
				//TODO:???
			}
		}else {
			PopupWindow pop = new PopupWindow();
			pop.alertWindow("无法上传", "科室讨论未完成，不能上传医院处理意见");
		}
		
	}
	@FXML void contactButton() {
		loader.loadWeb();
	}
	
	private void setupPage() {
		title.setText(report.get("title"));
		personArea.setText(report.get("detail"));
		departmentArea.setText(report.get("comment1"));
		hospitalArea.setText(report.get("comment2"));
	}


}
