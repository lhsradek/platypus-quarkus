package local.intranet.quarkus.api.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.smallrye.common.constraint.NotNull;
import local.intranet.quarkus.api.model.entity.Role;

/**
 * 
 * {@link RoleRepository} is repository for CRUD with
 * {@link local.intranet.quarkus.api.model.entity.Role}
 * 
 * https://quarkus.pro/guides/spring-data-jpa
 * 
 * To use RoleRepository see
 * {@link local.intranet.quarkus.api.service.RoleService#getUsersRoles} where is roleRepository.findAll
 * 
 * @author Radek Kádner
 *
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

	// @Override
	// @RestResource(exported = false)
	// void deleteById(Long id);

	// @Override
	// @RestResource(exported = false)
	// void delete(Role entity);
	
	// @Override
	// @RestResource(exported = false)
	// void deleteAll();

	// @Override
	// @RestResource(exported = false)
	// @SuppressWarnings("unchecked")
	// Role save(Role entity);
	
	// @Override
	// @RestResource(exported = false)
	// long count();
	
	/**
	 *
	 * Find by name
	 * 
	 * @param roleName {@link String}
	 * @return {@link Role}
	 */
	@Query(value = "select u from Role u where u.roleName = ?1")
	public Role findByName(@NotNull String roleName);

}
