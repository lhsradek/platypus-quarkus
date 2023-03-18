package local.intranet.quarkus.api.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import local.intranet.quarkus.api.info.LevelCount;
import local.intranet.quarkus.api.info.LoggingEventInfo;
import local.intranet.quarkus.api.model.entity.LoggingEvent;
import local.intranet.quarkus.api.model.repository.LoggingEventRepository;

/**
 * 
 * {@link LoggingEventService} for {@link local.intranet.quarkus.api}
 * <p>
 * An entity {@link LoggingEvent} without setters is only used to read data
 * written by logback-spring DbAppender
 * 
 * @author Radek Kádner
 *
 */
@ApplicationScoped
public class LoggingEventService {

	private static final Logger LOG = LoggerFactory.getLogger(LoggingEventService.class);

	@Inject
	protected LoggingEventRepository loggingEventRepository;

	/**
	 * 
	 * Count Total Logging Event
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.model.repository.LoggingEventRepository#countTotalLoggingEvents}
	 *
	 * @return {@link List}&lt;{@link LevelCount}&gt;
	 */
	@Operation(hidden = true)
	public List<LevelCount> countTotalLoggingEvents() {
		final List<LevelCount> ret = new ArrayList<>();
		for (Object[] o : loggingEventRepository.countTotalLoggingEvents()) {
			ret.add(new LevelCount((String) o[0], (Long) o[1]));
		}
		LOG.trace("{}", ret);
		return ret;
	}

	/**
	 * 
	 * findPageByLevelString {@link LoggingEvent}
	 * <p>
	 * Used
	 * {@link local.intranet.quarkus.api.model.repository.LoggingEventRepository#findPageByLevelString}
	 * 
	 * @param pageable    {@link Pageable}
	 * @param levelString {@link List}&lt;{@link String}&gt;
	 * 
	 * @return {@link Page}&lt;{@link LoggingEventInfo}&gt;
	 */
	@Operation(hidden = true)
	public Page<LoggingEventInfo> findPageByLevelString(Pageable pageable, List<String> levelString) {
		try {
			Page<LoggingEvent> pa = loggingEventRepository.findPageByLevelString(pageable, levelString);
			List<LoggingEventInfo> list = new ArrayList<>();
			for (LoggingEvent l : pa) {
				list.add(makeLoggingEventInfo(l));
			}
			Page<LoggingEventInfo> ret = new PageImpl<>(list, pageable, pa.getTotalElements());
			LOG.trace("{}", ret);
			return ret;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 
	 * Make loggingEventInfo
	 * 
	 * @param loggingEvent {@link LoggingEvent}
	 * @return {@link LoggingEventInfo}
	 */
	protected LoggingEventInfo makeLoggingEventInfo(LoggingEvent loggingEvent) {
		String[] s = loggingEvent.getCallerClass().split("\\.");
		String arg0 = (loggingEvent.getArg0() == null) ? "[NULL]" : loggingEvent.getArg0();
		String arg1 = (loggingEvent.getArg1() == null) ? "[NULL]" : loggingEvent.getArg1();
		String arg2 = (loggingEvent.getArg2() == null) ? "[NULL]" : loggingEvent.getArg2();
		String arg3 = (loggingEvent.getArg3() == null) ? "[NULL]" : loggingEvent.getArg3();
		// LOG.trace("arg0:{} arg1:{} arg2:{} arg3:{}", arg0, arg1, arg2, arg3);
		LoggingEventInfo ret = new LoggingEventInfo(loggingEvent.getId(), loggingEvent.getFormattedMessage(),
				loggingEvent.getLevelString(), (s.length > 0) ? s[s.length - 1] : "", loggingEvent.getCallerMethod(),
				arg0, arg1, arg2, arg3,
				ZonedDateTime.ofInstant(Instant.ofEpochMilli(loggingEvent.getTimestmp()), ZoneId.systemDefault()));

		LOG.trace("{}", ret);
		return ret;
	}

}
