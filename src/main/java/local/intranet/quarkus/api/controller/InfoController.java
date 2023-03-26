package local.intranet.quarkus.api.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.exception.PlatypusQuarkusException;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.info.LevelCount;
import local.intranet.quarkus.api.info.RoleInfo;
import local.intranet.quarkus.api.info.UserInfo;
import local.intranet.quarkus.api.info.content.PlatypusCounter;
import local.intranet.quarkus.api.service.CounterService;
import local.intranet.quarkus.api.service.LoggingEventService;
import local.intranet.quarkus.api.service.RoleService;
import local.intranet.quarkus.api.service.UserService;

/**
 * 
 * {@link InfoController}
 * 
 * Info from services in {@link local.intranet.quarkus.api.service}
 * 
 * @author Radek Kádner
 *
 */
// @Path("/app/v1/info")
@RestController
@RequestMapping(value = "/app/v1/info")
@Tag(name = InfoController.TAG)
public class InfoController extends PlatypusCounter implements Countable, Invocationable, Statusable, Nameable {

	private static final Logger LOG = LoggerFactory.getLogger(InfoController.class);

	/**
	 * 
	 * String TAG = "info-controller"
	 */
	protected static final String TAG = "info-controller";

	@Autowired
	protected LoggingEventService loggingEventService;

	@Autowired
	protected RoleService roleService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected CounterService counterService;

	/**
	 * 
	 * LoggingEvent informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.LoggingEventService#countTotalLoggingEvents}.
	 * 
	 * @see <a href="/q/swagger-ui/#/info-controller/loggingEventInfo" target=
	 *      "_blank">/q/swagger-ui/#/info-controller/loggingEventInfo</a>
	 * 
	 * @return {@link List}&le;{@link LevelCount}&ge;
	 */
	// @GET
	// @Path("/loggingEvent")
	// @Produces(MediaType.APPLICATION_JSON)
	@GetMapping(value = "/loggingEvent", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Count Total LoggingEvents", description = "**Count Total Logging Events**<br/><br/>"
			+ "This method is calling LoggingEventService.countTotalLoggingEvents<br/><br/>"
			+ "See [InfoController.loggingEventInfo](/javadoc/local/intranet/quarkus/api/controller/InfoController.html#loggingEventInfo())")
	public List<LevelCount> loggingEventInfo() {
		final List<LevelCount> ret = loggingEventService.countTotalLoggingEvents();
		incrementCounter();
		LOG.debug("serviceName:{} size:{}", LoggingEventService.class.getSimpleName(), ret.size());
		return ret;
	}

	/**
	 * 
	 * Role informations
	 * <p>
	 * Used {@link local.intranet.quarkus.api.service.RoleService#getRoleInfo}.
	 * 
	 * @see <a href="/q/swagger-ui/#/info-controller/roleInfo" target=
	 *      "_blank">/q/swagger-ui/#/info-controller/roleInfo</a>
	 * 
	 * @return {@link RoleInfo}
	 */
	// @GET
	// @Path("/role")
	// @Produces(MediaType.APPLICATION_JSON)
	@GetMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get Role Info", description = "**Get Role Info**<br/><br/>"
			+ "This method is calling RoleService.getRoleInfo<br/<br>"
			+ "See [InfoController.roleInfo](/javadoc/local/intranet/quarkus/api/controller/InfoController.html#roleInfo())")
	public RoleInfo roleInfo() {
		final RoleInfo ret = roleService.getRoleInfo();
		incrementCounter();
		LOG.debug("roleName:{} size:{}", ret.getName(), ret.getRoles().size());
		return ret;
	}

	/**
	 * 
	 * User informations
	 * <p>
	 * Used {@link local.intranet.quarkus.api.service.UserService#getUserInfo}.
	 * 
	 * @see <a href="/q/swagger-ui/#/info-controller/userInfo" target=
	 *      "_blank">/q/swagger-ui/#/info-controller/userInfo</a>
	 * 
	 * @return {@link UserInfo}
	 */
	// @GET
	// @Path("/user")
	// @Produces(MediaType.APPLICATION_JSON)
	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get User Info", description = "**Get User Info**<br/><br/>"
			+ "This method is calling UserService.getUserInfo<br/><br/>"
			+ "See [InfoController.userInfo](/javadoc/local/intranet/quarkus/api/controller/InfoController.html#userInfo())")
	public UserInfo userInfo() {
		final UserInfo ret = userService.getUserInfo("admin");
		incrementCounter();
		LOG.debug("userName:{} nonExpired:{} nonLocked:{} enabled:{} ", ret.getUsername(), ret.isAccountNonExpired(),
				ret.isAccountNonLocked(), ret.isEnabled());
		return ret;
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
	@GetMapping(value = "/infoCounter", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get Counter Info", description = "**Get Counter Info**<br/><br/>"
			+ "This method is calling CounterService.getCounterInfo<br/><br/>"
			+ "See [InfoController.infoCounter](/javadoc/local/intranet/quarkus/api/controller/InfoController.html#infoCounter())")
	public CounterInfo infoCounter() throws PlatypusQuarkusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.getCount(),
				formatDateTime(ret.getDate()), ret.getStatus());
		return ret;
	}

}
