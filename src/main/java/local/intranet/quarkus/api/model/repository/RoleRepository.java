package local.intranet.quarkus.api.model.repository;

import io.smallrye.common.constraint.NotNull;

import java.util.Optional;

import local.intranet.quarkus.api.model.entity.Role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * 
 * {@link RoleRepository} is repository for CRUD with
 * {@link local.intranet.quarkus.api.model.entity.Role}
 * <p>
 * https://quarkus.pro/guides/spring-data-jpa
 * <p>
 * https://quarkus.pro/guides/spring-di.html
 * 
 * To use RoleRepository see
 * {@link local.intranet.quarkus.api.service.RoleService#getUsersRoles} where is
 * roleRepository.findAll
 * 
 * @author Radek Kádner
 *
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

	/**
	 *
	 * Find by name
	 * 
	 * @param roleName {@link String}
	 * @return {@link Optional}&lt;{@link Role}&gt;
	 */
	@Query(value = "select u from Role u where u.roleName = ?1")
	public Optional<Role> findByName(@NotNull String roleName);

}
