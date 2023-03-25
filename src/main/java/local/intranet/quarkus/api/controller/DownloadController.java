package local.intranet.quarkus.api.controller;

import java.io.File;
import java.text.MessageFormat;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.smallrye.mutiny.Uni;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.info.content.PlatypusCounter;

/**
 * 
 * {@link DownloadController}
 * 
 * Download files from quarkus
 * 
 * @author Radek Kádner
 *
 */
//@formatter:off
@OpenAPIDefinition( 
	    info = @Info(
	        title="Platypus Quarkus",
	        version = "1.0.0-SNAPSHOT",
	        contact = @Contact(
	            name = "Radek Kádner",
	            url = "https://www.linkedin.com/in/radekkadner/",
	            email = "radek.kadner@gmail.com"),
	        	description = "* Flyway for migrate data\n"
	        			+ "* Hibernate Envers Audit\n"
	        			+ "* SmallRye Health\n"
	        			+ "* Prometheus Metrics\n"
	        			+ "* Spring DATA JPA with CrudRepository and JpaRepository\n"
	        			+ "* Logging to db with Logback\n"
	        			+ "* [Javadoc](/javadoc/)\n",
	        license = @License(
	            name = "The MIT License",
	            url = "https://opensource.org/licenses/MIT"))
	)
//@formatter:on
@ApplicationScoped
public class DownloadController extends PlatypusCounter implements Countable, Invocationable, Statusable, Nameable {

	private static final Logger LOG = LoggerFactory.getLogger(DownloadController.class);

	/**
	 * 
	 * TAG = "download-controller"
	 */
	protected static final String TAG = "download-controller";

	/**
	 * 
	 * {@link ConfigProperty} for downloadDirectory
	 */
	@ConfigProperty(name = "platypus.quarkus.downloadDirectory")
	protected String downloadDirectory;

	private static final String CONTENT_DISPOSITION = "Content-Disposition";

	private static final String ATTACHMENT_FILENAME = "attachment;filename=";

	@Inject
	protected StatusController statusController;

	/**
	 * 
	 * List of files from Quarkus in downloads directory as JSON
	 * 
	 * @param ex {@link RoutingExchange}
	 * @return {@link String}
	 */
	@Operation(hidden = true)
	@Route(path = "/downloads", methods = Route.HttpMethod.GET, produces = { MediaType.TEXT_HTML })
	public void listFiles(RoutingExchange ex) {
		final String dir = downloadDirectory;
		if (new File(dir).exists()) {
			final Set<String> set = Stream.of(new File(dir).listFiles()).filter(file -> !file.isDirectory())
					.map(File::getName).collect(Collectors.toCollection(TreeSet::new));
			// .collect(Collectors.toCollection(() -> new
			// TreeSet<>(String.CASE_INSENSITIVE_ORDER)));
			final StringJoiner message = new StringJoiner(System.lineSeparator());
			message.add("<html><body><div>");
			message.add("<h2>Platypus Quarkus files</h2><p>");
			message.add("<h3>" + statusController.getServerName() + "</h3><p>");
			if (set.size() > 0) {
				message.add("<ul>");
				set.forEach(k -> {
					message.add(MessageFormat.format("<li><a alt=\"{0}\" href=\"/downloads/{0}\">{0}</a></li>", k));
				});
				message.add("</ul>");
			}
			message.add("</p></div></body></html>");
			ex.ok(message.toString());
		} else {
			ex.notFound().toString();
		}
	}

	/**
	 * 
	 * Download file from Quarkus
	 * 
	 * @param fileName {@link String}
	 * @param ex       {@link RoutingExchange}
	 * @return {@link Uni}&lt;{@link Response}&gt;
	 * @throws NotFoundException {@link NotFoundException}
	 */
	@Operation(hidden = true)
	@Route(path = "/downloads:name", methods = Route.HttpMethod.GET, produces = { MediaType.APPLICATION_OCTET_STREAM })
	public Uni<Response> getFile(@Param String fileName, RoutingExchange ex) throws NotFoundException {
		try {
			if (new File(downloadDirectory).exists()) {
				final File nf = new File(fileName);
				LOG.trace("file:'{}' exists:{}", fileName, nf.exists());
				final ResponseBuilder response = Response.ok((Object) nf);
				response.header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + nf);
				final Uni<Response> ret = Uni.createFrom().item(response.build());
				incrementCounter();
				return ret;
			} else {
				throw new NotFoundException();
			}
		} catch (Exception e) {
			// LOG.warn("file:'{}' statusCode:{}", fileName,
			// Response.Status.NOT_FOUND.getStatusCode());
			// LOG.error(Response.Status.NOT_FOUND.toString(), e);
			throw new NotFoundException();
		}
	}

}