import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Controller2 implements Initializable{
	@FXML
	Pane pane;
	@FXML
	Button CheckIfCorrect;
	@FXML
	Button PressWhenDone;
	@FXML
	Button NewGame;
	ArrayList<int[][]> arraylist = new ArrayList<>();
	Controller1 c1 = new Controller1();
	int[][] board = c1.getBoard();
	public int[][] getBoard() {
		return board;
	}
	public void setBoard(int[][] board) {
		this.board = board;
	}
	int[][] boardans = new int[9][9];
	TextField[][] textfields = new TextField[9][9];
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		GridPane grid = new GridPane();
		grid.setLayoutX(41);
		grid.setLayoutY(28);
		grid.setPrefWidth(425);
		grid.setPrefHeight(412);

		//TextField[][] textFields = new TextField[9][9];
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				TextField textField = null;
				if (board[x][y] != 0) {
					textField = new TextField(String.valueOf(board[x][y]));
					
					textField.setEditable(false);
				}else
					textField = new TextField(" ");
				textField.setStyle(
						" -fx-border-color : #141413 ;" + "-fx-font-size : 18 ; " + "-fx-background-color : #F0FFFF");
				textField.setAlignment(Pos.CENTER);

				textField.setPrefHeight(45);
				textField.setPrefWidth(45);

				GridPane.setConstraints(textField, y, x);
				grid.getChildren().add(textField);
				if ((x < 3 && y < 3) || (x > 5 && 5 < y) || (x < 3 && 5 < y) || (x > 5 && y < 3)
						|| ((x > 2 && x < 6) && (y > 2 && y < 6)))
					textField.setStyle(" -fx-border-color :#141413;" + "-fx-font-size : 18 ; "
							+ "-fx-background-color : #2F4F4F;"+"-fx-text-inner-color: white");

				textfields[x][y] = textField;
			}
			
		}
		pane.getChildren().add(grid);
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				boardans[i][j]=board[i][j];
			}
		}
		SolveTheBoard(boardans,boardans.length);
	}
	public void pressWhenDoneOnAction(ActionEvent e) {
		if(SolveTheBoard(board,board.length)) {
			for(int i=0; i<9; i++) {
				for(int j=0; j<9; j++) {
					textfields[i][j].setText(""+board[i][j]);
					//System.out.println(board[i][j]);
				}
			}
		}
	}
	public void NewGameOnAction(ActionEvent e) {
		try {
		setBoard(c1.getBoard());
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if (board[i][j] != 0) {
					textfields[i][j].setText(board[i][j]+"");
					
				}else
					textfields[i][j].setText(" ");;
			}
		}
		}catch(NullPointerException x) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText("You Are Out Of Boards");
			alert.showAndWait();
		}
	}
	public void CheckCorrectOnAction(ActionEvent e) {
		ArrayList<Integer> al = new ArrayList<>();
		int[][] userdata = new int[9][9];
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(textfields[i][j].getText().equals(" ")) {
					userdata[i][j]=0;
				}else {
					userdata[i][j]=Integer.parseInt(textfields[i][j].getText().trim());
				}
			}
		}
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(userdata[i][j]!=0) {
					if(Check(userdata,i,j,userdata[i][j])==false && userdata[i][j]!=boardans[i][j]) {
						textfields[i][j].setStyle(" -fx-border-color : #141413 ;" + "-fx-font-size : 18 ; " + "-fx-background-color : #FF0000");
					}else {
						if ((i < 3 && j < 3) || (i > 5 && 5 < j) || (i < 3 && 5 < j) || (i > 5 && j < 3) || ((i > 2 && i < 6) && (j > 2 && j < 6)))
							textfields[i][j].setStyle(" -fx-border-color :#141413;" + "-fx-font-size : 18 ; "
									+ "-fx-background-color : #2F4F4F");
						else
							textfields[i][j].setStyle(
									" -fx-border-color : #141413 ;" + "-fx-font-size : 18 ; " + "-fx-background-color : #F0FFFF");
					}
				}
			}
		}
		
	}
	
	public boolean Check(int[][] board, int row, int col, int num) {

		if (num < 1 || num > 9)
			return false;

		for (int d = 0; d < board.length; d++)
			if (!checkRow(board,row,num)&& (d != col))
				return false;

		for (int d = 0; d < board.length; d++)
			if (!checkColumn(board,col,num)&& (d != row))
				return false;

	
		int boxRow = row - row % 3;
		int boxCol = col - col % 3;

		for (int r = boxRow; r < boxRow + 3; r++)
			for (int d = boxCol; d < boxCol + 3; d++)
				if (!checkBox(board,boxRow,boxCol,num) && !((r == row) && (d == col)))
					return false;

		return true;

	}
	public  boolean SolveTheBoard(int[][] board, int n){
		int row=0;
		int col=0;
		boolean NotEmpty = true;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == 0) //if there is still empty cells
				{
					row = i;//store the row and column
					col = j;
					NotEmpty = false;
					break;
				}
			}
			if (!NotEmpty) {
				break;
			}
		}

		
		if (NotEmpty) {
			return true;
		}

		
		for (int num = 1; num <= n; num++) {
			if (Check(board, row, col, num)) //if the number we are trying to put it on the board is safe then put it and backtrack the row
			//else replace it with zero and check a different number
			{
				board[row][col] = num;
				if (SolveTheBoard(board, n)) {
					// print(board, n);
					return true;
				}
				else {
					// replace it
					board[row][col] = 0;
				}
			}
		}
		return false;
	}

	public boolean checkRow(int[][] a,int row,int num) {
		for (int d = 0; d < board.length; d++)
			if (a[row][d] == num)
				return false;
		return true;
	}
	public boolean checkColumn(int[][] a,int col,int num) {
		for (int d = 0; d < a.length; d++)
			if (a[d][col] == num)
				return false;
		return true;
	}
	public boolean checkBox(int[][] a,int row,int col,int num) {
		
		for (int r = row; r < row + 3; r++)
			for (int d =col; d < col + 3; d++)
				if (board[r][d] == num)
					return false;
		return true;

	}
}
