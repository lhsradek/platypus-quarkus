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
	 * DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 * JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss-Z"
	 */
	public static final String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss-Z";

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
	@JsonFormat(pattern = JSON_DATE_FORMAT, timezone = JsonFormat.DEFAULT_TIMEZONE)
	public ZonedDateTime lastInvocation();

}
