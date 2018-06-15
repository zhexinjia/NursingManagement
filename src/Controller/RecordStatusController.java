package Controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXComboBox;

import Model.DBhelper;
import Model.Loader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class RecordStatusController implements Initializable {

	@FXML VBox box;
	@FXML PieChart pieChart;
	@FXML Label averageField;
	@FXML Label highestField;
	@FXML Label lowestField;
	@FXML Label departmentHighest;
	@FXML Label departmentLowest;
	@FXML Label departmentAverage;
	@FXML JFXComboBox<String> departmentPicker;
	@FXML TableView<HashMap<String, String>> tableView;
	
	ArrayList<HashMap<String, String>> scoreList;
	HashMap<String, ArrayList<HashMap<String, String>>> departmentList = new HashMap<String, ArrayList<HashMap<String, String>>>();
	DBhelper dbHelper = new DBhelper();
	Loader loader = new Loader();

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		setupTable();
		getList();
		caculatePercent();
		setupPicker();
	}
	
	@FXML void loadHome() {
		loader.loadVBox(box, "/View/Welcome.fxml");
	}
	
	@FXML
    void contactButton() {
		loader.loadWeb();
    }
	
	void getList(){
		String tableName = "user_score inner join user_primary_info on user_primary_info.ssn = user_score.ssn "
				+ "where user_primary_info.branch = '" + LoginController.branch + "'";
		String[] columns = {"user_score.totalScore", "user_score.currentScore", "user_primary_info.name",
				"user_primary_info.department", "user_primary_info.level", "user_primary_info.position"};
		scoreList = dbHelper.getList(tableName, columns);
	}
	void caculatePercent() {
		Double highest = (double) 0;
		Double lowest = (double) 1;
		int[] countArray = {0, 0, 0, 0, 0};
		double sum = 0;
 		//HashMap<String, ArrayList<HashMap<String, String>>> departmentList = new HashMap<String, ArrayList<HashMap<String, String>>>();
		for(HashMap<String, String> score:scoreList) {
			double currentScore = Double.parseDouble(score.get("currentScore"));
			double totalScore = Double.parseDouble(score.get("totalScore"));
			double percent;
			if(totalScore != 0) {
				percent = currentScore/totalScore;
				score.put("percent", Double.toString(percent));
				sum += percent;
				if(percent > highest) {
					highest = percent;
				}
				if(percent < lowest) {
					lowest = percent;
				}
				if (percent < 0.6) {
					countArray[0]++;
				}else if(percent < 0.7) {
					countArray[1]++;
				}else if(percent < 0.8) {
					countArray[2]++;
				}else if(percent < 0.9) {
					countArray[3]++;
				}else if (percent <= 1){
					countArray[4]++;
				}
			}else {
				percent = 1.0;
				score.put("percent", Double.toString(percent));
				sum += percent;
				if(percent > highest) {
					highest = percent;
				}
				if(percent < lowest) {
					lowest = percent;
				}
				if (percent < 0.6) {
					countArray[0]++;
				}else if(percent < 0.7) {
					countArray[1]++;
				}else if(percent < 0.8) {
					countArray[2]++;
				}else if(percent < 0.9) {
					countArray[3]++;
				}else if (percent <= 1){
					countArray[4]++;
				}
			}
			String department = score.get("department");
			if(department!=null) {
				ArrayList<HashMap<String, String>> list;
				if(departmentList.get(department)==null) {
					list = new ArrayList<HashMap<String, String>>();
				}else {
					list = departmentList.get(department);
				}
				list.add(score);
				departmentList.put(department, list);
			}
		}
		Double ave = sum/scoreList.size();
		DecimalFormat df = new DecimalFormat("#.##");
		averageField.setText(df.format(ave*100));
		lowestField.setText(df.format(lowest*100));
		highestField.setText(df.format(highest*100));
		setupChart(countArray);
	}
	
	void setupPicker() {
		Set<String> keyset = departmentList.keySet();
		departmentPicker.getItems().setAll(keyset);
		departmentPicker.setOnAction(e->{
			loadTable(departmentPicker.getSelectionModel().getSelectedItem());
		});
		String firstDepartment = keyset.iterator().next();
		//init default display
		departmentPicker.setValue(firstDepartment);
		loadTable(firstDepartment);
		
	}
	private void loadTable(String department) {
		if(department != null) {
			ArrayList<HashMap<String, String>> list = departmentList.get(department);
			ObservableList<HashMap<String, String>> searchList = loader.search(list, "");
			tableView.setItems(searchList);
			caculateDepartment(list);
		}
	}
	private void caculateDepartment(ArrayList<HashMap<String, String>> list) {
		Double total = (double) 0;
		Double low = (double) 1;
		Double high = (double) 0;
		for(HashMap<String, String> map:list) {
			//Double current = map.get("percent").equals("NaN")?100:Double.parseDouble(map.get("percent"));
			Double current = Double.parseDouble(map.get("percent"));
			total+=current;
			if(current>high) {
				high =current;
			}
			if(current<low) {
				low=current;
			}
		}
		Double average = total/list.size();
		DecimalFormat df = new DecimalFormat("#.##");
		departmentAverage.setText(df.format(average*100));
		departmentHighest.setText(df.format(high*100));
		departmentLowest.setText(df.format(low*100));
	}
	private void setupTable() {
		String[] keys = {"name", "position", "level", "currentScore", "totalScore"};
		String[] fields = {"姓名", "职位", "层级", "得分", "总分"};
		loader.setupTable(tableView, keys, fields);
	}
	private void setupChart(int[] countArray) {
		String[] infoArray = {"低于60", "60-69", "70-79", "80-89", "90-100"};
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (int i = 0; i< 5; i++) {
			pieChartData.add(new PieChart.Data(infoArray[i], countArray[i]));
		}
		pieChart.getData().setAll(pieChartData);
	}

}

