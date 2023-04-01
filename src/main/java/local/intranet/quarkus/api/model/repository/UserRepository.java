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

import local.intranet.quarkus.api.model.entity.User;

/**
 * 
 * {@link UserRepository} is repository for CRUD with
 * {@link local.intranet.quarkus.api.model.entity.User}
 * 
 * https://quarkus.pro/guides/spring-data-jpa
 * 
 * @author Radek Kádner
 *
 */
@RepositoryRestResource(exported = false, path = "/userRepository")
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 *
	 * Find by name
	 * 
	 * @param userName {@link String}
	 * @return {@link User}
	 */
	@GET
	@Query(value = "select u from User u where u.userName = ?1")
	public User findByName(@NotNull @Param("useName") String userName);

	@GET
	@Override
	@RestResource(exported = true)
	Optional<User> findById(Long id);

	@DELETE
	@Override
	@RestResource(exported = false)
	void deleteById(Long id);

	@DELETE
	@Override
	@RestResource(exported = false)
	void delete(User entity);

	@DELETE
	@Override
	@RestResource(exported = false)
	void deleteAll();

	@PUT
	@Override
	@RestResource(exported = false)
	<S extends User> S save(S entity);

	@GET
	@Override
	@RestResource(exported = false)
	List<User> findAll();

}
