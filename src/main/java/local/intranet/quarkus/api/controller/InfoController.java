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
import local.intranet.quarkus.api.info.LevelCount;
import local.intranet.quarkus.api.info.RoleInfo;
import local.intranet.quarkus.api.info.UserInfo;
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
public class InfoController {

	private static final Logger LOG = LoggerFactory.getLogger(InfoController.class);

	@Inject
	protected LoggingEventService loggingEventService;

	@Inject
	protected RoleService roleService;

	@Inject
	protected UserService userService;

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
	@Timed
	@Counted
	@Path("loggingEvent")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Count Total LoggingEvents", description = "<strong>Get LoggingEvent Info</strong><br/><br/>"
			+ "This method is calling LoggingEventService.countTotalLoggingEvents")

	public List<LevelCount> loggingEventInfo() {
		final List<LevelCount> ret = loggingEventService.countTotalLoggingEvents();
		LOG.trace("{}", ret);
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
	@Timed
	@Counted
	@Path("role")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Role Info", description = "<strong>Get Role Info</strong><br/><br/>"
			+ "This method is calling RoleService.getRoleInfo")
	public RoleInfo getRoleInfo() {
		final RoleInfo ret = roleService.getRoleInfo();
		LOG.trace("{}", ret);
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
	@Timed
	@Counted
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get User Info", description = "<strong>Get User Info</strong><br/><br/>"
			+ "This method is calling UserService.getUserInfo")
	public UserInfo getUserInfo() {
		final UserInfo ret = userService.getUserInfo("admin");
		LOG.trace("{}", ret);
		return ret;
	}

}
