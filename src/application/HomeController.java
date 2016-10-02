package application;

import nodamushi.jfx.chart.linechart.LineChart;
import nodamushi.jfx.chart.linechart.LineChartData;
import nodamushi.jfx.chart.linechart.LinearAxis;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class HomeController {
  
  final static int DATA_LENGTH = 600;
  
  @FXML
  private Pane pane;
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
  
  private Timeline timeline;
  private double[] x = new double[DATA_LENGTH];
  private double[] acceleData1 = new double[DATA_LENGTH];
  private double[] acceleData2 = new double[DATA_LENGTH];
  private double[] acceleData3 = new double[DATA_LENGTH];
  private double[] gyroData1 = new double[DATA_LENGTH];
  private double[] gyroData2 = new double[DATA_LENGTH];
  private double[] gyroData3 = new double[DATA_LENGTH];
  private LineChartData acceleDataX, acceleDataY, acceleDataZ, gyroDataX, gyroDataY, gyroDataZ;
  private LineChart acceleChart;
  private LineChart gyroChart;
  private LinearAxis acceleXaxis;
  private LinearAxis acceleYaxis;
  private LinearAxis gyroXaxis;
  private LinearAxis gyroYaxis;
  private ObservableList<LineChartData> acceleDatas;
  private ObservableList<LineChartData> gyroDatas;
  
  @FXML
  public void initialize(){
    //加速度グラフの設定
    acceleXaxis = new LinearAxis();
    acceleYaxis = new LinearAxis();
    acceleChart = new LineChart();
    acceleChart.setRangeMarginX(1);
    acceleChart.setXAxis(acceleXaxis);
    acceleChart.setYAxis(acceleYaxis);
    acceleChart.setLayoutX(160);
    acceleChart.setLayoutY(107);
    acceleChart.setPrefHeight(330);
    acceleChart.setPrefWidth(900);
    acceleDatas = acceleChart.getDataList();
    acceleDataX = new LineChartData(DATA_LENGTH);
    acceleDataY = new LineChartData(DATA_LENGTH);
    acceleDataZ = new LineChartData(DATA_LENGTH);
    pane.getChildren().add(acceleChart);
    
    //ジャイログラフの設定
    gyroXaxis = new LinearAxis();
    gyroYaxis = new LinearAxis();
    gyroChart = new LineChart();
    gyroChart.setRangeMarginX(1);
    gyroChart.setXAxis(gyroXaxis);
    gyroChart.setYAxis(gyroYaxis);
    gyroChart.setLayoutX(160);
    gyroChart.setLayoutY(450);
    gyroChart.setPrefHeight(330);
    gyroChart.setPrefWidth(900);
    gyroDatas = gyroChart.getDataList();
    gyroDataX = new LineChartData(DATA_LENGTH);
    gyroDataY = new LineChartData(DATA_LENGTH);
    gyroDataZ = new LineChartData(DATA_LENGTH);
    pane.getChildren().add(gyroChart);
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
    
    calcData();
    acceleDatas.add(acceleDataX);
    acceleDatas.add(acceleDataY);
    acceleDatas.add(acceleDataZ);
    gyroDatas.add(gyroDataX);
    gyroDatas.add(gyroDataY);
    gyroDatas.add(gyroDataZ);

    timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>(){
      @Override
      public void handle(final ActionEvent e){
        calcData();
      }
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    
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
//            addDummyData();
          }
        }));
    timeline.play();
  }
  
  //グラフにデータを追加する
//  public void addDummyData(){
//    double y1 = Math.random()*25000 - 10000;
//    double y2 = Math.random()*6000 - 3000;
//    
//    final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
//    long currentTime = System.currentTimeMillis();
//    String strTime = dateFormatter.format(new Date(currentTime));
//    
//    if(accelerationData.getData().size()>10){
//      accelerationData.getData().remove(0);
//      gyroData.getData().remove(0);
//    }
//    
//    this.accelerationData.getData().add(new XYChart.Data<String, Double>(strTime, y1));
//    this.gyroData.getData().add(new XYChart.Data<String, Double>(strTime, y2));
//  }
  
  public void addChartData(){
    
  }
  
  private void calcData() {
    for(int i=0;i < DATA_LENGTH;i++){
      final double x = i;
      this.x[i] = x;
      acceleData1[i] = Math.random()*7000 + 2000;
      acceleData2[i] = Math.random()*4000 - 3000;
      acceleData3[i] = Math.random()*(-1000) - 3000;
      
      gyroData1[i] = Math.random()*7000 + 2000;
      gyroData2[i] = Math.random()*4000 - 3000;
      gyroData3[i] = Math.random()*(-1000) - 3000;
      
    }
    acceleDataX.setData(x, acceleData1);
    acceleDataY.setData(x, acceleData2);
    acceleDataZ.setData(x, acceleData3);
    
    gyroDataX.setData(x, gyroData1);
    gyroDataY.setData(x, gyroData2);
    gyroDataZ.setData(x, gyroData3);
    
  }
}
