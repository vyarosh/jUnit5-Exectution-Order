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

import org.junit.jupiter.api.extension.*;
import org.junit.platform.engine.TestExecutionResult;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;

import static com.example.project.TestFactory.staticLog;

public class TestWatcher implements
        BeforeEachCallback,
        AfterEachCallback,
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        BeforeAllCallback,
        AfterAllCallback,
        TestInstancePostProcessor,
        TestExecutionExceptionHandler {
    private static ThreadLocal<Method> currentMethods = new ThreadLocal<>();
    private static ThreadLocal<TestExecutionResult> currentResults = new ThreadLocal<>();

    private static final Logger LOGGER = Logger.getLogger(TestWatcher.class);

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        staticLog("BeforeAll-Callback Hook");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        staticLog("BeforeAll-Callback Hook");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        staticLog("BeforeTestExecution-Callback Hook");
        currentMethods.set(context.getRequiredTestMethod());
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        staticLog("Exception appeared Hook");
        throw throwable;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        staticLog("AfterTestExecution-Callback Hook");
        Boolean testResult = context.getExecutionException().isPresent();
        staticLog(testResult ? "FAILED" : "PASSED"); //false - SUCCESS, true - FAILED
        currentMethods.remove();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        staticLog("AfterEach-Callback Hook");
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        staticLog("AfterAll-Callback Hook");
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        staticLog("TestInstancePostProcessor Hook");
    }

    public static Method getTestMethod() {
        return currentMethods.get();
    }


}
