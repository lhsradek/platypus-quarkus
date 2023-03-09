package local.intranet.quarkus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author radek.kadner
 *
 */
@Path("/hello")
public class HelloResource {

	private static final Logger Log = LoggerFactory.getLogger(HelloResource.class);
	
	/**
	 * 
	 * @return String
	 */
    @GET
	@Operation(operationId = "hello", description = "Entry point: <strong>\"hello\"</strong><br/><br/>This method say: <strong>Hello from RESTEasy Reactive</strong>\n")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
    	final String ret = "Hello from RESTEasy Reactive";
    	Log.info("ret: {}", ret);
        return ret;
    }
}