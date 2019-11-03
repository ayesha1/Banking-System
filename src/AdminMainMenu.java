import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class AdminMainMenu extends Application {
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button accountSummary = new Button("Account Summary");
		Button reportA = new Button("Report A");
		Button reportB = new Button("Report B");
		Button exit = new Button("Exit");

		GridPane gridPane = new GridPane();

		gridPane.setMinSize(500, 500);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.setVgap(5);
		gridPane.setHgap(20);

		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(accountSummary, 1, 0);
		gridPane.add(reportA, 1, 1);
		gridPane.add(reportB, 1, 2);
		gridPane.add(exit, 1, 6);

		// ACCOUNT SUMMARY -----------------------
		accountSummary.setOnAction(e -> {
			AccountSummaryForAdmin acc = new AccountSummaryForAdmin();
			try {
				acc.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		// REPORT A -----------------------
		reportA.setOnAction(e -> {
			ReportA acc = new ReportA();
			try {
				acc.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		// ACCOUNT SUMMARY -----------------------
		reportB.setOnAction(e -> {
			ReportB acc = new ReportB();
			try {
				acc.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// GO BACK -----------------------
		exit.setOnAction(e -> {
			StartMenu acc = new StartMenu();
			CustomerLogin.id = 0;
			NewCustomer.id = 0;
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
		primaryStage.setTitle("Main Menu");

		// Adding scene to the stage
		primaryStage.setScene(scene);

		// Displaying the contents of the stage
		primaryStage.show();

	}

}
