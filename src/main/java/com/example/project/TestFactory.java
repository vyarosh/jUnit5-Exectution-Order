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

import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Base test behaviour @before, @after, @rules method tor Test cases and log test results. Web driver initialization.
 */
@ExtendWith(TestWatcher.class)
public abstract class TestFactory {

    private static final Logger LOGGER = Logger.getLogger(TestFactory.class);
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
        LOGGER.debug("@BeforeEach: Starting test... " + testInfo.getTestMethod().get().getName());
    }

    @AfterEach
    public void finishTestCase(TestInfo testInfo) {
//        ExtentManager.createScreenShot(driver);
        LOGGER.debug("@AfterEach: Finishing test... " + testInfo.getTestMethod().get().getName());
//        switch (testInfo.getStatus()) {
//            case FAILURE:
//                ExtentManager.getCurrentReporter().get().log(
//                        Status.FAIL,
//                        result.getThrowable().toString(),
//                        ExtentManager.buildScreenshotMedia(result.getName()));
//                break;
//            case SKIP:
//                ExtentManager.getCurrentReporter().get().skip(result.getThrowable().toString());
//                break;
//            case SUCCESS:
//                ExtentManager.getCurrentReporter().get().info(
//                        "Last Screen of the test",
//                        ExtentManager.buildScreenshotMedia(result.getName()));
//                break;
//        }
    }


    void log(String msg) {
//        String currentClass = Thread.currentThread().getStackTrace()[2].getClassName().replace("test.", "");
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

}