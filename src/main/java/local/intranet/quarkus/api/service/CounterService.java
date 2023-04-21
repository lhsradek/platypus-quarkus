package local.intranet.quarkus.api.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.common.constraint.NotNull;
import local.intranet.quarkus.api.domain.type.StatusType;
import local.intranet.quarkus.api.exception.PlatypusException;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.model.entity.Counter;
import local.intranet.quarkus.api.model.repository.CounterRepository;

/**
 * 
 * {@link CounterService}
 * 
 * https://quarkus.pro/guides/spring-di.html
 * 
 * @author Radek Kádner
 *
 */
@ApplicationScoped
public class CounterService {

	private static final Logger LOG = LoggerFactory.getLogger(CounterService.class);

	/**
	 * 
	 * {@link CounterRepository} for {@link #getCounterInfo}
	 */
	@Inject
	protected CounterRepository counterRepository;

	/**
	 * 
	 * Get CounterInfo
	 * 
	 * @param counterName {@link String}
	 * @return {@link CounterInfo}
	 * @throws PlatypusException {@link PlatypusException}
	 */
	@Transactional
	@Operation(hidden = true)
	public CounterInfo getCounterInfo(@NotNull String counterName) throws PlatypusException {
		final CounterInfo ret;
		try {
			final ZonedDateTime zonedDateTime = ZonedDateTime
					.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
			final Long timestmp = zonedDateTime.toInstant().toEpochMilli();
			final Optional<Counter> counter = counterRepository.findByName(counterName);
			if (counter.isEmpty()) {
				final Counter c = new Counter();
				c.setCounterName(counterName);
				c.setCnt(0L);
				c.setTimestmp(timestmp);
				c.setStatus(StatusType.UP.getStatus());
				final Counter c2 = counterRepository.save(c);
				ret = new CounterInfo(c2.getId(), counterName, c2.getCnt(), c2.getTimestmp(),
						c2.getStatus());
			} else {
				ret = new CounterInfo(counter.get().getId(), counterName, counter.get().getCnt(), counter.get().getTimestmp(),
						counter.get().getStatus());
				LOG.trace("name:{} cnt:{}", counter.get().getCounterName(), counter.get().getCnt());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new PlatypusException(e.getMessage());
		}
		return ret;
	}

}
