package local.intranet.quarkus.api.service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ValidationException;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.common.constraint.NotNull;
import local.intranet.quarkus.api.info.UserInfo;
import local.intranet.quarkus.api.model.entity.User;
import local.intranet.quarkus.api.model.repository.UserRepository;

/**
 * 
 * {@link UserService}
 * 
 * https://quarkus.pro/guides/spring-di.html
 * 
 * @author Radek Kádner
 *
 */
@Singleton
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Inject
	protected UserRepository userRepository;

	/**
	 * 
	 * For {@link local.intranet.quarkus.api.controller.InfoController#userInfo}
	 * 
	 * @param username {@link String}
	 * @return {@link UserInfo}
	 * @throws ValidationException   {@link ValidationException}
	 * @throws UnauthorizedException {@link UnauthorizedException}
	 * @throws ForbiddenException    {@link ForbiddenException}
	 */
	@Operation(hidden = true)
	public UserInfo getUserInfo(@NotNull String username)
			throws ValidationException, UnauthorizedException, ForbiddenException {
		final UserInfo ret = loadUserByUsername(username);
		LOG.trace("username:{} password:{} enabled:{}", ret.getUsername(), ret.getPassword(),
				ret.isEnabled());
		return ret;
	}

	/**
	 * 
	 * Locates the user based on the username.
	 *
	 * @param username the username identifying the user whose data is required.
	 * @return a fully populated user record (never <code>null</code>)
	 * @throws ValidationException   {@link ValidationException}
	 * @throws UnauthorizedException {@link UnauthorizedException}
	 * @throws ForbiddenException    {@link ForbiddenException}
	 */
	@Operation(hidden = true)
	public UserInfo loadUserByUsername(@NotNull String username)
			throws ValidationException, UnauthorizedException, ForbiddenException {
		if (username.length() == 0) {
			throw new ValidationException("Empty name!");
		}
		User user = userRepository.findByName(username);
		if (user == null) {
			throw new ValidationException("User not found!");
		}
		if (user.isAccountNonExpired() && user.isAccountNonLocked() && user.isCredentialsNonExpired()
				&& user.isEnabled()) {
			return UserInfo.build(user);
		} else if (!user.isCredentialsNonExpired()) {
			throw new UnauthorizedException("Credentials is Expired!");
		} else if (!user.isAccountNonExpired()) {
			throw new UnauthorizedException("Account is Expired!");
		} else {
			throw new ForbiddenException();
		}
	}

}
