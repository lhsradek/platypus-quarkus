package local.intranet.quarkus.api.controller;

import java.security.GeneralSecurityException;
import java.text.MessageFormat;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.exception.PlatypusException;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.info.Message;
import local.intranet.quarkus.api.info.UserInfo;
import local.intranet.quarkus.api.info.content.PlatypusCounter;
import local.intranet.quarkus.api.info.content.template.IndexTemplate;
import local.intranet.quarkus.api.info.content.template.LoginTemplate;
import local.intranet.quarkus.api.info.content.template.PropertiesTemplate;
import local.intranet.quarkus.api.scheduler.PlatypusJob;
import local.intranet.quarkus.api.service.CounterService;
import local.intranet.quarkus.api.service.UserService;
import local.intranet.quarkus.api.util.SecurityUtil;

/**
 * 
 * {@link IndexController}
 * 
 * index for quarkus
 * 
 * @author Radek Kádner
 *
 */
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
	 * {@link UserService} for {@link #signin(String, String)}
	 */
	@Inject
	protected UserService userService;

	/**
	 * 
	 * {@link SecurityContext} for {@link #signin(String, String)}
	 */
	@Inject
	protected SecurityContext securityContext;

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
	 * Index from Quarkus as HTML
	 * 
	 * @return {@link TemplateInstance}
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	public TemplateInstance index() {
		final TemplateInstance ret = IndexTemplate.index(statusController.getInfo());
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{}", cnt);
		return ret;
	}

	/**
	 * 
	 * Properties from Quarkus as HTML
	 * 
	 * @return {@link TemplateInstance}
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/properties")
	// @RolesAllowed({"userRole"})
	@Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	public TemplateInstance properties() {
		final TemplateInstance ret = PropertiesTemplate.properties(statusController.getInfo(),
				statusController.operatingSystem(), statusController.platypusProperties());
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{}", cnt);
		return ret;
	}

	/**
	 * 
	 * Login from Quarkus as HTML
	 * 
	 * @return {@link TemplateInstance}
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	public TemplateInstance login() {
		final TemplateInstance ret = LoginTemplate.login(statusController.getInfo());
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{}", cnt);
		return ret;
	}

	/**
	 * 
	 * Post login from Quarkus as HTML
	 * 
	 * @param username {@link String}
	 * @param password {@link String}
	 * @return {@link Response}
	 */
	@POST
	@Blocking
	@PermitAll
	@Path("/signin")
	@Operation(hidden = true)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signin(@FormParam("username") String username, @FormParam("password") String password) {
		final UserInfo user = userService.loadUserByUsername(username);
		try {
			LOG.debug("'{}' '{}'", user.getUsername(), SecurityUtil.verifyBCryptPassword(password, user.getPassword()));
		} catch (GeneralSecurityException e) {
		}
		// final Long cnt = incrementCounter();
		// LOG.trace("cnt:{}", cnt);
		return Response.seeOther(UriBuilder.fromUri("/").build()).build();
	}

	/**
	 * 
	 * Say: Ahoj ...
	 * 
	 * @see <a href="/q/swagger-ui/#/index-controller/get_ahoj">
	 *      /q/swagger-ui/#/index-controller/get_ahoj</a>
	 * 
	 * @return {@link Message}
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/ahoj")
	// @RolesAllowed({"adminRole"})
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Ahoj", description = "This method say: **" + AHOJ + "**<br/><br/>"
			+ "See [IndexController.ahoj](/javadoc/local/intranet/quarkus/api/controller/IndexController.html#ahoj())")
	public Message ahoj() {
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} msg:'{}'", cnt, AHOJ);
		return new Message(AHOJ);
	}

	/**
	 * 
	 * Say: Hello ...
	 * 
	 * @see <a href="/q/swagger-ui/#/index-controller/get_hello">
	 *      /q/swagger-ui/#/index-controller/get_hello</a>
	 * 
	 * @return {@link Message}
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/hello")
	// @RolesAllowed({"managerRole"})
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Hello", description = "This method say: **" + HELLO + "**<br/><br/>"
			+ "See [IndexController.hello](/javadoc/local/intranet/quarkus/api/controller/IndexController.html#hello())")
	public Message hello() {
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} msg:'{}'", cnt, HELLO);
		return new Message(HELLO);
	}

	/**
	 * 
	 * Job Counter
	 * <p>
	 * Used {@link local.intranet.quarkus.api.scheduler.PlatypusJob#jobCounter}.
	 * 
	 * @see <a href="/q/swagger-ui/#/index-controller/get_jobCounter">
	 *      /q/swagger-ui/#/index-controller/get_jobCounter</a>
	 * 
	 * @return {@link String}
	 * @throws PlatypusException {@link PlatypusException}
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/jobCounter")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Get Job Counter", description = "**Get Job Counter**<br/><br/>"
			+ "This method is calling PlatypusJob.getCounter<br/><br/>"
			+ "See [IndexController.jobCounter](/javadoc/local/intranet/quarkus/api/controller/IndexController.html#jobCounter())")
	public String jobCounter() throws PlatypusException {
		final Long ret = platypusJob.jobCounter();
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} ret:'{}'", cnt, ret);
		return MessageFormat.format("count: {0}", ret);
	}

	/**
	 * 
	 * Counter informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.CounterService#getCounterInfo}.
	 * 
	 * @see <a href="/q/swagger-ui/#/index-controller/get_indexCounter">
	 *      /q/swagger-ui/#/index-controller/get_indexCounter</a>
	 * 
	 * @return {@link CounterInfo}
	 * @throws PlatypusException {@link PlatypusException}
	 */
	@GET
	@PermitAll
	@Path("/indexCounter")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Counter Info", description = "**Get Counter Info**<br/><br/>"
			+ "This method is calling CounterService.getCounterInfo<br/><br/>"
			+ "See [IndexController.indexCounter](/javadoc/local/intranet/quarkus/api/controller/IndexController.html#indexCounter())")
	public CounterInfo indexCounter() throws PlatypusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.getCount(), formatDateTime(ret.getDate()),
				ret.getStatus());
		return ret;
	}

}