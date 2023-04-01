package local.intranet.quarkus.api.model.repository;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

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
@RepositoryRestResource(exported = false, path = "/counterRepository")
public interface CounterRepository extends CrudRepository<Counter, Long> {

	/**
	 *
	 * Find by name
	 * 
	 * @param counterName {@link String}
	 * @return {@link Counter}
	 */
	@GET
	@RestResource(exported = false)
	@Query(value = "select u from Counter u where u.counterName = ?1")
	Counter findByName(@NotNull @Param("counterName") String counterName);
	
	@GET
	@Override
	@RestResource(exported = true)
	Optional<Counter> findById(Long id);
	
	@DELETE
	@Override
	@RestResource(exported = false)
	void deleteById(Long id);

	@DELETE
	@Override
	@RestResource(exported = false)
	void delete(Counter entity);
	
	@DELETE
	@Override
	@RestResource(exported = false)
	void deleteAll();

	@PUT
	@Override
	@RestResource(exported = false)
	<S extends Counter> S save(S entity);

	@GET
	@Override
	@RestResource(exported = false)
	List<Counter> findAll();
	
	@GET
	@Override
	@RestResource(exported = false)
	long count();
	
}
