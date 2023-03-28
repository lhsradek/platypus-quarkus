package local.intranet.quarkus.api.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.smallrye.common.constraint.NotNull;
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
public interface UserRepository extends CrudRepository<User, Long> {

	// @Override
	// @RestResource(exported = false)
	// void deleteById(Long id);

	// @Override
	// @RestResource(exported = false)
	// void delete(User entity);
	
	// @Override
	// @RestResource(exported = false)
	// void deleteAll();

	// @Override
	// @RestResource(exported = false)
	// @SuppressWarnings("unchecked")
	// User save(User entity);
	
	// @Override
	// @RestResource(exported = false)
	// long count();
	
	/**
	 *
	 * Find by name
	 * 
	 * @param userName {@link String}
	 * @return {@link User}
	 */
	@Query(value = "select u from User u where u.userName = ?1")
	public User findByName(@NotNull String userName);

}
