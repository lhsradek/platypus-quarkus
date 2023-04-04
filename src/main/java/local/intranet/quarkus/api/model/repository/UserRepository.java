package local.intranet.quarkus.api.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
	 * @return {@link User}
	 */
	@Query(value = "select u from User u where u.userName = ?1")
	public User findByName(@NotNull @Param("useName") String userName);

}
