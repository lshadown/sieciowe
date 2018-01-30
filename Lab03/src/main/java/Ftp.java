import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import sun.net.ftp.FtpClient;

import java.io.IOException;

public class Ftp {
    private String host;
    private String userName;
    private String userPasswd;

    public Ftp(String host, String userName, String userPasswd) {
        this.host = host;
        this.userName = userName;
        this.userPasswd = userPasswd;
    }

    public FTPClient connectServer() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host);
        ftpClient.login(userName, userPasswd);
        return ftpClient;
    }

    public void disconectServer(FTPClient ftpClient) throws IOException {
        ftpClient.disconnect();
    }

    public void showStructureTree(FTPClient ftpClient) throws IOException {
        for(FTPFile file : ftpClient.listDirectories()){
            System.out.println(file.getName());
            if(!file.getName().equals(".") && !file.getName().equals(".."))
                readStructureTree(file.getName(), ftpClient,"");
        }
    }

    private void readStructureTree(String fileName, FTPClient ftpClient, String txt) throws IOException {
        for(FTPFile file :ftpClient.listDirectories(fileName)){
            System.out.println(txt +"---- "+file.getName());
            if(!file.getName().equals(".") && !file.getName().equals(".."))
                readStructureTree(fileName+"/"+file.getName(), ftpClient,txt+"-------");
        }
    }

}
