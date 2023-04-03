package local.intranet.quarkus.api.scheduler;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.arc.properties.IfBuildProperty;
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

	private static final String HELLO_FROM_PLATYPUS_QUARKUS = "Hello from job of Platypus Quarkus";
	private final AtomicInteger counter = new AtomicInteger();

	/**
	 * 
	 * Start method
	 */
	@PostConstruct
	@IfBuildProperty(name = "platypus.job.enabled", stringValue = "true")
	public void start() {
		LOG.info("Start scheduler");
	}

	/**
	 * 
	 * End method
	 */
	@PreDestroy
	@IfBuildProperty(name = "platypus.job.enabled", stringValue = "true")
	public void end() {
		LOG.info("End scheduler");
	}

	/**
	 * 
	 * Cron job is defined in application.properties
	 * 
	 */
	@Scheduled(cron = "{platypus.job.cron}", skipExecutionIf = PlatypusPredicate.class)
	public void job() {
		LOG.info(HELLO_FROM_PLATYPUS_QUARKUS);
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
