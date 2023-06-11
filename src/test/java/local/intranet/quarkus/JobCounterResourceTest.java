package local.intranet.quarkus;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.DisabledOnIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import local.intranet.quarkus.api.scheduler.PlatypusJob;

@QuarkusTest
@TestProfile(CustomProfile.class)
public class JobCounterResourceTest {

	/**
	 * 
	 * {@link PlatypusJob}
	 */
	@Inject
	protected PlatypusJob platypusJob;

	// @TestHTTPResource("/jobCounter")
	// private URL jobEndpoint;

	@Test
        @DisabledOnIntegrationTest
	void testPlatypusJob() {
		assertTrue(platypusJob.testJob());
	}

	@Test
        @DisabledOnIntegrationTest
	public void testJobStart() {
		assertTrue(platypusJob.startJob());
	}

}
