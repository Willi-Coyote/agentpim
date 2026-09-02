package com.agentpim.simulation.ecs;

import static org.assertj.core.api.Assertions.assertThat;

import com.agentpim.simulation.Simulation;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class EcsWorldSimulationIntegrationTest {

    private EcsWorld ecsWorld;

    @AfterEach
    void closeEcsWorld() {
        ecsWorld.close();
    }

    @Test
    void givenEcsWorldRegisteredWithSimulation_whenSimulationTicks_thenEcsWorldSystemsRunWithTheSimulationTickDuration() {
        Duration tickDuration = Duration.ofMillis(100);
        Simulation simulation = new Simulation(tickDuration);
        ecsWorld = EcsWorld.create("integration");
        AtomicInteger runCount = new AtomicInteger();
        ecsWorld.addSystem(runCount::incrementAndGet);
        simulation.addEntity(ecsWorld);

        simulation.tick();

        assertThat(runCount).hasValue(1);
        assertThat(ecsWorld.lastTickDuration()).isEqualTo(tickDuration);
    }
}
