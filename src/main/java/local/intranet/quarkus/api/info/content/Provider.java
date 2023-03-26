package local.intranet.quarkus.api.info.content;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.internal.reader.AuditReaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * {@link Provider} for
 * {@link local.intranet.quarkus.api.controller.IndexController}
 * 
 * @author Radek Kádner
 *
 */
@Component
public class Provider {

	private static final Logger LOG = LoggerFactory.getLogger(Provider.class);

	@Autowired
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
		LOG.trace("{}", query.toString());
		return query.toString();
	}

	/**
	 * 
	 * Get {@link AuditReader}
	 * 
	 * @return {@link AuditReader}
	 */
	public AuditReader auditReader() {
		AuditReader ret = AuditReaderFactory.get(entityManager);
		if (!((AuditReaderImpl) ret).getSession().isOpen()) {
			ret = AuditReaderFactory.get(entityManagerFactory.createEntityManager());
			LOG.warn("create EntityManager!");
		}
		return ret;
	}

}