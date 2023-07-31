package local.intranet.quarkus.api.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.smallrye.common.constraint.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.constraints.Size;

import local.intranet.quarkus.api.domain.Invocationable;
import local.intranet.quarkus.api.domain.Nameable;

/**
 * 
 * {@link Message}
 * 
 * @author Radek Kádner
 * 
 */
@JsonPropertyOrder({ "name", "content", "date" })
public class Message implements Invocationable, Nameable {

	private final String content;

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
	 * 
	 * @return {link @String}
	 */
	@Size(min = 0)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getContent() {
		return content;
	}

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
