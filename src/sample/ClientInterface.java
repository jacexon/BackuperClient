package sample;

import java.io.*;
import java.nio.file.Path;
import java.rmi.*;
import com.healthmarketscience.rmiio.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
public interface ClientInterface extends Remote{
    public void saveFile(InputStream input) throws IOException;

}