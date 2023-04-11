package local.intranet.quarkus.api.vertx;

import java.nio.charset.StandardCharsets;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Timed;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.file.OpenOptions;
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
	@Path("/hello")
	@Operation(hidden = true)
	public Uni<String> hello(@QueryParam("name") String name) {
		final Uni<String> ret = bus.<String>request("greetings", name).onItem().transform(response -> response.body());
		LOG.trace("{}", name);
		return ret;
	}
	
	/**
	 * 
	 * @return {@link Uni}&lt;{@link String}&gt;
	 */
	@GET
	@Path("/lorem")
	@Operation(hidden = true)
	public Uni<String> readShortFile() {
		final Uni<String> ret = vertx.fileSystem().readFile("lorem.txt").onItem()
				.transform(content -> content.toString(StandardCharsets.UTF_8));
		return ret;
	}

	/**
	 * 
	 * @return {@link Multi}&lt;{@link String}&gt;
	 */
	@GET
	@Path("/book")
	@Operation(hidden = true)
	public Multi<String> readLargeFile() {
		final Multi<String> ret = vertx.fileSystem().open("book.txt", new OpenOptions().setRead(true)).onItem()
				.transformToMulti(file -> file.toMulti()).onItem()
				.transform(content -> content.toString(StandardCharsets.UTF_8) + "\n------------\n");
		return ret;
	}

	/**
	 * 
	 * @return {@link Uni}&lt;{@link JsonArray}&gt;
	 */
	@GET
    @Path("/web")
    public Uni<JsonArray> retrieveDataFromWikipedia() {
        final String url = "https://en.wikipedia.org/w/api.php?action=parse&page=Quarkus&format=json&prop=langlinks";
        final Uni<JsonArray> ret = client.getAbs(url).send()
                .onItem().transform(HttpResponse::bodyAsJsonObject)
                .onItem().transform(json -> json.getJsonObject("parse").getJsonArray("langlinks"));
		LOG.trace("{}", url);
        return ret;
    }
	
}