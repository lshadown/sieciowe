import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import sun.net.ftp.FtpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        Ftp ftp = new Ftp("mkwk018.cba.pl","matgru","Programowaniesieciowe12345");
        FTPClient ftpClient = ftp.connectServer();
        ftp.showStructureTree(ftpClient);

    }
}
