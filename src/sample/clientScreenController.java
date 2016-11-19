package sample;

import com.healthmarketscience.rmiio.RemoteInputStream;
import javafx.application.Platform;
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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

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
    @FXML private Button get_button;

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
                        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION,"Confirm", ButtonType.OK, ButtonType.CANCEL);
                        dialog.setHeaderText("Backup succesful");
                        dialog.setContentText("File has been sent succesfully!");
                        dialog.setResizable(true);
                        dialog.getDialogPane().setPrefSize(250, 100);
                        dialog.showAndWait();
                    }

                    else{
                        //TODO okienko!
                        System.out.println("Niestety plik już jest na serwerze!");
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

    public void getButtonAction(ActionEvent event) throws RemoteException {
        if(table.getSelectionModel().getSelectedItem() != null){
            String pathToGet = table.getSelectionModel().getSelectedItem().getPath();
            System.out.println(pathToGet);
            String filename = table.getSelectionModel().getSelectedItem().getFileName() + "-v" +
                    table.getSelectionModel().getSelectedItem().getVersion();
            System.out.println(filename);
            try{
                if(!checkFile(filename)){
                    BackupClient.getFile(BackupClient.getServer().passAStream(pathToGet),filename);
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        else {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION,"Confirm", ButtonType.OK, ButtonType.CANCEL);
            dialog.setHeaderText("No file is selected!");
            dialog.setContentText("Please select the file first and then press GET");
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(250, 200);
            dialog.showAndWait();
        }


    }

    public boolean checkFile(String fileName){
        File file = new File("D:\\Client");
        boolean isInClient = false;
        Collection<File> files = FileUtils.listFiles(file, null, false);
        for(File file2 : files){
            if (file2.getName().substring(0,file2.getName().lastIndexOf(".")).equals(fileName)){
                isInClient = true;
                Alert dialog = new Alert(Alert.AlertType.ERROR,"Confirm", ButtonType.OK, ButtonType.CANCEL);
                dialog.setHeaderText("Retrieving file error");
                dialog.setContentText("You have the file already!");
                dialog.setResizable(true);
                dialog.getDialogPane().setPrefSize(250, 100);
                dialog.showAndWait();
                break;
            }
        }
        return isInClient;
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


