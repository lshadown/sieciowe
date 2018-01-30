package sample;

import com.sun.xml.internal.ws.util.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.net.ftp.FTPClient;
import sample.ftp.Ftp;

import java.awt.font.OpenType;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private FTPClient ftpClient;
    private Ftp ftp;
    public ComboBox<String> comboList = new ComboBox<>();
    public TextField textUrl;
    public TextField files;
    public TextField downloadFileText;


    public void connectServer(ActionEvent actionEvent) throws IOException {
        ftp = new Ftp("mkwk018.cba.pl","matgru","Programowaniesieciowe12345");
        ftpClient = ftp.connectServer();
       comboList.getItems().addAll("Test1", "test3");
    }

    public void showStructure(ActionEvent actionEvent) throws IOException {
        if(checkConnect()){
            ftp.showStructureTree(ftpClient);
        }
        else {
           showConnectAlert();
        }
    }

    public void disconnectServer(ActionEvent actionEvent) throws IOException {
        if(checkConnect()){
            ftp.disconnectServer(ftpClient);
        }
        else {
            showConnectAlert();
        }
    }
    private boolean checkConnect(){
        return ftp != null && ftpClient.isConnected();
    }
    private void showConnectAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Wystąpił problem z połączeniem");
        alert.setContentText("Proszę najpierw połączyć się z serwerem");

        alert.showAndWait();
    }

    public void uploadFile(ActionEvent actionEvent) throws IOException {
        if(checkConnect()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                InputStream targetStream = new FileInputStream(selectedFile);
                if (!textUrl.getText().equals(""))
                    ftp.uploadFile(textUrl.getText(), selectedFile.getName(), targetStream, ftpClient);
            }
        }else {
            showConnectAlert();
        }
    }

    public void showFiles(ActionEvent actionEvent) throws IOException {
        if(checkConnect()) {
            if (!files.getText().equals(""))
                ftp.showFiles(files.getText(), ftpClient);
        }else {
            showConnectAlert();
        }
    }

    public void downloadFile(ActionEvent actionEvent) throws IOException {
        if(checkConnect()) {
            DirectoryChooser fileChooser = new DirectoryChooser();
            fileChooser.setTitle("Open Resource File");

            File selectedDirectory = fileChooser.showDialog(null);
            if (selectedDirectory != null) {
                if (!downloadFileText.getText().equals(""))
                    ftp.downloadFile(downloadFileText.getText(), selectedDirectory.getAbsolutePath(), ftpClient);
                System.out.println("File download");
            }
        }else {
            showConnectAlert();
        }
    }
}
