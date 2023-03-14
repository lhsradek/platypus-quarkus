package local.intranet.quarkus.api.domain;

/**
 *
 * {@link Measureable}
 *
 * <p>
 * <b>&#64;Counted</b> (io.micrometer.core.annotation.Counted) and<br/>
 * <b>&#64;Timed</b> (io.micrometer.core.annotation.Timed) is needed with <b>quarkus-smallrye-metrics</b>.
 * <p>
 * With <b>quarkus-micrometer-registry-prometheus</b> make themselves as <b>http_server_requests_....</b> 
 *  
 * @author Radek Kádner
 *
 */
public interface Measureable {

	/**
	 * 
	 * TIMED_DESCRIPTION = "A measure of how long it takes the method."
	 */
 	public static final String TIMED_DESCRIPTION = "A measure of how long it takes the method.";
	
 	/**
 	 * 
 	 * COUNTED_DESCRIPTION = "A measure of how many times is the method called."
 	 */
	public static final String COUNTED_DESCRIPTION = "A measure of how many times is the method called.";
}