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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.annotation.Resource;
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

    public void handleUpload(ActionEvent event) throws IOException{
        Parent selectingFilesScreen= FXMLLoader.load(getClass().getResource("selectingFilesScreen.fxml"));
        Stage selectingFilesStage = new Stage();
        selectingFilesStage.setScene(new Scene(selectingFilesScreen,400,400));
        selectingFilesStage.setTitle("FileChooser");
        selectingFilesStage.show();
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        filenameColumn.setCellValueFactory(new PropertyValueFactory<>("FileName"));
        extensionColumn.setCellValueFactory(new PropertyValueFactory<>("Extension"));
        lastModifiedColumn.setCellValueFactory(new PropertyValueFactory<>("lastModified"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        versionColumn.setCellValueFactory(new PropertyValueFactory<>("Version"));

        table.setItems(data);
    }




}
