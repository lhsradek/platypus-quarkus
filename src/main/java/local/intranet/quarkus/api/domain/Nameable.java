package local.intranet.quarkus.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * 
 * {@link Nameable}
 * 
 * @author Radek Kádner
 *
 */
public interface Nameable {

	/**
	 * 
	 * Get name
	 * 
	 * <p>
	 * &#64;JsonInclude(JsonInclude.Include.NON_NULL)
	 * 
	 * @return name {@link String}
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String getName();

}
