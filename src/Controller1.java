import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller1 implements Initializable{
	@FXML
	Button PressWhenDone;
	@FXML
	Button EnterTheFile;
	@FXML
	Pane pane;
	ArrayList<String[]> arraylist = new ArrayList<>();
	static ArrayList<int[][]> arraylist1 = new ArrayList<>();
	static int counter=0;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	@FXML
	public void EnterTheFileOnAction(ActionEvent e) {
		try {
		FileChooser filechooser = new FileChooser();
		File file = filechooser.showOpenDialog(null);
		Scanner in = new Scanner(file);
		int counter=0;
		int[][] arr = new int[9][9];
		while(in.hasNextLine()) {
			String Str = in.nextLine();
			String[] arrstr = Str.split(" ");
			arraylist.add(arrstr);
			for(int i=0; i<9; i++) {
				arr[counter][i]=Integer.parseInt(arrstr[i]);
			}
			counter++;
		}
		arraylist1.add(arr);
		}catch(FileNotFoundException x) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText("Check The File Before Entering It");
			alert.showAndWait();
		}catch(NullPointerException x) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText("Check The File Before Entering It");
			alert.showAndWait();
		}catch(NumberFormatException x) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText("Check The File Before Entering It");
			alert.showAndWait();
		}
	}
	@FXML
	public void PressWhenDoneOnAction(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Project4Scene2.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	public int[][] getBoard(){
		
		if(counter<arraylist1.size())
			return arraylist1.get(counter++);
		else
			return null;
		
		
	}
}
