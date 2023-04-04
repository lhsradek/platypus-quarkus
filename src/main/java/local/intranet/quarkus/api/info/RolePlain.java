package local.intranet.quarkus.api.info;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.smallrye.common.constraint.NotNull;
import local.intranet.quarkus.api.domain.DefaultFieldLengths;
import local.intranet.quarkus.api.domain.Nameable;

/**
 * 
 * {@link RolePlain} for {@link local.intranet.quarkus.api.info.RoleInfo}
 * 
 * @author Radek Kádner
 *
 */
@JsonPropertyOrder({ "id", "roleName", "enabled", "users" })
public class RolePlain implements Nameable {

	@Size(min = 0)
	private final Long id;

	private final String roleName;

	private final Boolean enabled;

	@Size(min = 0)
	private final List<String> users;

	/**
	 * 
	 * Constructor with parameter
	 * 
	 * @param id       {@link Long}
	 * @param roleName {@link String}
	 * @param enabled  {@link Boolean}
	 * @param users    {@link List}&lt;{@link String}&gt;
	 */
	public RolePlain(@NotNull Long id, @NotNull String roleName, Boolean enabled, List<String> users) {
		this.id = id;
		this.roleName = roleName;
		this.users = users;
		this.enabled = enabled;
	}

	/**
	 * 
	 * Get id
	 * 
	 * @return {@link Long}
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * Get roleName
	 * 
	 * @return {@link String}
	 */
	@Size(min = 1, max = DefaultFieldLengths.DEFAULT_NAME)
	@JsonProperty("roleName")
	@Override
	public String getName() {
		return roleName;
	}

	/**
	 * 
	 * Is enabled?
	 * 
	 * @return {@link Boolean}
	 */
	@JsonProperty("enabled")
	public Boolean isEnabled() {
		return enabled;
	}

	/**
	 * 
	 * Get users
	 * 
	 * @return {@link List}&lt;{@link String}&gt;
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public List<String> getUsers() {
		return users;
	}

	/**
	 * 
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "RolePlain [id=" + id + ", roleName=" + roleName + ", enabled=" + enabled + ", users=" + users + "]";
	}

}
