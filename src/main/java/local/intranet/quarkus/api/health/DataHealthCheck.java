package local.intranet.quarkus.api.health;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

/**
 * 
 * Health data
 * <p>
 * https://quarkus.io/guides/smallrye-health
 * https://github.com/quarkusio/quarkus-quickstarts/tree/main/microprofile-health-quickstart
 * 
 */
@Liveness
@ApplicationScoped
public class DataHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("Health check with data")
                .up()
                .withData("foo", "fooValue")
                .withData("bar", "barValue")
                .build();
    }
}
