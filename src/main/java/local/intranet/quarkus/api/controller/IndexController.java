package local.intranet.quarkus.api.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.info.Message;
import local.intranet.quarkus.api.info.content.PlatypusCounter;

/**
 * 
 * {@link IndexController}
 * 
 * ROOT routes as hello or ahoj
 * 
 * @author Radek Kádner
 *
 */
@Path("/")
@Tag(name = IndexController.TAG)
public class IndexController extends PlatypusCounter implements Countable, Invocationable, Statusable, Nameable {

	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

	/**
	 * 
	 * TAG = "index-controller"
	 */
	protected static final String TAG = "index-controller";

	/**
	 * 
	 * HELLO = "Hello from Platypus-Quarkus"
	 */
	public static final String HELLO = "Hello from Platypus-Quarkus";

	/**
	 * 
	 * AHOJ = "Ahoj from Platypus-Quarkus"
	 */
	public static final String AHOJ = "Ahoj from Platypus-Quarkus";

	/**
	 * 
	 * Say: Hello ...
	 * 
	 * @see <a href="/q/swagger-ui/#/index-controller/hello" target=
	 *      "_blank">/q/swagger-ui/#/index-controller/hello</a>
	 * 
	 * @return {@link Message}
	 */
	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(operationId = "hello", summary = "Hello", description = "This method say: <strong>" + HELLO
			+ "</strong><br/><br/>"
			+ "See <a href=\"/javadoc/local/intranet/quarkus/api/controller/IndexController.html#hello()\" "
			+ "target=\"_blank\">IndexController.hello</a>")
	public Message hello() {
		incrementCounter();
		LOG.debug("{}", HELLO);
		return new Message(HELLO);
	}

	/**
	 * 
	 * Say: Ahoj ...
	 * 
	 * @see <a href="/q/swagger-ui/#/index-controller/ahoj" target=
	 *      "_blank">/q/swagger-ui/#/index-controller/ahoj</a>
	 * 
	 * @return {@link Message}
	 */
	@GET
	@Path("/ahoj")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(operationId = "ahoj", summary = "Ahoj", description = "This method say: <strong>" + AHOJ
	+ "</strong><br/><br/>"
	+ "See <a href=\"/javadoc/local/intranet/quarkus/api/controller/IndexController.html#ahoj()\" "
	+ "target=\"_blank\">IndexController.ahoj</a>")
	public Message ahoj() {
		incrementCounter();
		LOG.debug("{}", AHOJ);
		return new Message(AHOJ);
	}
	
}