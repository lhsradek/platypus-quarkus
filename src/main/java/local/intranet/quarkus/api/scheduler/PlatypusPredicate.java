package local.intranet.quarkus.api.scheduler;

import javax.inject.Singleton;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.scheduler.Scheduled.SkipPredicate;
import io.quarkus.scheduler.ScheduledExecution;

/**
 * 
 * {@link PlatypusPredicate}
 * 
 * @author Radek Kádner
 *
 */
@Singleton
public class PlatypusPredicate implements SkipPredicate {

	/**
	 * 
	 * platypus.job.enabled
	 * 
	 */
	@ConfigProperty(name = "platypus.job.enabled")
	public String job;

	@Override
	public boolean test(ScheduledExecution execution) {
		return !Boolean.valueOf(job);
	}

}
