package local.intranet.quarkus.api.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Measureable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.info.Message;
import local.intranet.quarkus.api.info.content.PlatypusCounter;

/**
 * 
 * {@link IndexController}
 * 
 * @author Radek Kádner
 *
 */
@Path("/")
@ApplicationScoped
@Tag(name = IndexController.TAG)
public class IndexController extends PlatypusCounter implements Countable, Invocationable, Statusable {

	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

	/**
	 * 
	 * TAG = "index-controller"
	 */
	protected static final String TAG = "index-controller";

	// @Inject
	// protected Validator validator;

	/**
	 * 
	 * HELLO = "Hello from Platypus-Quarkus"
	 */
	public static final String HELLO = "Hello from Platypus-Quarkus";

	/**
	 * 
	 * NAZDAR = "Nazdar from Platypus-Quarkus"
	 */
	public static final String NAZDAR = "Nazdar from Platypus-Quarkus";

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
	@Timed(value = Measureable.PREFIX + "hello", description = Measureable.TIMED_DESCRIPTION)
	@Counted(value = Measureable.PREFIX + "hello", description = Measureable.COUNTED_DESCRIPTION)
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
	 * Say: Nazdar ...
	 * 
	 * @see <a href="/q/swagger-ui/#/index-controller/nazdar" target=
	 *      "_blank">/q/swagger-ui/#/index-controller/nazdar</a>
	 * 
	 * @return {@link Message}
	 */
	@GET
	@Path("/nazdar")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(value = Measureable.PREFIX + "nazdar", description = Measureable.TIMED_DESCRIPTION)
	@Counted(value = Measureable.PREFIX + "nazdar", description = Measureable.COUNTED_DESCRIPTION)
	@Operation(operationId = "nazdar", summary = "Nazdar", description = "This method say: <strong>" + NAZDAR
			+ "</strong><br/><br/>"
			+ "See <a href=\"/javadoc/local/intranet/quarkus/api/controller/IndexController.html#nazdar()\" "
			+ "target=\"_blank\">IndexController.nazdar</a>")
	public Message nazdar() {
		incrementCounter();
		LOG.debug("{}", NAZDAR);
		return new Message(NAZDAR);
	}

}