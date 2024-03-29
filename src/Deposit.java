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

public class Deposit extends Application {
	Button submit;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Text number = new Text("Account Number");
		Text deposit = new Text("Desposit Amount $");

		Text alert = new Text("");

		TextField textField1 = new TextField();
		TextField textField2 = new TextField();

		Button button = new Button("Submit");

		GridPane gridPane = new GridPane();

		gridPane.setMinSize(500, 500);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.setVgap(5);
		gridPane.setHgap(5);

		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(number, 0, 0);
		gridPane.add(textField1, 1, 0);
		gridPane.add(deposit, 0, 1);
		gridPane.add(textField2, 1, 1);

		gridPane.add(button, 1, 3);
		gridPane.add(alert, 1, 5);

		button.setOnAction(e -> {
			if (textField1.getText().trim().equals("") || textField2.getText().trim().equals("")) {
				alert.setText("TEXTFIELD IS BLANK");
				alert.setFill(javafx.scene.paint.Color.RED);
			}

			else if (!textField1.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")
					|| !textField2.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				alert.setText("ID OR DEPOSIT IS NOT A NUMBER");
				alert.setFill(javafx.scene.paint.Color.RED);
			} else {
				// TODO: INSERT INTO DATABASE
				depositToAccount(Integer.parseInt(textField1.getText()), Integer.parseInt(textField2.getText()));
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

	private void depositToAccount(int parseInt, int amount) {
		try {
			// 1. Get a connection to the Database
			int originalAmount = 0;
			Connection con = DriverManager.getConnection(Driver.connectionString, Driver.dbname, Driver.password);

			// 2. Create a statement
			Statement stmt = con.createStatement();
			String query1 = "SELECT balance from P1.ACCOUNT where number = '" + parseInt + "'"; // Updated
			ResultSet rs = stmt.executeQuery(query1);

			while (rs.next()) {
				originalAmount = rs.getInt(1);
				break;
			}

			int sum = amount + originalAmount;

			String query2 = "UPDATE P1.ACCOUNT SET balance = '" + sum + "' " + "where number = '" + parseInt
					+ "' AND STATUS = 'A'"; // Updated
			stmt.execute(query2);

			con.close();
			stmt.close(); // Close the statement after we are done with the statement

		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

}
