package local.intranet.quarkus.api.controller;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.vertx.web.Param;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.exception.PlatypusQuarkusException;
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
@OpenAPIDefinition(info = @Info(title = "Platypus Quarkus", version = "1.0.0-SNAPSHOT", contact = @Contact(name = "Radek Kádner", url = "https://github.com/lhsradek/platypus-quarkus", email = "radek.kadner@gmail.com"), description = "* Flyway for migrate data\n"
		+ "* Hibernate Envers Audit\n" + "* SmallRye Health\n" + "* Prometheus Metrics\n"
		+ "* Spring DATA JPA with CrudRepository and JpaRepository\n" + "* Logging to db with Logback\n"
		+ "* [Javadoc](/javadoc/)\n", license = @License(name = "The MIT License", url = "https://opensource.org/licenses/MIT")))
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
	 * @return {@link TemplateInstance}
	 * @throws NotFoundException {@link NotFoundException}
	 */
	@GET
	@Path("/")
	@Blocking
	@Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	// @Route(path = "/", methods = Route.HttpMethod.GET, produces = MediaType.TEXT_HTML, type = Route.HandlerType.BLOCKING)
	public TemplateInstance listFiles() throws NotFoundException {
		final String dir = DOWNLOAD_DIRECTORY;
		if (new File(dir).exists()) {
			final TreeSet<File> set = Stream.of(new File(dir).listFiles())
					.collect(Collectors.toCollection(TreeSet::new));
			incrementCounter();
			final List<Map.Entry<String, File>> ret = new ArrayList<>();
			for (File f : set) {
				ret.add(new SimpleEntry<String, File>(f.getName(), f));
			}
			final Map<String, String> map = statusController.getInfo();
			return DownloadTemplate.files(ret, map);
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
	@Path("file/{name}")
	@Blocking
	@Operation(hidden = true)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	// @Route(path = "file:name", methods = Route.HttpMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM, type = Route.HandlerType.BLOCKING)
	public Uni<Response> getFile(@Param String fileName) throws NotFoundException {
		LOG.debug("filename:'{}'", fileName);
		if (new File(DOWNLOAD_DIRECTORY).exists()) {
			final File nf = new File(fileName);
			LOG.trace("file:'{}' exists:{}", fileName, nf.exists());
			final ResponseBuilder response = Response.ok((Object) nf);
			response.header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + nf);
			incrementCounter();
			final Uni<Response> ret = Uni.createFrom().item(response.build());
			return ret;
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
	 * @throws PlatypusQuarkusException {@link PlatypusQuarkusException}
	 */
	@GET
	@Path("/downloadCounter")
	@Produces(MediaType.APPLICATION_JSON)
	// @Route(path = "/downloadCounter", methods = Route.HttpMethod.GET, produces = MediaType.APPLICATION_JSON)
	@Operation(summary = "Get Counter Info", description = "**Get Counter Info**<br/><br/>"
			+ "This method is calling CounterService.getCounterInfo<br/><br/>"
			+ "See [DownloadController.downloadCounter](/javadoc/local/intranet/quarkus/api/controller/DownloadController.html#downloadCounter())")
	public CounterInfo downloadCounter() throws PlatypusQuarkusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.getCount(), formatDateTime(ret.getDate()),
				ret.getStatus());
		return ret;
	}

}
