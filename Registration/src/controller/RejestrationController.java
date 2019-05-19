package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class RejestrationController {

    @FXML
    private Button close;

    @FXML
    public void close(ActionEvent event){
        Stage stage =(Stage)close.getScene().getWindow();
        stage.close();

        showMain();
    }

    public void showMain(){
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("../view/MainView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Panel rejestracji");
        stage.setScene(new Scene(root, 600, 450));
        stage.show();
    }
}
