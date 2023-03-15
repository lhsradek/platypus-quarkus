package local.intranet.quarkus.api.controller;

import java.util.List;

import javax.inject.Inject;
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
@Path("/app/v1/info")
@Tag(name = "info-controller")
public class InfoController extends PlatypusCounter implements Countable, Invocationable, Statusable {

	private static final Logger LOG = LoggerFactory.getLogger(InfoController.class);

	@Inject
	protected CounterService counterService;

	@Inject
	protected LoggingEventService loggingEventService;

	@Inject
	protected RoleService roleService;

	@Inject
	protected UserService userService;

	/**
	 * 
	 * Counter informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.CounterService#getCounterInfo}.
	 * 
	 * @return {@link CounterInfo}
	 * @throws IllegalArgumentException {@link IllegalArgumentException}
	 */
	@GET
	@Path("counter")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(value = Measureable.PREFIX + "counterInfo", description = Measureable.TIMED_DESCRIPTION)
	@Counted(value = Measureable.PREFIX + "counterInfo", description = Measureable.COUNTED_DESCRIPTION)
	@Operation(summary = "Get Counter Info", description = "<strong>Get Counter Info</strong><br/><br/>"
			+ "This method is calling CounterService.getCounterInfo")
	public CounterInfo counterInfo() throws IllegalArgumentException {
		final CounterInfo ret = counterService.getCounterInfo();
		incrementCounter();
		LOG.debug("name:{} cnt:{}, date:{}: status:{}", ret.getName(), ret.countValue(), ret.lastInvocation(), ret.getStatus());
		return ret;
	}

	/**
	 * 
	 * LoggingEvent informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.LoggingEventService#countTotalLoggingEvents}.
	 * 
	 * @return {@link List}&le;{@link LevelCount}&ge;
	 */
	@GET
	@Path("loggingEvent")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(value = Measureable.PREFIX + "countTotalLoggingEvents", description = Measureable.TIMED_DESCRIPTION)
	@Counted(value = Measureable.PREFIX + "countTotalLoggingEvents", description = Measureable.COUNTED_DESCRIPTION)
	@Operation(summary = "Count Total LoggingEvents", description = "<strong>Count Total Logging Events</strong><br/><br/>"
			+ "This method is calling LoggingEventService.countTotalLoggingEvents")
	public List<LevelCount> loggingEventInfo() {
		final List<LevelCount> ret = loggingEventService.countTotalLoggingEvents();
		incrementCounter();
		LOG.debug("size:{}", ret.size());
		return ret;
	}

	/**
	 * 
	 * Role informations
	 * <p>
	 * Used {@link local.intranet.quarkus.api.service.RoleService#getRoleInfo}.
	 * 
	 * @return {@link RoleInfo}
	 */
	@GET
	@Path("role")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(value = Measureable.PREFIX + "getRoleInfo", description = Measureable.TIMED_DESCRIPTION)
	@Counted(value = Measureable.PREFIX + "getRoleInfo", description = Measureable.COUNTED_DESCRIPTION)
	@Operation(summary = "Get Role Info", description = "<strong>Get Role Info</strong><br/><br/>"
			+ "This method is calling RoleService.getRoleInfo")
	public RoleInfo getRoleInfo() {
		final RoleInfo ret = roleService.getRoleInfo();
		incrementCounter();
		LOG.debug("name:{} cnt:{}, roles:{}", ret.getName(), ret.getRoles().size());
		return ret;
	}

	/**
	 * 
	 * User informations
	 * <p>
	 * Used {@link local.intranet.quarkus.api.service.UserService#getUserInfo}.
	 * 
	 * @return {@link UserInfo}
	 */
	@GET
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(value = Measureable.PREFIX + "getUserInfo", description = Measureable.TIMED_DESCRIPTION)
	@Counted(value = Measureable.PREFIX + "getUserInfo", description = Measureable.COUNTED_DESCRIPTION)
	@Operation(summary = "Get User Info", description = "<strong>Get User Info</strong><br/><br/>"
			+ "This method is calling UserService.getUserInfo")
	public UserInfo getUserInfo() {
		final UserInfo ret = userService.getUserInfo("admin");
		incrementCounter();
		LOG.debug("userName:{} password:{} accountNonExpired:{} enabled:{} ", ret.getUsername(), ret.getPassword(), ret.isAccountNonExpired(), ret.isEnabled());
		return ret;
	}

}
