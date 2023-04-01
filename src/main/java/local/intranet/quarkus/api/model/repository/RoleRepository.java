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

import local.intranet.quarkus.api.model.entity.Role;

/**
 * 
 * {@link RoleRepository} is repository for CRUD with
 * {@link local.intranet.quarkus.api.model.entity.Role}
 * 
 * https://quarkus.pro/guides/spring-data-jpa
 * 
 * To use RoleRepository see
 * {@link local.intranet.quarkus.api.service.RoleService#getUsersRoles} where is
 * roleRepository.findAll
 * 
 * @author Radek Kádner
 *
 */
@RepositoryRestResource(exported = false, path = "/roleRepository")
public interface RoleRepository extends CrudRepository<Role, Long> {

	/**
	 *
	 * Find by name
	 * 
	 * @param roleName {@link String}
	 * @return {@link Role}
	 */
	@GET
	@RestResource(exported = false)
	@Query(value = "select u from Role u where u.roleName = ?1")
	public Role findByName(@NotNull @Param("roleName") String roleName);

	@GET
	@Override
	@RestResource(exported = true)
	Optional<Role> findById(Long id);

	@DELETE
	@Override
	@RestResource(exported = false)
	void deleteById(Long id);

	@DELETE
	@Override
	@RestResource(exported = false)
	void delete(Role entity);

	@DELETE
	@Override
	@RestResource(exported = false)
	void deleteAll();

	@PUT
	@Override
	@RestResource(exported = false)
	<S extends Role> S save(S entity);

	@GET
	@Override
	@RestResource(exported = false)
	List<Role> findAll();

}
