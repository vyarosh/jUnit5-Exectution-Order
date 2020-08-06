/*
 * Copyright 2015-2020 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package com.example.project.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import ch.qos.logback.classic.Level;
import com.example.project.ExampleTestWatcher;

/**
 * Custom appender implementation to use LOGGER.info|LOGGER.debug|LOGGER.warn methods
 * and automatically push desired event to ExtentReport.
 * Appender should be defined in logback.xml or added by .addAppender() method.
 */
public class ReportAppender extends ch.qos.logback.core.AppenderBase<ILoggingEvent> {

    /**
     * Main Extent Reporter injection goes here.
     */
    @Override
    protected void append(ILoggingEvent eventObject) {
        ExtentTest testReporter = ExtentManager.getCurrentReporter();
        if (testReporter != null) {
            switch (eventObject.getLevel().toInt()) {
                case Level.ERROR_INT:
                    testReporter.log(
                            Status.ERROR,
                            prettyReport(eventObject.getFormattedMessage()),
                            ExtentManager.buildScreenshotMedia(ExampleTestWatcher.getTestMethod().getName()));
                    break;
                case Level.WARN_INT:
                    testReporter.warning(eventObject.getFormattedMessage());
                    break;
                case Level.INFO_INT:
                    testReporter.info(prettyReport(eventObject.getFormattedMessage()));
                    break;
                case Level.DEBUG_INT:
//                    Reserved for the Framework logs into the console only. Debug logs are not pushed to Report
                    break;
                case Level.TRACE_INT:
                    //currently unused and not configured
                    break;
                //no default actions... push only recognized events
            }
        }
    }

    /**
     * To place pretty printed Json objects into Extent report with the same format
     * Intended to reformat multiline stings to be placed in to the Html
     *
     * @param uglyString console string
     * @return {@code String} with updated indents and EOLs for html insertion
     */
    private String prettyReport(String uglyString) {
        String prettyString;
        /*imitation of tabulation and html escaping */
        prettyString = uglyString
                .replace("  ", "&nbsp;")
                .replace("&", "&amp;")
                .replace("<","&lt;")
                .replace(">", "&gt;");
        prettyString = prettyString.replace("\n", "<br>");
        return prettyString;
    }
}
