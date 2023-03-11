package local.intranet.quarkus.api.info;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * {@link Message}
 * 
 * @author radek.kadner
 * 
 */
public class Message {

	@Size(min = 0)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public final String content;

	@JsonProperty("date")
	@JsonFormat(timezone = JsonFormat.DEFAULT_TIMEZONE)
	public final ZonedDateTime date = LocalDateTime.now().atZone(ZoneId.systemDefault());

	/**
	 * 
	 * Constructor with param
	 * 
	 * @param content {@link String}
	 */
	public Message(String content) {
		this.content = content;
	}

}
