package server;

import domain.*;
import repository.IRepoInscrieri;
import repository.IRepoParticipanti;
import repository.Repo;
import services.ContestException;
import services.IContestObserver;
import services.IContestService;
import utils.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ContestServicesImpl implements IContestService {
    ConnectionHelper connectionHelper;
    public IRepoParticipanti repoParticipanti;
    public IRepoInscrieri repoInscrieri;
    public Repo<Proba> repoProbe;
    public Repo<CategVarsta> repoCateg;
    public Map<String, IContestObserver> loggedInClients;

    public ContestServicesImpl(Properties prop,IRepoInscrieri repoIns, IRepoParticipanti repoPart, Repo<Proba> repoProbe, Repo<CategVarsta> repoCateg) {
        connectionHelper=new ConnectionHelper(prop);
        this.repoInscrieri = repoIns;
        this.repoParticipanti = repoPart;
        this.repoProbe = repoProbe;
        this.repoCateg = repoCateg;
        loggedInClients=new HashMap<>();
    }

    public synchronized void login(User user, IContestObserver client) throws ContestException{
        try (Connection connection=connectionHelper.getConnection()){
            try (PreparedStatement stat=connection.prepareStatement("select count(*) as nr from logindata where logindata.user=? and pass=?")){
                stat.setString(1,user.getUserName());
                stat.setString(2,user.getPassword());
                try (ResultSet re=stat.executeQuery()){
                    re.next();
                    if (re.getInt("nr")==0)
                        throw new ContestException("Date invalide");
                    loggedInClients.put(user.getUserName(),client);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public synchronized void logOut(User user, IContestObserver client) throws ContestException {
        if (loggedInClients.get(user.getUserName())!=null)
            loggedInClients.remove(user.getUserName());
        else
            throw new ContestException("User not found");
    }

    @Override
    public List<CategVarsta> getCategVarsta(Integer varsta) throws ContestException {
        Collection<CategVarsta> all = repoCateg.findAll();
        ArrayList<CategVarsta> allArr = new ArrayList<>();
        if (varsta!=null) {
            Predicate<CategVarsta> filter = (x -> {
                return x.apartine(varsta);
            });
            all.stream()
                    .filter(filter)
                    .forEach(x -> allArr.add(x));
        }
        else {
            all.stream()
                    .forEach(x -> allArr.add(x));
        }
            return allArr;
    }

    public List<Proba> getProbe(CategVarsta cat) {
        ArrayList<Proba> all =
                (ArrayList<Proba>) repoProbe.findAll().stream()
                        .filter(x -> x.getCategVarsta().equals(cat))
                        .collect(Collectors.toList());
        return all;
    }

/*
    public ArrayList<Participant> getParticipanti(Proba proba) {
        ArrayList<Participant> all =
                (ArrayList<Participant>) repoInscrieri.getParticipantiLaProba(proba.getIdProba());
        all.forEach(x -> {
            x.setNrProbe(repoInscrieri.getProbeLaParticipant(x.getId()).size());
        });
        return all;
    }

    public ArrayList<Participant> getParticipantiSearch(String s) {
        ArrayList<Participant> all =
                (ArrayList<Participant>) repoParticipanti.findAll()
                        .stream()
                        .filter(x -> x.getNume().toUpperCase().contains(s.toUpperCase()))
                        .collect(Collectors.toList());
        all.forEach(x -> {
            x.setNrProbe(repoInscrieri.getProbeLaParticipant(x.getId()).size());
        });
        return all;
    }

    public ArrayList<Participant> getParticipantiSearch(ArrayList<Participant> list, String s) {
        List<Participant> all = list
                .stream()
                .filter(x->x.getNume().toUpperCase().contains(s.toUpperCase()))
                .collect(Collectors.toList());
        return null;
    }


    public ArrayList<Participant> getParticipantiSearch(String s, Proba proba) {
        ArrayList<Participant> all =
                (ArrayList<Participant>) repoInscrieri.getParticipantiLaProba(proba.getIdProba())
                        .stream()
                        .filter(x -> x.getNume().toUpperCase().contains(s.toUpperCase()))
                        .collect(Collectors.toList());

        all.forEach(x -> {
            x.setNrProbe(repoInscrieri.getProbeLaParticipant(x.getId()).size());
        });
        return all;
    }

    public ArrayList<Participant> getAllParticipanti() {
        ArrayList<Participant> all = (ArrayList<Participant>) repoParticipanti.findAll();

        all.forEach(x -> {
            x.setNrProbe(repoInscrieri.getProbeLaParticipant(x.getId()).size());
        });
        return all;
    }

    public ArrayList<Proba> getProbe(Participant participant) {
        ArrayList<Proba> all = (ArrayList<Proba>) repoInscrieri.getProbeLaParticipant(participant.getId());
        return all;
    }

    public ArrayList<Proba> getProbePtVarsta(int varsta) {
        ArrayList<Proba> all =
                (ArrayList<Proba>) repoProbe.findAll().stream()
                        .filter(x -> x.getCategVarsta().apartine(varsta))
                        .collect(Collectors.toList());
        return all;
    }

    public void adaugaParticipant(String nume, int varsta, Proba p1, Proba p2) throws RepoException {
        Participant participant = new Participant(nume, varsta);
        int idPart = repoParticipanti.adauga(participant);
        if (p1 != null)
            repoInscrieri.adauga(new Inscriere(p1.getIdProba(), idPart));
        if (p2 != null && p1 != p2)
            repoInscrieri.adauga(new Inscriere(p2.getIdProba(), idPart));
    }

    public void stergeParticipant(Participant p) {
        repoParticipanti.sterge(p.getId());
    }
*/
}