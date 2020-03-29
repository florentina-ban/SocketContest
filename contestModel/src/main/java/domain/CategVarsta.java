package domain;

import java.io.Serializable;
import java.util.Objects;

public class CategVarsta implements Serializable {
    private int id;
    private String nume;
    private int varstaStart;
    private int varstaEnd;

    public CategVarsta(String nume, int varstaStart, int varstaEnd) {
        this.nume = nume;
        this.varstaStart = varstaStart;
        this.varstaEnd = varstaEnd;
    }

    public CategVarsta(Integer id,String nume, int varstaStart, int varstaEnd) {
        this.id=id;
        this.nume = nume;
        this.varstaStart = varstaStart;
        this.varstaEnd = varstaEnd;
    }

    @Override
    public String toString() {
        return "CategVarsta: "+nume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategVarsta that = (CategVarsta) o;
        return getNume().equals(that.getNume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNume());
    }

    public boolean apartine(int varsta){
        return varsta>=this.varstaStart && varsta<=varstaEnd;
    }

    public String getNume() {
        return nume;
    }

    public int getVarstaStart() {
        return varstaStart;
    }

    public int getVarstaEnd() {
        return varstaEnd;
    }
}
