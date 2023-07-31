package local.intranet.quarkus.api.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.smallrye.common.constraint.NotNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.type.StatusType;

/**
 * 
 * {@link CounterInfo} for
 * {@link local.intranet.quarkus.api.service.CounterService#getCounterInfo}
 * 
 * @author Radek Kádner
 *
 */
@JsonPropertyOrder({ "id", "counterName", "count", "date", "status" })
public class CounterInfo {

	private final Long id;
	private final String counterName;
	private final Long count;
	private final ZonedDateTime date;
	private final StatusType status;

	/**
	 * 
	 * Constructor with parameters
	 * 
	 * @param id          {@link Long}
	 * @param counterName {@link String}
	 * @param count       {@link Long}
	 * @param timestmp    {@link Long} in milli
	 * @param status      {@link String}
	 */
	public CounterInfo(Long id, @NotNull String counterName, Long count, Long timestmp, String status) {
		this.id = id;
		this.counterName = counterName;
		this.count = count;
		this.date = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestmp), ZoneId.systemDefault());
		this.status = StatusType.valueOf(status);
	}

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
	public String getCounterName() {
		return counterName;
	}

	/**
	 * 
	 * Get id
	 * 
	 * @return {@link Long}
	 */
	// @Size(min = 0)
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * Number of invocations
	 *
	 * &#64;JsonProperty("count")
	 * 
	 * @return number of invocations as count in JSON
	 */
	// @Size(min = 0)
	public Long getCount() {
		return count;
	}

	/**
	 *
	 * Time of last invocation
	 * 
	 * &#64;JsonInclude(JsonInclude.Include.NON_DEFAULT)
	 * 
	 * @return lastInvocation
	 */
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonFormat(pattern = Invocationable.JSON_DATE_FORMAT, timezone = JsonFormat.DEFAULT_TIMEZONE)
	public ZonedDateTime getDate() {
		return date;
	}

	/**
	 * 
	 * Get status
	 * 
	 * @return {@link StatusType}
	 */
	public StatusType getStatus() {
		return status;
	}

}
