package local.intranet.quarkus.api.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.intranet.quarkus.api.info.Message;

/**
 * 
 * {@link IndexController}
 * 
 * @author radek.kadner
 *
 */
@Path("/")
@Tag(name = "index-controller")
public class IndexController {

	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

	/**
	 * 
	 * HELLO = "Hello from RESTEasy Reactive"
	 */
	public static final String HELLO = "Hello from RESTEasy Reactive";

		/**
	 * 
	 * NAZDAR = "Nazdar from RESTEasy Reactive"
	 */
	public static final String NAZDAR = "Nazdar from RESTEasy Reactive";

	/**
	 * 
	 * Say: Hello ...
	 * 
	 * @return {@link Message}
	 */
	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	// @Timed(value="hello", description = Measure.TIMED_DESCRIPTION)
	// @Counted(value="hello", description = Measure.COUNTED_DESCRIPTION)
	@Operation(summary = "Hello", description = "This method say: <strong>" + HELLO + "</strong>\n")
	public Message hello() {
		LOG.debug("{}", HELLO);
		return new Message(HELLO);
	}
	

	/**
	 * 
	 * Say: Nazdar ...
	 * 
	 * @return {@link Message}
	 */
	@GET
	@Path("/nazdar")
	@Produces(MediaType.APPLICATION_JSON)
	// @Timed(value="nazdar", description = Measure.TIMED_DESCRIPTION)
	// @Counted(value="nazdar", description = Measure.COUNTED_DESCRIPTION)
	@Operation(summary = "Nazdar", description = "This method say: <strong>" + NAZDAR + "</strong>\n")
	public Message nazdar() {
		LOG.debug("{}", NAZDAR);
		return new Message(NAZDAR);
	}
	
}