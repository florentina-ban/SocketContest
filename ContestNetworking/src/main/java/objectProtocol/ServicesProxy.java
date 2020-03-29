package objectProtocol;

import domain.CategVarsta;
import domain.Proba;
import domain.User;
import services.ContestException;
import services.IContestObserver;
import services.IContestService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesProxy implements IContestService {
    private String host;
    private int port;

    private IContestObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServicesProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }
    private void initializeConnection() throws ContestException {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }
    private void sendRequest(Request request) throws ContestException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ContestException("Error sending object " + e);
        }
    }
    private Response readResponse() throws ContestException {
        Response response = null;
        try {
            response = qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void login(User user, IContestObserver client) throws ContestException {
        initializeConnection();
        sendRequest(new LogInRequest(user));
        Response response = readResponse();
        if (response instanceof OkResponse) {
            this.client = client;
            return;
        }
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new ContestException(err.getMessage());
        }
    }
    public void logOut(User user, IContestObserver controller) throws ContestException{
        sendRequest(new LogOutRequest(user));
        Response response=readResponse();
        if (response instanceof OkResponse){
            return;
        }
        if (response instanceof ErrorResponse)
            throw new ContestException(response.toString());
    }

    public List<CategVarsta> getCategVarsta(Integer varsta) throws ContestException{
        sendRequest(new GetCategVarstaRequest(varsta));
        Response response=readResponse();
        if (response instanceof GetCategVarstaResponse)
            return ((GetCategVarstaResponse) response).getList();
        throw new ContestException("Get categorii varsta Error");
    }
    public List<Proba> getProbe(CategVarsta categVarsta) throws ContestException{
        sendRequest(new GetProbeRequest(categVarsta));
        Response response=readResponse();
        if (response instanceof GetProbeResponse)
            return ((GetProbeResponse)response).getList();
        throw new ContestException("get probe for cat varsta Error");
    }


    private void handleUpdate(UpdateResponse update) {

    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                    } else {
                        /*responses.add((Response)response);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (responses){
                            responses.notify();
                        }*/
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}


