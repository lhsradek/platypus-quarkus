package local.intranet.quarkus.api.domain;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * {@link Invocationable}
 * 
 * @author Radek Kádner
 *
 */
public interface Invocationable {

	/**
	 *
	 * Time of last invocation
	 * 
	 * &#64;JsonInclude(JsonInclude.Include.NON_DEFAULT)
	 * 
	 * @return lastInvocation
	 */
	@JsonProperty("date")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-Z", timezone = JsonFormat.DEFAULT_TIMEZONE)
	ZonedDateTime lastInvocation();

}
