package services;

import domain.CategVarsta;
import domain.Proba;
import domain.User;

import java.util.List;

public interface IContestService {
    void login(User user, IContestObserver client) throws ContestException;
    void logOut(User user, IContestObserver client) throws ContestException;
    List<CategVarsta> getCategVarsta(Integer vasta) throws ContestException;
    List<Proba> getProbe(CategVarsta cat) throws ContestException;
}
