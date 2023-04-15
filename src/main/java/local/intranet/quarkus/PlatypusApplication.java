package local.intranet.quarkus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * 
 * {@link PlatypusApplication}
 * 
 * @author Radek Kádner
 */
@QuarkusMain
public class PlatypusApplication {

	/**
	 * 
	 * Main method
	 * 
	 * @param args parameters
	 */
	public static void main(String... args) {
		Quarkus.run(args);
	}

}
