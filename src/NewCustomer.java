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

public class NewCustomer extends Application {
	Button submit;
	String nameToEnter = "";
	String gender;
	Integer age;
	Integer pin;
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	      Text name = new Text("Name");       
	      Text gender = new Text("Gender"); 
	      Text age = new Text("Age"); 
	      Text pin = new Text("Pin"); 
	      Text alert = new Text("");
	      
	      TextField textField1 = new TextField();       
	      TextField textField2 = new TextField();  
	      TextField textField3 = new TextField();  
	      TextField textField4 = new TextField();  
	      
	      Button button = new Button("Submit"); 

	      GridPane gridPane = new GridPane();    

	      gridPane.setMinSize(500, 500); 
	      gridPane.setPadding(new Insets(10, 10, 10, 10)); 

	      gridPane.setVgap(5); 
	      gridPane.setHgap(5);       

	      gridPane.setAlignment(Pos.CENTER); 
	      
	      gridPane.add(name, 0, 0); 
	      gridPane.add(textField1, 1, 0); 
	      gridPane.add(gender, 0, 1);       
	      gridPane.add(textField2, 1, 1); 
	      gridPane.add(age, 0, 2); 
	      gridPane.add(textField3, 1, 2);  
	      gridPane.add(pin, 0, 3); 
	      gridPane.add(textField4, 1, 3);  
	      gridPane.add(button, 1, 4);
	      gridPane.add(alert, 1, 5);
	      
	      button.setOnAction(e -> {
	    	  if (textField1.getText().trim().equals("") ||
	    			  textField2.getText().trim().equals("") ||
	    			  textField3.getText().trim().equals("") ||
	    			  textField4.getText().trim().equals("")) {
	    		  alert.setText("SOME AREAS ARE BLANK");
	    		  alert.setFill(javafx.scene.paint.Color.RED);

	    		  System.out.print("blanm");
	    		  	    	  }
	    	  else if (!textField3.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") ||
	    			  !textField4.getText().trim().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
	    		  alert.setText("PIN OR AGE IS NOT A NUMBER");
	    		  alert.setFill(javafx.scene.paint.Color.RED);
	    		  System.out.print("blanm");
	    	  } else {
	    		  //TODO: INSERT INTO DATABASE
	    		  insertIntoDB(textField1.getText(),textField2.getText(), Integer.parseInt(textField3.getText()),
	    				  Integer.parseInt(textField4.getText()) );
	    		  CustomerMainMenu newCust = new CustomerMainMenu();
	  		    try {
	  				newCust.start(primaryStage);
	  			} catch (Exception e1) {
	  				// TODO Auto-generated catch block
	  				e1.printStackTrace();
	  			}
	    	  }
	    	  nameToEnter = String.valueOf(textField1.getText());
	    	  	System.out.println(nameToEnter);
	        });



	      gridPane.setStyle("-fx-padding: 10;" + 
	                "-fx-border-style: solid inside;" + 
	                "-fx-border-width: 2;" +
	                "-fx-border-insets: 5;" + 
	                "-fx-border-radius: 5;" + 
	                "-fx-border-color: pink;" +
					"-fx-background-color: white;");
	      
	      Scene scene = new Scene(gridPane);  
	      
	      //Setting title to the Stage 
	      primaryStage.setTitle("New Customer"); 
	         
	      //Adding scene to the stage 
	      primaryStage.setScene(scene); 
	         
	      //Displaying the contents of the stage 
	      primaryStage.show(); 

	}


	private void insertIntoDB(String name, String gender, Integer age, Integer pin) {
        Alert a = new Alert(AlertType.NONE); 
		try {
			// 1. Get a connection to the Database
			Connection con = DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/SAMPLE", "db2inst1", "kenward");
	
			// 2. Create a statement
			Statement stmt = con.createStatement();
	
			String query1 = "INSERT INTO P1.CUSTOMER (name, gender, age, pin) "
					+ "VALUES ('" + name + "', '" + gender + "', '" + age + "', '" + pin + "')"; // The query to run
			stmt.execute(query1);
			
			String query2 = "SELECT ID FROM P1.CUSTOMER AS C WHERE "
					+ "C.name = '" + name + "' AND C.pin = '" + pin + "'"; // The query to run
			ResultSet rs = stmt.executeQuery(query2);
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String s ="Your id is " + id;
				System.out.println("Your id is " + id);
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
