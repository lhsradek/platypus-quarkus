package local.intranet.quarkus.api.controller;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
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
 * 
 * https://quarkus.io/guides/vertx
 * https://quarkus.io/guides/vertx-reference
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
	 * ACCOUNTS
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_accounts">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_accounts</a>
	 * 
	 * @param json {@link String}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 */
	@POST
	@Blocking
	@PermitAll
	@Path(ACCOUNTS)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "ACCOUNTS", description = "**ACCOUNTS**<br/><br/>"
			+ "See [VertxController.quarkusAccounts](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusAccounts(java.lang.String))")
	// @Operation(hidden = true)
	public Uni<Object> quarkusAccounts(@NotNull @FormParam("json") String json) {
		final String url = swaggerEndpoint + ACCOUNTS;
		final Uni<Object> ret = client.postAbs(url).send().onItem().transform(HttpResponse::body);
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
		return ret;
	}

	/**
	 * 
	 * Accounts Person UUID
	 * 
	 * @see <a href=
	 *      "/q/swagger-ui/#/vertx-controller/get_vertx_accounts_persons__personsUUID__accounts">
	 *      /q/swagger-ui/#/vertx-controller/get__vertx_accounts_persons__personsUUID__accounts</a>
	 * 
	 * @param personUUID {@link UUID}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path(ACCOUNTS + "/persons/{personUUID}/accounts")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Accounts Person UUID", description = "**Accounts Person UUID**<br/><br/>"
			+ "See [VertxController.quarkusAccountsPersonUUID](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusAccountsPersonUUID(java.lang.UUID))")
	// @Operation(hidden = true)
	public Uni<Object> quarkusAccountsPersonUUID(@NotNull @PathParam("personUUID") UUID personUUID) {
		final String url = swaggerEndpoint + ACCOUNTS + "/persons/" + personUUID + ACCOUNTS;
		final Uni<Object> ret = client.getAbs(url).send().onItem().transform(HttpResponse::body);
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
		return ret;
	}

	/**
	 * 
	 * Accounts Id
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_accounts__id_">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_accounts__id_</a>
	 * 
	 * @param id {@link String}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path(ACCOUNTS + "/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Accounts Id", description = "**Accounts Id**<br/><br/>"
			+ "See [VertxController.quarkusAccountsId](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusAccountsId(java.lang.String))")
	// @Operation(hidden = true)
	public Uni<Object> quarkusAccountsId(@NotNull @PathParam("id") String id) {
		final String url = swaggerEndpoint + ACCOUNTS + "/" + id;
		final Uni<Object> ret = client.getAbs(url).send().onItem().transform(HttpResponse::body);
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
		return ret;
	}

	private static final String PERSONS = "/persons";

	/**
	 * 
	 * PERSONS
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_persons">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_persons</a>
	 * 
	 * @param json {@link String}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 */
	@POST
	@Blocking
	@PermitAll
	@Path(PERSONS)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "PERSONS", description = "**PERSONS**<br/><br/>"
			+ "See [VertxController.quarkusPersons](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusPersons(java.lang.String))")
	// @Operation(hidden = true)
	public Uni<Object> quarkusPersons(@NotNull @FormParam("json") String json) {
		final String url = swaggerEndpoint + PERSONS + "?json=" + json;
		final Uni<Object> ret = client.postAbs(url).send().onItem().transform(HttpResponse::body);
		final Long cnt = incrementCounter();
		LOG.info("cnt:{} ret:'{}'", cnt, ret);
		return ret;
	}

	/**
	 * 
	 * Persons Id
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_persons__id_">
	 * @param id {@link String}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path(PERSONS + "/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Persons Id", description = "**Persons Id**<br/><br/>"
			+ "See [VertxController.quarkusPersonsId](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusPersonsId(java.lang.String))")
	// @Operation(hidden = true)
	public Uni<Object> quarkusPersonsId(@NotNull @PathParam("id") String id) {
		final String url = swaggerEndpoint + PERSONS + "/" + id;
		final Uni<Object> ret = client.getAbs(url).send().onItem().transform(HttpResponse::body);
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
		return ret;
	}

	private static final String ALIASES = "/aliases";
	
	/**
	 * 
	 * ALIASES
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_aliases">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_aliases</a>
	 * 
	 * @param json {@link String}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 */
	@POST
	@Blocking
	@PermitAll
	@Path(ALIASES)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "ALIASES", description = "**ALIASES**<br/><br/>"
			+ "See [VertxController.quarkusAliases](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusAliases(java.lang.String))")
	// @Operation(hidden = true)
	public Uni<Object> quarkusAliases(@NotNull @FormParam("json") String json) {
		final String url;
		if (json.length() == 0) {
			url = swaggerEndpoint + ALIASES;
		} else {
			url = swaggerEndpoint + ALIASES + "?json=" + json;
		}
		final Uni<Object> ret = client.postAbs(url).send().onItem().transform(HttpResponse::body);
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
		return ret;
	}

	private static final String NAS_E02 = "/nas/e02";
	
	/**
	 * 
	 * NAS e02
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_nas_e02">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_nas_e02</a>
	 * 
	 * @param date {@link String}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path(NAS_E02)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "NAS e02", description = "**NAS e02**<br/><br/>"
			+ "See [VertxController.quarkusNas02](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusNas02(java.lang.String))")
	// @Operation(hidden = true)
	public Uni<Object> quarkusNas02(@Nullable @QueryParam("date") String date) {
		final String url;
		if (date == null || date.length() == 0) {
			url = swaggerEndpoint + NAS_E02;
		} else {
			url = swaggerEndpoint + NAS_E02 + "?date=" + date;
		}
		final Uni<Object> ret = client.getAbs(url).send().onItem().transform(HttpResponse::body);
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
		return ret;
	}

	private static final String NAS_E24 = "/nas/e24";

	/**
	 * 
	 * NAS e24
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_nas_e24">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_nas_e24</a>
	 * 
	 * @param date {@link String}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path(NAS_E24)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "NAS e24", description = "**NAS e24**<br/><br/>"
			+ "See [VertxController.quarkusNas24](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusNas24(java.lang.String))")
	// @Operation(hidden = true)
	public Uni<Object> quarkusNas24(@Nullable @QueryParam("date") String date) {
		final String url;
		if (date == null || date.length() == 0) {
			url = swaggerEndpoint + NAS_E24;
		} else {
			url = swaggerEndpoint + NAS_E24 + "?date=" + date;
		}
		final Uni<Object> ret = client.getAbs(url).send().onItem().transform(HttpResponse::body);
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
		return ret;
	}

	private static final String NAS_E25 = "/nas/e25";

	/**
	 * 
	 * NAS e25
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_nas_e25">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_nas_e25</a>
	 * 
	 * @param date {@link String}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path(NAS_E25)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "NAS e25", description = "**NAS e25**<br/><br/>"
			+ "See [VertxController.quarkusNas25](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusNas25(java.lang.String))")
	// @Operation(hidden = true)
	public Uni<Object> quarkusNas25(@Nullable @QueryParam("date") String date) {
		final String url;
		if (date == null || date.length() == 0) {
			url = swaggerEndpoint + NAS_E25;
		} else {
			url = swaggerEndpoint + NAS_E25 + "?date=" + date;
		}
		final Uni<Object> ret = client.getAbs(url).send().onItem().transform(HttpResponse::body);
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
		return ret;
	}

	private static final String NAS_E26 = "/nas/e26";

	/**
	 * 
	 * NAS e26
	 * 
	 * @see <a href="/q/swagger-ui/#/vertx-controller/get_vertx_nas_e26">
	 *      /q/swagger-ui/#/vertx-controller/get_vertx_nas_e26</a>
	 * 
	 * @param date {@link String}
	 * @return {@link Uni}&lt;{@link Object}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path(NAS_E26)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "NAS e26", description = "**NAS e26**<br/><br/>"
			+ "See [VertxController.quarkusNas26](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#quarkusNas26(java.lang.String))")
	// @Operation(hidden = true)
	public Uni<Object> quarkusNas26(@Nullable @QueryParam("date") String date) {
		final String url;
		if (date == null || date.length() == 0) {
			url = swaggerEndpoint + NAS_E26;
		} else {
			url = swaggerEndpoint + NAS_E26 + "?date=" + date;
		}
		final Uni<Object> ret = client.getAbs(url).send().onItem().transform(HttpResponse::body);
		final Long cnt = incrementCounter();
		LOG.trace("cnt:{} url:'{}'", cnt, url);
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
	 * Lorem
	 * 
	 * @return {@link Uni}&lt;{@link String}&gt;
	 */
	@GET
	@Blocking
	@PermitAll
	@Path("/lorem")
	// @Operation(summary = "Lorem", description = "**Lorem**<br/><br/>"
	// + "See
	// [VertxController.readShortFile](/javadoc/local/intranet/quarkus/api/controller/VertxController.html#readShortFile())")
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
	 * Now it returns null. Example:
	 *
	 * <p>
	 * <code>
	 * public Multi&lt;String&gt; readLargeFile() {<br/>
	 *     final Multi&lt;String&gt; ret = vertx<br/>
	 *       .fileSystem()<br/>
	 *       .open("book.txt", new OpenOptions()<br/>
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