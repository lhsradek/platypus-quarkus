package local.intranet.quarkus.api.exception;

import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * {@link PlatypusQuarkusException} for
 * {@link local.intranet.quarkus}
 * 
 * @author Radek Kádner
 *
 */
public class PlatypusQuarkusException extends ConnectException {
	
	private static final long serialVersionUID = -8560956746588815416L;
	
	private static final Logger LOG = LoggerFactory.getLogger(PlatypusQuarkusException.class);

	/**
	 * 
	 * Constructor with param
	 * 
	 * @param msg {@link String}
	 */
	public PlatypusQuarkusException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * Constructor with params
	 * 
	 * @param msg {@link String}
	 * @param t   {@link Throwable}
	 */
	public PlatypusQuarkusException(String msg, Throwable t) {
		super(msg);
		LOG.error(msg, t);
	}

}
