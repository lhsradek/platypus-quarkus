package local.intranet.quarkus.api.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import local.intranet.quarkus.api.info.content.PlatypusCounter;

/**
 * 
 * {@link StatusController}
 * 
 * @author Radek Kádner
 * 
 */
@ApplicationScoped
@Path("/app/v1/status")
@Tag(name = StatusController.TAG)
public class StatusController extends PlatypusCounter implements Countable, Invocationable, Statusable {

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
	 * TAG = "status-controller"
	 */
	protected static final String TAG = "status-controller";

	private static final String STATUS_BRACKET = "_";

	/**
	 *
	 * text/plain: "OK"
	 * 
	 * @see <a href="/q/swagger-ui/#/status-controller/plainStatus" target=
	 *      "_blank">/q/swagger-ui/#/status-controller/plainStatus</a>
	 * 
	 * @return "OK" if Platypus-Quarkus API is running
	 */
	@GET
	@Path(value = "/plainStatus")
	@Produces(MediaType.TEXT_PLAIN)
	@Timed(value = Measureable.PREFIX + "plainStatus", description = Measureable.TIMED_DESCRIPTION)
	@Counted(value = Measureable.PREFIX + "plainStatus", description = Measureable.COUNTED_DESCRIPTION)
	@Operation(operationId = "plainStatus", summary = "Get Plain Status", description = "<strong>Get OK if Platypus-Quarkus API is running</strong><br/><br/>"
			+ "See <a href=\"/javadoc/local/intranet/quarkus/api/controller/StatusController.html#plainStatus()\" "
			+ "target=\"_blank\">StatusController.plainStatus</a>")
	public String plainStatus() {
		incrementCounter();
		return STATUS_OK;
	}

	/**
	 *
	 * Info of Environment.
	 *
	 * @see <a href="/q/swagger-ui/#/status-controller/platypusEnvironment" target=
	 *      "_blank">/q/swagger-ui/#/status-controller/platypusEnvironment</a>
	 * 
	 * @return {@link List}&lt;{@link Map.Entry}&lt;{@link String},{@link String}&gt;&gt;
	 */
	@GET
	@Path(value = "/platypusEnvironment")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(operationId = "platypusEnvironment", summary = "Get Environment", description = "<strong>Get Environment</strong><br/><br/>"
			+ "See <a href=\"/javadoc/local/intranet/quarkus/api/controller/StatusController.html#"
			+ "platypusEnvironment()\" target=\"_blank\">StatusController.platypusEnvironment</a>")
	public List<Map.Entry<String, String>> platypusEnvironment() {
		final List<Map.Entry<String, String>> ret = Collections.synchronizedList(new ArrayList<>());
		final Map<String, String> map = System.getenv().entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		for (Map.Entry<String, String> e : map.entrySet()) {
			if (!e.getKey().equals(STATUS_BRACKET)) { // nelíbí
				if (e.getValue() != null && e.getValue().length() > 0) {
					ret.add(Map.entry(e.getKey(), e.getValue()));
				}
			}
		}
		incrementCounter();
		LOG.trace("{}", ret);
		return ret;
	}

	/**
	 *
	 * Get Operating System
	 *
	 * @see <a href="/q/swagger-ui/#/status-controller/getOperatingSystem" target=
	 *      "_blank">/q/swagger-ui/#/status-controller/getOperatingSystem</a>
	 * 
	 * @return {@link List}&lt;{@link Map.Entry}&lt;{@link String},{@link Object}&gt;&gt;
	 */
	@GET
	@Path(value = "/getOperatingSystem")
	@Produces(MediaType.TEXT_PLAIN)
	@Timed(value = Measureable.PREFIX + "getOperatingSystem", description = Measureable.TIMED_DESCRIPTION)
	@Counted(value = Measureable.PREFIX + "getOperatingSystem", description = Measureable.COUNTED_DESCRIPTION)
	@Operation(operationId = "getOperatingSystem", summary = "Get Operating System", description = "<strong>Get Operating System and load average</strong><br/><br/>"
			+ "See <a href=\"/javadoc/local/intranet/quarkus/api/controller/StatusController.html#getOperatingSystem()\" "
			+ "target=\"_blank\">StatusController.getOperatingSystem</a>")
	public synchronized List<Map.Entry<String, Object>> getOperatingSystem() {
		final List<Map.Entry<String, Object>> ret = new ArrayList<>();
		final OperatingSystemMXBean system = ManagementFactory.getOperatingSystemMXBean();
		ret.add(Map.entry("name", system.getName()));
		ret.add(Map.entry("loadAverage", system.getSystemLoadAverage()));
		ret.add(Map.entry("arch", system.getArch()));
		ret.add(Map.entry("processors", system.getAvailableProcessors()));
		ret.add(Map.entry("version", system.getVersion()));
		incrementCounter();
		LOG.trace("{}", ret);
		return ret;
	}

	/**
	 *
	 * Get time zone
	 *
	 * @return {@link ZoneId#systemDefault()}.getId()}
	 */
	public String timeZone() {
		final String ret = ZoneId.systemDefault().getId();
		incrementCounter();
		LOG.trace("{}", ret);
		return ret;
	}

}