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
@Path("/nazdar")
public class NazdarResource {

	private static final Logger Log = LoggerFactory.getLogger(NazdarResource.class);

	/**
	 * 
	 * @return String
	 */
	@GET
	@Operation(operationId = "nazdar", description = "Entry point: <strong>\"nazdar\"</strong><br/><br/>This method say: <strong>Nazdar from RESTEasy Reactive</strong>\n")
	@Produces(MediaType.TEXT_PLAIN)
	public String nazdar() {
		final String ret = "Nazdar from RESTEasy Reactive";
		Log.debug("ret: {}", ret);
		return ret;
	}
}