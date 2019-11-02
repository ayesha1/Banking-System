import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class CustomerMainMenu extends Application {
	Button openAccount;
	Button closeAccount;
	Button deposit;
	Button withdraw;
	Button transfer;
	Button accountSummary;
	Button exit;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Button openAccount = new Button("Open Account"); 
		Button closeAccount = new Button("Close Account"); 
		Button deposit = new Button("Deposit"); 
		Button withdraw = new Button("Withdraw"); 
		Button transfer = new Button("Transfer"); 
		Button accountSummary = new Button("Account Summary"); 
		Button exit = new Button("Exit"); 

	      GridPane gridPane = new GridPane();    

	      gridPane.setMinSize(500, 500); 
	      gridPane.setPadding(new Insets(10, 10, 10, 10)); 

	      gridPane.setVgap(5); 
	      gridPane.setHgap(20);       

	      gridPane.setAlignment(Pos.CENTER); 
	      
	      gridPane.add(openAccount, 1, 0); 
	      gridPane.add(closeAccount, 1, 1);       
	      gridPane.add(deposit, 1, 2); 
	      gridPane.add(withdraw, 1, 3); 
	      gridPane.add(transfer, 1, 4);
	      gridPane.add(accountSummary, 1, 5);
	      gridPane.add(exit, 1, 6);

	      gridPane.setStyle("-fx-padding: 10;" + 
	                "-fx-border-style: solid inside;" + 
	                "-fx-border-width: 2;" +
	                "-fx-border-insets: 5;" + 
	                "-fx-border-radius: 5;" + 
	                "-fx-border-color: pink;" +
					"-fx-background-color: white;");

	      Scene scene = new Scene(gridPane);  
	      
	      //Setting title to the Stage 
	      primaryStage.setTitle("Main Menu"); 
	         
	      //Adding scene to the stage 
	      primaryStage.setScene(scene); 
	         
	      //Displaying the contents of the stage 
	      primaryStage.show(); 

	}



}