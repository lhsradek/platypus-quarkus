package local.intranet.quarkus.api.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
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
@ApplicationScoped
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Inject
	protected UserRepository userRepository;
	
	/**
	 * 
	 * For {@link local.intranet.quarkus.api.controller.InfoController#getUserInfo}
	 * 
	 * Couldn't be better org.springframework.transaction.annotation.Transactional(readOnly = true) ?
	 * The transaction is due to lazy loading @ManyToMany(fetch = FetchType.LAZY)
	 * But it seems to work correctly even without a transaction
	 * 
	 * @param username {@link String}
	 * @return {@link UserInfo}
	 * @throws NotFoundException     {@link javax.ws.rs.core.Response.Status#NOT_FOUND}
	 * @throws UnauthorizedException {@link UnauthorizedException}
	 * @throws ForbiddenException    {@link ForbiddenException}
	 */
	// @Transactional
	@Operation(hidden = true)
	public UserInfo getUserInfo(@NotNull String username) throws NotFoundException, UnauthorizedException, ForbiddenException {
		final UserInfo ret = loadUserByUsername(username);
		LOG.debug("GetUserInfo username:{} password:{} enabled:{}", ret.getUsername(), ret.getPassword(), ret.isEnabled());
		return ret;
	}

	/**
	 * 
	 * Locates the user based on the username.
	 *
	 * Couldn't be better org.springframework.transaction.annotation.Transactional(readOnly = true) ?
	 * The transaction is due to lazy loading @ManyToMany(fetch = FetchType.LAZY)
	 * But it seems to work correctly even without a transaction
	 * 
	 * @param username the username identifying the user whose data is required.
	 * @return a fully populated user record (never <code>null</code>)
	 * @throws NotFoundException     {@link javax.ws.rs.core.Response.Status#NOT_FOUND}
	 * @throws UnauthorizedException {@link UnauthorizedException}
	 * @throws ForbiddenException    {@link ForbiddenException}
	 */
	// @Transactional
	@Operation(hidden = true)
	public UserInfo loadUserByUsername(@NotNull String username) throws NotFoundException, UnauthorizedException, ForbiddenException {
		if (username.length() == 0) {
			throw new ValidationException("Empty name!");
		}
		User user = userRepository.findByName(username);
		if (user == null) {
			throw new NotFoundException("User not found!");
		}
		if (user.isAccountNonExpired() && user.isAccountNonLocked() && user.isCredentialsNonExpired() && user.isEnabled()) {
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
