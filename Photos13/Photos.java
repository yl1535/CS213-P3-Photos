package Photos13;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * The stage and scenes of photos
 * @author Yue Luo
 * @author Nicole Le
 */

public class Photos extends Application {
    
    /**
     * Start the stage
     * @Override
     * @param primaryStage
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception {
		
    // create FXML loader
	FXMLLoader loader = new FXMLLoader();
	loader.setLocation(getClass().getResource("/Photos13/docs/Photos13UI.fxml"));   //requires path change when doing final correction on file path
		
	// load fmxl, connect root
	Pane root = (Pane)loader.load();
        
	// set scene to root
	Scene scene = new Scene(root);
	primaryStage.setScene(scene);
	primaryStage.setResizable(false);
    primaryStage.setTitle("Photos Manager 13");
	primaryStage.show();
        
     // set kill event
    primaryStage.setOnCloseRequest(event -> Admin.SafelyQuit());
    }

   /**
    * Main method to initialize and launch
    * @param args
    * @throws Exception
    */ 
    public static void main(String[] args) throws Exception{
        Admin.initializeList();
        launch(args);
    }
    
    //ToDo: Update GUI Bug Report
}
