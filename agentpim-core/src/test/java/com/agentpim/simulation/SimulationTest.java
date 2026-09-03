package com.agentpim.simulation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import org.junit.jupiter.api.Test;

class SimulationTest {

    @Test
    void givenNewSimulation_whenNoTicksHaveOccurred_thenElapsedTimeIsZero() {
        Simulation simulation = new Simulation(Duration.ofMillis(100));

        Duration elapsedTime = simulation.elapsedTime();

        assertThat(elapsedTime).isZero();
    }

    @Test
    void givenTickDuration_whenTickIsCalledOnce_thenElapsedTimeEqualsTickDuration() {
        Duration tickDuration = Duration.ofMillis(100);
        Simulation simulation = new Simulation(tickDuration);

        simulation.tick();

        assertThat(simulation.elapsedTime()).isEqualTo(tickDuration);
    }

    @Test
    void givenTickDuration_whenTickIsCalledMultipleTimes_thenElapsedTimeAccumulates() {
        Duration tickDuration = Duration.ofMillis(100);
        Simulation simulation = new Simulation(tickDuration);

        simulation.tick();
        simulation.tick();
        simulation.tick();

        assertThat(simulation.elapsedTime()).isEqualTo(Duration.ofMillis(300));
    }

    @Test
    void givenRegisteredEntity_whenTickIsCalled_thenEntityReceivesTickDuration() {
        Duration tickDuration = Duration.ofMillis(250);
        Simulation simulation = new Simulation(tickDuration);
        RecordingSimulationEntity entity = new RecordingSimulationEntity();
        simulation.addEntity(entity);

        simulation.tick();

        assertThat(entity.receivedTickDurations()).containsExactly(tickDuration);
    }

    @Test
    void givenMultipleRegisteredEntities_whenTickIsCalled_thenAllEntitiesAreTicked() {
        Duration tickDuration = Duration.ofMillis(50);
        Simulation simulation = new Simulation(tickDuration);
        RecordingSimulationEntity firstEntity = new RecordingSimulationEntity();
        RecordingSimulationEntity secondEntity = new RecordingSimulationEntity();
        simulation.addEntity(firstEntity);
        simulation.addEntity(secondEntity);

        simulation.tick();
        simulation.tick();

        assertThat(firstEntity.receivedTickDurations()).containsExactly(tickDuration, tickDuration);
        assertThat(secondEntity.receivedTickDurations()).containsExactly(tickDuration, tickDuration);
    }

    @Test
    void givenZeroTickDuration_whenSimulationIsConstructed_thenThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> new Simulation(Duration.ZERO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenNegativeTickDuration_whenSimulationIsConstructed_thenThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> new Simulation(Duration.ofMillis(-1)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
