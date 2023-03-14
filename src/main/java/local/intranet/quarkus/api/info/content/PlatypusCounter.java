package local.intranet.quarkus.api.info.content;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.inject.Inject;

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
 * @author radek.kadner
 *
 */
public abstract class PlatypusCounter implements Countable, Invocationable, Statusable {

	private static final Logger LOG = LoggerFactory.getLogger(PlatypusCounter.class);

	@Inject
	public CounterRepository counterRepository;

	@Inject
	public Provider provider;

	@Override
	public Long countValue() {
		final Long ret;
		final Counter counter = counterRepository.findByName(PlatypusCounter.class.getSimpleName());
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
	public Long incrementCounter() {
		final Long ret;
		final String className = PlatypusCounter.class.getSimpleName();
		final Long timestmp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()).toEpochSecond();
		final Counter counter = counterRepository.findByName(className);
		if (counter == null) {
			final Counter newCounter = new Counter();
			newCounter.setCounterName(className);
			newCounter.setTimestmp(timestmp);
			newCounter.setStatus(StatusType.UP);
			newCounter.setCnt(0L);
			ret = counterRepository.save(newCounter).getCnt();
		} else {
			counter.setCnt(counter.getCnt() + 1);
			counter.setTimestmp(timestmp);
			ret = counterRepository.save(counter).getCnt();
		}
		LOG.trace("count:{}", ret);
		return ret;
	}

	@Override
	public StatusType getStatus() {
		final Counter counter = counterRepository.findByName(PlatypusCounter.class.getSimpleName());
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
		final Counter counter = counterRepository.findByName(PlatypusCounter.class.getSimpleName());
		if (counter == null) {
			ret = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.systemDefault());
		} else {
			ret = ZonedDateTime.ofInstant(Instant.ofEpochMilli(counter.getTimestmp()), ZoneId.systemDefault());
		}
		LOG.trace("date: {}", ret);
		return ret;
	}

}
