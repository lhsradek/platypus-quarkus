package local.intranet.quarkus.api.controller;

import java.text.MessageFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Timed;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.exception.PlatypusQuarkusException;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.info.Message;
import local.intranet.quarkus.api.info.content.PlatypusCounter;
import local.intranet.quarkus.api.info.content.template.IndexTemplate;
import local.intranet.quarkus.api.info.content.template.PropertiesTemplate;
import local.intranet.quarkus.api.scheduler.PlatypusJob;
import local.intranet.quarkus.api.service.CounterService;

/**
 * 
 * {@link IndexController}
 * 
 * index for quarkus
 * 
 * @author Radek Kádner
 *
 */
@Timed
@Path("")
@ApplicationScoped
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
	@Inject
	protected CounterService counterService;

	/**
	 * 
	 * {@link PlatypusJob} for {@link #jobCounter()}
	 */
	@Inject
	protected PlatypusJob platypusJob;
	
	/**
	 * 
	 * {@link StatusController} for {@link #index()}
	 */
	@Inject
	protected StatusController statusController;

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
	@Blocking
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Hello", description = "This method say: **" + HELLO + "**<br/><br/>"
			+ "See [IndexController.hello](/javadoc/local/intranet/quarkus/api/controller/IndexController.html#hello())")
	public Message hello() {
		incrementCounter();
		LOG.trace("msg:{}", HELLO);
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
	@Blocking
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Ahoj", description = "This method say: **" + AHOJ + "**<br/><br/>"
			+ "See [IndexController.ahoj](/javadoc/local/intranet/quarkus/api/controller/IndexController.html#ahoj())")
	public Message ahoj() {
		incrementCounter();
		LOG.debug("msg:{}", AHOJ);
		return new Message(AHOJ);
	}

	/**
	 * 
	 * Index from Quarkus as HTML
	 * 
	 *  @return {@link TemplateInstance}
	 */
	@GET
	@Path("/")
	@Blocking
	@Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	public TemplateInstance index() {
		final TemplateInstance ret = IndexTemplate.index(statusController.getInfo());
		final Long cnt = incrementCounter();
		LOG.debug("cnt:{}", cnt);
		return ret;
	}

	/**
	 * 
	 * Properties from Quarkus as HTML
	 * 
	 *  @return {@link TemplateInstance}
	 */
	@GET
	@Path("/properties")
	@Produces(MediaType.TEXT_HTML)
	@Blocking
	@Operation(hidden = true)
	public TemplateInstance properties() {
		final TemplateInstance ret = PropertiesTemplate.properties(
				statusController.getInfo(),
				statusController.operatingSystem(),
				statusController.platypusProperties()); 
		final Long cnt = incrementCounter();
		LOG.debug("cnt:{}", cnt);
		return ret;
	}
	
	/**
	 * 
	 * Job Counter
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.scheduler.PlatypusJob#getCounter}.
	 * 
	 * @see <a href="/q/swagger-ui/#/index-controller/jobCounter" target=
	 *      "_blank">/q/swagger-ui/#/index-controller/jobCounter</a>
	 *      
	 * @return {@link String}
	 */
	@GET
	@Path("/jobCounter")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Get Job Counter", description = "**Get Job Counter**<br/><br/>"
			+ "This method is calling PlatypusJob.getCounter<br/><br/>"
			+ "See [IndexController.jobCounter](/javadoc/local/intranet/quarkus/api/controller/IndexController.html#jobCounter())")
    public String jobCounter() {
		final int ret = platypusJob.getCounter();
		incrementCounter();
		LOG.trace("cnt:{}", ret);
		return MessageFormat.format("count: {0}", ret);
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
	@GET
	@Path("/indexCounter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
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