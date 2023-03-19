package local.intranet.quarkus.api.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import io.smallrye.common.constraint.NotNull;
import local.intranet.quarkus.api.domain.DefaultFieldLengths;

/**
 * 
 * {@link Counter} is entity for CRUD with
 * {@link local.intranet.quarkus.api.model.repository.CounterRepository}
 * 
 * {@link Audited} is for hibernate envers
 * 
 * @author Radek Kádner
 *
 */
@Entity
@Audited
@Table(name = "platypus_counter")
public class Counter {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "counter_name", nullable = false)
	@Size(min = 1, max = DefaultFieldLengths.DEFAULT_NAME)
	private String counterName;

	@Min(0)
	@NotNull
	@Column(name = "cnt")
	private Long cnt;

	@Min(0)
	@NotNull
	@Column(name = "timestmp")
	private Long timestmp;

	@NotNull
	@Column(name = "status", nullable = false)
	@Size(min = 1, max = DefaultFieldLengths.DEFAULT_STATUS)
	private String status;

	/**
	 * 
	 * Get id
	 * 
	 * @return {@link Long}
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * Set id
	 * 
	 * @param id {@link Long}
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * Get counterName
	 * 
	 * @return {@link String}
	 */
	public String getCounterName() {
		return counterName;
	}

	/**
	 * 
	 * Set counterName {@link String}
	 * 
	 * @param counterName {@link String}
	 */
	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	/**
	 * 
	 * Get count
	 * 
	 * @return {@link Long}
	 */
	public Long getCnt() {
		return cnt;
	}

	/**
	 * 
	 * Set count
	 * 
	 * @param cnt {@link Long}
	 */
	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

	/**
	 * 
	 * Get timestmp
	 * 
	 * @return {@link Long}
	 */
	public Long getTimestmp() {
		return timestmp;
	}

	/**
	 * 
	 * Set timestmp
	 * 
	 * @param timestmp {@link Long}
	 */
	public void setTimestmp(Long timestmp) {
		this.timestmp = timestmp;
	}

	/**
	 * 
	 * Get Status
	 * 
	 * @return {@link String}
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * Set Status
	 * 
	 * @param status {@link String}
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "Counter [id=" + id + ", counterName=" + counterName + ", cnt=" + cnt + ", timestmp=" + timestmp
				+ ", status=" + status + "]";
	}

}
