package application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class HomeController {
  
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
  
  @FXML
  private LineChart<String, Double> acceleChart;
  @FXML
  private LineChart<String, Double> gyroChart;
  
  @FXML
  private CategoryAxis acceleXAxis;
  @FXML
  private NumberAxis acceleYAxis;
  @FXML
  private CategoryAxis gyroXAxis;
  @FXML
  private NumberAxis gyroYAxis;
  
  private XYChart.Series<String, Double> accelerationData;
  private XYChart.Series<String, Double> gyroData;
  private Timeline timeline;
  
  @FXML
  public void initialize(){
   this.accelerationData = new XYChart.Series<>();
   acceleChart.getData().add(accelerationData);
   this.gyroData = new XYChart.Series<>();
   gyroChart.getData().add(gyroData);
  }
  
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
    drawGraph();
  }
  @FXML
  private void stopButtonAction(ActionEvent event){
 // タイムラインの停止
    if(timeline != null) {
        timeline.stop();
        timeline = null;
        startButton.setDisable(false);
        stopButton.setDisable(true);
        System.out.println("stop");
    }
  }
  @FXML
  private void saveButtonAction(ActionEvent event){
  }
  
  //グラフ描画
  public void drawGraph(){
    addChartData();
    this.timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames().add(new KeyFrame(
        Duration.seconds(1),
        new EventHandler<ActionEvent>(){
          @Override
          public void handle(ActionEvent event){
            addDummyData();
          }
        }));
    timeline.play();
  }
  
  //グラフにデータを追加する
  public void addDummyData(){
    double y1 = Math.random()*25000 - 10000;
    double y2 = Math.random()*6000 - 3000;
    
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    long currentTime = System.currentTimeMillis();
    String strTime = dateFormatter.format(new Date(currentTime));
    
    if(accelerationData.getData().size()>10){
      accelerationData.getData().remove(0);
      gyroData.getData().remove(0);
    }
    
    this.accelerationData.getData().add(new XYChart.Data<String, Double>(strTime, y1));
    this.gyroData.getData().add(new XYChart.Data<String, Double>(strTime, y2));
  }
  
  public void addChartData(){
    
  }
}
