package local.intranet.quarkus.api.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.openapi.annotations.Operation;

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

	@Inject
	protected UserRepository userRepository;
	
	/**
	 * 
	 * For {@link local.intranet.quarkus.api.controller.InfoController#getUserInfo}
	 * 
	 * She couldn't be better org.springframework.transaction.annotation.Transactional(readOnly = true) ?
	 * The transaction is due to lazy loading @ManyToMany(fetch = FetchType.LAZY)
	 * 
	 * @return {@link UserInfo}
	 */
	@Transactional
	@Operation(hidden = true)
	public UserInfo getUserInfo(@NotNull String username) throws InternalError  {
		UserInfo ret = loadUserByUsername(username);
		return ret;
	}

	/**
	 * 
	 * Locates the user based on the username.
	 *
	 * She couldn't be better org.springframework.transaction.annotation.Transactional(readOnly = true) ?
	 * The transaction is due to lazy loading @ManyToMany(fetch = FetchType.LAZY)
	 * 
	 * @param username the username identifying the user whose data is required.
	 * @return a fully populated user record (never <code>null</code>)
	 */
	@Transactional
	@Operation(hidden = true)
	public UserInfo loadUserByUsername(@NotNull String username) throws InternalError {
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
