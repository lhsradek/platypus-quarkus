package local.intranet.quarkus.api.info.content;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
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
 * https://how-to.vertx.io/hibernate-reactive-howto/
 * 
 * @author Radek Kádner
 *
 */
@Dependent
public class Provider {

	private static final Logger LOG = LoggerFactory.getLogger(Provider.class);

	/**
	 * 
	 * {@link SessionFactory}
	 * 
	 */
	@Inject
	public SessionFactory sessionFactory;

	/**
	 * 
	 * {@link EntityManagerFactory}
	 */
	@PersistenceContext
	public EntityManagerFactory entityManagerFactory;

	/**
	 * 
	 * {@link EntityManager}
	 */
	@PersistenceContext
	public EntityManager entityManager;

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
	 * Suitable for envers data history
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