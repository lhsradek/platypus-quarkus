package local.intranet.quarkus.api.model.repository;

import java.util.List;

import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import local.intranet.quarkus.api.model.entity.LoggingEvent;

/**
 * 
 * {@link LoggingEventRepository} is repository for JPA with
 * {@link local.intranet.quarkus.api.model.entity.LoggingEvent}
 * <p>
 * 
 * An repository is only used to read data written by logback-spring DbAppender
 * 
 * @author Radek Kádner
 *
 */
@Immutable
public interface LoggingEventRepository extends JpaRepository<LoggingEvent, Long> {

	/**
	 * 
	 * Count total logging event
	 * 
	 * @return {@link List}&lt;{@link Object[]}&gt;
	 */
	@Query(value = "select u.levelString, count(u.levelString) "
			+ "from LoggingEvent u group by u.levelString order by u.levelString asc")
	public List<Object[]> countTotalLoggingEvents();

	/**
	 * 
	 * Find page by LevelString
	 * 
	 * @param pageable    {@link Pageable}
	 * @param levelString {@link List}&lt;{@link String}&gt;
	 * @return {@link Page}&lt;{@link LoggingEvent}&gt;
	 */
	@Query(value = "select u from LoggingEvent u where u.levelString in ?1")
	public Page<LoggingEvent> findPageByLevelString(Pageable pageable, List<String> levelString);

	/**
	 * 
	 * Find page by caller
	 * 
	 * @param pageable     {@link Pageable}
	 * @param callerClass  {@link List}&lt;{@link String}&gt;
	 * @param callerMethod {@link List}&lt;{@link String}&gt;
	 * @param levelString  {@link List}&lt;{@link String}&gt;
	 * @return {@link Page}&lt;{@link LoggingEvent}&gt;
	 */
	@Query(value = "select u from LoggingEvent u "
			+ "where u.callerClass in ?1 and u.callerMethod in ?2 and levelString in ?3")
	public Page<LoggingEvent> findPageByCaller(Pageable pageable, List<String> callerClass, List<String> callerMethod,
			List<String> levelString);

}
