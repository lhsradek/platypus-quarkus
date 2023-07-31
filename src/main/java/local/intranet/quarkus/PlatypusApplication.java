package local.intranet.quarkus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

import local.intranet.quarkus.api.controller.StatusController;

/**
 * 
 * {@link PlatypusApplication}
 * 
 * This class is used in {@link StatusController#getInfo()}
 * 
 * @author Radek Kádner
 */
@QuarkusMain
public class PlatypusApplication {

	/**
	 * 
	 * Main method runs Platypus Quarkus
	 * 
	 * @param args parameters
	 */
	public static void main(String... args) {
		Quarkus.run(args);
	}

}
