package io.robusta.tournament.common;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CircuitBreakerTest {

    @Test
    public void testResillience4J() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom().failureRateThreshold(20).slidingWindow(5, 5, CircuitBreakerConfig.SlidingWindowType.COUNT_BASED).build();
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(config);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("test-circuit-breaker");

        RemoteService service = Mockito.mock(RemoteService.class);
        Function<Integer, Integer> decorated = CircuitBreaker.decorateFunction(circuitBreaker, service::process);

        when(service.process(any(Integer.class))).thenThrow(new RuntimeException());
        for (int i = 0; i < 10; i++) {
            try {
                decorated.apply(i);
            } catch (Exception ignore) {}
        }
        verify(service, times(5)).process(any(Integer.class));
    }
}

interface RemoteService {
    int process(int i);
}
