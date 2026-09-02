package com.agentpim.simulation;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private final Duration tickDuration;
    private final List<SimulationEntity> entities = new ArrayList<>();
    private Duration elapsedTime = Duration.ZERO;

    public Simulation(Duration tickDuration) {
        if (tickDuration.isZero() || tickDuration.isNegative()) {
            throw new IllegalArgumentException("tickDuration must be positive: " + tickDuration);
        }

        this.tickDuration = tickDuration;
    }

    public void addEntity(SimulationEntity entity) {
        entities.add(entity);
    }

    public void tick() {
        for (SimulationEntity entity : entities) {
            entity.tick(tickDuration);
        }

        elapsedTime = elapsedTime.plus(tickDuration);
    }

    public Duration tickDuration() {
        return tickDuration;
    }

    public Duration elapsedTime() {
        return elapsedTime;
    }
}
