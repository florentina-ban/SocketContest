package repository;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import exceptions.InscrieriException;
import validator.ValInscriere;

import java.util.Collection;

public interface IRepoInscrieri extends Repo<Inscriere> {
    RepoParticipanti repoParticipanti=null;
    RepoProbe repoProbe=null;
    ValInscriere valInscriere=null;


    Collection<Participant> getParticipantiLaProba(int idProba);
    Collection<Proba> getProbeLaParticipant(int idPart);
    int adauga(Inscriere elem) throws InscrieriException;
    void sterge(Integer id);
    Inscriere getInscriere(int idPart, int idProba);
}
