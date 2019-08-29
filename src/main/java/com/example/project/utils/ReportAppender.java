/*
 * Lyons Consulting Group License
 * Copyright 2017 Lyons Consulting Group. All rights reserved.
 */

package com.example.project.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.example.project.ExampleTestWatcher;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Custom log4j appender implementation to use LOGGER.info|LOGGER.debug|LOGGER.warn methods
 * and automatically push desired event to ExtentReport.
 * Appender should be defined in log4j.properties or added by .addAppender() method.
 */
public class ReportAppender extends org.apache.log4j.AppenderSkeleton {

    @Override
    public void close() {
        // Do nothing. Added just per AppenderSkeleton demand
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    /**
     * Main Extent Reporter injection goes here.
     */
    @Override
    protected void append(LoggingEvent event) {
        ExtentTest testReporter = ExtentManager.getCurrentReporter();
        if (testReporter !=null) {
            switch (event.getLevel().toInt()) {
                case Level.FATAL_INT:
                    //Currently unused, use reporter.fatal() to push these events to report.
                    break;
                case Level.ERROR_INT:
                    testReporter.log(
                            Status.ERROR,
                            event.getMessage().toString(),
                            ExtentManager.buildScreenshotMedia(ExampleTestWatcher.getTestMethod().getName()));
                    break;
                case Level.WARN_INT:
                    testReporter.warning(event.getMessage().toString());
                    break;
                case Level.INFO_INT:
                    testReporter.info(event.getMessage().toString());
                    break;
                case Level.DEBUG_INT:
                    //Reserved for the Framework logs into the console only. Debug logs are not pushed to Report
                    break;
                case Level.TRACE_INT:
                    //currently unused and not configured
                    break;
                //no default actions... push only recognized events
            }
        }
    }
}
