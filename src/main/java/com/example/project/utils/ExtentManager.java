/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package com.example.project.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.Loader;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

/**
 * Class is used to handle base Extent Reporter managing actions.
 */
public class ExtentManager {

    private static final Logger LOGGER = Logger.getLogger(ExtentManager.class);

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();
    private static ExtentHtmlReporter htmlReporter;
    private static ThreadLocal<File> screenshotFile = new ThreadLocal<>();
    private static final String SCREENSHOT_FORMAT = ".png";
    private static final String PROPS_FILE = ".properties";
    private static final String START_DIR_RELATIVE = "./";
    private static final String DIR_DELIMITER = "/";
    private static final String DEFAULT_BUILD_DIR = "target";
    private static final String REPORT_DIR_NAME = "test-report";
    private static final String SCREENSHOTS_DIR_NAME = "screenshots";
    private static final String REPORT_FILE_NAME = "extent-report.html";
    private static final String EXTENT_CONFIG_FILE_NAME = "extent-config.xml";

    private static final String TEST_REPORT_DIR = DEFAULT_BUILD_DIR + DIR_DELIMITER + REPORT_DIR_NAME;
    private static final String TEST_SCREENSHOTS_DIR = TEST_REPORT_DIR + DIR_DELIMITER + SCREENSHOTS_DIR_NAME;
    private static final String REPORT_SCREENSHOTS_DIR = START_DIR_RELATIVE + SCREENSHOTS_DIR_NAME;



    private ExtentManager() {
        throw new IllegalStateException("Utility class");
    }

    //TODO: add LOGGER.debug() to all important events

    /**
     * Non-parametrized constructor. Creates {@link ExtentReports} configured instance
     * with {@link ExtentHtmlReporter} attached.
     *
     * @return {@link ExtentReports} instance
     */
    public static synchronized ExtentReports getExtent(){
        if (extent != null)
            return extent; //avoid creating new instance of html file
        extent = new ExtentReports();
        extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
        //TODO: implement runner system detection and get values from {@link SessionConfig
        extent.setSystemInfo("os", "Solaris");
        extent.attachReporter(getHtmlReporter());
        return extent;
    }

    /**
     *  Creates Configured {@link ExtentHtmlReporter}.
     *  Additionally creates directories tree as ExtentReport does not.
     *
     * @return {@link ExtentHtmlReporter} instance
     */
    private static synchronized ExtentHtmlReporter getHtmlReporter() {
        createDirectories(TEST_REPORT_DIR);
        String filePath = TEST_REPORT_DIR + DIR_DELIMITER + REPORT_FILE_NAME;
        htmlReporter = new ExtentHtmlReporter(filePath);
        setupHtmlReporter();
        return htmlReporter;
    }

    /**
     * Creates folders tree if it doesn't exist. Is used to encapsulate exception.
     *
     * @param   directoryPath
     *          String absolute|relative directory to create, must not be {@code null}
     *
     * @throws  QaRuntimeException
     *          Encapsulated {@link IOException}. If an I/O error occurred
     *
     * @throws NullPointerException
     *         if the directory is {@code null}
     */
    private static void createDirectories(String directoryPath) {
        File targetDir = new File(directoryPath);
        try {
            FileUtils.forceMkdir(targetDir);
        } catch (IOException e) {
            throw new QaRuntimeException("Failed to create folder tree: " + directoryPath, e);
        }
    }

    /**
     * Holds all Extent HTML Report configuration.
     */
    private static void setupHtmlReporter() {
        //TODO: investigate this really fix the spaces in project folders.
        // Otherwise try: 1) .properties file; 2) move all from xml to .confg() calls as commented lower
        try {
            htmlReporter.loadXMLConfig(URLDecoder.decode(Loader.getResource(EXTENT_CONFIG_FILE_NAME).getFile(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new QaRuntimeException("Failed to decode "+EXTENT_CONFIG_FILE_NAME + "form resource folder ", e);
        }
    }

    /**
     * Adds an empty test into the Extent Report with given name and description.
     * Creates {@link ExtentTest} object to fill in with test details.
     *
     * @param name
     *        {@code String} name for the Test to add into Report
     *
     * @param description
     *        {@code String} Description the Test to add into Report
     *
     */
    public static void createTest(String name, String description){
        extentTest.set(extent.createTest(name, description));
    }

    /**
     * Adds an empty test into the Extent Report with given name.
     * Creates {@link ExtentTest} object to fill in with test details.
     *
     * @param name
     *        {@code String} name for the Test to add into Report
     *
     */
    public static void createTest(String name){
        extentTest.set(extent.createTest(name));
    }

    /**
     * Adds "Step"  with given name into the existing {@link #extentTest} Test and switches reporter to created step.
     *
     * @param name
     *        {@code String} Name of test step
     *
     */
    public static void createStep(String name){
        testNode.set(extentTest.get().createNode(name));
    }

    /**
     * Selects latest actual reporter and returns it to handle reporters switching inside {@link ExtentManager} class.
     *
     * @return Step {@link #testNode} Reporter or Test {@link #extentTest} Reporter instance
     */
    public static ExtentTest getCurrentReporter() {
        if (testNode.get() != null) {
            return testNode.get();
        } else {
            return extentTest.get();
        }
    }

    /**
     * Makes Screenshot from specified browser and saves it to {@link #screenshotFile}
     *
     * @param currentBrowser
     *        <code>WebDriver</code> instance which should make a screenshot
     */
//    public static void createScreenShot(ThreadLocal<WebDriver> currentBrowser){
//        if (currentBrowser.get() != null){
//            LOGGER.debug("Taking screenshot...");
//            screenshotFile.set(((TakesScreenshot) currentBrowser.get()).getScreenshotAs(OutputType.FILE));
//        } else {
//            LOGGER.debug("ERROR: Trying to capture a screenshot while no Browser(WebDriver) is opened");
//        }
//    }

    /**
     * Is used to make Screenshots file names unique.
     *
     * @param fileName
     *        Base name to randomize. Usually testMethod name.
     *
     * @return Randomized string by appended {@link UUID}
     */
    private static String randomizeFileName(String fileName) {
        return fileName + UUID.randomUUID().toString();
    }

    /**
     * Used to append prepared screenshot ({@link MediaEntityModelProvider}) into report log entry.
     * Handles absence of {@link #screenshotFile} and performs all file operations.
     * Additionally swallows any {@link IOException} occurred, to keep the Framework running.
     *
     * @param screenshotFileName
     *        String with desired Screenshot Name. Usually testMethod name.
     *
     * @return {@link MediaEntityModelProvider} object
     *         ready to put into reporter {@link ExtentTest#log(Status, String, MediaEntityModelProvider)};
     *         <code>null</code> if ({@link #screenshotFile} is empty, or {@link IOException} occurred.
     */
    public static synchronized MediaEntityModelProvider buildScreenshotMedia(String screenshotFileName) {
        if (screenshotFile.get() == null) {
            LOGGER.debug("ERROR: Trying to save empty screenshot... please createScreenShot() before saving.");
            return null;
        }
        String fileName = randomizeFileName(screenshotFileName) + SCREENSHOT_FORMAT;
        File targetFile = new File(TEST_SCREENSHOTS_DIR + DIR_DELIMITER + fileName);
        try {
            FileUtils.copyFile(screenshotFile.get(), targetFile);
            return MediaEntityBuilder.createScreenCaptureFromPath(REPORT_SCREENSHOTS_DIR + DIR_DELIMITER + fileName).build();
        } catch (IOException e) {
            //Show must go on! But without a screenshot though...
            LOGGER.debug("Failed to save screenshot to file: " + targetFile, e);
            return null;
        }
    }
}
