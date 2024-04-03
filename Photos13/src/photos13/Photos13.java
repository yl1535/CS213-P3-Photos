package photos13;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Photos13 extends Application{
    
    @Override
	public void start(Stage primaryStage) throws Exception {
		
		// create FXML loader
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/photos13/docs/Photos13UI.fxml"));
		
		// load fmxl, root layout manager in fxml file is GridPane
		Pane root = (Pane)loader.load();

		// set scene to root
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
