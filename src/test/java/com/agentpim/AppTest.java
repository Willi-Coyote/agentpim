package com.agentpim;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void mainRunsWithoutError() {
        assertThat(new App()).isNotNull();
    }
}
