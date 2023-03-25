package local.intranet.quarkus.api.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.MessageFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.info.content.PlatypusCounter;

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

	@Context
	protected ServletContext servletContext;

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
	@Operation(operationId = "plainStatus", summary = "Get Plain Status", description = "**Get OK if Platypus-Quarkus API is running**<br/><br/>"
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
	@Operation(operationId = "platypusEnvironment", summary = "Get Environment", description = "**Get Environment</strong>**<br/><br/>"
			+ "See [StatusController.platypusEnvironment](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#platypusEnvironment())")
	public List<Map.Entry<String, String>> platypusEnvironment() {
		final List<Map.Entry<String, String>> ret = Collections.synchronizedList(new ArrayList<>());
		final Map<String, String> map = System.getenv().entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		for (Map.Entry<String, String> e : map.entrySet()) {
			if (!(e.getKey().equals(STATUS_BRACKET) || e.getKey().equals(EQUAL_WITH_COLONS))) { // nelíbí
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
	 * Info of Servlet context
	 *
	 * @see <a href= "/q/swagger-ui/#/status-controller/platypusServletContext"
	 *      target=
	 *      "_blank">/q/swagger-ui/#/status-controller/platypusServletContext</a>
	 *
	 * @return {@link List}&lt;{@link Map.Entry}&lt;{@link String},{@link String}&gt;&gt;
	 */
	@GET
	@Path(value = "/servletContext")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(operationId = "platypusServletContext", summary = "Get ServletContext", description = "**Get ServletContext**<br/><br/>"
			+ "See [StatusController.platypusServletContext](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#platypusServletContext())")
	public List<Map.Entry<String, String>> platypusServletContext() {
		final List<Map.Entry<String, String>> ret = new ArrayList<>();
		final Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		for (Enumeration<String> en = servletContext.getAttributeNames(); en.hasMoreElements();) {
			final String key = en.nextElement();
			final String value = servletContext.getAttribute(key).toString();
			map.put(key, value);
		}
		map.put("serverInfo", servletContext.getServerInfo());
		map.put("virtualServerName", servletContext.getVirtualServerName());
		map.put("servletContextName", servletContext.getServletContextName());
		map.put("version",
				MessageFormat.format("{0}.{1}", servletContext.getMajorVersion(), servletContext.getMinorVersion()));
		for (Map.Entry<String, String> e : map.entrySet()) {
			if (e.getValue() != null && e.getValue().length() > 0) {
				ret.add(Map.entry(e.getKey(), e.getValue()));
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
	@Operation(operationId = "getOperatingSystem", summary = "Get Operating System", description = "**Get Operating System and load average**<br/><br/>"
			+ "See [StatusController.getOperatingSystem](/javadoc/local/intranet/quarkus/api/controller/StatusController.html#getOperatingSystem())")
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
		LOG.trace("{}", ret);
		return ret;
	}

	/**
	 *
	 * Get virtualServerName from ServletContext.getVirtualServerName()
	 *
	 * @return getlServerName()
	 */
	public String getServerName() {
		final String ret = servletContext.getVirtualServerName();
		LOG.trace("{}", ret);
		return ret;
	}

	/**
	 *
	 * Get server info from ServletContext.getServerInfo()
	 *
	 * @return servletContext.getServerInfo()
	 */
	public String getServerInfo() {
		final String ret = servletContext.getServerInfo();
		LOG.trace("{}", ret);
		return ret;
	}

}