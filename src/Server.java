import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server
{
    public static final int PORT = 6666;
    public static ServerSocket serverSocket = null; // Server gets found
    public static Socket openSocket = null;         // Server communicates with the client

    public static ServerSocket startupServer() throws UnknownHostException, IOException
    {
        String serverIP = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Server ip: " + serverIP);
        serverSocket = new ServerSocket(PORT);

        return serverSocket;
    }

    public static Socket configureServer(ServerSocket serverSocket) throws UnknownHostException, IOException
    {
        openSocket = serverSocket.accept();
        System.out.println("Server accepts requests at: " + openSocket);

        return openSocket;
    }

    public static void main(String[] args) throws IOException
    {
        try
        {
            ServerSocket socket = startupServer();
            Map<Integer,Account> listOfAccounts = new HashMap<>();

            do{
                openSocket = configureServer(socket);
                Thread t1 = new Thread(new ClientHandler (openSocket, listOfAccounts));
                t1.start();

            }while(true);
        }
        catch(Exception e)
        {
            System.out.println(" Connection fails: " + e);
        }
        finally
        {
            serverSocket.close();
            System.out.println("Server port closed");
        }

    }
}