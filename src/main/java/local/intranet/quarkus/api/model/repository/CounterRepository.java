package local.intranet.quarkus.api.model.repository;

import java.util.Optional;

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

	/**
	 *
	 * Find by name
	 * 
	 * @param counterName {@link String}
	 * @return {@link Optional}&lt;{@link Counter}&gt;
	 */
	@Query(value = "select u from Counter u where u.counterName = ?1")
	public Optional<Counter> findByName(@NotNull String counterName);

}
