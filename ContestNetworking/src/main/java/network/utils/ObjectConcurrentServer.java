package network.utils;

import objectProtocol.ClientWorker;
import services.IContestService;

import java.net.Socket;

public class ObjectConcurrentServer extends ConcurrentServer {
    IContestService services;

    public ObjectConcurrentServer(int port, IContestService service) {
        super(port);
        this.services=service;
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientWorker worker=new ClientWorker(services, client);
        Thread tw=new Thread(worker);
        return tw;
    }
}
