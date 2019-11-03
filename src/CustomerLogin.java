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

public class CustomerLogin extends Application {
	Button submit;
	public static int id;

	public static void main(String[] args) {
		launch(args);
		System.out.print(id);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Text customerId = new Text("Customer ID");
		Text pin = new Text("Pin");
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

		gridPane.add(customerId, 0, 0);
		gridPane.add(textField1, 1, 0);
		gridPane.add(pin, 0, 1);
		gridPane.add(textField2, 1, 1);
		gridPane.add(button, 1, 4);
		gridPane.add(alert, 1, 5);

		button.setOnAction(e -> {
			if (textField1.getText().trim().equals("") || textField2.getText().trim().equals("")) {
				alert.setText("SOME AREAS ARE BLANK");
				alert.setFill(javafx.scene.paint.Color.RED);
			}
			if (textField1.getText().trim().equals("0") || textField2.getText().trim().equals("0")) {
				AdminMainMenu newCust = new AdminMainMenu();
				try {
					newCust.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (!textField1.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")
					|| !textField2.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				alert.setText("PIN OR AGE IS NOT A NUMBER");
				alert.setFill(javafx.scene.paint.Color.RED);
				System.out.print("blanm");
			} else if (checkIfInDB(Integer.parseInt(textField1.getText()),
					Integer.parseInt(textField2.getText())) == false) {
				alert.setText("INCORRECT CUSTOMER ID OR PIN");
				alert.setFill(javafx.scene.paint.Color.RED);

			} else {
				// TODO: INSERT INTO DATABASE

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
		primaryStage.setTitle("Customer Login");

		// Adding scene to the stage
		primaryStage.setScene(scene);

		// Displaying the contents of the stage
		primaryStage.show();

	}

	private boolean checkIfInDB(int parseInt, int parseInt2) {

		try {
			// 1. Get a connection to the Database
			Connection con = DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/SAMPLE", "db2inst1", "kenward");

			// 2. Create a statement
			Statement stmt = con.createStatement();

			String query2 = "SELECT NAME FROM P1.CUSTOMER AS C WHERE " + "C.id = '" + parseInt + "' AND C.pin = '"
					+ parseInt2 + "'"; // The query to run
			ResultSet rs = stmt.executeQuery(query2);
			CustomerLogin.id = parseInt;
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
