import gui.Controller;
import gui.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objectProtocol.ServicesProxy;
import services.IContestService;

public class StartClient extends Application{

    public static void main(String[] args) {
        launch(args);
    }
    private Stage primaryStage;

        private static int defaultChatPort = 55555;
        private static String defaultServer = "localhost";


        public void start(Stage primaryStage) throws Exception {
            System.out.println("In start");
            /*
            Properties clientProps = new Properties();
            try {
                clientProps.load(StartRpcClientFX.class.getResourceAsStream("/chatclient.properties"));
                System.out.println("Client properties set. ");
                clientProps.list(System.out);
            } catch (IOException e) {
                System.err.println("Cannot find chatclient.properties " + e);
                return;
            }
            String serverIP = clientProps.getProperty("chat.server.host", defaultServer);
            int serverPort = defaultChatPort;

            try {
                serverPort = Integer.parseInt(clientProps.getProperty("chat.server.port"));
            } catch (NumberFormatException ex) {
                System.err.println("Wrong port number " + ex.getMessage());
                System.out.println("Using default port: " + defaultChatPort);
            }*/
            System.out.println("Using server IP " + defaultServer);
            System.out.println("Using server port " + defaultChatPort);

            IContestService server = new ServicesProxy(defaultServer, defaultChatPort);

            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("logInView.fxml"));
            Parent root=loader.load();

            LogInController logInctrl =
                    loader.<LogInController>getController();
            logInctrl.setServer(server);

            primaryStage.setTitle("Children Contest");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }

    }
