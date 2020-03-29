package objectProtocol;

import domain.CategVarsta;

public class GetProbeRequest implements Request {
    private CategVarsta categVarsta;

    public GetProbeRequest(CategVarsta categVarsta) {
        this.categVarsta = categVarsta;
    }

    public CategVarsta getCategVarsta() {
        return categVarsta;
    }
}
