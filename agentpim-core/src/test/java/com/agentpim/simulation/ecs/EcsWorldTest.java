package com.agentpim.simulation.ecs;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class EcsWorldTest {

    private EcsWorld ecsWorld;

    @AfterEach
    void closeEcsWorld() {
        ecsWorld.close();
    }

    @Test
    void givenNewEcsWorld_whenNoTicksHaveOccurred_thenLastTickDurationIsZero() {
        ecsWorld = EcsWorld.create("no-ticks");

        Duration lastTickDuration = ecsWorld.lastTickDuration();

        assertThat(lastTickDuration).isZero();
    }

    @Test
    void givenEcsWorld_whenTickIsCalled_thenLastTickDurationIsUpdated() {
        ecsWorld = EcsWorld.create("single-tick");
        Duration tickDuration = Duration.ofMillis(100);

        ecsWorld.tick(tickDuration);

        assertThat(ecsWorld.lastTickDuration()).isEqualTo(tickDuration);
    }

    @Test
    void givenRegisteredSystem_whenTickIsCalled_thenSystemRunsExactlyOnce() {
        ecsWorld = EcsWorld.create("single-system");
        AtomicInteger runCount = new AtomicInteger();
        ecsWorld.addSystem(runCount::incrementAndGet);

        ecsWorld.tick(Duration.ofMillis(50));

        assertThat(runCount).hasValue(1);
    }

    @Test
    void givenRegisteredSystem_whenTickIsCalledMultipleTimes_thenSystemRunsOncePerTick() {
        ecsWorld = EcsWorld.create("repeated-ticks");
        AtomicInteger runCount = new AtomicInteger();
        ecsWorld.addSystem(runCount::incrementAndGet);

        ecsWorld.tick(Duration.ofMillis(50));
        ecsWorld.tick(Duration.ofMillis(50));
        ecsWorld.tick(Duration.ofMillis(50));

        assertThat(runCount).hasValue(3);
    }

    @Test
    void givenMultipleRegisteredSystems_whenTickIsCalled_thenAllSystemsRunInRegistrationOrder() {
        ecsWorld = EcsWorld.create("ordered-systems");
        List<String> executionOrder = new ArrayList<>();
        ecsWorld.addSystem(() -> executionOrder.add("first"));
        ecsWorld.addSystem(() -> executionOrder.add("second"));

        ecsWorld.tick(Duration.ofMillis(50));

        assertThat(executionOrder).containsExactly("first", "second");
    }
}
