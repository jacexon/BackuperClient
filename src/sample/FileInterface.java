package sample;
import java.io.*;
import java.rmi.*;
import java.util.Properties;

import com.healthmarketscience.rmiio.*;

public interface FileInterface extends Remote{
    public void sendFile(RemoteInputStream ris, String filename, String extension, long lastModified) throws RemoteException, IOException;
    public void writeToFile(InputStream stream, String filename, String extension, long lastModified) throws IOException, RemoteException;
    public RemoteInputStream passAStream(String filename) throws  RemoteException;

    public boolean checkFileOnServer(String nameOfFile, long modifyDate) throws RemoteException;
    public Properties getSavedFilesList() throws RemoteException;
}
