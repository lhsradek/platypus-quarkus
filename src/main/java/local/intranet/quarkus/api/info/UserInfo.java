package local.intranet.quarkus.api.info;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.smallrye.common.constraint.NotNull;
import local.intranet.quarkus.api.domain.DefaultFieldLengths;
import local.intranet.quarkus.api.model.entity.User;

/**
 * 
 * {@link UserInfo} for {@link local.intranet.quarkus.api.service.UserService}
 * 
 * @author Radek KádnerS
 *
 */
@JsonPropertyOrder({ "username", "password", "isAccountNonExpire", "isAccountNonLocked", "isCredentialsNonExpired",
		"isEnabled" })
public class UserInfo {

	private final String username;

	private final String password = "[PROTECTED]";

	private final boolean enabled;

	/**
	 * 
	 * Constructor with parameters
	 * 
	 * @param user {@link User}
	 */
	public UserInfo(@NotNull User user) {
		this.username = user.getUserName();
		this.enabled = true;
	}

	/**
	 * 
	 * Build {@link UserInfo}
	 * 
	 * @param user {@link User}
	 * @return {@link UserInfo}
	 */
	public static UserInfo build(@NotNull User user) {
		return new UserInfo(user);
	}

	/**
	 * 
	 * Get username
	 * 
	 * @return the username (never <code>null</code>)
	 */
	@Size(min = 1, max = DefaultFieldLengths.DEFAULT_NAME)
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * Get password
	 * 
	 * @return {@link String}
	 */
	@JsonIgnore
	@Size(min = 1, max = DefaultFieldLengths.DEFAULT_NAME)
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * Indicates whether the user's account has expired. An expired account cannot
	 * be authenticated.
	 *
	 * @return <code>true</code> if the user's account is valid (ie non-expired),
	 *         <code>false</code> if no longer valid (ie expired)
	 */
	public boolean isAccountNonExpired() {
		return enabled;
	}

	/**
	 * 
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is not locked, <code>false</code>
	 *         otherwise
	 */
	public boolean isAccountNonLocked() {
		return enabled;
	}

	/**
	 * 
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 *
	 * @return <code>true</code> if the user's credentials are valid (ie
	 *         non-expired), <code>false</code> if no longer valid (ie expired)
	 */
	public boolean isCredentialsNonExpired() {
		return enabled;
	}

	/**
	 * 
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is enabled, <code>false</code>
	 *         otherwise
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "UserInfo [username=" + username + ", password=[PROTECTED], enabled=" + enabled + "]";
	}

}
