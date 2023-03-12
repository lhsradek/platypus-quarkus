package local.intranet.quarkus.api.health;

import java.time.Duration;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheckResponse;

import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;

/**
 * 
 * Health async
 * <p>
 * https://quarkus.io/guides/smallrye-health
 * https://github.com/quarkusio/quarkus-quickstarts/tree/main/microprofile-health-quickstart
 * 
 */
@ApplicationScoped
public class LivenessAsync implements AsyncHealthCheck {

    @Override
    public Uni<HealthCheckResponse> call() {
        return Uni.createFrom().item(HealthCheckResponse.up("liveness-reactive"))
                .onItem().delayIt().by(Duration.ofMillis(10));
    }
    
}
