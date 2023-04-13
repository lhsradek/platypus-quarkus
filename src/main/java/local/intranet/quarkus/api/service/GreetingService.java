package local.intranet.quarkus.api.service;

import java.text.MessageFormat;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.vertx.ConsumeEvent;
import local.intranet.quarkus.api.controller.VertxController;

/**
 * 
 * 
 * {@link VertxController}
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
    @ConsumeEvent("ahoj")              
    public String Ahoj(String name) {
    	final String ret;
    	final String ahoj = "Ahoj"; 
    	if (name == null) {
    		ret = ahoj;
    	} else {
    		ret = MessageFormat.format("{0} {1}", ahoj, name);
    	}
    	LOG.trace("{}", ret);
    	return ret;             
    }
    
	/**
	 * 
	 * @param name {@link String}
	 * @return {@link String}
	 */
    @ConsumeEvent("hello")              
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
