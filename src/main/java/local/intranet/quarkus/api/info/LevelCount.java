package local.intranet.quarkus.api.info;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import local.intranet.quarkus.api.domain.DefaultFieldLengths;

/**
 * 
 * {@link LevelCount} for
 * {@link local.intranet.quarkus.api.service.LoggingEventService}
 * <p>
 * https://www.baeldung.com/jpa-queries-custom-result-with-aggregation-functions
 *
 * @author Radek Kádner
 */
@JsonPropertyOrder({ "level", "total" })
public class LevelCount {

	@Size(min = 0, max = DefaultFieldLengths.DEFAULT_STATUS)
	private final String level;

	@Size(min = 0)
	private final Long total;

	/**
	 * 
	 * Constructor with parameters
	 * 
	 * @param level {@link String}
	 * @param total {@link Long}
	 */
	public LevelCount(String level, Long total) {
		super();
		this.level = level;
		this.total = total;
	}

	/**
	 * 
	 * Get level
	 * 
	 * @return the level
	 */
	@Size(min = 1, max = DefaultFieldLengths.DEFAULT_STATUS)
	public String getLevel() {
		return level;
	}

	/**
	 * 
	 * Get total
	 * 
	 * @return the total
	 */
	@Size(min = 0)
	public Long getTotal() {
		return total;
	}

	/**
	 * 
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "LevelCount [level=" + level + ", total=" + total + "]";
	}

}
