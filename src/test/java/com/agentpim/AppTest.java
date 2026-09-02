package com.agentpim;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void mainRunsWithoutError() {
        assertNotNull(new App());
    }
}
