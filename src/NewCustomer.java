import java.awt.Font;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField; 

//prompt for Name, Gender, Age, and Pin.  System will return a customer ID if successful.

public class NewCustomer extends Application {
	Button submit;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	      Text name = new Text("Name");       
	      Text gender = new Text("Gender"); 
	      Text age = new Text("Age"); 
	      Text pin = new Text("Pin"); 

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

	      Scene scene = new Scene(gridPane);  
	      
	      //Setting title to the Stage 
	      primaryStage.setTitle("New Customer"); 
	         
	      //Adding scene to the stage 
	      primaryStage.setScene(scene); 
	         
	      //Displaying the contents of the stage 
	      primaryStage.show(); 

	}


}
