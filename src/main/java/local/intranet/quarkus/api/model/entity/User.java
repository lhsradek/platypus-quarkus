package local.intranet.quarkus.api.model.entity;

import io.smallrye.common.constraint.NotNull;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import local.intranet.quarkus.api.controller.StatusController;
import local.intranet.quarkus.api.domain.DefaultFieldLengths;

/**
 * 
 * {@link User} is entity for CRUD with
 * {@link local.intranet.quarkus.api.model.repository.UserRepository}
 * <p>
 * https://quarkus.io/guides/getting-started-reactive Mutiny - an intuitive and
 * event-driven reactive programming library
 * 
 * @author Radek Kádner
 *
 */
@Entity
@Table(name = "platypus_user")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "user_name", nullable = false)
	@Size(max = DefaultFieldLengths.DEFAULT_NAME)
	private String userName;

	@NotNull
	@Column(name = "password", nullable = false)
	@Size(max = DefaultFieldLengths.DEFAULT_NAME)
	private String password;

	@NotNull
	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;

	@NotNull
	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;

	@NotNull
	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@NotNull
	@Column(name = "enabled")
	private Boolean enabled;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "platypus_user_role")
	private Set<Role> role = new HashSet<>();

	/**
	 * 
	 * get id
	 * 
	 * @return {@link Long}
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * Set id
	 * 
	 * @param id {@link Long}
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * Get userName
	 * 
	 * @return {@link String}
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * Set userName
	 * 
	 * @param userName {@link String}
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * Get password
	 * 
	 * @return {@link String}
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * Set password
	 * 
	 * @param password {@link String}
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * Get accountNonExpired
	 * 
	 * @return {@link Boolean}
	 */
	public Boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * 
	 * Set accountNonExpired
	 * 
	 * @param accountNonExpired {@link Boolean}
	 */
	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * 
	 * Is accountNonLocked?
	 * 
	 * @return {@link Boolean}
	 */
	public Boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * 
	 * Set accountNonLocked
	 * 
	 * @param accountNonLocked {@link Boolean}
	 */
	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * 
	 * Is credentialsNonExpired?
	 * 
	 * @return {@link Boolean}
	 */
	public Boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * 
	 * Set credentialsNonExpired
	 * 
	 * @param credentialsNonExpired {@link Boolean}
	 */
	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * 
	 * Is enabled?
	 * 
	 * @return {@link Boolean}
	 */
	public Boolean isEnabled() {
		return enabled;
	}

	/**
	 * 
	 * Set enabled
	 * 
	 * @param enabled {@link Boolean}
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 
	 * Get role
	 * 
	 * @return {@link Set}&lt;{@link Role}&gt;
	 */
	public Set<Role> getRole() {
		return role;
	}

	/**
	 * 
	 * Set role
	 * 
	 * @param role {@link Set}&lt;{@link Role}&gt;
	 */
	public void setRole(Set<Role> role) {
		this.role = role;
	}

	/**
	 * 
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + StatusController.STATUS_PROTECTED
				+ ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + ", role=" + role + "]";
	}

}
