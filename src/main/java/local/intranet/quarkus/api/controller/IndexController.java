package local.intranet.quarkus.api.controller;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.exception.PlatypusQuarkusException;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.info.Message;
import local.intranet.quarkus.api.info.content.PlatypusCounter;
import local.intranet.quarkus.api.service.CounterService;

/**
 * 
 * {@link IndexController}
 * 
 * ROOT routes as hello or ahoj
 * 
 * @author Radek Kádner
 *
 */
// @Path("")
@RestController
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
	 * {@link CounterService} for {@link #indexCounter()}
	 */
	@Autowired
	protected CounterService counterService;

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
	// @GET
	// @Path("/hello")
	// @Produces(MediaType.APPLICATION_JSON)
	@GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Hello", description = "This method say: **" + HELLO + "**<br/><br/>"
			+ "See [IndexController.hello](/javadoc/local/intranet/quarkus/api/controller/IndexController.html#hello())")
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
	// @GET
	// @Path("/ahoj")
	// @Produces(MediaType.APPLICATION_JSON)
	@GetMapping(value = "/ahoj", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Ahoj", description = "This method say: **" + AHOJ + "**<br/><br/>"
			+ "See [IndexController.ahoj](/javadoc/local/intranet/quarkus/api/controller/IndexController.html#ahoj())")
	public Message ahoj() {
		incrementCounter();
		LOG.debug("{}", AHOJ);
		return new Message(AHOJ);
	}

	/**
	 * 
	 * Counter informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.CounterService#getCounterInfo}.
	 * 
	 * @return {@link CounterInfo}
	 * @throws PlatypusQuarkusException {@link PlatypusQuarkusException}
	 */
	// @GET
	// @Path("counter")
	// @Produces(MediaType.APPLICATION_JSON)
	@GetMapping(value = "/indexCounter", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get Counter Info", description = "**Get Counter Info**<br/><br/>"
			+ "This method is calling CounterService.getCounterInfo<br/><br/>"
			+ "See [IndexController.indexCounter](/javadoc/local/intranet/quarkus/api/controller/IndexController.html#indexCounter())")
	public CounterInfo indexCounter() throws PlatypusQuarkusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.getCount(), formatDateTime(ret.getDate()),
				ret.getStatus());
		return ret;
	}

}