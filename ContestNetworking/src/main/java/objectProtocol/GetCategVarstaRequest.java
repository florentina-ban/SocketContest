package objectProtocol;

import java.io.Serializable;

public class GetCategVarstaRequest implements Request {
    private Integer varsta=null;

    public GetCategVarstaRequest(Integer varsta) {
        this.varsta = varsta;
    }
    public GetCategVarstaRequest(){}

    public Integer getVarsta() {
        return varsta;
    }
}
