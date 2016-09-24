package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
  private void homeButtonAction(ActionEvent event){
    System.out.println("ホームボタン");
  }
  @FXML
  private void compareButtonAction(ActionEvent event){
    System.out.println("比較ボタン");
  }
  @FXML
  private void configButtonAction(ActionEvent event){
    System.out.println("設定ボタン");
  }
  @FXML
  private void startButtonAction(ActionEvent event){
    System.out.println("スタートボタン");
  }
  @FXML
  private void stopButtonAction(ActionEvent event){
    System.out.println("ストップボタン");
  }
  @FXML
  private void saveButtonAction(ActionEvent event){
    System.out.println("保存ボタン");
  }
}
