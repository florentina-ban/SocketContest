package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import domain.CategVarsta;
import domain.Participant;
import domain.Proba;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import services.ContestException;
import services.IContestObserver;
import services.IContestService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller  implements Initializable, IContestObserver {
    public LogInController logController;
    private IContestService service;
    private User user;

    ObservableList<Proba> modelProbe = FXCollections.observableArrayList();
    ObservableList<CategVarsta> modelCateg = FXCollections.observableArrayList();
    ObservableList<Participant> modelPart = FXCollections.observableArrayList();
    ObservableList<Participant> modelPartAdauga = FXCollections.observableArrayList();
    ObservableList<Proba> modelProbaCombo = FXCollections.observableArrayList();

    @FXML TableView<CategVarsta> tabelCateg;
    @FXML TableColumn<CategVarsta, String> colCateg;
    @FXML TableView<Proba> tabelProbe;
    @FXML TableColumn<Proba, String> colProbe;
         /*
            @FXML
            TableView<Participant> tabelPart;
            @FXML
            TableColumn<Participant, Integer> colIdPart;
            @FXML
            TableColumn<Participant, String> colNumePart;
            @FXML
            TableColumn<Participant, Integer> colVarstaPart;
            @FXML
            TableColumn<Participant, Integer> colNrProbe;

            @FXML
            AnchorPane mainAnchor;
            @FXML
            AnchorPane categAnchor;
            @FXML
            TextField searchTextField;
            @FXML
            CheckBox allCheck;
            @FXML
            TextField proba1;
            @FXML
            TextField proba2;
            @FXML
            Button showCategAnchor;
            //tab adauga
            @FXML
            ComboBox<Proba> combo1;
            @FXML
            ComboBox<Proba> combo2;
            @FXML
            TextField numeTxt;
            @FXML
            TextField varstaTxt;
            @FXML
            TextField errorTxt;
            @FXML
            TableView<Participant> tabelAdauga;
            @FXML
            TableColumn<Participant, Integer> idColAdd;
            @FXMLf
            TableColumn<Participant, String> numeColAdd;
            @FXML
            TableColumn<Participant, Integer> varstaColAdd;
            @FXML
            TableColumn<Participant, Integer> nrProbeColAdd;
            @FXML
            Button stergeBtn;
    */
    public void setService(IContestService s) {
        this.service = s;
        initializeVizualizeazaTab();
        tabelCateg.setItems(modelCateg);
        tabelProbe.setItems(modelProbe);
            try{
                modelCateg.setAll(service.getCategVarsta(null));
            } catch (ContestException e) {
                e.printStackTrace();
            }
        //modelPartAdauga.setAll(service.getAllParticipanti());
        //modelPart.setAll(service.getAllParticipanti());
        // allCheck.setSelected(true);
    }

    public IContestService getService() {
        return service;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLogController(LogInController logController) {
        this.logController = logController;
    }

    public void logOut() {
        try {
            service.logOut(user, this);
            logController.handleLogOut();

        } catch (ContestException e) {
            System.out.println("Logout error " + e);
        }
    }
    /*
    public void handleVizTab(Event event) {
            allCheck.setSelected(true);
        }

        public void initialize() throws IOException {
            probeAnchor.setVisible(false);
            categAnchor.setVisible(false);
            tabelProbe.setItems(modelProbe);
            tabelCateg.setItems(modelCateg);
            tabelPart.setItems(modelPart);
            tabelAdauga.setItems(modelPartAdauga);

            colCateg.setCellValueFactory(new PropertyValueFactory<CategVarsta, String>("nume"));
            colProbe.setCellValueFactory(new PropertyValueFactory<Proba, String>("nume"));
            colIdPart.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("id"));
            colNumePart.setCellValueFactory(new PropertyValueFactory<Participant, String>("nume"));
            colVarstaPart.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("varsta"));
            colNrProbe.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("nrProbe"));
            idColAdd.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("id"));
            numeColAdd.setCellValueFactory(new PropertyValueFactory<Participant, String>("nume"));
            varstaColAdd.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("varsta"));
            nrProbeColAdd.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("nrProbe"));

            combo1.setItems(modelProbaCombo);
            combo2.setItems(modelProbaCombo);

            initializeVizualizeazaTab();
        }

        public void setLogController(LogController logController) {
            this.logController = logController;
        }

        public void showProbe(CategVarsta categ) {
            modelProbe.setAll(service.getProbe(categ));
            probeAnchor.setVisible(true);
            final float movingDistance = 110;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    double i = 0;
                    try {
                        while (i < movingDistance) {
                            Thread.sleep(1);
                            mainAnchor.setLeftAnchor(probeAnchor, i);
                            i += 1;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }

        public void showParticipanti(Proba proba) {
            allCheck.setSelected(false);
            modelPart.setAll(service.getParticipantiSearch(searchTextField.getText(), proba));
            searchTextField.clear();
            tabelPart.getSelectionModel().clearSelection();
            proba1.clear();
            proba2.clear();
        }

        public void showAllParticipants() {
            if (allCheck.isSelected()) {
                tabelProbe.getSelectionModel().clearSelection();
                tabelCateg.getSelectionModel().clearSelection();
                backCategFunction(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1,
                        true, true, true, true, true, false,
                        false, false, false, false, null));
                searchTextField.clear();
                modelPart.setAll(service.getAllParticipanti());
            } else {
                searchTextField.clear();
                modelPart.clear();
                modelCateg.setAll(service.getCategVarsta());
                showAnchorCategFunction(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1,
                        true, true, true, true, true, false,
                        false, false, false, false, null));
            }
        }

        public void handleSearchField(String newVal) {
            if (allCheck.isSelected()) {
                modelPart.setAll(service.getParticipantiSearch(newVal));
            } else {
                Proba p = tabelProbe.getSelectionModel().getSelectedItem();
                if (p != null)
                    modelPart.setAll(service.getParticipantiSearch(newVal, p));
            }
        }

        public void participantSelection(Participant participant) {
            ArrayList<Proba> all = service.getProbe(participant);
            if (all.size() >= 1) {
                proba1.setText(all.get(0).getNume());
            } else
                proba1.clear();
            if (all.size() == 2) {
                proba2.setText(all.get(1).getNume());
            } else proba2.clear();
        }
        */

        public void initializeVizualizeazaTab() {
            tabelCateg.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try{
                if (newValue!=null){
                    modelProbe.setAll(service.getProbe(newValue));
                }
                }catch (ContestException ex){
                    Alert alert=new Alert(Alert.AlertType.WARNING,ex.getMessage());
                    alert.showAndWait();
                }
            });
        }
        /*
            tabelProbe.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                showParticipanti(newValue);
            });
            tabelPart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                participantSelection(newValue);
            });
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                handleSearchField(newValue);
            });
            allCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
                showAllParticipants();
            });
            allCheck.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (allCheck.isFocused())
                    allCheck.setSelected(true);
            });

        }

        public void initializeAdaugaTab() {
            modelPartAdauga.setAll(service.getParticipantiSearch(""));
            combo1.setCellFactory(new Callback<ListView<Proba>, ListCell<Proba>>() {
                @Override
                public ListCell<Proba> call(ListView<Proba> param) {
                    final ListCell<Proba> cell = new ListCell<Proba>() {
                        @Override
                        public void updateItem(Proba item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null)
                                setText(item.getNume());
                            else
                                setText("...");
                        }
                    };
                    return cell;
                }
            });
            combo1.setButtonCell(new ListCell<Proba>() {
                @Override
                protected void updateItem(Proba item, boolean btl) {
                    super.updateItem(item, btl);
                    if (item != null)
                        setText(item.getNume());
                    else setText("...");
                }
            });
            combo2.setCellFactory(new Callback<ListView<Proba>, ListCell<Proba>>() {
                @Override
                public ListCell<Proba> call(ListView<Proba> param) {
                    final ListCell<Proba> cell = new ListCell<Proba>() {
                        @Override
                        public void updateItem(Proba item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null)
                                setText(item.getNume());
                            else
                                setText("...");
                        }
                    };
                    return cell;
                }
            });
            combo2.setButtonCell(new ListCell<Proba>() {
                @Override
                protected void updateItem(Proba item, boolean btl) {
                    super.updateItem(item, btl);
                    if (item != null)
                        setText(item.getNume());
                    else setText("...");
                }
            });
            varstaTxt.textProperty().addListener((obs, oldV, newV) -> {
                try {
                    if (newV.length() > 0) {
                        int varsta = Integer.parseInt(newV);
                        modelProbaCombo.setAll(service.getProbePtVarsta(varsta));
                        modelProbaCombo.add(null);
                        errorTxt.clear();
                    } else {
                        errorTxt.clear();
                        modelProbaCombo.removeAll();
                    }
                } catch (NumberFormatException ex) {
                    errorTxt.setText(ex.getMessage());
                }
            });
        }


        public void showAnchorCategFunction(MouseEvent mouseEvent) {
            mainAnchor.setLeftAnchor(categAnchor, -110d);
            categAnchor.setVisible(true);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    double i = -110;
                    try {
                        while (i <= 0) {
                            Thread.sleep(1);
                            mainAnchor.setLeftAnchor(categAnchor, i);
                            i += 1;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }

        public void backCategFunction(MouseEvent mouseEvent) {
            final float movingDistance = 110;
            if (probeAnchor.isVisible())
                return;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    double i = 0d;
                    try {
                        while (i >= -110) {
                            Thread.sleep(1);
                            mainAnchor.setLeftAnchor(categAnchor, i);
                            i -= 1;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            categAnchor.setVisible(false);
        }

        public void logOut(MouseEvent mouseEvent) {
            this.logController.setLogScene();
        }

        public void adaugaParticipant(MouseEvent mouseEvent) {
            try {
                String nume = numeTxt.getText();
                int varsta = Integer.parseInt(varstaTxt.getText());
                Proba p1 = combo1.getSelectionModel().getSelectedItem();
                Proba p2 = combo2.getSelectionModel().getSelectedItem();
                service.adaugaParticipant(nume, varsta, p1, p2);
                modelPartAdauga.setAll(service.getAllParticipanti());
                numeTxt.clear();
                varstaTxt.clear();
                modelProbaCombo.clear();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Participantul a fost adaugat cu succes");
                alert.showAndWait();
            } catch (RepoException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("varsta invalida");
                alert.showAndWait();
            }
        }

        public void handleAdaugaTab(Event event) {
            initializeAdaugaTab();
        }

        public void handleSterge(MouseEvent mouseEvent) {
            Participant participant = tabelAdauga.getSelectionModel().getSelectedItem();
            if (participant != null) {
                service.stergeParticipant(participant);
                modelPartAdauga.setAll(service.getAllParticipanti());
                numeTxt.setText(participant.getNume());
                varstaTxt.setText(participant.getVarsta().toString());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Participantul a fost sters din concurs");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Selectati un participant din tabel");
                alert.showAndWait();
            }
        }
*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCateg.setCellValueFactory(new PropertyValueFactory<CategVarsta, String>("nume"));
        colProbe.setCellValueFactory(new PropertyValueFactory<Proba, String>("nume"));

    }
}

