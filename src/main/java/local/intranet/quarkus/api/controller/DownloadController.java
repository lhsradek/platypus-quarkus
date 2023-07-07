package local.intranet.quarkus.api.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
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

	/**
	 * 
	 * <code>platypus.download.directory</code> from application.properties
	 * 
	 */
	@ConfigProperty(name = "platypus.download.directory")
	public String downloadDirectory;

	/**
	 * 
	 * List of files from Quarkus in downloads directory as HTML
	 * 
	 * @return {@link Response}
	 * @throws NotFoundException {@link NotFoundException}
	 * @throws IOException {@link IOException}
	 */
	@GET
	@Path("/")
	@WithSpan
	@Blocking
	@PermitAll
	@Produces(MediaType.TEXT_HTML)
	@Operation(hidden = true)
	// @Operation(summary = "List Files", description = "**List Files**<br/><br/>"
	// + "See
	// [DownloadController.listFiles](/javadoc/local/intranet/quarkus/api/controller/DownloadController.html#listFiles())")
	public TemplateInstance listFiles() throws NotFoundException, IOException {
		LOG.debug("Working Directory: '{}'", System.getProperty("user.dir"));
		final File file = new File(downloadDirectory);
		if (file.exists()) {
			if (file.isDirectory()) {
				final TreeSet<File> set = Stream.of(file.listFiles()).collect(Collectors.toCollection(TreeSet::new));
				final Long cnt = incrementCounter();
				final List<Map.Entry<String, File>> ret = new ArrayList<>();
				for (File f : set) {
					if (f.canRead()) {
						ret.add(new SimpleImmutableEntry<String, File>(f.getName(), f));
					}
				}
				LOG.debug("fileDir:'{}' cnt:{} ret.size:{}", downloadDirectory, cnt, ret.size());
				return DownloadTemplate.downloads(ret, statusController.getInfo());
			} else {
				final Long cnt = incrementCounter();
				final List<Map.Entry<String, File>> ret = new ArrayList<>();
				// ret.add(new SimpleImmutableEntry<String, File>(file.getName(),
				// file.getAbsoluteFile()));
				// LOG.debug("fileDir:'{}' file.length:{}", downloadDirectory, file.length());

				final URL jar = new URL("file://" + file.getAbsolutePath());
				final ZipInputStream zip = new ZipInputStream(jar.openStream());
				final Map<String, File> map = new TreeMap<>();
				while (true) {
					final ZipEntry e = zip.getNextEntry();
					if (e == null)
						break;
					final String name = e.getName();
					if (name.startsWith("META-INF/resources/downloads/")
							&& !name.contains("personal")) {
						final File f = new File(e.getName());
						if (!e.isDirectory()) {
							map.put(f.getName(), f);
						}
					}
				}
				map.forEach((k, v) -> {
					ret.add(new SimpleImmutableEntry<String, File>(k, v));
				});
				LOG.debug("fileDir:'{}' cnt:{} ret.size:{}", downloadDirectory, cnt, ret.size());
				return DownloadTemplate.downloads(ret, statusController.getInfo());
			}
		} else {
			LOG.error("NotFoundException");
			throw new NotFoundException();
		}
	}

	// private static final String CONTENT_DISPOSITION = "Content-Disposition";
	// private static final String ATTACHMENT_FILENAME = "attachment;filename=";

	/**
	 * 
	 * Download file from Quarkus
	 * 
	 * @param fileName {@link String}
	 * @return {@link Multi}&lt;{@link Response}&gt;
	 * @throws NotFoundException {@link NotFoundException}
	@GET
	@WithSpan
	@Path("file/{name}")
	@Blocking
	@PermitAll
	@Operation(hidden = true)
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Multi<Response> getFile(@PathParam("name") String fileName) throws NotFoundException {
		if (new File(downloadDirectory).exists()) {
			final File nf = new File(fileName);
			final ResponseBuilder response = Response.ok((Object) nf);
			response.header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + nf);
			response.encoding(fileName);
			response.language(Locale.US);
			final Long cnt = incrementCounter();
			LOG.debug("fileName:'{}' cnt:{}", fileName, cnt);
			return Multi.createFrom().item(response.build());
		} else {
			LOG.error("NotFoundException");
			throw new NotFoundException();
		}
	}
	 */

	/**
	 * 
	 * Counter informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.CounterService#getCounterInfo}.
	 * 
	 * @see <a href=
	 *      "/q/swagger-ui/#/download-controller/get_downloads_downloadCounter">
	 *      /q/swagger-ui/#/download-controller/get_downloads_downloadCounter</a>
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
	// @Route(path = "/downloadCounter", methods = Route.HttpMethod.GET, produces =
	// MediaType.APPLICATION_JSON, type = Route.HandlerType.BLOCKING)
	public CounterInfo downloadCounter() throws PlatypusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.getCount(), formatDateTime(ret.getDate()),
				ret.getStatus());
		return ret;
	}

}
