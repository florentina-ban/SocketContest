package validator;

import domain.Participant;
import exceptions.ParticipException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.RepoParticipanti;

public class ValParticipanti implements Validator<Participant> {
    private static final Logger logger= LogManager.getLogger();
    private RepoParticipanti repoParticipanti;

    public ValParticipanti() {
    }

    public void setRepoParticipanti(RepoParticipanti repoParticipanti) {
        this.repoParticipanti = repoParticipanti;
    }

    @Override
    public void valideaza(Participant particip) throws ParticipException {
        logger.traceEntry("valideaza participant {}",particip);
        String errorString="";

        if (particip.getNume().trim().length()==0)
            errorString+="participantul nu are nume";
        if (particip.getVarsta()<6 || particip.getVarsta()>15)
            errorString+="nu exista probe pentru aceasta categorie de varsta";
        Participant part=repoParticipanti.cautaNume(particip.getNume());
        if (part!=null)
            errorString+="mai exista un participant cu numele: "+part.getNume();
        if (errorString.length()>5)
            throw new ParticipException(errorString);
    }
}
