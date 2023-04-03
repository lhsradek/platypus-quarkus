package local.intranet.quarkus.api.scheduler;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.scheduler.Scheduled;

/**
 * 
 * {@link PlatypusJob} for platypusJob.enable=true
 * 
 * @author Radek Kádner
 *
 */
@ApplicationScoped
public class PlatypusJob {

	private static final Logger LOG = LoggerFactory.getLogger(PlatypusJob.class);

	private final AtomicInteger counter = new AtomicInteger();

	/**
	 * 
	 * <code>platypus.job.enabled</code> from application.properties
	 * 
	 * <p>
	 * Change to Boolean:
	 * <code>
	 * if (Boolean.valueOf(job)) {
	 * ...
	 * }
	 * </code>
	 * 
	 */
	@ConfigProperty(name = "platypus.job.enabled") // toBoolean
	protected String job;

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
		if (Boolean.valueOf(job)) {
			LOG.info("Start scheduler");
		}
	}

	/**
	 * 
	 * End method
	 */
	@PreDestroy
	public void end() {
		if (Boolean.valueOf(job)) {
			LOG.info("End scheduler");
		}
	}

	/**
	 * 
	 * Cron job is defined in application.properties
	 * 
	 */
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
