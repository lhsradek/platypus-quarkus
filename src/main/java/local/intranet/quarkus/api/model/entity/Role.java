package local.intranet.quarkus.api.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jetbrains.annotations.NotNull;

import local.intranet.quarkus.api.domain.DefaultFieldLengths;

/**
 * 
 * {@link Role} is entity for CRUD with
 * {@link local.intranet.quarkus.api.model.repository.RoleRepository}
 * <p>
 * https://quarkus.io/guides/getting-started-reactive
 * Mutiny - an intuitive and event-driven reactive programming library
 * 
 * @author Radek Kádner
 *
 */
@Entity
@Cacheable
@Table(name = "platypus_role")
public class Role {

	@Id
	@GeneratedValue
    @Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "role_name", nullable = false)
	@Size(max = DefaultFieldLengths.DEFAULT_NAME)
	private String roleName;

	@Column(name = "enabled")
	private Boolean enabled;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "platypus_user_role")
	private Set<User> user = new HashSet<>();

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
	 * Set id
	 * 
	 * @param id {@link Long}
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * Get roleName
	 * 
	 * @return {@link String}
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 
	 * Set roleName
	 * 
	 * @param roleName {@link String}
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	 * Get user
	 * 
	 * @return {@link Set}&lt;{@link User}&gt;
	 */
	public Set<User> getUser() {
		return user;
	}

	/**
	 * 
	 * Set user
	 * 
	 * @param user {@link Set}&lt;{@link User}&gt;
	 */
	public void setUser(Set<User> user) {
		this.user = user;
	}

	/**
	 * 
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", enabled=" + enabled + ", user=" + user + "]";
	}

}
