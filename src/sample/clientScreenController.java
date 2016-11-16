package sample;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class clientScreenController implements Initializable {
    @FXML
    private TableView<ServerTable> table;

    @FXML
    private TableColumn<ServerTable, String> filenameColumn;

    @FXML
    private TableColumn<ServerTable, String> lastModifiedColumn;

    @FXML
    private TableColumn<ServerTable, String> versionColumn;

    @FXML private TableColumn<ServerTable,String> pathColumn;

    @FXML
    private MenuItem upload_menuitem;
    @FXML
    private MenuItem download_menuitem;
    @FXML private MenuItem help_menuitem;
    @FXML private Button show_button;

    @FXML
    private ObservableList<ServerTable> data;
    @FXML
    private Menu file_menu;
    @FXML
    private Menu about_menu;
    @FXML
    private Menu info_menu;
    private List<File> chosenFiles;

    public void handleUpload(ActionEvent event) throws Exception {
        //Parent selectingFilesScreen= FXMLLoader.load(getClass().getResource("selectingFilesScreen.fxml"));
        Stage selectingFilesStage = new Stage();
        //selectingFilesStage.setScene(new Scene(selectingFilesScreen,400,400));
        //selectingFilesStage.setTitle("FileChooser");
        //selectingFilesStage.show();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file(s)...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );


        chosenFiles = fileChooser.showOpenMultipleDialog(selectingFilesStage);
        System.out.println(chosenFiles);

        try {
            if (chosenFiles != null) {
                for (File f : chosenFiles) {
                    System.out.println(f.getName() + " " + f.lastModified());
                    Date dt = new Date(f.lastModified());
                    if (!(BackupClient.getServer().checkFileOnServer(f.getName(), dt))) {
                        BackupClient.send(BackupClient.getServer(), f.getPath(), f.getName(), BackupClient.getFileExtension(f), f.lastModified());
                        System.out.println("Przesłano plik: " + f.getName() + " " + "!");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());
        }
    }

    public void showButtonAction(ActionEvent event){
        try {
            RemoteInputStream ris = BackupClient.getServer().tableStream();
            data = BackupClient.getTable(ris);
            table.getItems().clear();
            table.getItems().addAll(data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        upload_menuitem.setAccelerator(new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN));
        download_menuitem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        filenameColumn.setCellValueFactory(new PropertyValueFactory<>("FileName"));
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<>("lastModified"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<>("version"));
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));

        Platform.runLater(() -> {
            try {
                RemoteInputStream ris = BackupClient.getServer().tableStream();
                data = BackupClient.getTable(ris);
                table.getItems().clear();
                table.getItems().addAll(data);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

    }




}


