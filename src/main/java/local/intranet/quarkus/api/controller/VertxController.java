package local.intranet.quarkus.api.controller;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.constraint.NotNull;
import io.smallrye.common.constraint.Nullable;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
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
 * 
 * {@link VertxController}
 * 
 * Vert.x for quarkus
 * <p>
 * https://quarkus.io/guides/vertx https://quarkus.io/guides/vertx-reference
 * https://how-to.vertx.io/web-and-openapi-howto/
 *
 */
@Path("/vertx")
@ApplicationScoped
@Tag(name = VertxController.TAG)
public class VertxController extends PlatypusCounter implements Countable, Invocationable, Statusable, Nameable {

	private static final Logger LOG = LoggerFactory.getLogger(VertxController.class);

	/**
	 * 
	 * String TAG = "vertx-controller"
	 */
	protected static final String TAG = "vertx-controller";

	private final Vertx vertx;

	private final WebClient client;

	/**
	 *
	 * <code>dlt.jolokia.username</code> from application.properties
	 */
	@ConfigProperty(name = "platypus.jolokia.username")
	protected String jolokiaUsername;

	/**
	 *
	 * <code>dlt.jolokia.username</code> from application.properties
	 */
	@ConfigProperty(name = "platypus.jolokia.password")
	protected String jolokiaPassword;

	/**
	 * 
	 * {@link EventBus}
	 */
	@Inject
	public EventBus bus;

	/**
	 * 
	 * {@link CounterService} for {@link #vertxCounter()}
	 */
	@Inject
	protected CounterService counterService;

	/**
	 * 
	 * @param vertx {@link VertxController}
	 */
	@Inject
	public VertxController(Vertx vertx) {
		this.vertx = vertx;
		this.client = WebClient.create(vertx);
	}

	/**
	 * 
	 * Say ahoj ${name}
	 * 
	 * @param name {@link String}
	 * @return {@link Uni}&lt;{@link String}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/ahoj")
	@Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Uni<String> ahoj(@Nullable @QueryParam("name") String name) {
		final Uni<String> ret = bus.<String>request("ahoj", name).onItem().transform(response -> response.body());
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} name:'{}'", cnt, name);
		return ret;
	}

	/**
	 * 
	 * Say hello ${name}
	 * 
	 * @param name {@link String}
	 * @return {@link Uni}&lt;{@link String}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/hello")
	@Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Uni<String> hello(@Nullable @QueryParam("name") String name) {
		final Uni<String> ret = bus.<String>request("hello", name).onItem().transform(response -> response.body());
		incrementCounter();
		LOG.trace("{}", name);
		return ret;
	}

	/**
	 *
	 * Jolokia Quarkus
	 *
	 * @param id {@link Integer}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 * @throws IllegalArgumentException {@link IllegalArgumentException}
	 */
	@GET
	@Path("/jolokia/{id}")
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @RequestBody(required = false, content = @Content(schema = @Schema(implementation = String.class, required = false)))
	@Operation(summary = "Jolokia Quarkus", description = "**Jolokia Quarkus**<br/><br/>")
	// @Operation(hidden = true)
	public Uni<Object> quarkusDataFromJolokia(@NotNull @PathParam("id") Integer id) {
		if (Arrays.asList(7005, 7006, 7007, 7008, // accord
				7009, 7010 // bttf += dev
		).contains(id)) {
			final String url;
			final String qs = "";
			// if (q == null) {
			// qs = "";
			// } else {
			// qs = q;
			// }
			if (Arrays.asList(7009, 7010).contains(id)) {
				if (id.equals(7009)) {
					url = "https://www.tomcat.local/jolokia/" + qs;
				} else {
					url = "https://docker.tomcat.local/jolokia/" + qs;
				}
			} else {
				url = "http://127.0.0.1:" + id + "/jolokia/" + qs;
			}
			return client.getAbs(url).basicAuthentication(jolokiaUsername, jolokiaPassword).send().onItem()
					.transform(HttpResponse::bodyAsString);
		} else {
			throw new IllegalArgumentException("Unknown id!");
		}
	}

	/**
	 * 
	 * Wiki Platypus
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_platypus">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_platypus</a>
	 * 
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/platypus")
	@Operation(summary = "Wiki Platypus", description = "**Wiki Platypus**<br/><br/>"
			+ "See [VertxController.platypusRetrieveDataFromWikipedia](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#platypusRetrieveDataFromWikipedia())")
	// @Operation(hidden = true)
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<JsonArray> platypusRetrieveDataFromWikipedia() {
		final String url = "https://en.wikipedia.org/w/api.php?action=parse&page=Platypus&format=json&prop=langlinks";
		final Uni<JsonArray> ret = client.getAbs(url).send().onItem().transform(HttpResponse::bodyAsJsonObject).onItem()
				.transform(json -> json.getJsonObject("parse").getJsonArray("langlinks"));
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
		return ret;
	}

	/**
	 * 
	 * Wiki Quarkus
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_quarkus">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_quarkus</a>
	 * 
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/quarkus")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Wiki Quarkus", description = "**Wiki Quarkus**<br/><br/>"
			+ "See [VertxController.quarkusDataFromWikipedia](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusDataFromWikipedia())")
	// @Operation(hidden = true)
	public Uni<JsonArray> quarkusDataFromWikipedia() {
		final String url = "https://en.wikipedia.org/w/api.php?action=parse&page=Quarkus&format=json&prop=langlinks";
		final Uni<JsonArray> ret = client.getAbs(url).send().onItem().transform(HttpResponse::bodyAsJsonObject).onItem()
				.transform(json -> json.getJsonObject("parse").getJsonArray("langlinks"));
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
		return ret;
	}

	/**
	 *
	 * Now it returns null. Example:
	 *
	 * <p>
	 * <code>
	 * public Multi&lt;String&gt; readLargeFile() {<br/>
	 *     final Multi&lt;String&gt; ret = vertx<br/>
	 *       .fileSystem()<br/>
	 *       .open("book.txt", new OpenOptions()<br/>
	 *       .setRead(true)).onItem()<br/>
	 *       .transformToMulti(file -&gt; file.toMulti()).onItem()<br/>
	 *       .transform(content -&gt; content.toString(StandardCharsets.UTF_8) + "\n------------\n");<br/>
	 *       return ret;<br/>
	 * }
	 * </code>
	 * </p>
	 * 
	 * @return {@link Multi}&lt;{@link String}&gt;
	 */
	@GET
	@Path("/book")
	@PermitAll
	@Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Multi<String> readLargeFile() {
		return null;
	}

	/**
	 * 
	 * Lorem
	 * 
	 * @return {@link Uni}&lt;{@link String}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/lorem")
	@Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Uni<String> readShortFile() {
		final Uni<String> ret = vertx.fileSystem().readFile("lorem.txt").onItem()
				.transform(content -> content.toString(StandardCharsets.UTF_8));
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{}", cnt);
		return ret;
	}

	/**
	 * 
	 * Counter informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.CounterService#getCounterInfo}.
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_vertxCounter">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_vertxCounter</a>
	 * 
	 * @return {@link CounterInfo}
	 * @throws PlatypusException {@link PlatypusException}
	 */
	@GET
	@PermitAll
	@Path("/vertxCounter")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Counter Info", description = "**Get Counter Info**<br/><br/>"
			+ "This method is calling CounterService.getCounterInfo<br/><br/>"
			+ "See [VertxController.vertxCounter](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#vertxCounter())")
	public CounterInfo vertxCounter() throws PlatypusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.getCount(), formatDateTime(ret.getDate()),
				ret.getStatus());
		return ret;
	}

}
