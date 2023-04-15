package local.intranet.quarkus.api.domain.type;

/**
 * 
 * {@link StatusType} for
 * {@link local.intranet.quarkus.api.controller.InfoController} and
 * {@link local.intranet.quarkus.api.model.entity.Counter}
 * <p>
 * UP, DOWN, NONE
 * 
 * @author Radek Kádner
 */
public enum StatusType {

	/**
	 * 
	 * UP = "UP"
	 * <p>
	 * Service is ready
	 */
	UP("UP"),

	/**
	 * 
	 * DOWN = "DOWN"
	 * <p>
	 * Service isn't ready
	 */
	DOWN("DOWN"),

	/**
	 * 
	 * NONE = "NONE"
	 * <p>
	 * NONE is for non REST request mapping services
	 */
	NONE("NONE");

	private final String status;

	private StatusType(String status) {
		this.status = status;
	}

	/**
	 * 
	 * Method returns enum, based on value passed as parameter. Values are
	 * compared ignoring cas. If parameter value passed is null, null is
	 * returned. If value field of enum constant is null the name of enum is
	 * used for comparison, If no match is found null is returned.
	 * 
	 * @param value {@link String}
	 * @return {@link RoleType}
	 */
	public static StatusType fromValue(String value) {
		StatusType ret = null;
		if (value != null) {
			for (StatusType k : values()) {
				if (k.getStatus().equalsIgnoreCase(value)) {
					ret = k;
					break;
				}
			}
		}
		return ret;
	}	
	
	/**
	 * 
	 * Get status
	 * 
	 * @return UP or DOWN as String
	 */
	public String getStatus() {
		return (status == null) ? name() : status;
	}

}
