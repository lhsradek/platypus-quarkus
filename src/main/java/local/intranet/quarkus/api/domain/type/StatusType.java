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
	 * Get status
	 * 
	 * @return UP or DOWN as String
	 */
	public String getStatus() {
		return status;
	}

}
