package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Model.DBhelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class UserTestDetailController implements Initializable {
	@FXML Label singleQuestion;
	@FXML Label multiQuestion;
	@FXML Label tfQuestion;
	@FXML Label singleCorrect;
	@FXML Label multiCorrect;
	@FXML Label tfCorrect;
	
	@FXML JFXButton singleBackButton;
	@FXML JFXButton singleNextButton;
	@FXML JFXButton multiBackButton;
	@FXML JFXButton multiNextButton;
	@FXML JFXButton tfBackButton;
	@FXML JFXButton tfNextButton;
	
	
	
	ToggleGroup singleGroup = new ToggleGroup();
	@FXML RadioButton singleRB1;
	@FXML RadioButton singleRB2;
	@FXML RadioButton singleRB3;
	@FXML RadioButton singleRB4;
	
	ToggleGroup tfGroup = new ToggleGroup();
	@FXML RadioButton tfRB1;
	@FXML RadioButton tfRB2;
	
	@FXML CheckBox multiCB1;
	@FXML CheckBox multiCB2;
	@FXML CheckBox multiCB3;
	@FXML CheckBox multiCB4;
	@FXML CheckBox multiCB5;
	
	private HashMap<String, String> selectedUser;
	private HashMap<String, String> userAnswers;
	private ArrayList<HashMap<String,String>> singleList;
	private ArrayList<HashMap<String, String>> multiList;
	private ArrayList<HashMap<String, String>> tfList;
	
	String[] singleAnswers;
	String[] multiAnswers;
	String[] tfAnswers;
	
	DBhelper dbHelper = new DBhelper();
	
	int singleCount;
	int multiCount;
	int tfCount;
	
	int singleIndex;
	int multiIndex;
	int tfIndex;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedUser = TestDetailController.selectedUser;
		getList();
		singleIndex = 0;
		multiIndex = 0;
		tfIndex = 0;
		setupButtons();
		loadSingle();
		loadMulti();
		loadTf();
	}
	
	@FXML
    void contactButton() {

    }
	
	@FXML void singleBack(){
		singleIndex--;
		loadSingle();
	}
	@FXML void singleNext() {
		singleIndex++;
		loadSingle();
	}
	@FXML void multiBack() {
		multiIndex--;
		loadMulti();
	}
	@FXML void multiNext() {
		multiIndex++;
		loadMulti();
	}
	@FXML void tfBack() {
		tfIndex--;
		loadTf();
	}
	@FXML void tfNext() {
		tfIndex++;
		loadTf();
	}
	
	void loadSingle(){
		singleRB1.setTextFill(Color.web("#5e6378"));
		singleRB2.setTextFill(Color.web("#5e6378"));
		singleRB3.setTextFill(Color.web("#5e6378"));
		singleRB4.setTextFill(Color.web("#5e6378"));
		if(singleIndex == 0 && singleIndex == singleCount-1) {
			singleBackButton.setDisable(true);
			singleNextButton.setDisable(true);
		}else if(singleIndex == 0) {
			singleBackButton.setDisable(true);
			singleNextButton.setDisable(false);
		}else if(singleIndex == singleCount-1) {
			singleBackButton.setDisable(false);
			singleNextButton.setDisable(true);
		}else {
			singleBackButton.setDisable(false);
			singleNextButton.setDisable(false);
		}
		HashMap<String, String> question = singleList.get(singleIndex);
		singleQuestion.setText(question.get("question"));
		singleRB1.setText("A. " + question.get("option1"));
		singleRB2.setText("B. " + question.get("option2"));
		singleRB3.setText("C. " + question.get("option3"));
		singleRB4.setText("D. " + question.get("option4"));
		String answer = question.get("answer");
		String userAnswer = singleAnswers[singleIndex];
		if(answer.equals(userAnswer)) {
			singleCorrect.setText("回答正确。");
			singleCorrect.setTextFill(Color.GREEN);
		}else {
			singleCorrect.setText("回答错误！");
			singleCorrect.setTextFill(Color.FIREBRICK);
		}
		switch (userAnswer) {
			case "A" : singleRB1.setSelected(true);
				break;
			case "B" : singleRB2.setSelected(true);
				break;
			case "C" : singleRB3.setSelected(true);
				break;
			case "D" : singleRB4.setSelected(true);
				break;
			default:
		}
		switch (answer) {
			case "A" : singleRB1.setTextFill(Color.GREEN);
				break;
			case "B" : singleRB2.setTextFill(Color.GREEN);
				break;
			case "C" : singleRB3.setTextFill(Color.GREEN);
				break;
			case "D" : singleRB4.setTextFill(Color.GREEN);
				break;
			default:

		}
	}
	void loadMulti() {
		multiCB1.setTextFill(Color.web("#5e6378"));
		multiCB2.setTextFill(Color.web("#5e6378"));
		multiCB3.setTextFill(Color.web("#5e6378"));
		multiCB4.setTextFill(Color.web("#5e6378"));
		multiCB5.setTextFill(Color.web("#5e6378"));
		multiCB1.setSelected(false);
		multiCB2.setSelected(false);
		multiCB3.setSelected(false);
		multiCB4.setSelected(false);
		multiCB5.setSelected(false);
		if(multiIndex == 0 && multiIndex == multiCount-1) {
			multiBackButton.setDisable(true);
			multiNextButton.setDisable(true);
		}else if(multiIndex == 0) {
			multiBackButton.setDisable(true);
			multiNextButton.setDisable(false);
		}else if(multiIndex == multiCount-1) {
			multiBackButton.setDisable(false);
			multiNextButton.setDisable(true);
		}else {
			multiBackButton.setDisable(false);
			multiNextButton.setDisable(false);
		}
		
		HashMap<String, String> question = multiList.get(multiIndex);
		multiQuestion.setText(question.get("question"));
		multiCB1.setText("A. " + question.get("option1"));
		multiCB2.setText("B. " + question.get("option2"));
		multiCB3.setText("C. " + question.get("option3"));
		multiCB4.setText("D. " + question.get("option4"));
		multiCB5.setText("E. " + question.get("option5"));
		
		String answer = question.get("answer");
		String userAnswer = multiAnswers[multiIndex];
		
		//compare answers and modify checkbox
		String[] correctAnswerList = answer.split("");
		String[] userAnswerList = userAnswer.split("");
		Arrays.sort(correctAnswerList);
		Arrays.sort(userAnswerList);
		if(Arrays.equals(correctAnswerList, userAnswerList)) {
			multiCorrect.setText("回答正确。");
			multiCorrect.setTextFill(Color.GREEN);
		}else {
			multiCorrect.setText("回答错误！");
			multiCorrect.setTextFill(Color.FIREBRICK);
		}
		
		for(String item:correctAnswerList) {
			switch (item) {
			case "A": multiCB1.setTextFill(Color.GREEN);
			break;
			case "B": multiCB2.setTextFill(Color.GREEN);
			break;
			case "C": multiCB3.setTextFill(Color.GREEN);
			break;
			case "D": multiCB4.setTextFill(Color.GREEN);
			break;
			case "E": multiCB5.setTextFill(Color.GREEN);
			break;
			default:	
			}
		}
		for(String item:userAnswerList) {
			switch(item) {
			case "A": multiCB1.setSelected(true);
			break;
			case "B": multiCB2.setSelected(true);
			break;
			case "C": multiCB3.setSelected(true);
			break;
			case "D": multiCB4.setSelected(true);
			break;
			case "E": multiCB5.setSelected(true);
			break;
			default:
				
			}
		}
	}
	void loadTf() {
		tfRB1.setTextFill(Color.web("#5e6378"));
		tfRB2.setTextFill(Color.web("#5e6378"));
		if(tfIndex == 0 && tfIndex == tfCount-1) {
			tfBackButton.setDisable(true);
			tfNextButton.setDisable(true);
		}else if(tfIndex == 0) {
			tfBackButton.setDisable(true);
			tfNextButton.setDisable(false);
		}else if(tfIndex == tfCount-1) {
			tfBackButton.setDisable(false);
			tfNextButton.setDisable(true);
		}else {
			tfBackButton.setDisable(false);
			tfNextButton.setDisable(false);
		}
		HashMap<String, String> question = tfList.get(tfIndex);
		tfQuestion.setText((tfIndex+1) + ". " + question.get("question"));
		tfRB1.setText("A. 对。");
		tfRB2.setText("B. 错。");
		String answer = question.get("answer");
		String userAnswer = tfAnswers[tfIndex];
		if(answer.equals(userAnswer)) {
			tfCorrect.setText("回答正确。");
			tfCorrect.setTextFill(Color.GREEN);
		}else {
			tfCorrect.setText("回答错误！");
			tfCorrect.setTextFill(Color.FIREBRICK);
		}
		switch (userAnswer) {
			case "A" : tfRB1.setSelected(true);
				break;
			case "B" : tfRB2.setSelected(true);
				break;
			default:
		}
		switch (answer) {
			case "A" : tfRB1.setTextFill(Color.GREEN);
				break;
			case "B" : tfRB2.setTextFill(Color.GREEN);
				break;
			default:
		}
	}
	
	
	private void getList() {
		String[] searchColumn= {"id"};
		String[] values = {selectedUser.get("id")};
		String tableName = "exam_history";
		String[] columns = {"single_answer", "multi_answer", "tf_answer"};
 		userAnswers = dbHelper.getList(searchColumn, values, tableName, columns).get(0);
 		String singleAns = userAnswers.get("single_answer");
 		String multiAns = userAnswers.get("multi_answer");
 		String tfAns = userAnswers.get("tf_answer");
 		
 		singleAnswers = singleAns.split(",|，");
 		multiAnswers = multiAns.split(",|，");
 		tfAnswers = tfAns.split(",|，");
 		
 		
 		getSingleQuestions();
 		getMultipleQuestions();
 		getTfQuestions();
	}
	
	void getSingleQuestions(){
		String[] searchColumn= {"exam_id"};
		String[] values = {selectedUser.get("exam_id")};
		String tableName = "exam_qa_single";
		String[] columns = {"question", "answer", "option1", "option2", "option3", "option4"};
 		singleList = dbHelper.getList(searchColumn, values, tableName, columns);
 		singleCount = singleList.size();
	}
	
	void getMultipleQuestions() {
		String[] searchColumn= {"exam_id"};
		String[] values = {selectedUser.get("exam_id")};
		String tableName = "exam_qa_multiple";
		String[] columns = {"question", "answer", "option1", "option2", "option3", "option4", "option5"};
 		multiList = dbHelper.getList(searchColumn, values, tableName, columns);
 		multiCount = multiList.size();
	}
	
	void getTfQuestions() {
		String[] searchColumn= {"exam_id"};
		String[] values = {selectedUser.get("exam_id")};
		String tableName = "exam_qa_tf";
		String[] columns = {"question", "answer"};
 		tfList = dbHelper.getList(searchColumn, values, tableName, columns);
 		tfCount = tfList.size();
	}
	
	void setupButtons() {
		singleRB1.setToggleGroup(singleGroup);
		singleRB2.setToggleGroup(singleGroup);
		singleRB3.setToggleGroup(singleGroup);
		singleRB4.setToggleGroup(singleGroup);
		tfRB1.setToggleGroup(tfGroup);
		tfRB2.setToggleGroup(tfGroup);
		singleRB1.setDisable(true);
		singleRB2.setDisable(true);
		singleRB3.setDisable(true);
		singleRB4.setDisable(true);
		tfRB1.setDisable(true);
		tfRB2.setDisable(true);
		multiCB1.setDisable(true);
		multiCB2.setDisable(true);
		multiCB3.setDisable(true);
		multiCB4.setDisable(true);
		multiCB5.setDisable(true);
	}
	
}
