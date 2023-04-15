package local.intranet.quarkus.api.exception;

import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * {@link PlatypusException} for
 * {@link local.intranet.quarkus}
 * 
 * @author Radek Kádner
 *
 */
public class PlatypusException extends ConnectException {
	
	private static final long serialVersionUID = -8560956746588815416L;
	
	private static final Logger LOG = LoggerFactory.getLogger(PlatypusException.class);

	/**
	 * 
	 * Constructor with parameter
	 * 
	 * @param msg {@link String}
	 */
	public PlatypusException(String msg) {
		super(msg);
	}
	
	/**
	 * 
	 * Constructor with parameters
	 * 
	 * @param code of error scale
	 * @param msg {@link String}
	 */
	public PlatypusException(int code, String msg) {
		super(msg);
		LOG.error("code:{} msg:'{}'", code, msg);
	}

	/**
	 * 
	 * Constructor with parameter
	 * 
	 * @param msg {@link String}
	 * @param t   {@link Throwable}
	 */
	public PlatypusException(String msg, Throwable t) {
		super(msg);
		LOG.error(msg, t);
	}

}
