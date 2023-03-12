package local.intranet.quarkus.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * {@link Countable}
 *  
 * @author Radek Kádner
 *
 */
public interface Countable {

	/**
	 * 
	 * Number of invocations
	 *
	 * &#64;JsonProperty("count")
	 * 
	 * @return number of invocations as count in JSON
	 */
	@JsonProperty("count")
	public Long countValue();

}