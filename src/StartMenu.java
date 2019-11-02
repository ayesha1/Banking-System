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
import javafx.geometry.Pos;

public class StartMenu extends Application {
	Button button;
	Button button1;
	Button button2;

	public static void main(String[] args) {
		launch(args);
		try {
			// 1. Get a connection to the Database
			Connection con = DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/SAMPLE", "db2inst1", "kenward");

			// 2. Create a statement
			Statement stmt = con.createStatement();

			// 3. Execute SQL query FOR TESTING
			String query = "SELECT FIRSTNME, LASTNAME, EDLEVEL, SALARY FROM EMPLOYEE";
			ResultSet rs = stmt.executeQuery(query);

			// 4. Process the result set
			while (rs.next()) { // Loop through result set and retrieve contents of each row
				String firstname = rs.getString(1);
				String lastname = rs.getString(2);
				int edlevel = rs.getInt(3);
				double salary = rs.getDouble(4);
				System.out.println(firstname + ",\t" + lastname + "," + edlevel + ",\t\t" + salary); // Print out each
																										// row's values
																										// to the screen
			}

			String sqlCreate = "CREATE TABLE IF NOT EXISTS P1.CUSTOMER"
					+ "  (ID           integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),"
					+ "   name            VARCHAR(15)," 
					+ "   gender          CHAR," 
					+ "   age           INTEGER,"
					+ "   pin           INTEGER," 
					+ "PRIMARY KEY (ID))";

			stmt.execute(sqlCreate);

			con.close();
			rs.close();// Close the result set after we are done with the result set
			stmt.close(); // Close the statement after we are done with the statement
			con.close();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Main Menu");
		
		Text text1 = new Text("Welcome to the Self Services Banking System!");
		Text text2 = new Text("Please select an option from the menu below.");

		button = new Button();
		button.setText("New Customer");
		button.setOnAction(e -> {
		    NewCustomer newCust = new NewCustomer();
		    try {
				newCust.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		
		button1 = new Button();
		button1.setText("Customer Login");
		button1.setOnAction(e -> {
		    CustomerLogin login = new CustomerLogin();
		    try {
		    	login.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		button2 = new Button();
		button2.setText("Exit");

		
		GridPane gridPane = new GridPane();
	    gridPane.setAlignment(Pos.CENTER); 
	    gridPane.add(text1, 1, 0); 
	    gridPane.add(text2, 1, 1);       
		gridPane.add(button, 2, 2);
		gridPane.add(button1, 1, 2);
		gridPane.add(button2, 0, 2);
		gridPane.setHgap(5);
		gridPane.setVgap(20);

		gridPane.setStyle("-fx-padding: 10;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" + 
                "-fx-border-radius: 5;" + 
                "-fx-border-color: pink;" +
				"-fx-background-color: white;");
	
		Scene scene = new Scene(gridPane, 500, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
