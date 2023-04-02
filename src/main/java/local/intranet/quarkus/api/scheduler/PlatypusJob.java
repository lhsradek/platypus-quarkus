package local.intranet.quarkus.api.scheduler;

import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.scheduler.Scheduled;

/**
 * 
 * {@link PlatypusJob}
 * 
 * @author Radek Kádner
 *
 *
 */
@ApplicationScoped
public class PlatypusJob {

	private static final Logger LOG = LoggerFactory.getLogger(PlatypusJob.class);
	
	private static final String HELLO_FROM_PLATYPUS_QUARKUS = "Hello from Platypus Quarkus";

	private final AtomicInteger counter = new AtomicInteger();
	
	/**
	 * 
	 * Cron job is defined in application.properties
	 * 
	 */
	@Scheduled(cron = "{platypusJob.cron}")
    public void job() {
        LOG.info(HELLO_FROM_PLATYPUS_QUARKUS);
    }

	/**
	 * 
	 * get counter for {@link local.intranet.quarkus.api.controller.IndexController#jobCounter}
	 * 
	 * @return int
	 */
	public int getCounter() {
		return counter.get();
	}
	
}
