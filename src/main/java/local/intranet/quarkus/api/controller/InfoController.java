package local.intranet.quarkus.api.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.intranet.quarkus.api.info.LevelCount;
import local.intranet.quarkus.api.info.RoleInfo;
import local.intranet.quarkus.api.info.UserInfo;
import local.intranet.quarkus.api.service.LoggingEventService;
import local.intranet.quarkus.api.service.RoleService;
import local.intranet.quarkus.api.service.UserService;

/**
 * 
 * {@link InfoController}
 * It's for charge of working with buffers and for REST methods
 * <p>
 * info from services in {@link local.intranet.quarkus.api.service}
 * 
 * @author Radek Kádner
 *
 */
@Path("/app/v1/info")
@Tag(name = "info-controller")
public class InfoController {

	private static final Logger LOG = LoggerFactory.getLogger(InfoController.class);

	@Inject
	public LoggingEventService loggingEventService;

	@Inject
	public RoleService roleService;
	
	@Inject
	public UserService userService;
	
	/**
	 * 
	 * LoggingEvent informations
	 * <p>
	 * Used {@link local.intranet.quarkus.api.service.LoggingEventService#countTotalLoggingEvents}.
	 * <p>
	 * 
	 * @return {@link List}&le{@link LevelCount}&ge;
	 */
	@GET
	@Path("/loggingEvent")
    @Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Logging Info", description = "Entry point: <strong>\"/app/v1/info/loggingEvent\"</strong><br/><br/>Get LoggingEvent Info<br/><br/>"
			+ "This method is calling LoggingEventService.countTotalLoggingEvents")
	public List<LevelCount> loggingEventInfo() {
		List<LevelCount> ret = loggingEventService.countTotalLoggingEvents();
		LOG.debug("{}", ret);
		return ret;
	}

	/**
	 * 
	 * Role informations
	 * <p>
	 * Used {@link local.intranet.quarkus.api.service.RoleService#getRoleInfo}.
	 * <p>
	 * 
	 * @return {@link RoleInfo}
	 */
	@GET
	@Path("/role")
    @Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Role Info", description = "Entry point: <strong>\"/app/v1/info/role\"</strong><br/><br/>Get Role Info<br/><br/>"
			+ "This method is calling RoleService.getRoleInfo")
	public RoleInfo getRoleInfo() {
		RoleInfo ret = roleService.getRoleInfo();
		LOG.debug("{}", ret);
		return ret;
	}

	/**
	 * 
	 * User informations
	 * <p>
	 * Used {@link local.intranet.quarkus.api.service.UserService#getUserInfo}.
	 * <p>
	 * 
	 * @param userName {@link String}
	 * 
	 * @return {@link UserInfo}
	 */
	@GET
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get User Info", description = "Entry point: <strong>\"/app/v1/info/user\"</strong><br/><br/>Get User Info<br/><br/>"
			+ "This method is calling UserService.getUserInfo")
	public UserInfo getUserInfo(@Parameter(allowEmptyValue = false, example = "lhs",
			description = "userName") String userName) throws InternalError {
		UserInfo ret = userService.getUserInfo(userName);
		LOG.debug("{}", ret);
		return ret;
	}
	
}