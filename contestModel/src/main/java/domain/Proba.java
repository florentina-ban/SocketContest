package domain;

import java.io.Serializable;
import java.util.Objects;

public class Proba implements Serializable {
    private int idProba;
    private String nume;
    private CategVarsta categVarsta;


    public Proba(int id,String nume, CategVarsta categVarsta) {
        this.idProba=id;
        this.nume = nume;
        this.categVarsta=categVarsta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proba proba = (Proba) o;
        return getIdProba() == proba.getIdProba() &&
                getNume().equals(proba.getNume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdProba(), getNume(), getCategVarsta());
    }

    public int getIdProba() {
        return idProba;
    }

    public String getNume() {
        return nume;
    }

    public CategVarsta getCategVarsta() {
        return categVarsta;
    }

    @Override
    public String toString() {
        return "proba: "+nume+" "+categVarsta;
    }
}
