package local.intranet.quarkus.api.controller;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.vertx.web.Param;
import io.smallrye.mutiny.Uni;
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
@OpenAPIDefinition(info = @Info(title = "Platypus Quarkus", version = "1.0.0-SNAPSHOT", contact = @Contact(name = "Radek Kádner", url = "https://github.com/lhsradek/platypus-quarkus", email = "radek.kadner@gmail.com"), description = "* Flyway for migrate data\n"
		+ "* Hibernate Envers Audit\n" + "* SmallRye Health\n" + "* Prometheus Metrics\n"
		+ "* Spring DATA JPA with CrudRepository and JpaRepository\n" + "* Logging to db with Logback\n"
		+ "* [Javadoc](/javadoc/)\n", license = @License(name = "The MIT License", url = "https://opensource.org/licenses/MIT")))
// @Path("/downloads")
@RestController
@RequestMapping(value = "/downloads")
// @Tag(name = DownloadController.TAG)
public class DownloadController
		extends PlatypusCounter /* implements Countable, Invocationable, Statusable, Nameable */ {

	private static final Logger LOG = LoggerFactory.getLogger(DownloadController.class);

	/**
	 * 
	 * String TAG = "download-controller" protected static final String TAG =
	 * "download-controller";
	 */

	private static final String CONTENT_DISPOSITION = "Content-Disposition";

	private static final String ATTACHMENT_FILENAME = "attachment;filename=";

	private static final String DOWNLOAD_DIRECTORY = "src/main/resources/META-INF/resources/downloads";

	/**
	 * 
	 * HTML Template for /downloads
	 *
	 */
	@CheckedTemplate
	protected static class Templates {

		/**
		 * 
		 * @param files {@link Set}&lt;{@link String}&gt;
		 * @return {@link TemplateInstance}
		 */
		public static native TemplateInstance files(Set<String> files);
	}

	/**
	 * 
	 * List of files from Quarkus in downloads directory as HTML
	 * 
	 * @return {@link TemplateInstance}
	 * @throws NotFoundException {@link NotFoundException}
	 */
	// @GET
	// @Path("/")
	// @Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public TemplateInstance listFiles() throws NotFoundException {
		final String dir = DOWNLOAD_DIRECTORY;
		if (new File(dir).exists()) {
			final Set<String> set = Stream.of(new File(dir).listFiles()).filter(file -> !file.isDirectory())
					.map(File::getName).collect(Collectors.toCollection(TreeSet::new));
			// .collect(Collectors.toCollection(() -> new
			// TreeSet<>(String.CASE_INSENSITIVE_ORDER)));
			// incrementCounter();
			return Templates.files(set);
		} else {
			throw new NotFoundException();
		}
	}

	/**
	 * 
	 * Download file from Quarkus
	 * 
	 * @param fileName {@link String}
	 * @return {@link Uni}&lt;{@link Response}&gt;
	 * @throws NotFoundException {@link NotFoundException}
	 */
	// @GET
	// @Path("/{name}")
	// @Operation(hidden = true)
	// @Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Operation(hidden = true)
	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public Uni<Response> getFile(@Param String fileName) throws NotFoundException {
		try {
			if (new File(DOWNLOAD_DIRECTORY).exists()) {
				final File nf = new File(fileName);
				LOG.trace("file:'{}' exists:{}", fileName, nf.exists());
				final ResponseBuilder response = Response.ok((Object) nf);
				response.header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + nf);
				final Uni<Response> ret = Uni.createFrom().item(response.build());
				// incrementCounter();
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
