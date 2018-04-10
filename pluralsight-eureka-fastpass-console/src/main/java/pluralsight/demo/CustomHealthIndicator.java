package pluralsight.demo;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

	private int count = 0;

	@Override
	public Health health() {
		System.out.println("Health check is performed Count is " + count);
		if (count > 4 && count < 8) {
			count++;
			return Health.down().withDetail("0001", count).build();
		} else {
			count++;
			return Health.up().build();
		}
	}

}
