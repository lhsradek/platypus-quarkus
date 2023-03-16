package local.intranet.quarkus.api.domain;

import local.intranet.quarkus.api.domain.type.StatusType;

/**
 * 
 * {@link Statusable}
 * 
 * @author Radek Kádner
 *
 */
public interface Statusable {

	/**
	 * 
	 * Get status
	 * 
	 * @return {@link StatusType}
	 */
	public StatusType getStatus();

}
