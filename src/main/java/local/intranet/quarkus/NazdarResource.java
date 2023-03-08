package local.intranet.quarkus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/nazdar")
public class NazdarResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Nazdar() {
        return "Nazdar from RESTEasy Reactive";
    }
}