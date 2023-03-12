package local.intranet.quarkus.api.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import local.intranet.quarkus.api.model.entity.Role;

/**
 * 
 * {@link RoleRepository} is repository for CRUD with
 * {@link local.intranet.quarkus.api.model.entity.Role}
 * 
 * To use RoleRepository
 * see {@link local.intranet.quarkus.api.service.RoleService#getUsersRoles} where is findAll
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
	 * @return {@link Role}
	 */
	@Query(value = "select u from Role u where u.roleName = ?1")
	public Role findByName(String roleName);

}
