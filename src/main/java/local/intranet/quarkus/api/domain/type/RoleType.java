package local.intranet.quarkus.api.domain.type;

import javax.annotation.security.RolesAllowed;

/**
 * 
 * {@link RoleType} for {@link RolesAllowed}
 * <p>
 * ANONYMOUS_ROLE, USER_ROLE, MANAGER_ROLE, ADMIN_ROLE
 * 
 * @author Radek Kádner
 */
public enum RoleType {

	/**
	 *
	 * ANONYMOUS_ROLE = "anonymousRole"
	 */
	ANONYMOUS_ROLE("anonymousRole"),

	/**
	 *
	 * USER_ROLE = "userRole"
	 */
	USER_ROLE("userRole"),

	/**
	 * 
	 * MANAGER_ROLE = "managerRole"
	 */
	MANAGER_ROLE("managerRole"),

	/**
	 * 
	 * ADMIN_ROLE = "adminRole"
	 */
	ADMIN_ROLE("adminRole");

	private final String role;

	private RoleType(String role) {
		this.role = role;
	}

	/**
	 * 
	 * Method returns enum, based on value passed as parameter. Values are compared
	 * ignoring cas. If parameter value passed is null, null is returned. If value
	 * field of enum constant is null the name of enum is used for comparison, If no
	 * match is found null is returned.
	 * 
	 * @param value {@link String}
	 * @return {@link RoleType}
	 */
	public static RoleType fromValue(String value) {
		RoleType ret = null;
		if (value != null) {
			for (RoleType k : values()) {
				if (k.getRole().equalsIgnoreCase(value)) {
					ret = k;
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * 
	 * Get role
	 * 
	 * @return userRole, managerRole or adminRole as String
	 */
	public String getRole() {
		return (role == null) ? name() : role;
	}

}
