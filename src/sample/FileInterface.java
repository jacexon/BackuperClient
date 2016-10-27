package sample;
import java.io.*;
import java.rmi.*;
import com.healthmarketscience.rmiio.*;

public interface FileInterface extends Remote{
    public void sendFile(RemoteInputStream ris, String filename, String extension) throws RemoteException, IOException;
    public void writeToFile(InputStream stream, String filename, String extension) throws IOException, RemoteException;
    public RemoteInputStream passAStream(String filename) throws  RemoteException;

}
