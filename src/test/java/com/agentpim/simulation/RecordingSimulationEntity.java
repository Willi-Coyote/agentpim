package com.agentpim.simulation;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

class RecordingSimulationEntity implements SimulationEntity {

    private final List<Duration> receivedTickDurations = new ArrayList<>();

    @Override
    public void tick(Duration tickDuration) {
        receivedTickDurations.add(tickDuration);
    }

    List<Duration> receivedTickDurations() {
        return receivedTickDurations;
    }
}
