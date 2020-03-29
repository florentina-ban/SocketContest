package domain;

public class Participant {
    private Integer id;
    private String nume;
    private int varsta;
    private int nrProbe;

    public Participant(Integer id, String nume, int varsta) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
        this.nrProbe=0;
    }
    public Participant(String nume, int varsta) {
        this.nume = nume;
        this.varsta = varsta;
        this.nrProbe=0;
    }
    public Participant(Integer id, String nume, int varsta,int nrProb) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
        this.nrProbe=nrProb;
    }

    public int getNrProbe() {
        return nrProbe;
    }

    public void setNrProbe(int nrProbe) {
        this.nrProbe = nrProbe;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                '}';
    }


    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
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
}
