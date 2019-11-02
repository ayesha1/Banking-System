import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class OpenAccount extends Application {
	Button submit;
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Text id = new Text("Customer ID");
		Text accType = new Text("Account Type");
		Text balance = new Text("Balance");
		Text alert = new Text("");
		

		TextField textField1 = new TextField();
		TextField textField2 = new TextField();
		TextField textField3 = new TextField();
		textField3.setPromptText("C - Checking S - Savings");

		Button button = new Button("Submit");

		GridPane gridPane = new GridPane();

		gridPane.setMinSize(500, 500);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.setVgap(5);
		gridPane.setHgap(5);

		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(id, 0, 0);
		gridPane.add(textField1, 1, 0);
		gridPane.add(balance, 0, 1);
		gridPane.add(textField2, 1, 1);
		gridPane.add(accType, 0, 2);
		gridPane.add(textField3, 1, 2);
		gridPane.add(button, 1, 3);
		gridPane.add(alert, 1, 5);

		button.setOnAction(e -> {
			if (textField1.getText().trim().equals("") || textField2.getText().trim().equals("")
					|| textField3.getText().trim().equals("")) {
				alert.setText("SOME AREAS ARE BLANK");
				alert.setFill(javafx.scene.paint.Color.RED);

				System.out.print("blanm");
			} else if (!textField1.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")
					|| !textField2.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				alert.setText("ID OR BALANCE IS NOT A NUMBER");
				alert.setFill(javafx.scene.paint.Color.RED);
				System.out.print("blanm");
			}	else if (checkIfInDB( Integer.parseInt(textField1.getText())) == false) {
		  		  alert.setText("INCORRECT CUSTOMER ID OR PIN");
		  		  alert.setFill(javafx.scene.paint.Color.RED);
		
		  	  }
			else {
				// TODO: INSERT INTO DATABASE
				insertIntoDB(Integer.parseInt(textField1.getText()),  Integer.parseInt(textField2.getText()), textField3.getText());
				CustomerMainMenu newCust = new CustomerMainMenu();
				try {
					newCust.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		gridPane.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: pink;"
				+ "-fx-background-color: white;");

		Scene scene = new Scene(gridPane);

		// Setting title to the Stage
		primaryStage.setTitle("Open Account");

		// Adding scene to the stage
		primaryStage.setScene(scene);

		// Displaying the contents of the stage
		primaryStage.show();

	}
	
	private boolean checkIfInDB(int parseInt) {
		
		try {
			// 1. Get a connection to the Database
			Connection con = DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/SAMPLE", "db2inst1", "kenward");
	
			// 2. Create a statement
			Statement stmt = con.createStatement();
				
			String query2 = "SELECT NAME FROM P1.CUSTOMER AS C WHERE "
					+ "C.id = '" + parseInt + "'"; // The query to run
			ResultSet rs = stmt.executeQuery(query2);
			
			while(rs.next()) {
				String name = rs.getString(1);
				System.out.println("Your name is " + name);
				return true;
			}
	
			con.close();
			stmt.close(); // Close the statement after we are done with the statement
	
		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
		return false;
		// TODO Auto-generated method stub
		
	}



	private void insertIntoDB(Integer id, Integer balance, String type) {
		Alert a = new Alert(AlertType.NONE);
		try {
			// 1. Get a connection to the Database
			Connection con = DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/SAMPLE", "db2inst1", "kenward");

			// 2. Create a statement
			Statement stmt = con.createStatement();
			
			// 3. Create a SQL Create Statement for the Account
			String sqlCreate = "CREATE TABLE IF NOT EXISTS P1.ACCOUNT"
					+ "  (NUMBER           integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),"
					+ "   ID            INTEGER," 
					+ "   balance          INTEGER," 
					+ "   type           CHAR,"
					+ "   status           CHAR," 
					+ "PRIMARY KEY (NUMBER))";

			stmt.execute(sqlCreate);

			String query1 = "INSERT INTO P1.Account (id, balance, type, status) " + "VALUES ('" + id + "', '" + balance
					+ "', '" + type + "'," + "'A')"; // The query to run
			stmt.execute(query1);

			String query2 = "SELECT number FROM P1.ACCOUNT AS A WHERE " + "A.id = '" + id + "' " + " AND A.type = 'A'"; // The query to run
			ResultSet rs = stmt.executeQuery(query2);

			while (rs.next()) {
				int number = rs.getInt(1);
				String s = "Your id is " + number;
				System.out.println("Your number is " + number);
				a.setAlertType(AlertType.CONFIRMATION);

				// set content text
				a.setContentText(s);

				// show the dialog
				a.show();

			}

			con.close();
			stmt.close(); // Close the statement after we are done with the statement

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
