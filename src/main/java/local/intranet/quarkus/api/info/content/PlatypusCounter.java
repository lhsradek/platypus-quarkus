package local.intranet.quarkus.api.info.content;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import local.intranet.quarkus.api.controller.IndexController;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Measureable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.domain.type.StatusType;
import local.intranet.quarkus.api.exception.PlatypusQuarkusException;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.model.entity.Counter;
import local.intranet.quarkus.api.model.repository.CounterRepository;
import local.intranet.quarkus.api.service.CounterService;

/**
 * 
 * {@link PlatypusCounter} for {@link IndexController}
 * 
 * @author Radek Kádner
 *
 */
@Dependent
public abstract class PlatypusCounter implements Countable, Invocationable, Statusable, Nameable {

	private static final Logger LOG = LoggerFactory.getLogger(PlatypusCounter.class);

	private static final String SUBCLASS = "_Subclass";

	@Inject
	protected CounterRepository counterRepository;

	@Inject
	protected CounterService counterService;

	@Inject
	protected Provider provider;

	/**
	 * 
	 * Format dateTime with {@link Invocationable#DATE_FORMAT}
	 * <p>
	 * Static method for use with several different parts of the program
	 * 
	 * @param dateTime {@link ZonedDateTime}
	 * @return {@link String}
	 */
	public static String formatDateTime(ZonedDateTime dateTime) {
		return formatDateTime(dateTime, Invocationable.DATE_FORMAT);
	}

	/**
	 * 
	 * Format dateTime
	 * <p>
	 * Static method for use with several different parts of the program
	 * 
	 * @param dateTime {@link ZonedDateTime}
	 * @param format   use {@link Invocationable#DATE_FORMAT} or
	 *                 {@link Invocationable#JSON_DATE_FORMAT}
	 * @return {@link String}
	 */
	public static String formatDateTime(ZonedDateTime dateTime, String format) {
		return dateTime.format(DateTimeFormatter.ofPattern(format));
	}

	@Override
	public Long countValue() {
		final Long ret;
		final String counterName = getName();
		final Counter counter = counterRepository.findByName(counterName);
		if (counter == null) {
			ret = 0L;
		} else {
			ret = counter.getCnt();
		}
		LOG.trace("name:'{}' count:{}", counterName, ret);
		return ret;
	}

	/**
	 * 
	 * Increment counter
	 * 
	 * @return {@link Long}
	 */
	@Transactional
	public Long incrementCounter() {
		final Long ret;
		final String counterName = getName();
		final ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
				ZoneId.systemDefault());
		final Long timestmp = zonedDateTime.toInstant().toEpochMilli();
		Counter counter = counterRepository.findByName(counterName);
		if (counter == null) {
			counter = new Counter();
			counter.setCounterName(counterName);
			counter.setCnt(1L);
			counter.setTimestmp(timestmp);
			counter.setStatus(StatusType.UP.getStatus());
			counter = counterRepository.save(counter);
			ret = counter.getCnt();
		} else {
			counter.setCnt(counter.getCnt() + 1L);
			counter.setTimestmp(timestmp);
			counter = counterRepository.save(counter);
			ret = counter.getCnt();
		}
		LOG.debug("name:'{}' count:{}", counterName, ret);
		return ret;
	}

	@Override
	public StatusType getStatus() {
		final String counterName = getName();
		final Counter counter = counterRepository.findByName(counterName);
		final StatusType ret;
		if (counter == null) {
			ret = StatusType.NONE;
		} else {
			ret = StatusType.valueOf(counter.getStatus());
		}
		LOG.trace("name:'{}' status:'{}'", counterName, ret);
		return ret;
	}

	@Override
	public ZonedDateTime lastInvocation() {
		final ZonedDateTime ret;
		final String counterName = getName();
		final Counter counter = counterRepository.findByName(counterName);
		if (counter == null) {
			ret = ZonedDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault());
		} else {
			ret = ZonedDateTime.ofInstant(Instant.ofEpochSecond(counter.getTimestmp()), ZoneId.systemDefault());
		}
		LOG.trace("name:'{}' date:'{}'", counterName, formatDateTime(ret));
		return ret;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName().replace(SUBCLASS, "");
	}

	/**
	 * 
	 * Counter informations
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.service.CounterService#getCounterInfo}.
	 * 
	 * @return {@link CounterInfo}
	 * @throws PlatypusQuarkusException {@link PlatypusQuarkusException}
	 */
	@GET
	@Path("counter")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(value = Measureable.PREFIX + "counterInfo", description = Measureable.TIMED_DESCRIPTION)
	@Counted(value = Measureable.PREFIX + "counterInfo", description = Measureable.COUNTED_DESCRIPTION)
	@Operation(summary = "Get Counter Info", description = "<strong>Get Counter Info</strong><br/><br/>"
			+ "This method is calling CounterService.getCounterInfo<br/><br/>"
			+ "See <a href=\"/javadoc/local/intranet/quarkus/api/info/content/PlatypusCounter.html#counterInfo()\" "
			+ "target=\"_blank\">PlatypusCounter.counterInfo</a>")
	public CounterInfo counterInfo() throws PlatypusQuarkusException {
		final String counterName = getName();
		final CounterInfo ret = counterService.getCounterInfo(counterName);
		// incrementCounter();
		LOG.debug("name:'{}' cnt:{} date:'{}': status:'{}'", counterName, ret.countValue(),
				formatDateTime(ret.lastInvocation()), ret.getStatus());
		return ret;
	}

}
