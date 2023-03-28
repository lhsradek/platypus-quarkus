package local.intranet.quarkus.api.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.smallrye.common.constraint.NotNull;
import local.intranet.quarkus.api.model.entity.Counter;

/**
 * 
 * {@link CounterRepository} is repository for CRUD with
 * {@link local.intranet.quarkus.api.model.entity.Counter}
 * 
 * https://quarkus.pro/guides/spring-data-jpa
 * 
 * @author Radek Kádner
 *
 */
public interface CounterRepository extends CrudRepository<Counter, Long> {

	@Override
	// @RestResource(exported = false)
	void deleteById(Long id);

	@Override
	// @RestResource(exported = false)
	void delete(Counter entity);
	
	@Override
	// @RestResource(exported = false)
	void deleteAll();

	@Override
	// @RestResource(exported = false)
	<S extends Counter> S save(S entity);

	@Override
	// @RestResource(exported = false)
	long count();
	
	/**
	 *
	 * Find by name
	 * 
	 * @param counterName {@link String}
	 * @return {@link Counter}
	 */
	@Query(value = "select u from Counter u where u.counterName = ?1")
	Counter findByName(@NotNull String counterName);
	
}
