package application;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
  
  final static int DATA_LENGTH = 201;
  int bluetoothBtnCount=0;
  
  @FXML
  private Pane pane;
  @FXML
  private Button powerButton;
  @FXML
  private Button startButton;
  @FXML
  private Button stopButton;
  
  private Timeline timeline;
  private static ArrayList<Double> x = new ArrayList<Double>();
  private static ArrayList<Double> acceleData1 = new ArrayList<Double>();
  private static ArrayList<Double> acceleData2 = new ArrayList<Double>();
  private static ArrayList<Double> acceleData3 = new ArrayList<Double>();
  private static ArrayList<Double> gyroData1 = new ArrayList<Double>();
  private static ArrayList<Double> gyroData2 = new ArrayList<Double>();
  private static ArrayList<Double> gyroData3 = new ArrayList<Double>();
  private static LineChartData acceleDataX, acceleDataY, acceleDataZ, gyroDataX, gyroDataY, gyroDataZ;
  private LineChart acceleChart;
  private LineChart gyroChart;
  private LinearAxis acceleXaxis;
  private LinearAxis acceleYaxis;
  private LinearAxis gyroXaxis;
  private LinearAxis gyroYaxis;
  private ObservableList<LineChartData> acceleDatas;
  private ObservableList<LineChartData> gyroDatas;
  
  public void setSensorData(double value, String sensor){
	  if (sensor =="ax")
		  acceleData1.add(value);
	  else if(sensor == "ay")
		  acceleData2.add(value);
	  else if(sensor == "az")
		  acceleData3.add(value);
	  else if(sensor == "gx")
		  gyroData1.add(value);
	  else if(sensor == "gy")
		  gyroData2.add(value);
	  else if(sensor == "gz")
		  gyroData3.add(value);
  }
  
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
    
    for(int i=0;i<DATA_LENGTH;i++){
      acceleData1.add(0.0);
      acceleData2.add(0.0);
      acceleData3.add(0.0);
      gyroData1.add(0.0);
      gyroData2.add(0.0);
      gyroData3.add(0.0);
      x.add((double)i);
    }
  }

  @FXML
  private void powerButtonAction(ActionEvent event) throws IOException{
    if(bluetoothBtnCount%2==0){
      Main.host = "localhost";
      Main.port = 11000;
      Main.tc.execute(Main.host, Main.port);
      bluetoothBtnCount++;
    }
    else{
      if(TelnetConnector.socket.isConnected()==true){
        Main.tc.DisConnect();
        bluetoothBtnCount++;
      }
    }
  }
  
  @FXML
  private void startButtonAction(ActionEvent event) throws IOException{
    startButton.setDisable(true);
    stopButton.setDisable(false);
    Main.tc.CommandToSensor("start\n", TelnetConnector.os);
  }
  @FXML
  private void stopButtonAction(ActionEvent event) throws IOException{
    startButton.setDisable(false);
    stopButton.setDisable(true);
    Main.tc.CommandToSensor("stop\n", TelnetConnector.os);
  }
  
  static public void addSensorData(byte[] buff) throws UnsupportedEncodingException{
	  double accele1=0, accele2=0, accele3=0, gyro1=0, gyro2=0, gyro3=0;
	  
	  if(buff[0]==97 && buff[1]==103 && buff[2]==115){
		  System.out.println("----------------------------------------------------------");

		  //改行コードをカンマに変換
		  for(int i=0;i<buff.length;i++){
			  if(buff[i]==10){
				  buff[i] = 44;
			  }
		  }
		  
		  String buffStr = new String(buff, "UTF-8");
		  String[] splitStr = buffStr.split(",");
		  for(int i=0;i<splitStr.length;i++){
			  System.out.print("splitStr["+i+"]=「"+splitStr[i]+"」, ");
		  }
		  accele1 = Double.parseDouble(splitStr[2]);
		  accele2 = Double.parseDouble(splitStr[3]);
		  accele3 = Double.parseDouble(splitStr[4]);
		  gyro1 = Double.parseDouble(splitStr[5]);
		  gyro2 = Double.parseDouble(splitStr[6]);
		  gyro3 = Double.parseDouble(splitStr[7]);
		  
		  // グラフ描画のためのセット
		  acceleData1.add(accele1);
		  acceleData2.add(accele2);
		  acceleData3.add(accele3);
		  gyroData1.add(gyro1);
		  gyroData2.add(gyro2);
		  gyroData3.add(gyro3);
		    
		  if (acceleData1.size()>DATA_LENGTH){
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
  
  private void addDummyData() {
    acceleData1.add(Math.random()*7000 + 2000);
    acceleData2.add(Math.random()*4000 - 3000);
    acceleData3.add(Math.random()*(-1000) - 3000);
    gyroData1.add(Math.random()*7000 + 2000);
    gyroData2.add(Math.random()*4000 - 3000);
    gyroData3.add(Math.random()*(-1000) - 3000);
    
    if (acceleData1.size()>DATA_LENGTH){
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
