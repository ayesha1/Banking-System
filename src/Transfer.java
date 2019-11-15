import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class Transfer extends Application {
	Button submit;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Text sourceId = new Text("Source Account #");
		Text destinationId = new Text("Destination Account #");
		Text amount = new Text("Amount");

		Text alert = new Text("");

		TextField textField1 = new TextField();
		TextField textField2 = new TextField();
		TextField textField3 = new TextField();

		Button button = new Button("Submit");

		GridPane gridPane = new GridPane();

		gridPane.setMinSize(500, 500);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.setVgap(5);
		gridPane.setHgap(5);

		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(sourceId, 0, 0);
		gridPane.add(textField1, 1, 0);
		gridPane.add(destinationId, 0, 1);
		gridPane.add(textField2, 1, 1);
		gridPane.add(amount, 0, 2);
		gridPane.add(textField3, 1, 2);

		gridPane.add(button, 1, 3);
		gridPane.add(alert, 1, 5);

		button.setOnAction(e -> {
			int accountId = 0;
			if (CustomerLogin.id != 0) {
				accountId = CustomerLogin.id;
			} else {
				accountId = NewCustomer.id;
			}

			// First make sure none of the textfields are blank
			if (textField1.getText().trim().equals("") || textField2.getText().trim().equals("")
					|| textField3.getText().trim().equals("")) {
				alert.setText("TEXTFIELD IS BLANK");
				alert.setFill(javafx.scene.paint.Color.RED);
			}
			// Make sure initial Account is your ID
			else if (getIdFromAccountNumber(Integer.parseInt(textField1.getText())) != accountId) {
				alert.setText("NOT YOUR ID");
				alert.setFill(javafx.scene.paint.Color.RED);

			}

			// Make sure they're all numbers
			else if (!textField1.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")
					|| !textField2.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")
					|| !textField2.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				alert.setText("ID OR DEPOSIT OR AMOUNT IS NOT A NUMBER");
				alert.setFill(javafx.scene.paint.Color.RED);
			}

			// Make sure each bank is in the database
			else if (Integer.parseInt(textField1.getText()) == Integer.parseInt(textField2.getText())) {
				alert.setText("YOU CANNOT TRANSFER TO YOUR OWN ACCOUNT. ");
				alert.setFill(javafx.scene.paint.Color.RED);

			}

			else if (transferToAccount(Integer.parseInt(textField1.getText()), Integer.parseInt(textField2.getText()),
					Integer.parseInt(textField3.getText())) == false) {
				alert.setText("BALANCE IS TOO LOW");
				alert.setFill(javafx.scene.paint.Color.RED);

			} else {
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
		primaryStage.setTitle("Close Account");

		// Adding scene to the stage
		primaryStage.setScene(scene);

		// Displaying the contents of the stage
		primaryStage.show();

	}

	private boolean transferToAccount(int sourceAccNum, int destinationAccNum, int amount) {
		try {
			int balanceOfsource = 0;
			// 1. Get a connection to the Database
			Connection con = DriverManager.getConnection(Driver.connectionString, Driver.dbname, Driver.password);

			// 2. Create a statement
			// 1. Check if sourceAccNum's balance is >= amount
			// else return false
			Statement stmt = con.createStatement();
			String query1 = "SELECT balance from P1.ACCOUNT where number = '" + sourceAccNum + "'";

			ResultSet rs = stmt.executeQuery(query1);
			while (rs.next()) {
				balanceOfsource = rs.getInt(1);
				break;
			}

			if (balanceOfsource < amount) {
				return false;
			}

			// 2. Subtract the amount from sourceAccNum
			int newBalance = balanceOfsource - amount;
			String query2 = "UPDATE P1.ACCOUNT set balance = '" + newBalance + "' where number = '" + sourceAccNum
					+ "'";
			stmt.execute(query2);

			// 3. Add the amount to destination AccNum and return true
			int originalBalanceOfDestinationAccount = 0;
			String query3 = "SELECT balance from P1.ACCOUNT where number = '" + destinationAccNum + "'"; // Updated
			ResultSet rs2 = stmt.executeQuery(query3);

			while (rs2.next()) {
				originalBalanceOfDestinationAccount = rs2.getInt(1);
				break;
			}
			int newSum = originalBalanceOfDestinationAccount + amount;
			String query4 = "UPDATE P1.ACCOUNT set balance = '" + newSum + "' where number = '" + destinationAccNum
					+ "' AND status = 'A'";
			stmt.execute(query4);

			con.close();
			stmt.close(); // Close the statement after we are done with the statement
			return true;

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}

	private int getIdFromAccountNumber(int number) {
		try {
			// 1. Get a connection to the Database
			Connection con = DriverManager.getConnection(Driver.connectionString, Driver.dbname, Driver.password);

			// 2. Create a statement
			Statement stmt = con.createStatement();

			String query2 = "select id from p1.account where number = '" + number + "'"; // The query to
																							// run
			ResultSet rs = stmt.executeQuery(query2);
			int id = 0;
			while (rs.next()) {
				id = rs.getInt(1);
				System.out.println("Your id is " + id);
			}
			System.out.print(id);
			con.close();
			stmt.close(); // Close the statement after we are done with the statement

			return id;

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return 5;
	}

}
