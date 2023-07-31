package local.intranet.quarkus.api.service;

import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.common.constraint.NotNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import local.intranet.quarkus.api.info.UserInfo;
import local.intranet.quarkus.api.model.entity.User;
import local.intranet.quarkus.api.model.repository.UserRepository;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * {@link UserService} for
 * {@link local.intranet.quarkus.api.controller.InfoController#userInfo()}
 * <p>
 * https://quarkus.pro/guides/spring-di.html
 * 
 * @author Radek Kádner
 *
 */
@ApplicationScoped
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	/**
	 * 
	 * {@link UserRepository} for {@link #loadUserByUsername}
	 */
	@Inject
	protected UserRepository userRepository;

	/**
	 * 
	 * For {@link local.intranet.quarkus.api.controller.InfoController#userInfo}
	 * 
	 * @param username {@link String}
	 * @return {@link UserInfo}
	 * @throws IllegalArgumentException {@link IllegalArgumentException}
	 * @throws UnauthorizedException    {@link UnauthorizedException}
	 * @throws ForbiddenException       {@link ForbiddenException}
	 */
	@Operation(hidden = true)
	public UserInfo getUserInfo(@NotNull String username)
			throws IllegalArgumentException, UnauthorizedException, ForbiddenException {
		final UserInfo ret = new UserInfo(loadUserByUsername(username).getUsername());
		LOG.trace("username:{} password:{} enabled:{}", ret.getUsername(), ret.getPassword(), ret.isEnabled());
		return ret;
	}

	/**
	 * 
	 * Locates the user based on the username.
	 *
	 * @param username the username identifying the user whose data is required.
	 * @return a fully populated user record (never <code>null</code>)
	 * @throws IllegalArgumentException {@link IllegalArgumentException}
	 * @throws UnauthorizedException    {@link UnauthorizedException}
	 * @throws ForbiddenException       {@link ForbiddenException}
	 */
	@Operation(hidden = true)
	public UserInfo loadUserByUsername(@NotNull String username)
			throws IllegalArgumentException, UnauthorizedException, ForbiddenException {
		if (username.length() == 0) {
			throw new ForbiddenException();
		}
		final Optional<User> user = userRepository.findByName(username);
		if (user.isEmpty()) {
			throw new IllegalArgumentException("Username not found!");
		}
		if (user.get().isAccountNonExpired() && user.get().isAccountNonLocked() && user.get().isCredentialsNonExpired()
				&& user.get().isEnabled()) {
			return UserInfo.build(user.get());
		} else if (!user.get().isCredentialsNonExpired()) {
			throw new UnauthorizedException("Credentials is Expired!");
		} else if (!user.get().isAccountNonExpired()) {
			throw new UnauthorizedException("Account is Expired!");
		} else {
			throw new ForbiddenException();
		}
	}

}
