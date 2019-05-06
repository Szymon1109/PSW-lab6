package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RejestrationController {

    @FXML
    private Button close;

    @FXML
    public void close(ActionEvent event){
        Stage stage =(Stage)close.getScene().getWindow();
        stage.close();
    }
}
