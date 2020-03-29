package objectProtocol;

import domain.CategVarsta;

import java.util.List;

public class GetCategVarstaResponse implements Response {
    private Integer varsta;
    private List<CategVarsta> list;

    public GetCategVarstaResponse(Integer varsta, List<CategVarsta> categVarstas) {
        this.varsta = varsta;
        this.list = categVarstas;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public List<CategVarsta> getList() {
        return list;
    }
}
