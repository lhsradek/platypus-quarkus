package local.intranet.quarkus.api.info;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import local.intranet.quarkus.api.domain.DefaultFieldLengths;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.service.RoleService;

/**
 * 
 * {@link RoleInfo} for
 * {@link local.intranet.quarkus.api.service.RoleService#getRoleInfo}
 * 
 * @author Radek Kádner
 *
 */
@JsonPropertyOrder({ "name", "name", "roles" })
public class RoleInfo implements Nameable {

	private final List<RolePlain> role;

	/**
	 * 
	 * Constructor with parameters
	 * 
	 * @param role {@link List}&lt;{@link RolePlain}&gt;
	 */
	public RoleInfo(List<RolePlain> role) {
		this.role = role;
	}

	@Size(min = 1, max = DefaultFieldLengths.DEFAULT_NAME)
	@Override
	public String getName() {
		String ret = RoleService.class.getSimpleName();
		return ret;
	}

	/**
	 *
	 * Get RoleInfo
	 * <p>
	 * &#64;JsonInclude(JsonInclude.Include.NON_NULL)
	 * 
	 * @return {@link List}&lt;{@link RolePlain}&gt;
	 */
	@Size(min = 0)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public List<RolePlain> getRoles() {
		List<RolePlain> ret = role;
		return ret;
	}

}
