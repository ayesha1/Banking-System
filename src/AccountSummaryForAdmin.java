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

public class AccountSummaryForAdmin extends Application {
	public static int i = 2;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Text customerId = new Text("Customer Id");
		TextField textField1 = new TextField();

		Text accountSummary = new Text("Account Summary:");
		Text alert = new Text("");
		Button submitButton = new Button("Submit");
		Button button = new Button("Go Back");

		GridPane gridPane = new GridPane();

		gridPane.setMinSize(500, 500);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.setVgap(5);
		gridPane.setHgap(5);

		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(customerId, 0, 0);
		gridPane.add(textField1, 1, 0);
		gridPane.add(accountSummary, 0, 1);
		gridPane.add(submitButton, 1,  20);

		// Display Account Results
		// Add i++ at the end.
		//gridPane.add(something, 1, i);
		
int m = 0;
		button.setOnAction(e -> {
				AdminMainMenu newCust = new AdminMainMenu();
				try {
					newCust.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		});
		submitButton.setOnAction(e -> {
			while (m != 1) {
			if (checkIfInDB(Integer.parseInt(textField1.getText())) == false) {
				gridPane.add(alert, 1, 5);
				alert.setText("ID NOT IN DATABASE.");
				alert.setFill(javafx.scene.paint.Color.RED);
				AccountSummaryForAdmin.i =2;
			} else {
			try {
				gridPane.getChildren().remove(alert);
				gridPane.getChildren().remove(submitButton);
				int accountId = Integer.parseInt(textField1.getText());
				System.out.print("ID is" + accountId);

				// 1. Get a connection to the Database
				Connection con = DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/SAMPLE", "db2inst1", "kenward");

				// 2. Create a statement
				Statement stmt = con.createStatement();
				String query1 = "SELECT number, balance from P1.ACCOUNT where id = '" + accountId + "'"
						+ "AND status = 'A'";  // Updated
				ResultSet rs = stmt.executeQuery(query1);
				
				while(rs.next()) {
					int number = rs.getInt(1);
					int balance = rs.getInt(2);
					Text newRow = new Text("Account Number:" + number + " Balance: $" + balance);
					gridPane.add(newRow, 1, i);
					AccountSummaryForAdmin.i++;
				}
				con.close();
				stmt.close(); // Close the statement after we are done with the statement

			} catch (Exception exc) {
				exc.printStackTrace();
			}

			gridPane.add(button, 1, AccountSummaryForAdmin.i);

		}
		}
		});

		gridPane.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: pink;"
				+ "-fx-background-color: white;");

		Scene scene = new Scene(gridPane);

		// Setting title to the Stage
		primaryStage.setTitle("Account Summary");

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

			String query2 = "SELECT NAME FROM P1.CUSTOMER AS C WHERE " + "C.id = '" + parseInt + "'"; // The query to
																										// run
			ResultSet rs = stmt.executeQuery(query2);

			while (rs.next()) {
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

}
