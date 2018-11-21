package ws.index;

import javax.jws.WebService;

@WebService(endpointInterface = "ws.index.Index")
public class IndexImpl implements Index{
    @Override
    public String index() {
        return "SOAP is Online";
    }
}
