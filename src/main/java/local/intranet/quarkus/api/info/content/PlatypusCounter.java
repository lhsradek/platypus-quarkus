package local.intranet.quarkus.api.info.content;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.intranet.quarkus.api.controller.IndexController;
import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.domain.type.StatusType;
import local.intranet.quarkus.api.model.entity.Counter;
import local.intranet.quarkus.api.model.repository.CounterRepository;

/**
 * 
 * {@link PlatypusCounter} for {@link IndexController}
 * 
 * @author Radek Kádner
 *
 */
public abstract class PlatypusCounter implements Countable, Invocationable, Statusable {

	private static final Logger LOG = LoggerFactory.getLogger(PlatypusCounter.class);

	/**
	 * 
	 * String SUBCLASS = "_Subclass"
	 */
	public static final String SUBCLASS = "_Subclass";

	@Inject
	protected CounterRepository counterRepository;

	@Inject
	protected Provider provider;

	@Override
	public Long countValue() {
		final Long ret;
		final String counterName = getClass().getSimpleName().replace(SUBCLASS, "");
		final Counter counter = counterRepository.findByName(counterName);
		if (counter == null) {
			ret = 0L;
		} else {
			ret = counter.getCnt();
		}
		LOG.trace("count:{}", ret);
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
		final String counterName = getClass().getSimpleName().replace(SUBCLASS, "");
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
		LOG.trace("count:{}", ret);
		return ret;
	}

	@Override
	public StatusType getStatus() {
		final String counterName = getClass().getSimpleName().replace(SUBCLASS, "");
		final Counter counter = counterRepository.findByName(counterName);
		final StatusType ret;
		if (counter == null) {
			ret = StatusType.NONE;
		} else {
			ret = StatusType.valueOf(counter.getStatus());
		}
		LOG.trace("status:{}", ret);
		return ret;
	}

	@Override
	public ZonedDateTime lastInvocation() {
		final ZonedDateTime ret;
		final String counterName = getClass().getSimpleName().replace(SUBCLASS, "");
		final Counter counter = counterRepository.findByName(counterName);
		if (counter == null) {
			ret = ZonedDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault());
		} else {
			ret = ZonedDateTime.ofInstant(Instant.ofEpochSecond(counter.getTimestmp()), ZoneId.systemDefault());
		}
		LOG.trace("date:{}", ret);
		return ret;
	}

}
