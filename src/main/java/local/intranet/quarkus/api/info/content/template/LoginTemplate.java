package local.intranet.quarkus.api.info.content.template;

import java.util.Map;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

/**
 * 
 * HTML Template for /login
 *
 * @author Radek Kádner
 */
@CheckedTemplate
public class LoginTemplate {

	/**
	 * 
	 * @param map {@link Map}&lt;{@link String}, {@link String}&gt;
	 * @return {@link TemplateInstance}
	 */
	public static native TemplateInstance login(Map<String, String> map);
}
