package local.intranet.quarkus.api.info.content.template;

import java.util.List;
import java.util.Map;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

/**
 * 
 * HTML Template for /properties
 *
 * @author Radek Kádner
 */
@CheckedTemplate
public class PropertiesTemplate {

	/**
	 * 
	 * @param map  {@link Map}&lt;{@link String}, {@link String}&gt;
	 * @param os   {@link List}&lt;{@link Map.Entry}&lt;{@link String},{@link String}&gt;&gt;
	 * @param list {@link List}&lt;{@link Map.Entry}&lt;{@link String},{@link String}&gt;&gt;
	 * @return {@link TemplateInstance}
	 */
	public static native TemplateInstance properties(
			Map<String, String> map, List<Map.Entry<String, String>> os, List<Map.Entry<String, String>> list);
}