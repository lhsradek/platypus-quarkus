package local.intranet.quarkus.api.vertx;

import java.text.MessageFormat;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.vertx.ConsumeEvent;

/**
 * 
 * 
 * {@link VertxResource}
 * 
 * https://quarkus.io/guides/vertx
 *
 */
@ApplicationScoped                          
public class GreetingService {

	private static final Logger LOG = LoggerFactory.getLogger(GreetingService.class);
	
	/**
	 * 
	 * @param name {@link String}
	 * @return {@link String}
	 */
    @ConsumeEvent("greetings")              
    public String hello(String name) {
    	final String ret;
    	final String hello = "Hello"; 
    	if (name == null) {
    		ret = hello;
    	} else {
    		ret = MessageFormat.format("{0} {1}", hello, name);
    	}
    	LOG.trace("{}", ret);
        return ret;             
    }
}
