package network.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server;

    public AbstractServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            server = new ServerSocket(port);
            while (true) {
                System.out.println("Server is open, waiting for clients");
                Socket client = server.accept();
                System.out.println("Client connected");
                processRequest(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
