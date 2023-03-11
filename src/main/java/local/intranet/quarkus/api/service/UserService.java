package local.intranet.quarkus.api.service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;

import local.intranet.quarkus.api.info.UserInfo;
import local.intranet.quarkus.api.model.entity.User;
import local.intranet.quarkus.api.model.repository.UserRepository;

/**
 * 
 * {@link UserService}
 * 
 * @author Radek Kádner
 *
 */
@Path("/service/user")
public class UserService {

	@Inject
	public UserRepository userRepository;
	
	/**
	 * 
	 * For {@link local.intranet.quarkus.api.controller.InfoController#getUserInfo}
	 * 
	 * @return {@link UserInfo}
	 */
	@Transactional
	public UserInfo getUserInfo(@NotNull String username) throws InternalError  {
		UserInfo ret = loadUserByUsername(username);
		return ret;
	}

	/**
	 * 
	 * Locates the user based on the username.
	 *
	 * @param username the username identifying the user whose data is required.
	 * @return a fully populated user record (never <code>null</code>)
	 */
	@Transactional
	public UserInfo loadUserByUsername(@NotNull String username) throws InternalError {
		if (username.length() == 0) {
			throw new InternalError("Empty name!");
		}
		User user = userRepository.findByName(username);
		if (user == null) {
			throw new InternalError("User not found!");
		}
		if (user.isAccountNonExpired() && user.isAccountNonLocked() && user.isCredentialsNonExpired() && user.isEnabled()) {
			return UserInfo.build(user);
		} else if (!user.isCredentialsNonExpired()) {
			throw new InternalError("Credentials is Expired!");
		} else if (!user.isAccountNonExpired()) {
			throw new InternalError("Account is Expired!");
		} else {
			throw new InternalError("Account is Disabled!");
		}
	}

}
