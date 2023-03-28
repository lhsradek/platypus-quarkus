package local.intranet.quarkus.api.info.content.template;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

/**
 * 
 * HTML Template for /downloads
 *
 */
@CheckedTemplate
public class DownloadTemplate {

	/**
	 * 
	 * @param files {@link List}&lt;{@link Map.Entry}&lt;{@link String}, {@link File}&gt;&gt;
	 * @return {@link TemplateInstance}
	 */
	public static native TemplateInstance files(List<Map.Entry<String, File>> files);
}
