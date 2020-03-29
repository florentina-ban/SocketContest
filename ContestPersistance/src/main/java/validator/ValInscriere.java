package validator;

import domain.Inscriere;
import exceptions.InscrieriException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.RepoInscrieri;

public class ValInscriere implements Validator<Inscriere> {
    private static final Logger logger= LogManager.getLogger();
    private RepoInscrieri repoInscrieri;

    public ValInscriere(){

    }

    public void setRepoInscrieri(RepoInscrieri repoInscrieri) {
        this.repoInscrieri = repoInscrieri;
    }

    public void valideaza(Inscriere inscriere) throws InscrieriException {
        logger.traceEntry("valideaza nr de inscrieri pt participant");
        if(repoInscrieri.getProbeLaParticipant(inscriere.getIdPart()).size()>=2)
            throw new InscrieriException("candidatul e deja inscris la 2 probe");
    }
}
