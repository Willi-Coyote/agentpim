package com.agentpim.simulation;

import java.time.Duration;

public interface SimulationEntity {

    void tick(Duration tickDuration);
}
