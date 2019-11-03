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

public class ReportA extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Text accountSummary = new Text("ReportA:");

		Button button = new Button("Go Back");

		GridPane gridPane = new GridPane();

		gridPane.setMinSize(500, 500);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.setVgap(5);
		gridPane.setHgap(5);

		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(accountSummary, 0, 0);
		int i = 1;
		// Display Account Results
		// Add i++ at the end.
		// gridPane.add(something, 1, i);
		try {
			int accountId = 0;
			if (CustomerLogin.id != 0) {
				accountId = CustomerLogin.id;
			} else {
				accountId = NewCustomer.id;
			}
			System.out.print("ID is" + accountId);

			// 1. Get a connection to the Database
			Connection con = DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/SAMPLE", "db2inst1", "kenward");

			// 2. Create a statement
			Statement stmt = con.createStatement();
			String query1 = "select c.id, name, age, gender, totalOfBalances from p1.customer as c "
					+ "join (select id, sum(balance) as totalOfBalances from p1.account group by id) "
					+ "as a on a.id = c.id"; // Updated
			ResultSet rs = stmt.executeQuery(query1);

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				int age = rs.getInt(3);
				String gender = rs.getString(4);
				int balance = rs.getInt(5);
				Text newRow = new Text("Id: " + id + " Name: " + name + " Age: " + age + " Gender: " + gender
						+ " Total Balance: $" + balance);
				gridPane.add(newRow, 1, i);
				i++;
			}
			con.close();
			stmt.close(); // Close the statement after we are done with the statement

		} catch (Exception exc) {
			exc.printStackTrace();
		}

		gridPane.add(button, 1, i);

		button.setOnAction(e -> {
			AdminMainMenu newCust = new AdminMainMenu();
			try {
				newCust.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		gridPane.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: pink;"
				+ "-fx-background-color: white;");

		Scene scene = new Scene(gridPane);

		// Setting title to the Stage
		primaryStage.setTitle("Report A");

		// Adding scene to the stage
		primaryStage.setScene(scene);

		// Displaying the contents of the stage
		primaryStage.show();

	}
}
