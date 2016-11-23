package sample;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;


public class creditsController implements Initializable{

    @FXML
    private TextArea creditsText;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        creditsText.setText("Bartłomiej Bielecki\n" +
                "Jacek Polak\n" +
                "Backuper 23.11.2016\n");
    }
}