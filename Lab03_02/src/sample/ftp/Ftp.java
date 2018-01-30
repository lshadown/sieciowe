
package sample.ftp;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilters;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Ftp {
    private String host;
    private String userName;
    private String userPasswd;
    private Collection<String> test = new ArrayList<>();

    public Ftp(String host, String userName, String userPasswd) {
        this.host = host;
        this.userName = userName;
        this.userPasswd = userPasswd;
    }

    public FTPClient connectServer() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host);
        ftpClient.login(userName, userPasswd);
        System.out.println("Connected");
        //getTree(ftpClient);
        return ftpClient;
    }

    public void disconnectServer(FTPClient ftpClient) throws IOException {
        System.out.println("Disconnected");
        ftpClient.disconnect();
    }

    public void showFiles(String path, FTPClient ftpClient) throws IOException {
        for(FTPFile file : ftpClient.listFiles(path, FTPFileFilters.ALL)){
            if(!file.getName().equals(".") && !file.getName().equals(".."))
                System.out.println(file.getRawListing());
        }
    }

    public void downloadFile(String pathRemote, String pathLocal, FTPClient ftpClient) throws IOException {
        String fileNames[] = pathRemote.split("/");
        String fileName = fileNames[fileNames.length -1];
        File downloadFile2 = new File(pathLocal +"/"+ fileName);
        OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
        ftpClient.retrieveFile(pathRemote,outputStream1);
    }

    private void getTree(FTPClient ftpClient) throws IOException {
        for(FTPFile file : ftpClient.listDirectories()){
            if(!file.getName().equals(".") && !file.getName().equals(".."))
                test.add(file.getName());
                readTree(file.getName(), ftpClient,"");
        }
    }
    private void readTree(String fileName, FTPClient ftpClient, String txt) throws IOException {
        for(FTPFile file :ftpClient.listDirectories(fileName)){
            if(!file.getName().equals(".") && !file.getName().equals(".."))
                test.add(fileName+"/"+file.getName());
                readTree(fileName+"/"+file.getName(), ftpClient,txt+"-------");
        }
    }

    public void showStructureTree(FTPClient ftpClient) throws IOException {
        for(FTPFile file : ftpClient.listDirectories()){
            System.out.println(file.getName());
            if(!file.getName().equals(".") && !file.getName().equals(".."))
                //treeFolder.add(file.getName());
                readStructureTree(file.getName(), ftpClient,"");
        }
    }

    public void uploadFile(String url, String fileName, InputStream file, FTPClient ftpClient) throws IOException {
        ftpClient.storeFile(url+ "/" + fileName, file);
        System.out.println("File uploaded");
    }

    private void readStructureTree(String fileName, FTPClient ftpClient, String txt) throws IOException {
        for(FTPFile file :ftpClient.listDirectories(fileName)){
            System.out.println(txt +"---- "+file.getName());
            if(!file.getName().equals(".") && !file.getName().equals(".."))
                //treeFolder.add(fileName+"/"+file.getName());
                readStructureTree(fileName+"/"+file.getName(), ftpClient,txt+"-------");
        }
    }



}
