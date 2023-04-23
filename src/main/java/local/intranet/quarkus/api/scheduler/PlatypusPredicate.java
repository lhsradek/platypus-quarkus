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
	 * <code>platypus.job.enabled</code> from application.properties
	 * 
	 * <p>
	 * Change to Boolean:<br/>
	 * <code><br/>
	 * if (Boolean.valueOf(job)) {<br/>
	 * ...<br/>
	 * }<br/>
	 * </code>
	 * 
	 */
	@ConfigProperty(name = "platypus.job.enabled")
	protected String job;

	@Override
	public boolean test(ScheduledExecution execution) {
		return !Boolean.valueOf(job);
	}

}
