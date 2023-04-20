package local.intranet.quarkus.api.util.health;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

/**
 * 
 * Health simple
 * <p>
 * https://quarkus.io/guides/smallrye-health
 * https://github.com/quarkusio/quarkus-quickstarts/tree/main/microprofile-health-quickstart
 * 
 */
@Liveness
@ApplicationScoped
public class SimpleHealthCheck implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("Simple health check");
	}
}
