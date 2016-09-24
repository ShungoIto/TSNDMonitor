package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
  static String log = new String();
  Rectangle2D d = Screen.getPrimary().getVisualBounds();//画面サイズ取得
	@Override
	public void start(Stage stage) throws Exception {
	  stage.setTitle("TSND Moniter");
	  
	  Parent fxmlSchene = FXMLLoader.load(getClass().getResource("home.fxml"));
	  Scene scene = new Scene(fxmlSchene, 1280, 800);
	  stage.setScene(scene);
	  stage.show();
	  
		  
		  
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,d.getWidth(),d.getHeight());
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
