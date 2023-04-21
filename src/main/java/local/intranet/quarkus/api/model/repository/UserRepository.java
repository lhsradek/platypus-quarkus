package local.intranet.quarkus.api.model.repository;

import java.util.Optional;

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

	/**
	 *
	 * Find by name
	 * 
	 * @param userName {@link String}
	 * @return {@link Optional}&lt;{@link User}&gt;
	 */
	@Query(value = "select u from User u where u.userName = ?1")
	public Optional<User> findByName(@NotNull String userName);

}
