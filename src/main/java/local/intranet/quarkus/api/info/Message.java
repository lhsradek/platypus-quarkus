package local.intranet.quarkus.api.info;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.smallrye.common.constraint.NotNull;
import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;

/**
 * 
 * {@link Message}
 * 
 * @author Radek Kádner
 * 
 */
@JsonPropertyOrder({ "content", "date" })
public class Message implements Invocationable, Nameable {

	/**
	 * 
	 * Constructor with param
	 * 
	 * @param content {@link String}
	 */
	public Message(@NotNull String content) {
		this.content = content;
	}

	/**
	 * 
	 * Message content
	 */
	@Size(min = 0)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public final String content;

	@Override
	public ZonedDateTime lastInvocation() {
		return LocalDateTime.now().atZone(ZoneId.systemDefault());
	}

	@Override
	@JsonIgnore
	public String getName() {
		return getClass().getName();
	}

}
