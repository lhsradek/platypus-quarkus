package local.intranet.quarkus.api.util.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Startup;

import javax.enterprise.context.ApplicationScoped;

/**
 * 
 * Health startup
 * <p>
 * https://quarkus.io/guides/smallrye-health
 * https://github.com/quarkusio/quarkus-quickstarts/tree/main/microprofile-health-quickstart
 * 
 */
@Startup
@Liveness
@ApplicationScoped
public class StartupHealthCheck implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("Startup health check");
	}
}
