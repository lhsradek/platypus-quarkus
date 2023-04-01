package local.intranet.quarkus.api.model.repository;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import local.intranet.quarkus.api.model.entity.LoggingEvent;

/**
 * 
 * {@link LoggingEventRepository} is repository for JPA with
 * {@link local.intranet.quarkus.api.model.entity.LoggingEvent}
 * <p>
 * 
 * https://quarkus.pro/guides/spring-data-jpa
 * 
 * An repository is only used to read data written by logback-spring DbAppender
 * 
 * @author Radek Kádner
 *
 */
@RepositoryRestResource(exported = false, path = "/loggingEventRepository")
public interface LoggingEventRepository extends JpaRepository<LoggingEvent, Long> {

	/**
	 * 
	 * Find page by LevelString
	 * 
	 * @param pageable    {@link Pageable}
	 * @param levelString {@link List}&lt;{@link String}&gt;
	 * @return {@link Page}&lt;{@link LoggingEvent}&gt;
	 */
	@RestResource(exported = false)
	@Query(value = "select u from LoggingEvent u where u.levelString in ?1")
	public Page<LoggingEvent> findPageByLevelString(Pageable pageable, List<String> levelString);

	/**
	 * 
	 * Count total logging event
	 * 
	 * @return {@link List}&lt;{@link Object[]}&gt;
	 */
	@GET
	@RestResource(exported = false)
	@Query(value = "select u.levelString, count(u.levelString) "
			+ "from LoggingEvent u group by u.levelString order by u.levelString asc")
	public List<Object[]> countTotalLoggingEvents();

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
	@RestResource(exported = false)
	@Query(value = "select u from LoggingEvent u "
			+ "where u.callerClass in ?1 and u.callerMethod in ?2 and levelString in ?3")
	public Page<LoggingEvent> findPageByCaller(Pageable pageable, List<String> callerClass, List<String> callerMethod,
			List<String> levelString);

	@GET
	@Override
	@RestResource(exported = true)
	Optional<LoggingEvent> findById(Long id);
	
    @DELETE	
	@Override
	@RestResource(exported = false)
	void deleteById(Long id);

    @DELETE	
	@Override
	@RestResource(exported = false)
	void delete(LoggingEvent entity);
	
    @DELETE	
	@Override
	@RestResource(exported = false)
	void deleteAll();
	
    @DELETE	
	@Override
	@RestResource(exported = false)
	void deleteAllInBatch();

    @DELETE	
	@Override
	@RestResource(exported = false)
	void deleteInBatch(Iterable<LoggingEvent> entities);
	
    @PUT
	@Override
	@RestResource(exported = false)
	<S extends LoggingEvent> S save(S entity);
	
    @PUT	
	@Override
	@RestResource(exported = false)
	<S extends LoggingEvent> S saveAndFlush(S entity);
	
	@GET
	@Override
	@RestResource(exported = false)
	List<LoggingEvent> findAll();
	
	@GET
	@Override
	@RestResource(exported = false)
	long count();
	
}
