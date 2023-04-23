package local.intranet.quarkus.api.info;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.smallrye.common.constraint.NotNull;
import io.smallrye.common.constraint.Nullable;

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

	private final String level;

	private final Long total;

	/**
	 * 
	 * Constructor with parameters
	 * 
	 * @param level {@link String}
	 * @param total {@link Long}
	 */
	public LevelCount(@NotNull String level, @NotNull Long total) {
		this.level = level;
		this.total = total;
	}

	/**
	 * 
	 * Get level
	 * 
	 * @return the level
	 */
	// @Size(min = 1, max = DefaultFieldLengths.DEFAULT_STATUS)
	@Nullable
	public String getLevel() {
		return level;
	}

	/**
	 * 
	 * Get total
	 * 
	 * @return the total
	 */
	// @NotNull
	@Nullable
	// @Size(min = 0)
	// @Size(min = 0, max = DefaultFieldLengths.DEFAULT_STATUS)
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
