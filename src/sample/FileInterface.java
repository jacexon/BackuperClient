package sample;

import com.healthmarketscience.rmiio.*;
import com.sun.corba.se.spi.activation.Server;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface FileInterface extends Remote {
    public void sendFile(RemoteInputStream ris, String filename, String extension, long lastModified) throws RemoteException, IOException;
    public String writeToFile(InputStream stream, String filename, String extension, long lastModified) throws IOException, RemoteException;
    public RemoteInputStream passAStream(String filepath) throws RemoteException;
    public boolean checkFileOnServer(String name, Date date) throws RemoteException;
    public RemoteInputStream tableStream() throws RemoteException, IOException;
    public RemoteInputStream chunkStream() throws RemoteException, IOException;
    public int getChunk() throws RemoteException;
}