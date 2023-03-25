package local.intranet.quarkus.api.controller;

import java.io.File;
import java.text.MessageFormat;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@Path("/downloads")
@Tag(name = DownloadController.TAG)
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
	
	
	/**
	 * 
	 * List of files from Quarkus in downloads directory as JSON
	 * 
	 * @return {@link String}
	 * @throws NotFoundException {@link NotFoundException}
	 */
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	public String listFiles() throws NotFoundException {
		final String dir = downloadDirectory;
		if (new File(dir).exists()) {
			final Set<String> set = Stream.of(new File(dir).listFiles()).filter(file -> !file.isDirectory())
					.map(File::getName)
					.collect(Collectors.toCollection(TreeSet::new));
					// .collect(Collectors.toCollection(() -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER)));
			final StringJoiner message = new StringJoiner(System.lineSeparator());
			message.add("<html><body><div>");
			message.add("<h2>Platypus Quarkus files</h2><p>");
			if (set.size() > 0) {
				message.add("<ul>");
				set.forEach(k -> {
					message.add(
							MessageFormat.format("<li><a alt=\"{0}\" href=\"/downloads/{0}\">{0}</a></li>", k, k, k));
				});
				message.add("</ul>");
			}
			message.add("</p></div></body></html>");
			return message.toString();
		} else {
			throw new NotFoundException();
		}
	}

	/**
	 * 
	 * Download file from Quarkus
	 * 
	 * @param fileName {link @String}
	 * @return {@link Uni}&lt;{@link Response}&gt;
	 * @throws NotFoundException {@link NotFoundException}
	 */
	@GET
	@Path("/{fileName}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Operation(hidden = true)
	public Uni<Response> getFile(@PathParam("fileName") String fileName) throws NotFoundException {
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