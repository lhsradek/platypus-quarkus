package local.intranet.quarkus.api.info.content;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.internal.reader.AuditReaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * {@link Provider} for
 * {@link local.intranet.quarkus.api.controller.IndexController}
 * 
 * @author Radek Kádner
 *
 */
@Dependent
public class Provider {

	private static final Logger LOG = LoggerFactory.getLogger(Provider.class);

	@Inject
	protected EntityManagerFactory entityManagerFactory;

	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * 
	 * Get queryProvider
	 * 
	 * @param params {@link List}&lt;{@link Map.Entry}&lt;{@link String},
	 *               {@link String}&gt;&gt;
	 * @return {@link String}
	 */
	public String queryProvider(List<Map.Entry<String, String>> params) {
		final StringBuilder query = new StringBuilder();
		final AtomicBoolean first = new AtomicBoolean(true);
		params.forEach(pair -> {
			if (first.get()) {
				query.append("?");
				query.append(pair.toString());
				first.set(false);
			} else {
				query.append("&");
				query.append(pair.toString());
			}
		});
		return query.toString();
	}

	/**
	 * 
	 * Get AuditReader
	 * 
	 * @return {@link AuditReader}
	 */
	public AuditReader auditReader() {
		AuditReader ret = AuditReaderFactory.get(entityManager);
		if (!((AuditReaderImpl) ret).getSession().isOpen()) {
			ret = AuditReaderFactory.get(entityManagerFactory.createEntityManager());
			LOG.warn("AuditReader create EntityManager!");
		}
		return ret;
	}

}