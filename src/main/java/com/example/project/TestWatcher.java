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

import static com.example.project.TestFactory.staticLog;

public class TestWatcher implements
        BeforeEachCallback,
        AfterEachCallback,
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        BeforeAllCallback,
        AfterAllCallback,
        TestInstancePostProcessor, TestExecutionExceptionHandler {

    private static final String EXTENT_REPORT = "htmlReport";

    private static ThreadLocal<Method> currentMethods = new ThreadLocal<>();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        //initialize "after all test run hook"
        context.getStore(ExtensionContext.Namespace.GLOBAL).put(EXTENT_REPORT, new CloseableOnlyOnceResource());
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

    private static class CloseableOnlyOnceResource implements ExtensionContext.Store.CloseableResource {
        private static ExtentReports extentReporter = ExtentManager.getExtent();

        @Override
        public void close() {
            //After all tests run hook.
            extentReporter.flush();
        }
    }
}
