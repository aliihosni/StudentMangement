package TP2.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import TP2.Services.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;


    @FXML
    private Text error;

    @FXML
    private JFXButton closeButton;


    Service service;


    public Controller(){
        service = new Service();
    }


    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {



        String url = "users/login";

        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", username.getText());
        parameters.put("password", password.getText());



        if(service.sendPost(url, parameters,"POST")== 200){
            error.setFill(Color.GREEN);
            error.setText("Loading..");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../Views/consult.fxml"));
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UTILITY);
                stage.centerOnScreen();
                stage.setResizable(false);
                Scene scene = new Scene(root);
                stage.setAlwaysOnTop(true);
                stage.setScene(scene);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            error.setFill(Color.RED);
            error.setText("Username or password is invalid, try again");
        }


    }


    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void signup(ActionEvent actionEvent) throws Exception {

        String url = "users";

        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", username.getText());
        parameters.put("password", password.getText());



        if(service.sendPost(url, parameters,"POST")== 201){
            error.setFill(Color.GREEN);
            error.setText("You are registered.. Go log in");

        }else {
            error.setFill(Color.RED);
            error.setText("Try again ..");
        }
    }
}
