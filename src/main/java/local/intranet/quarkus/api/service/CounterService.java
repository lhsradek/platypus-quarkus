package local.intranet.quarkus.api.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.intranet.quarkus.api.controller.InfoController;
import local.intranet.quarkus.api.domain.type.StatusType;
import local.intranet.quarkus.api.info.CounterInfo;
import local.intranet.quarkus.api.info.content.PlatypusCounter;
import local.intranet.quarkus.api.model.entity.Counter;
import local.intranet.quarkus.api.model.repository.CounterRepository;

/**
 * 
 * {@link CounterService} for
 * {@link local.intranet.quarkus.api.controller.InfoController#getRoleInfo}
 * 
 * @author Radek Kádner
 *
 */
@ApplicationScoped
public class CounterService {

	private static final Logger LOG = LoggerFactory.getLogger(CounterService.class);

	@Inject
	protected CounterRepository counterRepository;

	/**
	 * 
	 * Get CounterInfo
	 * 
	 * @return {@link CounterInfo}
	 * @throws IllegalArgumentException {@link IllegalArgumentException}
	 */
	@Operation(hidden = true)
	public CounterInfo getCounterInfo() throws IllegalArgumentException {
		final CounterInfo ret;
		try {
			final String counterName = InfoController.class.getSimpleName().replace(PlatypusCounter.SUBCLASS, "");
			final ZonedDateTime zonedDateTime = ZonedDateTime
					.ofInstant(Instant.ofEpochSecond(System.currentTimeMillis()), ZoneId.systemDefault());
			final Long timestmp = zonedDateTime.toInstant().toEpochMilli();
			Counter counter = counterRepository.findByName(counterName);
			if (counter == null) {
				counter = new Counter();
				counter.setCounterName(counterName);
				counter.setCnt(1L);
				counter.setTimestmp(timestmp);
				counter.setStatus(StatusType.UP.getStatus());
				counter = counterRepository.save(counter);
				ret = new CounterInfo(counter.getId(), counterName, counter.getCnt(), counter.getTimestmp(),
						counter.getStatus());
			} else {
				ret = new CounterInfo(counter.getId(), counterName, counter.getCnt(), counter.getTimestmp(),
						counter.getStatus());
				LOG.trace("name:{} cnt:{}", counter.getCounterName(), counter.getCnt());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new IllegalArgumentException(e.getMessage());
		}
		return ret;
	}

}
