package local.intranet.quarkus.api.info.content.template;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import java.util.Map;

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
         * For log in form
	 * 
	 * @param map {@link Map}&lt;{@link String}, {@link String}&gt;
	 * @return {@link TemplateInstance}
	 */
	public static native TemplateInstance login(Map<String, String> map);
}
