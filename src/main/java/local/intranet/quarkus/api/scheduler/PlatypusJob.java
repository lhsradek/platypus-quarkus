package local.intranet.quarkus.api.scheduler;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Timed;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.scheduler.Scheduled;

/**
 * 
 * {@link PlatypusJob} for platypusJob.enable=true
 * 
 * @author Radek Kádner
 *
 */
@Timed
@ApplicationScoped
public class PlatypusJob {

	private static final Logger LOG = LoggerFactory.getLogger(PlatypusJob.class);

	private final AtomicInteger counter = new AtomicInteger();

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
		LOG.info(jobMessage);
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