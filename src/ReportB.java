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

public class ReportB extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Text accountSummary = new Text("Report B:");
		Text maxNumberText = new Text("Enter Maximum Age:");
		Text minNumbetText = new Text("Enter Minimum Age:");

		TextField maxTextField = new TextField();
		TextField minTextField = new TextField();

		Button button = new Button("Go Back");
		Button submit = new Button("Submit");
		Text newRow = new Text("Average Balance: ");

		GridPane gridPane = new GridPane();
		gridPane.add(newRow, 1, 3);

		gridPane.add(accountSummary, 0, 0);
		gridPane.add(maxNumberText, 0, 1);
		gridPane.add(maxTextField, 1, 1);
		gridPane.add(minNumbetText, 0, 2);
		gridPane.add(minTextField, 1, 2);

		gridPane.setMinSize(500, 500);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.setVgap(5);
		gridPane.setHgap(5);

		gridPane.setAlignment(Pos.CENTER);


		gridPane.add(submit, 1, 4);
		gridPane.add(button, 1, 5);

		submit.setOnAction(e -> {
			try {

				// 1. Get a connection to the Database
				Connection con = DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/SAMPLE", "db2inst1",
						"kenward");

				// 2. Create a statement
				Statement stmt = con.createStatement();
				String query1 = "select avg(balance) from p1.account as a join (select id, age from p1.customer where age <= '"
						+ Integer.parseInt(maxTextField.getText()) + "' and age >= '"
						+ Integer.parseInt(minTextField.getText()) + "') as c on a.id = c.id"; // Updated
				ResultSet rs = stmt.executeQuery(query1);

				while (rs.next()) {
					int id = rs.getInt(1);
					newRow.setText("Average Balance: " + id);
					gridPane.getChildren().remove(submit);

				}
				con.close();
				stmt.close(); // Close the statement after we are done with the statement

			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});

		button.setOnAction(e -> {
			AdminMainMenu acc = new AdminMainMenu();
			try {
				acc.start(primaryStage);
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
		primaryStage.setTitle("Report B");

		// Adding scene to the stage
		primaryStage.setScene(scene);

		// Displaying the contents of the stage
		primaryStage.show();

	}
}
