package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.*;
public class clientScreenController implements Initializable{
    @FXML
    private TableView<ServerTable> table;

    @FXML
    private TableColumn<ServerTable, Integer> idColumn;

    @FXML
    private TableColumn<ServerTable,String> filenameColumn;

    @FXML
    private TableColumn<ServerTable,String> extensionColumn;

    @FXML
    private TableColumn<ServerTable,Long> sizeColumn;

    @FXML
    private TableColumn<ServerTable,String> lastModifiedColumn;

    @FXML
    private TableColumn<ServerTable,String> versionColumn;

    @FXML private MenuItem upload_menuitem;
    @FXML private MenuItem download_menuitem;

    @FXML private Menu file_menu;
    @FXML private Menu about_menu;
    @FXML private Menu info_menu;

    public void handleUpload(ActionEvent event) throws IOException{
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
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        fileChooser.showOpenDialog(selectingFilesStage);
    }


    private int iNumber = 1;

    private final ObservableList<ServerTable> data = FXCollections.observableArrayList(
            new ServerTable(iNumber++,"lol",".jpg","2015-12-12",1212122,"2.0"),
            new ServerTable(iNumber++,"be",".png","2015-12-12", 20102121, "2.1"),
            new ServerTable(iNumber++,"l",".jpg","2015-12-12",1212122,"2.0"),
            new ServerTable(iNumber++,"b.",".png","2015-12-12", 20102121, "2.1")
    );


    public void addRow(int id, String filename, String extension, String lastModified, long size, String version){
        data.add(new ServerTable(iNumber++,filename,extension,lastModified,size,version));
        table.setItems(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        upload_menuitem.setAccelerator(new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN));
        download_menuitem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        filenameColumn.setCellValueFactory(new PropertyValueFactory<>("FileName"));
        extensionColumn.setCellValueFactory(new PropertyValueFactory<>("Extension"));
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<>("lastModified"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<>("Version"));

        table.setItems(data);
    }






}
