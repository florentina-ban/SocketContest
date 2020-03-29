package objectProtocol;

import domain.CategVarsta;
import domain.Proba;

import java.util.List;

public class GetProbeResponse implements Response {
    private CategVarsta categVarsta;
    private List<Proba> list;

    public GetProbeResponse(CategVarsta categVarsta, List<Proba> list) {
        this.categVarsta = categVarsta;
        this.list = list;
    }

    public CategVarsta getCategVarsta() {
        return categVarsta;
    }

    public List<Proba> getList() {
        return list;
    }
}
