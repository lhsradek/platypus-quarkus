package local.intranet.quarkus.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.intranet.quarkus.api.info.RoleInfo;
import local.intranet.quarkus.api.info.RolePlain;
import local.intranet.quarkus.api.model.repository.RoleRepository;

/**
 * 
 * {@link RoleService} for
 * {@link local.intranet.quarkus.api.controller.InfoController#roleInfo}
 * 
 * https://quarkus.pro/guides/spring-di.html
 * 
 * @author Radek Kádner
 *
 */
@ApplicationScoped
public class RoleService {

	private static final Logger LOG = LoggerFactory.getLogger(RoleService.class);

	/**
	 * 
	 * {@link RoleRepository} for {@link #getUsersRoles}
	 */
	@Inject
	protected RoleRepository roleRepository;

	/**
	 * 
	 * Get RoleInfo
	 * 
	 * @return {@link RoleInfo}
	 */
	@Operation(hidden = true)
	public RoleInfo getRoleInfo() {
		return new RoleInfo(getUsersRoles());
	}

	/**
	 * 
	 * Get userRole
	 * 
	 * @return {@link List}&lt;{@link RolePlain}&gt;
	 */
	@Operation(hidden = true)
	protected List<RolePlain> getUsersRoles() {
		final List<RolePlain> ret = new ArrayList<>();
		roleRepository.findAll().forEach(r -> {
			ret.add(new RolePlain(r.getId(), r.getRoleName(), r.isEnabled(),
					r.getUser().stream().map(u -> u.getUserName()).collect(Collectors.toList())));
		});
		LOG.trace("size:{}", ret.size());
		return ret;
	}

}
