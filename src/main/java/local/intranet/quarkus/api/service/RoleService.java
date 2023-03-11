package local.intranet.quarkus.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.intranet.quarkus.api.info.RoleInfo;
import local.intranet.quarkus.api.info.RolePlain;
import local.intranet.quarkus.api.model.repository.RoleRepository;

/**
 * 
 * {@link RoleService} for
 * {@link local.intranet.quarkus.api.controller.InfoController#getRoleInfo}
 * 
 * @author Radek Kádner
 *
 */
@Path("/service/role")
public class RoleService {

	private static final Logger LOG = LoggerFactory.getLogger(RoleService.class);
	
	@Inject
	public RoleRepository roleRepository;

	@Transactional
	public RoleInfo getRoleInfo() {
		return new RoleInfo(getUsersRoles());
	}

	/**
	 * 
	 * Get userRole
	 * 
	 * @return {@link List}&lt;{@link RolePlain}&gt;
	 */
	@Transactional
	protected List<RolePlain> getUsersRoles() {
		final List<RolePlain> ret = new ArrayList<>();
		roleRepository.findAll().forEach(r -> {
			ret.add(new RolePlain(r.getId(), r.getRoleName(), r.isEnabled(),
					r.getUser().stream().map(u -> u.getUserName()).collect(Collectors.toList())));
		});
		LOG.debug("GetUserRoles ret:{}", ret);
		return ret;
	}

}
