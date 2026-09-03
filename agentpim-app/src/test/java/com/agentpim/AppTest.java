package com.agentpim;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void givenNoArguments_whenAppIsConstructed_thenInstanceIsNotNull() {
        App app = new App();

        assertThat(app).isNotNull();
    }
}
