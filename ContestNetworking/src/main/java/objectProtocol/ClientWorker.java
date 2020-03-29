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
import java.util.List;

public class ClientWorker  implements Runnable, IContestObserver {
    private IContestService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientWorker(IContestService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Object response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse((Response) response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;
        if (request instanceof LogInRequest) {
            System.out.println("Login request ...");
            LogInRequest logReq = (LogInRequest) request;
            User user = logReq.getUser();
            try {
                server.login(user, this);
                return new OkResponse();
            } catch (ContestException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof LogOutRequest) {
            System.out.println("LogOut request ...");
            LogOutRequest logReq = (LogOutRequest) request;
            User user = logReq.getUser();
            try {
                server.logOut(user, this);
                return new OkResponse();
            } catch (ContestException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GetCategVarstaRequest) {
            System.out.println("getCategVarsta request ...");
            GetCategVarstaRequest req = (GetCategVarstaRequest) request;
            Integer age = req.getVarsta();
            try {
                    List<CategVarsta> categ=server.getCategVarsta(age);
                    return new GetCategVarstaResponse(age,categ);
            } catch (ContestException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GetProbeRequest){
            System.out.println("getProbe request...");
            GetProbeRequest req=(GetProbeRequest)request;
            CategVarsta cat=req.getCategVarsta();
            try {
                List<Proba> list=server.getProbe(cat);
                return new GetProbeResponse(cat,list);
            } catch (ContestException e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        return response;
    }
    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

}
