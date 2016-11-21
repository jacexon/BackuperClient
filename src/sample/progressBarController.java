package sample;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class progressBarController implements Initializable{
    @FXML private ProgressBar prg;
    @FXML private Label info_label;
    @FXML private Label sent_label;
    @FXML private Button ok_button;

    public static long fsize = 0;
    public static int order = 0;
    public static int amountOfFiles = 0;
    public static String filename = "";

    public void controlProgress(String filename, long fsize){
        Runnable task = () -> {
            long progressSize = 0L;
            System.out.println("ROZMIAR PLIKU: " + fsize);
            order++;
            info_label.setText("Sending file " + filename + "... (" + order + " of " + amountOfFiles+ ")");
            while (progressSize<fsize){
                try {
                    //progressSize=(BackupClient.getNumberOfChunks(BackupClient.getServer().chunkStream())*4096);

                    progressSize=(BackupClient.getServer().getChunk()*4096);
                    double coeff = (progressSize*1.0/fsize*1.0);
                    System.out.println("Stosunek: " + coeff);
                    prg.setProgress(coeff);
                    Thread.sleep(10);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sent_label.setVisible(true);
            ok_button.setVisible(true);
            try{
                BackupClient.getServer().resetChunks();
                Thread.sleep(3000);
            }
            catch (Exception e){
                e.getMessage();
            }
            Platform.runLater(() -> ok_button.fire());



        };

        Thread t = new Thread(task);
        t.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ok_button.setOnAction(e -> {
            // get a handle to the stage
            Stage stage = (Stage) ok_button.getScene().getWindow();
            // do what you have to do
            stage.close();
           // ((Node)(e.getSource())).getScene().getWindow().hide();
        });
        controlProgress(filename,fsize);
    }
}
