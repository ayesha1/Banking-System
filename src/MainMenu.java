import java.awt.Font;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class MainMenu extends Application {
	Button button;
	Button button1;
	Button button2;

	public static void main(String[] args) {
		launch(args);
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

