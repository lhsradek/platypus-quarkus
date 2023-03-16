package local.intranet.quarkus.api.info;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import local.intranet.quarkus.api.domain.Countable;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;
import local.intranet.quarkus.api.domain.Statusable;
import local.intranet.quarkus.api.domain.type.StatusType;

/**
 * 
 * {@link CounterInfo} for
 * {@link local.intranet.quarkus.api.service.CounterService#getCounterInfo}
 * 
 * @author Radek Kádner
 *
 */
@JsonPropertyOrder({ "id", "name", "count", "date", "status" })
public class CounterInfo implements Invocationable, Nameable, Statusable, Countable {

	private final Long id;
	private final String counterName;
	private final Long cnt;
	private final ZonedDateTime date;
	private final StatusType status;

	/**
	 * 
	 * Constructor with parameters
	 * 
	 * @param id          {@link Long}
	 * @param counterName {@link String}
	 * @param cnt         {@link Long}
	 * @param timestmp    {@link Long} in milli
	 * @param status      {@link String}
	 */
	public CounterInfo(Long id, String counterName, Long cnt, Long timestmp, String status) {
		this.id = id;
		this.counterName = counterName;
		this.cnt = cnt;
		this.date = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestmp), ZoneId.systemDefault());
		this.status = StatusType.valueOf(status);
	}

	@Override
	// @JsonIgnore
	public String getName() {
		return counterName;
	}

	/**
	 * 
	 * Get id
	 * 
	 * @return {@link Long}
	 */
	@Size(min = 0)
	public Long getId() {
		return id;
	}

	@Override
	public Long countValue() {
		return cnt;
	}

	@Override
	public ZonedDateTime lastInvocation() {
		return date;
	}

	@Override
	public StatusType getStatus() {
		return status;
	}

	/**
	 * 
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "CounterInfo [id=" + id + ", counterName=" + counterName + ", cnt=" + cnt + ", date=" + date
				+ ", status=" + status + "]";
	}

}
