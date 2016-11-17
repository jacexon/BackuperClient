/*package sample;

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
    public static void saveFile(InputStream stream, String filename) throws IOException, RemoteException {
        FileOutputStream output = null;
        File file = null;
        String extension = filename.substring(filename.lastIndexOf("."),filename.lastIndexOf("-"));
        try {
            file = File.createTempFile(filename, extension, new File("D:\\Client"));
            output = new FileOutputStream(file);

            int chunk = 4096;
            byte [] result = new byte[chunk];

            int readBytes;
            do {
                readBytes = stream.read(result);
                if (readBytes > 0)
                    output.write(result, 0, readBytes);
                System.out.println("Zapisuje...");
            } while(readBytes != -1);
            System.out.println(file.length());

            output.flush();


        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(output != null){
                output.close();
                if(file.renameTo(new File(file.getParent() + "\\" + filename + extension))){

                    System.out.println("Rename succesful");
                }
                else{
                    System.out.println("Rename failed");
                }
                System.out.println("Zamykam strumień...");
            }
        }
    }
}*/