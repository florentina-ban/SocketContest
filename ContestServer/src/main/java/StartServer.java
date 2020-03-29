import network.utils.AbstractServer;
import network.utils.ObjectConcurrentServer;
import network.utils.ServerException;
import repository.RepoCategVarsta;
import repository.RepoInscrieri;
import repository.RepoParticipanti;
import repository.RepoProbe;
import server.ContestServicesImpl;
import services.IContestService;
import validator.ValInscriere;
import validator.ValParticipanti;

import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps = new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties " + e);
            return;
        }

        RepoCategVarsta repoCategVarsta = new RepoCategVarsta(serverProps);
        RepoProbe repoProbe = new RepoProbe(serverProps, repoCategVarsta);
        ValParticipanti valParticipanti = new ValParticipanti();
        RepoParticipanti repoParticipanti = new RepoParticipanti(serverProps, repoProbe, valParticipanti);
        valParticipanti.setRepoParticipanti(repoParticipanti);

        ValInscriere valInscriere = new ValInscriere();
        RepoInscrieri repoInscrieri = new RepoInscrieri(serverProps, repoParticipanti, repoProbe, valInscriere);
        valInscriere.setRepoInscrieri(repoInscrieri);
        IContestService contestServices = new ContestServicesImpl(serverProps, repoInscrieri, repoParticipanti, repoProbe, repoCategVarsta);

        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong  Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: " + serverPort);
        AbstractServer server = new ObjectConcurrentServer(serverPort, contestServices);
        server.start();
    }
}
