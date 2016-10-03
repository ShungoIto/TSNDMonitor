package application;

import java.util.ArrayList;

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
  private ArrayList<Double> x = new ArrayList<Double>();
  private ArrayList<Double> acceleData1 = new ArrayList<Double>();
  private ArrayList<Double> acceleData2 = new ArrayList<Double>();
  private ArrayList<Double> acceleData3 = new ArrayList<Double>();
  private ArrayList<Double> gyroData1 = new ArrayList<Double>();
  private ArrayList<Double> gyroData2 = new ArrayList<Double>();
  private ArrayList<Double> gyroData3 = new ArrayList<Double>();
  private int count = 0;
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
    acceleDatas.add(acceleDataX);
    acceleDatas.add(acceleDataY);
    acceleDatas.add(acceleDataZ);
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
    gyroDatas.add(gyroDataX);
    gyroDatas.add(gyroDataY);
    gyroDatas.add(gyroDataZ);
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
    addDummyData();

    timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>(){
      @Override
      public void handle(final ActionEvent e){
        addDummyData();
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
  
  private void addDummyData() {
    this.x.add((double) count);
    for(int i=0;i<50;i++){
      this.x.add((double)i);
    }
    
    acceleData1.add(Math.random()*7000 + 2000);
    acceleData2.add(Math.random()*4000 - 3000);
    acceleData3.add(Math.random()*(-1000) - 3000);
    gyroData1.add(Math.random()*7000 + 2000);
    gyroData2.add(Math.random()*4000 - 3000);
    gyroData3.add(Math.random()*(-1000) - 3000);
    
    if (acceleData1.size()>50){
      acceleData1.remove(0);
      acceleData2.remove(0);
      acceleData3.remove(0);
      gyroData1.remove(0);
      gyroData2.remove(0);
      gyroData3.remove(0);
    }
    
    double[] x1 = new double[x.size()];
    double[] a1 = new double[acceleData1.size()];
    double[] a2 = new double[acceleData2.size()];
    double[] a3 = new double[acceleData3.size()];
    double[] g1 = new double[gyroData1.size()];
    double[] g2 = new double[gyroData2.size()];
    double[] g3 = new double[gyroData3.size()];
    
    
    for(int i=0;i<x.size();i++){
      x1[i] = x.get(i);
    }
    for(int i=0;i<acceleData1.size();i++){
      a1[i] = acceleData1.get(i);
      a2[i] = acceleData2.get(i);
      a3[i] = acceleData3.get(i);
      g1[i] = gyroData1.get(i);
      g2[i] = gyroData2.get(i);
      g3[i] = gyroData3.get(i);
    }
    acceleDataX.setData(x1, a1);
    acceleDataY.setData(x1, a2);
    acceleDataZ.setData(x1, a3);
    
    gyroDataX.setData(x1, g1);
    gyroDataY.setData(x1, g2);
    gyroDataZ.setData(x1, g3);
    
  }
}
