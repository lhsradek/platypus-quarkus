package local.intranet.quarkus.api.info.content.template;

import java.util.Map;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

/**
 * 
 * HTML Template for ROOT
 *
 * @author Radek Kádner
 */
@CheckedTemplate
public class IndexTemplate {

	/**
	 * 
         * For index.html
         *
	 * @param map {@link Map}&lt;{@link String}, {@link String}&gt;
	 * @return {@link TemplateInstance}
	 */
	public static native TemplateInstance index(Map<String, String> map);
}
