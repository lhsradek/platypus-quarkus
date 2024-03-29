package local.intranet.quarkus.api.info.content.template;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 
 * HTML Template for /downloads
 *
 * @author Radek Kádner
 */
@CheckedTemplate
public class DownloadTemplate {

	/**
         *
         * For downloads file
	 * 
	 * @param files {@link List}&lt;{@link Map.Entry}&lt;{@link String},
	 *              {@link File}&gt;&gt;
	 * @param map   {@link Map}&lt;{@link String}, {@link String}&gt;
	 * @return {@link TemplateInstance}
	 */
	public static native TemplateInstance downloads(List<Map.Entry<String, File>> files, Map<String, String> map);
}
