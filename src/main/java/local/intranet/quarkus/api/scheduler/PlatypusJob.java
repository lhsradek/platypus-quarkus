package local.intranet.quarkus.api.scheduler;

import java.text.MessageFormat;
import java.util.StringJoiner;

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
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.info.content.PlatypusCounter;

/**
 * 
 * {@link PlatypusJob} for platypusJob.enable=true
 * 
 * @author Radek Kádner
 *
 */
@Timed
@Singleton
public class PlatypusJob extends PlatypusCounter implements Countable, Invocationable, Statusable, Nameable {

	private static final Logger LOG = LoggerFactory.getLogger(PlatypusJob.class);

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
		incrementCounter();
		LOG.info(MessageFormat.format("{0} status:{1} level:[{2}]", jobMessage, statusController.plainStatus(), buf.toString()));
	}
	
	/**
	 *
	 * startJob
	 * @return boolean
	 */
	public boolean startJob() {
		LOG.debug("Start Job");
		job();
		return true;
	}
	
	/**
	 *
	 * testJob
	 * @return boolean
	 */
	public boolean testJob() {
		LOG.debug("Test Job");
		return true;
	}


	/**
	 * 
	 * get counter for
	 * {@link local.intranet.quarkus.api.controller.IndexController#jobCounter}
	 * 
	 * @return long
	 */
	public long getCounter() {
		return countValue();
	}

}
