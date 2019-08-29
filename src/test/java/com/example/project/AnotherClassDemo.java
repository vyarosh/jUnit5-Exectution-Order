/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package com.example.project;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("fast")
@Execution(ExecutionMode.CONCURRENT)
class AnotherClassDemo extends TestFactory {

    @Test
    @DisplayName("My 1st JUnit 5 test! 😎")
    void anotherTest1(TestInfo testInfo) {
        LOGGER.info("test!");
        logTestName();
        delay();
        assertEquals("My 1st JUnit 5 test! 😎", testInfo.getDisplayName(), () -> "TestInfo is injected correctly");
    }
 
    @Test
    void anotherTest2() {
        logTestName();
        delay();
        assumeTrue(2 > 3);
    }

    @Test
    void anotherTest3() {
        delay();
        fail(getFailMessage());
    }

    @Disabled("Ignored message for demo purposes")
    @Test
    void anotherTest4() {
        logSkipTest();
        delay();
    }

}
