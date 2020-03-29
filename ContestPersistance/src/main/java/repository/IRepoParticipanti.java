package repository;

import domain.Participant;
import domain.ParticipantDTO;
import exceptions.RepoException;

public interface IRepoParticipanti extends Repo<Participant> {
    int adauga(Participant elem) throws RepoException;
    void sterge(Integer id);
    Participant cautaNume(String nume);
    void modifica(Participant newPart);
}
