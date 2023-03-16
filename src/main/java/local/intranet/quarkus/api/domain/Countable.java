package local.intranet.quarkus.api.domain;

import javax.validation.constraints.Size;

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
	@Size(min = 0)
	@JsonProperty("count")
	public Long countValue();

}