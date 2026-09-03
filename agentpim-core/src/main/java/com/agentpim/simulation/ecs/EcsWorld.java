package com.agentpim.simulation.ecs;

import com.agentpim.simulation.SimulationEntity;
import dev.dominion.ecs.api.Dominion;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class EcsWorld implements SimulationEntity, AutoCloseable {

    private final Dominion dominion;
    private final List<Runnable> systems = new ArrayList<>();
    private Duration lastTickDuration = Duration.ZERO;

    public EcsWorld(Dominion dominion) {
        this.dominion = dominion;
    }

    public static EcsWorld create(String name) {
        return new EcsWorld(Dominion.create(name));
    }

    public Dominion dominion() {
        return dominion;
    }

    public void addSystem(Runnable system) {
        systems.add(system);
    }

    public Duration lastTickDuration() {
        return lastTickDuration;
    }

    @Override
    public void tick(Duration tickDuration) {
        lastTickDuration = tickDuration;

        for (Runnable system : systems) {
            system.run();
        }
    }

    @Override
    public void close() {
        dominion.close();
    }
}
