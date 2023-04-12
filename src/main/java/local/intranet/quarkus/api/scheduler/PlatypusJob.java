package local.intranet.quarkus.api.scheduler;

import java.text.MessageFormat;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Timed;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.scheduler.Scheduled;
import local.intranet.quarkus.api.controller.InfoController;
import local.intranet.quarkus.api.controller.StatusController;

/**
 * 
 * {@link PlatypusJob} for platypusJob.enable=true
 * 
 * @author Radek Kádner
 *
 */
@Timed
@Singleton
public class PlatypusJob {

	private static final Logger LOG = LoggerFactory.getLogger(PlatypusJob.class);

	private final AtomicInteger counter = new AtomicInteger();

	@Inject
	protected InfoController infoController;
	
	@Inject
	protected StatusController statusController;
	
	/**
	 * 
	 * <code>platypus.job.message</code> from application.properties
	 * 
	 */
	@ConfigProperty(name = "platypus.job.message")
	protected String jobMessage;
	
	/**
	 * 
	 * Start method
	 */
	@PostConstruct
	public void start() {
		LOG.debug("Start scheduler");
	}

	/**
	 * 
	 * End method
	 */
	@PreDestroy
	public void end() {
		LOG.debug("End scheduler");
	}

	/**
	 * 
	 * Cron job is defined in application.properties
	 * 
	 */
	@WithSpan(kind = SpanKind.SERVER)
	@Scheduled(cron = "{platypus.job.cron}", skipExecutionIf = PlatypusPredicate.class)
	public void job() {
		final StringJoiner buf = new StringJoiner(", "); 
		infoController.loggingEventInfo().forEach(k -> {
			buf.add(MessageFormat.format("{0}={1}", k.getLevel(), String.join("", k.getTotal().toString().split("/s+"))));
		});
		LOG.info(MessageFormat.format("{0} status:{1} level:[{2}]", jobMessage, statusController.plainStatus(), buf.toString()));
	}

	/**
	 * 
	 * get counter for
	 * {@link local.intranet.quarkus.api.controller.IndexController#jobCounter}
	 * 
	 * @return int
	 */
	public int getCounter() {
		return counter.get();
	}

}
