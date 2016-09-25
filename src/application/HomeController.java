package application;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class HomeController {
  private static boolean sensor = false;  
  
  @FXML
  private Button homeButton;
  @FXML
  private Button compareButton;
  @FXML
  private Button configButton;
  @FXML
  private Button startButton;
  @FXML
  private Button stopButton;
  @FXML
  private Button saveButton;
  
  private ExecutorService executor;
  Task<Void> task;
  
  
  
  
  @FXML
  private void homeButtonAction(ActionEvent event){
  }
  @FXML
  private void compareButtonAction(ActionEvent event){
  }
  @FXML
  private void configButtonAction(ActionEvent event){
  }
  @FXML
  private void startButtonAction(ActionEvent event){
    startButton.setDisable(true);
    stopButton.setDisable(false);
    Main.log = "";
    sensor = true;
    int procs = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors(); 
    executor = Executors.newFixedThreadPool(procs);
    
    task = new Task<Void>(){
      @Override
      protected Void call() throws Exception {
        while(sensor == true){
          drawGraph();
        }
        return null;
      }
    };
    new Thread(task).start();
  }
  @FXML
  private void stopButtonAction(ActionEvent event){
    if(task.isRunning()){
      task.cancel();
      sensor = false;
      startButton.setDisable(false);
      stopButton.setDisable(true);
      System.out.println("stop");
      executor.shutdown();
    }
  }
  @FXML
  private void saveButtonAction(ActionEvent event){
  }
  
  //グラフ描画
  public static void drawGraph(){
  }
}
