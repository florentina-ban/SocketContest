package domain;

import java.util.ArrayList;

public class ParticipantDTO {
    private Integer id;
    private String nume;
    private int varsta;
    private int nrProbe;
    ArrayList<Proba> probe;

    public ParticipantDTO(Integer id, String nume, int varsta, int nrProbe,ArrayList<Proba> pr) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
        this.nrProbe = nrProbe;
        probe=pr;
    }
    public void addProba(Proba proba){
        this.probe.add(proba);
    }

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public int getNrProbe() {
        return nrProbe;
    }

    public ArrayList<Proba> getProbe() {
        return probe;
    }
}
