package local.intranet.quarkus.api.controller;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

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

	private static final String CONTENT_DISPOSITION = "Content-Disposition";

	private static final String ATTACHMENT_FILENAME = "attachment;filename=";

	/**
	 * 
	 * Download file from quarkus
	 * 
	 * @param fileName {link @String}
	 * @return {@link Uni}&lt;{@link Response}&gt;
	 * @throws NotFoundException {@link NotFoundException}
	 */
	@GET
	@Path("/{fileName}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Operation(hidden = true)
	// @Timed(value = Measureable.PREFIX + "getfile", description = Measureable.TIMED_DESCRIPTION)
	// @Counted(value = Measureable.PREFIX + "getFile", description = Measureable.COUNTED_DESCRIPTION)
	// @Measured()
	public Uni<Response> getFile(@PathParam(value = "\\w[\\w\\.-]*") String fileName) throws NotFoundException {
		try {
			final File nf = new File(fileName);
			LOG.info("file:'{}'", nf.exists());
			final ResponseBuilder response = Response.ok((Object) nf);
			response.header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + nf);
			final Uni<Response> ret = Uni.createFrom().item(response.build());
			incrementCounter();
			return ret;
		} catch (Exception e) {
			// LOG.warn("status:{}", Response.Status.NOT_FOUND.getStatusCode());
			LOG.error(Response.Status.NOT_FOUND.toString(), e);
			throw new NotFoundException();
		}
	}

}