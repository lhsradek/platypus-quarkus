package local.intranet.quarkus.api.info.content;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.smallrye.common.annotation.Blocking;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import local.intranet.quarkus.api.controller.IndexController;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.domain.type.StatusType;
import local.intranet.quarkus.api.model.entity.Counter;
import local.intranet.quarkus.api.model.repository.CounterRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * {@link PlatypusCounter} for {@link IndexController}
 * 
 * @author Radek Kádner
 *
 */
public abstract class PlatypusCounter implements Countable, Invocationable, Statusable, Nameable {

	private static final Logger LOG = LoggerFactory.getLogger(PlatypusCounter.class);

	private static final String SUBCLASS = "_Subclass";

	@Inject
	protected CounterRepository counterRepository;

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
		final Optional<Counter> counter = counterRepository.findByName(counterName);
		if (counter.isEmpty()) {
			ret = 0L;
		} else {
			ret = counter.get().getCnt();
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
	@Blocking
	@Transactional
	public Long incrementCounter() {
		final Long ret;
		final String counterName = getName();
		final ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
				ZoneId.systemDefault());
		final Long timestmp = zonedDateTime.toInstant().toEpochMilli();
		final Optional<Counter> counter = counterRepository.findByName(counterName);
		if (counter.isEmpty()) {
			final Counter c = new Counter();
			c.setCounterName(counterName);
			c.setCnt(1L);
			c.setTimestmp(timestmp);
			c.setStatus(StatusType.UP.getStatus());
			final Counter c2 = counterRepository.save(c);
			ret = c2.getCnt();
		} else {
			counter.get().setCnt(counter.get().getCnt() + 1L);
			counter.get().setTimestmp(timestmp);
			final Counter c = counterRepository.save(counter.get());
			ret = c.getCnt();
		}
		LOG.debug("name:'{}' count:{}", counterName, ret);
		return ret;
	}

	@Override
	public StatusType getStatus() {
		final String counterName = getName();
		final Optional<Counter> counter = counterRepository.findByName(counterName);
		final StatusType ret;
		if (counter.isEmpty()) {
			ret = StatusType.NONE;
		} else {
			ret = StatusType.valueOf(counter.get().getStatus());
		}
		LOG.trace("name:'{}' status:'{}'", counterName, ret);
		return ret;
	}

	@Override
	@JsonProperty("date")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonFormat(pattern = JSON_DATE_FORMAT, timezone = JsonFormat.DEFAULT_TIMEZONE)
	public ZonedDateTime lastInvocation() {
		final ZonedDateTime ret;
		final String counterName = getName();
		final Optional<Counter> counter = counterRepository.findByName(counterName);
		if (counter.isEmpty()) {
			ret = ZonedDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault());
		} else {
			ret = ZonedDateTime.ofInstant(Instant.ofEpochSecond(counter.get().getTimestmp()), ZoneId.systemDefault());
		}
		LOG.trace("name:'{}' date:'{}'", counterName, formatDateTime(ret));
		return ret;
	}

	@Override
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("counterName")
	public String getName() {
		return getClass().getSimpleName().replace(SUBCLASS, "");
	}

}
