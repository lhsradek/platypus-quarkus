package local.intranet.quarkus.api.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.ZoneId;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Timed;
import io.quarkus.runtime.Application;
import io.smallrye.config.SmallRyeConfig;
import local.intranet.quarkus.PlatypusApplication;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.exception.PlatypusException;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.info.content.PlatypusCounter;
import local.intranet.quarkus.api.service.CounterService;

/**
 * 
 * {@link StatusController}
 * 
 * Status info as environment, system info, properties...
 * 
 * @author Radek Kádner
 * 
 */
@Timed
@Path("/app/v1/status")
@ApplicationScoped
@Tag(name = StatusController.TAG)
public class StatusController extends PlatypusCounter implements Countable, Invocationable, Statusable, Nameable {

	private static final Logger LOG = LoggerFactory.getLogger(StatusController.class);

	/**
	 *
	 * STATUS_OK = "OK"
	 */
	public static final String STATUS_OK = "OK";

	/**
	 * 
	 * STATUS_PROTECTED = "[PROTECTED]"
	 */
	public static final String STATUS_PROTECTED = "[PROTECTED]";

	/**
	 * 
	 * UNKNOWN = "unknown"
	 */
	public static final String UNKNOWN = "unknown";

	/**
	 * 
	 * TAG = "status-controller"
	 */
	protected static final String TAG = "status-controller";

	/**
	 * 
	 * {@link CounterService} for {@link #statusCounter()}
	 */
	@Inject
	protected CounterService counterService;

	/**
	 * 
	 * <code>platypus.remote.server</code> for application.properties
	 * 
	 */
	@ConfigProperty(name = "platypus.remote.server")
	public String remoteServer;

	/**
	 * 
	 * <code>quarkus.application.artifactId</code> for application.properties
	 * 
	 */
	@ConfigProperty(name = "platypus.application.artifactId")
	public String quarkusApplicationArtifactId;

	/**
	 * 
	 * <code>platypus.host.name</code> for application.properties
	 * 
	 */
	@ConfigProperty(name = "platypus.host.name")
	public String platypusHostName;

	/**
	 * 
	 * <code>quarkus.application.groupId</code> for application.properties
	 * 
	 */
	@ConfigProperty(name = "quarkus.application.name")
	public String quarkusApplicationName;

	/**
	 * 
	 * <code>platypus.deployment.environment</code> for application.properties
	 * 
	 */
	@ConfigProperty(name = "platypus.deployment.environment")
	public String platypusDeploymentEnvironment;

	/**
	 * 
	 * <code>platypus.api.key</code>from application.properties or <code>.env</code>
	 * 
	 */
	@ConfigProperty(name = "platypus.api.key")
	public String key;

	/**
	 *
	 * text/plain: "OK"
	 * 
	 * @see <a href=
	 *      "/q/swagger-ui/#/status-controller/get_app_v1_status_plainStatus">
	 *      /q/swagger-ui/#/status-controller/get_app_v1_status_plainStatus</a>
	 * 
	 * @return "OK" if Platypus-Quarkus API is running
	 */
	@GET
	@PermitAll
	@Path("/plainStatus")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Get Plain Status", description = "**Get OK if Platypus-Quarkus API is running**<br/><br/>"
			+ "See [StatusController.plainStatus](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#plainStatus())")
	public String plainStatus() {
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{}", cnt);
		return STATUS_OK;
	}

	private static final String STATUS_BRACKET = "_";
	private static final String EQUAL_WITH_COLONS = "=::";
	private static final String USER = "USER";
	private static final String PASSWORD = "PASSWORD";
	private static final String API_KEY = "API_KEY";

	/**
	 *
	 * Info of Properties
	 *
	 * @see <a href=
	 *      "/q/swagger-ui/#/status-controller/get_app_v1_status_platypusProperties">
	 *      /q/swagger-ui/#/status-controller/get_app_v1_status_platypusProperties</a>
	 * 
	 * @return {@link List}&lt;{@link Map.Entry}&lt;{@link String},{@link String}&gt;&gt;
	 */
	@GET
	@PermitAll
	@Path("/platypusProperties")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Properties", description = "**Get Properties</strong>**<br/><br/>"
			+ "See [StatusController.platypusProperties](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#platypusProperties())")
	public List<Map.Entry<String, String>> platypusProperties() {
		final List<Map.Entry<String, String>> ret = new ArrayList<>();
		final Map<String, String> map = new TreeMap<>();
		StreamSupport
				.stream(ConfigProvider.getConfig().unwrap(SmallRyeConfig.class).getPropertyNames().spliterator(), false)
				.forEach(k -> {
					if (!(k == null || ConfigProvider.getConfig().unwrap(SmallRyeConfig.class).getRawValue(k) == null))
						map.put(k, ConfigProvider.getConfig().unwrap(SmallRyeConfig.class).getRawValue(k));
				});
		map.put("quarkus.version", quarkusVersion()); // add quarkus.version
		map.forEach((k, v) -> {
			if (!(k == null || k.equals(STATUS_BRACKET) || k.equals(EQUAL_WITH_COLONS))) {
				boolean isProtected = false;
				if (!k.startsWith("user.")) {
					for (String s : Arrays.asList(API_KEY, PASSWORD, USER)) {
						if (k.toUpperCase().contains(s) || k.toUpperCase().contains(s.replace("_", "."))) {
							isProtected = true;
							break;
						}
					}
				}
				if (isProtected) {
					ret.add(new SimpleImmutableEntry<>(k, STATUS_PROTECTED));
				} else {
					ret.add(new SimpleImmutableEntry<>(k, map.get(k)));
				}
			}
		});
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} ret:'{}'", cnt, ret);
		return ret;
	}

	/**
	 *
	 * Get Operating System
	 *
	 * @see <a href=
	 *      "/q/swagger-ui/#/status-controller/get_app_v1_status_operatingSystem">
	 *      /q/swagger-ui/#/status-controller/get_app_v1_status_operatingSystem</a>
	 * 
	 * @return {@link List}&lt;{@link Map.Entry}&lt;{@link String},{@link Object}&gt;&gt;
	 */
	@GET
	@PermitAll
	@Path("/operatingSystem")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Operating System", description = "**Get Operating System and load average**<br/><br/>"
			+ "See [StatusController.operatingSystem](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#operatingSystem())")
	public List<Map.Entry<String, String>> operatingSystem() {
		final List<Map.Entry<String, String>> ret = new ArrayList<>();
		final OperatingSystemMXBean system = ManagementFactory.getOperatingSystemMXBean();
		ret.add(new SimpleImmutableEntry<>("name", system.getName()));
		final double d = system.getSystemLoadAverage();
		if (d > 0) {
			ret.add(new SimpleImmutableEntry<>("loadAverage",
					String.valueOf(system.getSystemLoadAverage())));
		}
		ret.add(new SimpleImmutableEntry<>("arch", system.getArch()));
		ret.add(new SimpleImmutableEntry<>("processors",
				String.valueOf(system.getAvailableProcessors())));
		ret.add(new SimpleImmutableEntry<>("version", system.getVersion()));
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} ret:'{}'", cnt, ret);
		return ret;
	}

	/**
	 * 
	 * Counter informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.CounterService#getCounterInfo}.
	 * 
	 * @see <a href=
	 *      "/q/swagger-ui/#/status-controller/get_app_v1_status_statusCounter">
	 *      /q/swagger-ui/#/status-controller/get_app_v1_status_statusCounter</a>
	 * 
	 * @return {@link CounterInfo}
	 * @throws PlatypusException {@link PlatypusException}
	 */
	@GET
	@PermitAll
	@Path("/statusCounter")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Counter Info", description = "**Get Counter Info**<br/><br/>"
			+ "This method is calling CounterService.getCounterInfo<br/><br/>"
			+ "See [StatusController.statusCounter](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#statusCounter())")
	public CounterInfo statusCounter() throws PlatypusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.getCount(), formatDateTime(ret.getDate()),
				ret.getStatus());
		return ret;
	}

	/**
	 * 
	 * get Info
	 * 
	 * @return {@link Map}&lt;{@link String}, {@link String}&gt;
	 */
	public Map<String, String> getInfo() {
		final Map<String, String> map = new ConcurrentHashMap<>();
		map.put("quarkus.application.groupId", PlatypusApplication.class.getPackage().getName());
		map.put("quarkus.application.artifactId", quarkusApplicationArtifactId);
		final ArrayList<String> arr = new ArrayList<>();
		for (String s : Arrays.asList(quarkusApplicationArtifactId.replace("-", " ").split(" "))) {
			arr.add(StringUtils.capitalize(s));
		}
		map.put("quarkus.application.header", String.join(" ", arr));
		map.put("quarkus.application.version",
				ConfigProvider.getConfig().getValue("quarkus.application.version", String.class));
		map.put("quarkus.version", quarkusVersion());
		map.put("quarkus.profile", ConfigProvider.getConfig().getValue("quarkus.profile", String.class));
		// map.put("quarkus.application.name",
		// ConfigProvider.getConfig().getValue("quarkus.application.name",
		// String.class));
		map.put("quarkus.application.name", Application.currentApplication().getName());
		map.put("quarkus.datasource.db-kind",
				ConfigProvider.getConfig().getValue("quarkus.datasource.db-kind", String.class));
		return map;
	}

	/**
	 *
	 * Get time zone
	 *
	 * @return {@link ZoneId#systemDefault()}.getId()}
	 */
	public String timeZone() {
		final String ret = ZoneId.systemDefault().getId();
		LOG.trace("{}", ret);
		return ret;
	}

	/**
	 *
	 * Quarkus Implementation version
	 *
	 * @return {@link Package#getImplementationVersion()}
	 * 
	 */
	public String quarkusVersion() {
		final String ret = Optional.ofNullable(Application.class.getPackage().getImplementationVersion())
				.orElse(UNKNOWN);
		LOG.trace("{}", ret);
		return ret;
	}

}