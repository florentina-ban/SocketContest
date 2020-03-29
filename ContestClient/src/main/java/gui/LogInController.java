package gui;
import domain.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import services.ContestException;
import services.IContestService;

import java.io.IOException;

public class LogInController {
    private IContestService server;
    private Controller chatCtrl;
    private User crtUser;
    Stage stage;

    @FXML TextField userField;
    @FXML TextField passField;
    @FXML Button autentificaBtn;

    public void setServer(IContestService s){
        server=s;
    }

    public void handleAutentifica(MouseEvent actionEvent) {
        //Parent root;
        String nume = userField.getText();
        String passwd = passField.getText();
        crtUser = new User(nume, passwd);
        stage=new Stage();
        try{
            server.login(crtUser, chatCtrl);
            // Util.writeLog("User succesfully logged in "+crtUser.getId());
            //Stage stage=new Stage();
            FXMLLoader cloader = new FXMLLoader(getClass().getClassLoader().getResource("mainView.fxml"));
            Parent mainRoot=cloader.load();
            Controller mainCtrl = cloader.<Controller>getController();
                mainCtrl.setService(server);
                mainCtrl.setLogController(this);

            this.setMainController(mainCtrl);
            stage.setTitle("Contest Window for " +crtUser.getUserName());
            stage.setScene(new Scene(mainRoot));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    chatCtrl.logOut();
                    System.exit(0);
                }
            });
            chatCtrl.setUser(crtUser);
            stage.show();

        }   catch (ContestException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Children Contest");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        }
    }
public void handleLogOut(){
        stage.close();
}

    public void pressCancel(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void setUser(User user) {
        this.crtUser = user;
    }

    public void setMainController(Controller chatController) {
        this.chatCtrl = chatController;
    }
}
