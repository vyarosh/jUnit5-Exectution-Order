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
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.Optional;

import static com.example.project.TestFactory.staticLog;

public class ExampleTestWatcher implements
        BeforeEachCallback,
        AfterEachCallback,
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        BeforeAllCallback,
        AfterAllCallback,
        TestInstancePostProcessor, TestExecutionExceptionHandler, TestWatcher  {

    private static final String HTML_REPORT = "htmlReport";

    private static ThreadLocal<Method> currentMethods = new ThreadLocal<>();

    @Override
    public void beforeAll(ExtensionContext context) {
        /* initialize "after all test run hook" NOTE!! executed once for each class... */
        staticLog("Creating Html report");
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put(HTML_REPORT, new CloseableOnlyOnceResource());
        staticLog("BeforeAll-Callback Hook");
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        staticLog("BeforeAll-Callback Hook");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        staticLog("BeforeTestExecution-Callback Hook");
        currentMethods.set(context.getRequiredTestMethod());
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        staticLog("Exception appeared Hook");
        throw throwable;
    }

    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable) {
        staticLog("test ABORTED hook");
    }

    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> optional) {
        staticLog("test DISABLED hook");
    }

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
        staticLog("test FAILED hook");
    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext) {
        staticLog("test PASSED hook");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        staticLog("AfterTestExecution-Callback Hook");
        currentMethods.remove();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        staticLog("AfterEach-Callback Hook");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        staticLog("AfterAll-Callback Hook");
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        staticLog("TestInstancePostProcessor Hook");
    }

    public static Method getTestMethod() {
        return currentMethods.get();
    }

    private static class CloseableOnlyOnceResource implements ExtensionContext.Store.CloseableResource {
        private static ExtentReports extentReporter = ExtentManager.getExtent();

        @Override
        public void close() {
            //After all tests run hook.
            staticLog("After all tests hook...");
            extentReporter.flush();
        }
    }
}
