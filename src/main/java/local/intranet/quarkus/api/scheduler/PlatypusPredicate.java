package local.intranet.quarkus.api.scheduler;

import io.quarkus.scheduler.Scheduled.SkipPredicate;
import io.quarkus.scheduler.ScheduledExecution;

import javax.inject.Singleton;

import org.eclipse.microprofile.config.inject.ConfigProperty;

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
	 * <code>platypus.job.enabled</code> from application.properties
	 * 
	 */
	@ConfigProperty(name = "platypus.job.enabled")
	protected Boolean job;

	@Override
	public boolean test(ScheduledExecution execution) {
		return !job;
	}

}
