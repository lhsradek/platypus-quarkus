package local.intranet.quarkus.api.controller;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import io.micrometer.core.annotation.Timed;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniOnItem;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
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
 * 
 * https://quarkus.io/guides/vertx https://quarkus.io/guides/vertx-reference
 * https://how-to.vertx.io/web-and-openapi-howto/
 *
 */
@Timed
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

	/**
	 * 
	 * <code>platypus.swagger.url</code> for application.properties
	 */
	@ConfigProperty(name = "platypus.swagger.url")
	protected URL swaggerEndpoint;

	private final Vertx vertx;
	private final WebClient client;

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

	private static final String ACCOUNTS = "/accounts";

	/**
	 * 
	 * Accounts Person UUID
	 * 
	 * @param personUUID {@link UUID}
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Blocking
	@Path(ACCOUNTS + "/persons/{personUUID}/accounts")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Accounts Person UUID", description = "**Accounts Person UUID**<br/><br/>"
			+ "See [VertxController.quarkusAccountsPersonUUID](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusAccountsPersonUUID())")
	// @Operation(hidden = true)
	public Uni<JsonObject> quarkusAccountsPersonUUID(@PathParam("personUUID") UUID personUUID) {
		final String url = swaggerEndpoint + ACCOUNTS + "/persons/" + personUUID + ACCOUNTS;
		final Uni<HttpResponse<Buffer>> send = client.getAbs(url).send();
		final UniOnItem<HttpResponse<Buffer>> sendOnItem = send.onItem();
		final Uni<JsonObject> ret = sendOnItem.transform(HttpResponse::bodyAsJsonObject);
		incrementCounter();
		LOG.trace("{}", url);
		return ret;
	}

	/**
	 * 
	 * Accounts Id
	 * 
	 * @param id {@link String}
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Blocking
	@Path(ACCOUNTS + "/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Accounts Id", description = "**Accounts Id**<br/><br/>"
			+ "See [VertxController.quarkusAccountsId](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusAccountsId())")
	// @Operation(hidden = true)
	public Uni<JsonObject> quarkusAccountsId(@PathParam("id") String id) {
		final String url = swaggerEndpoint + ACCOUNTS + "/" + id;
		final Uni<HttpResponse<Buffer>> send = client.getAbs(url).send();
		final UniOnItem<HttpResponse<Buffer>> sendOnItem = send.onItem();
		final Uni<JsonObject> ret = sendOnItem.transform(HttpResponse::bodyAsJsonObject);
		incrementCounter();
		LOG.trace("{}", url);
		return ret;
	}

	private static final String PERSONS = "/persons";

	/**
	 * 
	 * Persons Id
	 * 
	 * @param id {@link String}
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Blocking
	@Path(PERSONS + "/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Persons Id", description = "**Persons Id**<br/><br/>"
			+ "See [VertxController.quarkusPersonsId](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusPersonsId())")
	// @Operation(hidden = true)
	public Uni<JsonObject> quarkusPersonsId(@PathParam("id") String id) {
		final String url = swaggerEndpoint + PERSONS + "/" + id;
		final Uni<HttpResponse<Buffer>> send = client.getAbs(url).send();
		final UniOnItem<HttpResponse<Buffer>> sendOnItem = send.onItem();
		final Uni<JsonObject> ret = sendOnItem.transform(HttpResponse::bodyAsJsonObject);
		incrementCounter();
		LOG.trace("{}", url);
		return ret;
	}

	private static final String NAS_E02 = "/nas/e02";

	/**
	 * 
	 * NAS e02
	 * 
	 * @param date {@link String}
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Blocking
	@Path(NAS_E02)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "NAS e02", description = "**NAS e02**<br/><br/>"
			+ "See [VertxController.quarkusNas02](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusNas02())")
	// @Operation(hidden = true)
	public Uni<JsonArray> quarkusNas02(@QueryParam("date") String date) {
		final String url;
		if (date == null || date.length() == 0) {
			url = swaggerEndpoint + NAS_E02;
		} else {
			url = swaggerEndpoint + NAS_E02 + "?date=" + date;
		}
		final Uni<HttpResponse<Buffer>> send = client.getAbs(url).send();
		final UniOnItem<HttpResponse<Buffer>> sendOnItem = send.onItem();
		final Uni<JsonArray> ret = sendOnItem.transform(HttpResponse::bodyAsJsonArray);
		incrementCounter();
		LOG.trace("{}", url);
		return ret;
	}

	private static final String NAS_E24 = "/nas/e24";

	/**
	 * 
	 * NAS e24
	 * 
	 * @param date {@link String}
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Blocking
	@Path(NAS_E24)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "NAS e24", description = "**NAS e24**<br/><br/>"
			+ "See [VertxController.quarkusNas24](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusNas24())")
	// @Operation(hidden = true)
	public Uni<JsonArray> quarkusNas24(@QueryParam("date") String date) {
		final String url;
		if (date == null || date.length() == 0) {
			url = swaggerEndpoint + NAS_E24;
		} else {
			url = swaggerEndpoint + NAS_E24 + "?date=" + date;
		}
		final Uni<HttpResponse<Buffer>> send = client.getAbs(url).send();
		final UniOnItem<HttpResponse<Buffer>> sendOnItem = send.onItem();
		final Uni<JsonArray> ret = sendOnItem.transform(HttpResponse::bodyAsJsonArray);
		incrementCounter();
		LOG.trace("{}", url);
		return ret;
	}

	private static final String NAS_E25 = "/nas/e25";

	/**
	 * 
	 * NAS e25
	 * 
	 * @param date {@link String}
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Blocking
	@Path(NAS_E25)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "NAS e25", description = "**NAS e25**<br/><br/>"
			+ "See [VertxController.quarkusNas25](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusNas25())")
	// @Operation(hidden = true)
	public Uni<JsonArray> quarkusNas25(@QueryParam("date") String date) {
		final String url;
		if (date == null || date.length() == 0) {
			url = swaggerEndpoint + NAS_E25;
		} else {
			url = swaggerEndpoint + NAS_E25 + "?date=" + date;
		}
		final Uni<HttpResponse<Buffer>> send = client.getAbs(url).send();
		final UniOnItem<HttpResponse<Buffer>> sendOnItem = send.onItem();
		final Uni<JsonArray> ret = sendOnItem.transform(HttpResponse::bodyAsJsonArray);
		incrementCounter();
		LOG.trace("{}", url);
		return ret;
	}

	private static final String NAS_E26 = "/nas/e26";

	/**
	 * 
	 * NAS e26
	 * 
	 * @param date {@link String}
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Blocking
	@Path(NAS_E26)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "NAS e26", description = "**NAS e26**<br/><br/>"
			+ "See [VertxController.quarkusNas26](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusNas26())")
	// @Operation(hidden = true)
	public Uni<JsonArray> quarkusNas26(@QueryParam("date") String date) {
		final String url;
		if (date == null || date.length() == 0) {
			url = swaggerEndpoint + NAS_E26;
		} else {
			url = swaggerEndpoint + NAS_E26 + "?date=" + date;
		}
		final Uni<HttpResponse<Buffer>> send = client.getAbs(url).send();
		final UniOnItem<HttpResponse<Buffer>> sendOnItem = send.onItem();
		final Uni<JsonArray> ret = sendOnItem.transform(HttpResponse::bodyAsJsonArray);
		incrementCounter();
		LOG.trace("{}", url);
		return ret;
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
	@Path("/ahoj")
	@Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Uni<String> ahoj(@QueryParam("name") String name) {
		final Uni<String> ret = bus.<String>request("ahoj", name).onItem().transform(response -> response.body());
		incrementCounter();
		LOG.debug("{}", name);
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
	@Path("/hello")
	@Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Uni<String> hello(@QueryParam("name") String name) {
		final Uni<String> ret = bus.<String>request("hello", name).onItem().transform(response -> response.body());
		incrementCounter();
		LOG.trace("{}", name);
		return ret;
	}

	/**
	 * 
	 * Wiki Platypus
	 * 
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Blocking
	@Path("/platypus")
	@Operation(summary = "Wiki Platypus", description = "**Wiki Platypus**<br/><br/>"
			+ "See [VertxController.platypusRetrieveDataFromWikipedia](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#platypusRetrieveDataFromWikipedia())")
	// @Operation(hidden = true)
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<JsonArray> platypusRetrieveDataFromWikipedia() {
		final String url = "https://en.wikipedia.org/w/api.php?action=parse&page=Platypus&format=json&prop=langlinks";
		final Uni<HttpResponse<Buffer>> send = client.getAbs(url).send();
		final UniOnItem<HttpResponse<Buffer>> sendOnItem = send.onItem();
		final Uni<JsonArray> ret = sendOnItem.transform(HttpResponse::bodyAsJsonObject).onItem()
				.transform(json -> json.getJsonObject("parse").getJsonArray("langlinks"));
		incrementCounter();
		LOG.trace("{}", url);
		return ret;
	}

	/**
	 * 
	 * Wiki Quarkus
	 * 
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
	@Path("/quarkus")
	@Blocking
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Wiki Quarkus", description = "**Wiki Quarkus**<br/><br/>"
			+ "See [VertxController.quarkusDataFromWikipedia](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusDataFromWikipedia())")
	// @Operation(hidden = true)
	public Uni<JsonArray> quarkusDataFromWikipedia() {
		final String url = "https://en.wikipedia.org/w/api.php?action=parse&page=Quarkus&format=json&prop=langlinks";
		final Uni<HttpResponse<Buffer>> send = client.getAbs(url).send();
		final UniOnItem<HttpResponse<Buffer>> sendOnItem = send.onItem();
		final Uni<JsonArray> ret = sendOnItem.transform(HttpResponse::bodyAsJsonObject).onItem()
				.transform(json -> json.getJsonObject("parse").getJsonArray("langlinks"));
		incrementCounter();
		LOG.trace("{}", url);
		return ret;
	}

	/**
	 * 
	 * Lorem
	 * 
	 * @return {@link Uni}&lt;{@link String}&gt;
	 */
	@GET
	@Path("/lorem")
	@Blocking
	// @Operation(summary = "Lorem", description = "**Lorem**<br/><br/>"
	//		+ "See [VertxController.readShortFile](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#readShortFile())")
	@Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Uni<String> readShortFile() {
		final Uni<String> ret = vertx.fileSystem().readFile("lorem.txt").onItem()
				.transform(content -> content.toString(StandardCharsets.UTF_8));
		incrementCounter();
		return ret;
	}

	/**
	 *
	 * Now it returns null. Example:
	 *
	 * <p>
	 * <code>
	 * public Multi<String> readLargeFile() {<br/>
	 *     final Multi<String> ret = vertx
	 *       .fileSystem()
	 *       .open("book.txt", new OpenOptions()
	 *       .setRead(true)).onItem()<br/>
	 *       .transformToMulti(file -> file.toMulti()).onItem()<br/>
	 *       .transform(content -> content.toString(StandardCharsets.UTF_8) + "\n------------\n");<br/>
	 *       return ret;<br/>
	 * }
	 * </code>
	 * </p>
	 * 
	 * @return {@link Multi}&lt;{@link String}&gt;
	 */
	@POST
	@Path("/book")
	@Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Multi<String> readLargeFile() {
		return null;
	}

	/**
	 * 
	 * Counter informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.CounterService#getCounterInfo}.
	 * 
	 * @return {@link CounterInfo}
	 * @throws PlatypusException {@link PlatypusException}
	 */
	@GET
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