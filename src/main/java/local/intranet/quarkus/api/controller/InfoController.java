package local.intranet.quarkus.api.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Timed;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.exception.PlatypusException;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.info.LevelCount;
import local.intranet.quarkus.api.info.RoleInfo;
import local.intranet.quarkus.api.info.UserInfo;
import local.intranet.quarkus.api.info.content.PlatypusCounter;
import local.intranet.quarkus.api.scheduler.PlatypusJob;
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
@Timed
@Path("/app/v1/info")
@ApplicationScoped
@Tag(name = InfoController.TAG)
public class InfoController extends PlatypusCounter implements Countable, Invocationable, Statusable, Nameable {

	private static final Logger LOG = LoggerFactory.getLogger(InfoController.class);

	/**
	 * 
	 * String TAG = "info-controller"
	 */
	protected static final String TAG = "info-controller";

	/**
	 * 
	 * {@link LoggingEventService} for {@link #loggingEventInfo()}
	 */
	@Inject
	protected LoggingEventService loggingEventService;

	/**
	 * 
	 * {@link RoleService} for {@link #roleInfo()}
	 */
	@Inject
	protected RoleService roleService;

	/**
	 * 
	 * {@link UserService} for {@link #userInfo()}
	 */
	@Inject
	protected UserService userService;

	/**
	 * 
	 * {@link CounterService} for {@link #infoCounter()}
	 */
	@Inject
	protected CounterService counterService;

	/**
	 * 
	 * {@link PlatypusJob} for {@link #startJob()}
	 */
	@Inject
	protected PlatypusJob platypusJob;

	/**
	 * 
	 * LoggingEvent informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.LoggingEventService#countTotalLoggingEvents}.
	 * 
	 * @see <a href=
	 *      "/q/swagger-ui/#/info-controller/get_app_v1_info_loggingEventInfo">
	 *      /q/swagger-ui/#/info-controller/get_app_v1_info_loggingEventInfo</a>
	 * 
	 * @return {@link List}&le;{@link LevelCount}&ge;
	 */
	@GET
	@PermitAll
	@Path("/loggingEvent")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Count Total LoggingEvents", description = "**Count Total Logging Events**<br/><br/>"
			+ "This method is calling LoggingEventService.countTotalLoggingEvents<br/><br/>"
			+ "See [InfoController.loggingEventInfo](/javadoc/local/intranet/quarkus/api/controller/InfoController.html#loggingEventInfo())")
	public List<LevelCount> loggingEventInfo() {
		final List<LevelCount> ret = loggingEventService.countTotalLoggingEvents();
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{}", cnt);
		LOG.debug("serviceName:{} size:{}", LoggingEventService.class.getSimpleName(), ret.size());
		return ret;
	}

	/**
	 * 
	 * Role informations
	 * <p>
	 * Used {@link local.intranet.quarkus.api.service.RoleService#getRoleInfo}.
	 * 
	 * @see <a href="/q/swagger-ui/#/info-controller/get_app_v1_info_roleInfo">
	 *      /q/swagger-ui/#/info-controller/get_app_v1_info_roleInfo</a>
	 * 
	 * @return {@link RoleInfo}
	 */
	@GET
	@PermitAll
	@Path("/role")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Role Info", description = "**Get Role Info**<br/><br/>"
			+ "This method is calling RoleService.getRoleInfo<br/<br>"
			+ "See [InfoController.roleInfo](/javadoc/local/intranet/quarkus/api/controller/InfoController.html#roleInfo())")
	public RoleInfo roleInfo() {
		final RoleInfo ret = roleService.getRoleInfo();
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{}", cnt);
		LOG.debug("roleName:{} size:{}", ret.getName(), ret.getRoles().size());
		return ret;
	}

	/**
	 * 
	 * User informations
	 * <p>
	 * Used {@link local.intranet.quarkus.api.service.UserService#getUserInfo}.
	 * 
	 * @see <a href="/q/swagger-ui/#/info-controller/get_app_v1_info_userInfo">
	 *      /q/swagger-ui/#/info-controller/get_app_v1_info_userInfo</a>
	 * 
	 * @return {@link UserInfo}
	 */
	@GET
	@PermitAll
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get User Info", description = "**Get User Info**<br/><br/>"
			+ "This method is calling UserService.getUserInfo<br/><br/>"
			+ "See [InfoController.userInfo](/javadoc/local/intranet/quarkus/api/controller/InfoController.html#userInfo())")
	public UserInfo userInfo() {
		final UserInfo ret = userService.getUserInfo("user");
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{}", cnt);
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
	 * @see <a href="/q/swagger-ui/#/info-controller/get_app_v1_info_infoCounter">
	 *      /q/swagger-ui/#/info-controller/get_app_v1_info_infoCounter</a>
	 * 
	 * @return {@link CounterInfo}
	 * @throws PlatypusException {@link PlatypusException}
	 */
	@GET
	@PermitAll
	@Path("/infoCounter")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Counter Info", description = "**Get Counter Info**<br/><br/>"
			+ "This method is calling CounterService.getCounterInfo<br/><br/>"
			+ "See [InfoController.infoCounter](/javadoc/local/intranet/quarkus/api/controller/InfoController.html#infoCounter())")
	public CounterInfo infoCounter() throws PlatypusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.getCount(), formatDateTime(ret.getDate()),
				ret.getStatus());
		return ret;
	}

	/**
	 * 
	 * Start Job
	 * <p>
	 * Used {@link local.intranet.quarkus.api.scheduler.PlatypusJob#startJob}.
	 * 
	 * @return boolean
	 */
	@GET
	@PermitAll
	@Path("startJob")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(hidden = true)
	/*
	 * @Operation(summary = "Start Job", description = "**Start Job**<br/><br/>" +
	 * "This method is calling PlatypusJob.startJob<br/><br/>" +
	 * "See [PlatypusJob.startJob](/javadoc/local/intranet/quarkus/api/scheduler/PlatypusJob.html#startJob())"
	 * )
	 */
	public boolean startJob() {
		final String counterName = getName();
		final boolean ret = platypusJob.startJob();
		LOG.debug("name:'{}' ret:'{}'", counterName, ret);
		return ret;
	}

}
