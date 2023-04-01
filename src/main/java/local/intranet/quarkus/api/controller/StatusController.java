package local.intranet.quarkus.api.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.ZoneId;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.exception.PlatypusQuarkusException;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.info.content.PlatypusCounter;
import local.intranet.quarkus.api.service.CounterService;

/**
 * 
 * {@link StatusController}
 * 
 * Status info as environment, system info...
 * 
 * @author Radek Kádner
 * 
 */
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
	 * TAG = "status-controller"
	 */
	protected static final String TAG = "status-controller";

	/**
	 * 
	 * {@link CounterService} for {@link #statusCounter()}
	 */
	@Inject
	protected CounterService counterService;

	private static final String STATUS_BRACKET = "_";
	private static final String EQUAL_WITH_COLONS = "=::";

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
	@Operation(summary = "Get Plain Status", description = "**Get OK if Platypus-Quarkus API is running**<br/><br/>"
			+ "See [StatusController.plainStatus](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#plainStatus())")
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
	@Operation(summary = "Get Environment", description = "**Get Environment</strong>**<br/><br/>"
			+ "See [StatusController.platypusEnvironment](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#platypusEnvironment())")
	public List<Map.Entry<String, String>> platypusEnvironment() {
		final List<Map.Entry<String, String>> ret = Collections.synchronizedList(new ArrayList<>());
		final Map<String, String> map = System.getenv().entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		for (Map.Entry<String, String> e : map.entrySet()) {
			if (!(e.getKey().equals(STATUS_BRACKET) || e.getKey().equals(EQUAL_WITH_COLONS))) { // nelíbí
				if (e.getValue() != null && e.getValue().length() > 0) {
					ret.add(new SimpleEntry<String, String>(e.getKey(), e.getValue()));
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
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Operating System", description = "**Get Operating System and load average**<br/><br/>"
			+ "See [StatusController.getOperatingSystem](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#getOperatingSystem())")
	public List<Map.Entry<String, String>> getOperatingSystem() {
		final List<Map.Entry<String, String>> ret = new ArrayList<>();
		final OperatingSystemMXBean system = ManagementFactory.getOperatingSystemMXBean();
		ret.add(new SimpleEntry<String, String>("name", system.getName()));
		ret.add(new SimpleEntry<String, String>("loadAverage", String.valueOf(system.getSystemLoadAverage())));
		ret.add(new SimpleEntry<String, String>("arch", system.getArch()));
		ret.add(new SimpleEntry<String, String>("processors", String.valueOf(system.getAvailableProcessors())));
		ret.add(new SimpleEntry<String, String>("version", system.getVersion()));
		incrementCounter();
		LOG.debug("{}", ret);
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
	@GET
	@Path("counter")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Counter Info", description = "**Get Counter Info**<br/><br/>"
			+ "This method is calling CounterService.getCounterInfo<br/><br/>"
			+ "See [StatusController.statusCounter](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#statusCounter())")
	public CounterInfo statusCounter() throws PlatypusQuarkusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		// incrementCounter();
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.getCount(), formatDateTime(ret.getDate()),
				ret.getStatus());
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
		LOG.trace("{}", ret);
		return ret;
	}

}