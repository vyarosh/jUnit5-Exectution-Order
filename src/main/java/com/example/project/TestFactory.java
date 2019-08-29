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

import com.aventstack.extentreports.ExtentReports;
import com.example.project.utils.ExtentManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;


/**
 * Base test behaviour @before, @after, @rules method tor Test cases and log test results. Web driver initialization.
 */
@ExtendWith(ExampleTestWatcher.class)
public abstract class TestFactory {

    static final Logger LOGGER = Logger.getLogger(TestFactory.class);
    TestInfo testInfo;

    @BeforeAll
    static void setUpAll() {
        staticLog("@BeforeAll");
    }

    @AfterAll
    static void tearDownAll() {
        staticLog("@AfterAll");
    }

    @BeforeEach
    public void setUp(TestInfo testInf) {
        testInfo = testInf;
        staticLog("@BeforeEach: Starting test... " + testInfo.getTestMethod().get().getName());
        addTestToReport(testInf);
    }

    /**
     * Adds test to {@link ExtentReports} with detected name and description.
     * Encapsulates "Magic" to extract testName and Description from @Test annotation
     * and handle possible absence as I failed to find easier way.
     *
     * @param   testInfo  {@code Method} current test instance
     */
    private static synchronized void addTestToReport(TestInfo testInfo) {
        if (testInfo.getDisplayName().isEmpty()) {
            ExtentManager.createTest(testInfo.getTestMethod().toString());
        } else {
            ExtentManager.createTest(testInfo.getDisplayName());
        }
    }

    @AfterEach
    public void finishTestCase(TestInfo testInfo) {
        LOGGER.debug("@AfterEach: Finishing test... " + testInfo.getTestMethod().get().getName());
    }


    void log(String msg) {
        String currentClass = this.toString().replace("test.", "");
        System.out.println(currentClass+ ":: \t\t" + msg);
    }

    static void staticLog(String msg) {
        String currentClass = Thread.currentThread().getStackTrace()[2].getClassName().replace("test.", "");
        System.out.println(msg + " \t\t<< " + currentClass);
    }

    void logTestName() {
        log("@Test " + testInfo.getTestMethod().get().getName() + "()");
    }

    void logSkipTest() {
        log("ERROR: If you read this: @Test " + testInfo.getTestMethod().get().getName() + "() was not skipped!");
    }

    String getFailMessage() {
        return "@Test " + testInfo.getTestMethod().get().getName() + "() - Fail";
    }

    void delay() {
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // just move on
        }
    }

}
