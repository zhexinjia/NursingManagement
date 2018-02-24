package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class Loader {
	//replace the old vbox to new vbox
	public void loadVBox(VBox vbox, String path) {
		BorderPane mainPane = (BorderPane) vbox.getParent();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(path));
			VBox mainView;
			mainView = (VBox) loader.load();
			mainPane.setCenter(mainView);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@param: table view, table view keySet, fields[]
	//set up the table
	public void setupTable(TableView<HashMap<String, String>> tableView, String[] keys, String[] fields) {
		ArrayList<TableColumn<HashMap<String, String>, String>> cols = new ArrayList<TableColumn<HashMap<String, String>, String>>();
		for(int i = 0; i < keys.length; i++) {
			final int index = i;
			TableColumn<HashMap<String, String>, String> col = new TableColumn<HashMap<String, String>, String>(fields[i]);
			col.setCellValueFactory(new Callback<CellDataFeatures<HashMap<String, String>, String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<HashMap<String, String>, String> param) {
					SimpleStringProperty output = new SimpleStringProperty();
					output.setValue(param.getValue().get(keys[index]));
					return output;
				}
				
			});
			cols.add(col);
		}
		tableView.getColumns().setAll(cols);
	}
	
	//search function, return list of hash-map contains search item
	public ObservableList<HashMap<String, String>> search(ArrayList<HashMap<String, String>> list, String[] searchList) {
		Collection<String> searchCollection = Arrays.asList(searchList);
		ObservableList<HashMap<String, String>> output = FXCollections.observableArrayList();
		for(HashMap<String, String> map:list) {
			Collection<String> values = map.values();
			if (!Collections.disjoint(values, searchCollection)) {
				output.add(map);
			}
		}
		return output;
	}
	
	//return array of given key
	public ArrayList<String> getArray(ArrayList<HashMap<String, String>> maplist, String key) {
		ArrayList<String> output = new ArrayList<String>();
		for(HashMap<String, String> map:maplist) {
			output.add(map.get(key));
		}
		return output;
	}
}
