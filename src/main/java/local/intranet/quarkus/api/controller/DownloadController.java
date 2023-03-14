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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.smallrye.mutiny.Uni;
import local.intranet.quarkus.api.domain.Measure;

/**
 * 
 * {@link DownloadController}
 * 
 * @author radek.kadner
 *
 */
@Path("/downloads")
public class DownloadController {

	private static final Logger LOG = LoggerFactory.getLogger(DownloadController.class);

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
	@Timed(value="getFile", description = Measure.TIMED_DESCRIPTION)
	@Counted(value="getFile", description = Measure.COUNTED_DESCRIPTION)
	public Uni<Response> getFile(@PathParam(value = "") String fileName) throws NotFoundException {
		try {
			final File nf = new File(fileName);
			LOG.info("file:'{}'", nf.exists());
			final ResponseBuilder response = Response.ok((Object) nf);
			response.header("Content-Disposition", "attachment;filename=" + nf);
			final Uni<Response> ret = Uni.createFrom().item(response.build());
			return ret;
		} catch (Exception e) {
			// LOG.warn("status:{}", Response.Status.NOT_FOUND.getStatusCode());
			LOG.error(Response.Status.NOT_FOUND.toString(), e);
			throw new NotFoundException();
		}
	}

}