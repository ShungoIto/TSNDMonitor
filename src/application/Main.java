package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
  static TelnetConnector tc = new TelnetConnector();
  static String host = new String();
  static int port;
  //static String log = new String();
  static FileChooser fc = new FileChooser();
	
  Rectangle2D d = Screen.getPrimary().getVisualBounds();//画面サイズ取得
	@Override
	public void start(Stage stage) throws Exception {
	  stage.setTitle("TSND Moniter");
	  
	  // センサに接続
	  // TODO: 設定画面を作って、設定画面内で接続できるようにする 
	  host = "localhost";
	  port = 11000;
	  tc.execute(host, port);
	  
	  Parent fxmlSchene = FXMLLoader.load(getClass().getResource("home.fxml"));
	  Scene scene = new Scene(fxmlSchene, 1280, 800);
	  stage.setScene(scene);
	  stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
