package local.intranet.quarkus.api.controller;

import java.io.File;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

import io.micrometer.core.annotation.Timed;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.exception.PlatypusException;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.info.content.PlatypusCounter;
import local.intranet.quarkus.api.info.content.template.DownloadTemplate;
import local.intranet.quarkus.api.service.CounterService;

/**
 * 
 * {@link DownloadController}
 * 
 * Download files from quarkus
 * 
 * @author Radek Kádner
 *
 */
@Timed
@Path("/downloads")
@ApplicationScoped
@Tag(name = DownloadController.TAG)
public class DownloadController extends PlatypusCounter implements Countable, Invocationable, Statusable, Nameable {

	private static final Logger LOG = LoggerFactory.getLogger(DownloadController.class);

	/**
	 * 
	 * String TAG = "download-controller"
	 */
	protected static final String TAG = "download-controller";

	/**
	 * 
	 * {@link CounterService} for {@link #downloadCounter()}
	 */
	@Inject
	protected CounterService counterService;

	/**
	 * 
	 * {@link StatusController} for {@link #listFiles()}
	 */
	@Inject
	protected StatusController statusController;

	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	private static final String ATTACHMENT_FILENAME = "attachment;filename=";
	private static final String DOWNLOAD_DIRECTORY = "src/main/resources/META-INF/resources/downloads";

	/**
	 * 
	 * List of files from Quarkus in downloads directory as HTML
	 * 
	 * @return {@link Uni}&lt;{@link Response}&gt;
	 * @throws NotFoundException {@link NotFoundException}
	 */
	@GET
	@Path("/")
	@WithSpan
	@Blocking
	@Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	// @Route(path = "/", methods = Route.HttpMethod.GET, produces = MediaType.TEXT_HTML, type = Route.HandlerType.BLOCKING)
	public Uni<TemplateInstance> listFiles() throws NotFoundException {
		final String dir = DOWNLOAD_DIRECTORY;
		if (new File(dir).exists()) {
			final TreeSet<File> set = Stream.of(new File(dir).listFiles())
					.collect(Collectors.toCollection(TreeSet::new));
			final Long cnt = incrementCounter();
			LOG.trace("cnt:{}", cnt);
			final List<Map.Entry<String, File>> ret = new ArrayList<>();
			for (File f : set) {
				ret.add(new SimpleImmutableEntry<String, File>(f.getName(), f));
			}
			return Uni.createFrom().item(DownloadTemplate.files(ret, statusController.getInfo()));
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
	@GET
	@WithSpan
	@Path("file/{name}")
	@Blocking
	@Operation(hidden = true)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	// @Route(path = "file:name", methods = Route.HttpMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM, type = Route.HandlerType.BLOCKING)
	public Uni<Response> getFile(@PathParam("name") String fileName) throws NotFoundException {
		LOG.debug("filename:'{}'", fileName);
		if (new File(DOWNLOAD_DIRECTORY).exists()) {
			final File nf = new File(fileName);
			LOG.trace("file:'{}' exists:{}", fileName, nf.exists());
			final ResponseBuilder response = Response.ok((Object) nf);
			response.header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + nf);
			final Long cnt = incrementCounter();
			LOG.trace("cnt:{}", cnt);
			return Uni.createFrom().item(response.build());
		} else {
			throw new NotFoundException();
		}
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
	@WithSpan
	@Path("/downloadCounter")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Counter Info", description = "**Get Counter Info**<br/><br/>"
			+ "This method is calling CounterService.getCounterInfo<br/><br/>"
			+ "See [DownloadController.downloadCounter](/javadoc/local/intranet/quarkus/api/controller/DownloadController.html#downloadCounter())")
	// @Route(path = "/downloadCounter", methods = Route.HttpMethod.GET, produces = MediaType.APPLICATION_JSON, type = Route.HandlerType.BLOCKING)
	public CounterInfo downloadCounter() throws PlatypusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.getCount(), formatDateTime(ret.getDate()),ret.getStatus());
		return ret;
	}

}
