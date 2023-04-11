package local.intranet.quarkus;

import java.util.Map;

import io.quarkus.test.junit.QuarkusTestProfile;

/**
 * 
 * 
 * https://quarkus.io/blog/quarkus-test-profiles/
 * https://quarkus.io/guides/getting-started-testing
 * https://stackoverflow.com/questions/69267442/can-i-override-quarkus-application-properties-value-in-my-test-class
 * 
 * @author Radek Kádner
 *
 */
public class CustomProfile implements QuarkusTestProfile {

	@Override
	public Map<String, String> getConfigOverrides() {
		return Map.of(
				"quarkus.http.insecure-requests", "enabled", // http enable
				"quarkus.flyway.migrate-at-start", "true"    // start flyway
		);
	}

}
