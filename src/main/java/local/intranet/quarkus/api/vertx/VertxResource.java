package local.intranet.quarkus.api.vertx;

import java.nio.charset.StandardCharsets;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Timed;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;

/**
 * 
 * 
 * {@link VertxResource}
 * 
 * https://quarkus.io/guides/vertx
 *
 */
@Timed
@Path("/vertx")
@ApplicationScoped
@Tag(name = VertxResource.TAG)
public class VertxResource {
	
	private static final Logger LOG = LoggerFactory.getLogger(VertxResource.class);

	/**
	 * 
	 * String TAG = "vertx-resource"
	 */
	protected static final String TAG = "vertx-resource";

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
	 * @param vertx {@link VertxResource} 
	 */
	@Inject
	public VertxResource(Vertx vertx) {
		this.vertx = vertx;
		this.client = WebClient.create(vertx);
	}

	/**
	 * 
	 * @param name {@link String}
	 * @return {@link Uni}&lt;{@link String}&gt;
	 */
	@GET
	@Path("/ahoj")
	// @Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Uni<String> ahoj(@QueryParam("name") String name) {
		final Uni<String> ret = bus.<String>request("ahoj", name).onItem().transform(response -> response.body());
		LOG.trace("{}", name);
		return ret;
	}
	
	/**
	 * 
	 * @param name {@link String}
	 * @return {@link Uni}&lt;{@link String}&gt;
	 */
	@GET
	@Path("/hello")
	// @Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Uni<String> hello(@QueryParam("name") String name) {
		final Uni<String> ret = bus.<String>request("hello", name).onItem().transform(response -> response.body());
		LOG.trace("{}", name);
		return ret;
	}
	
	/**
	 * 
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
    @Path("/platypus")
	// @Operation(hidden = true)
	@Produces(MediaType.APPLICATION_JSON)
    public Uni<JsonArray> platypusRetrieveDataFromWikipedia() {
        final String url = "https://en.wikipedia.org/w/api.php?action=parse&page=Platypus&format=json&prop=langlinks";
        final Uni<JsonArray> ret = client.getAbs(url).send()
                .onItem().transform(HttpResponse::bodyAsJsonObject)
                .onItem().transform(json -> json.getJsonObject("parse").getJsonArray("langlinks"));
		LOG.trace("{}", url);
        return ret;
    }
	
	/**
	 * 
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
    @Path("/quarkus")
	@Produces(MediaType.APPLICATION_JSON)
	// @Operation(hidden = true)
    public Uni<JsonArray> quarkusDataFromWikipedia() {
        final String url = "https://en.wikipedia.org/w/api.php?action=parse&page=Quarkus&format=json&prop=langlinks";
        final Uni<JsonArray> ret = client.getAbs(url).send()
                .onItem().transform(HttpResponse::bodyAsJsonObject)
                .onItem().transform(json -> json.getJsonObject("parse").getJsonArray("langlinks"));
		LOG.trace("{}", url);
        return ret;
    }
	
	/**
	 * 
	 * @return {@link Uni}&lt;{@link String}&gt;
	 */
	@PUT
	@Path("/lorem")
	@Operation(hidden = true)
	@Produces(MediaType.TEXT_PLAIN)
	public Uni<String> readShortFile() {
		final Uni<String> ret = vertx.fileSystem().readFile("lorem.txt").onItem()
				.transform(content -> content.toString(StandardCharsets.UTF_8));
		return ret;
	}

	/**
	 *
	 *  Now it returns null. Example:
	 *
	 *  <p><code>
	public Multi<String> readLargeFile() {<br/>
		final Multi<String> ret = vertx.fileSystem().open("book.txt", new OpenOptions().setRead(true)).onItem()<br/>
				.transformToMulti(file -> file.toMulti()).onItem()<br/>
				.transform(content -> content.toString(StandardCharsets.UTF_8) + "\n------------\n");<br/>
		return ret;<br/>
	}
	 *  </code></p>
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

}