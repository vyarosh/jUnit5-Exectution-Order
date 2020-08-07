/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2015, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */


import ch.qos.logback.classic.boolex.GEventEvaluator
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.filter.EvaluatorFilter
import com.example.project.utils.ReportAppender

import static ch.qos.logback.classic.Level.*
import static ch.qos.logback.core.spi.FilterReply.DENY
import static ch.qos.logback.core.spi.FilterReply.NEUTRAL


String levelExpression = 'e.level.toInt() < DEBUG.toInt()'
def logLevel = readLevelProperty()

static String readLevelProperty() {
    String PropertyKey = "logLevel"

    if (System.getProperty(PropertyKey) != null) {
        return System.getProperty(PropertyKey)
    }
    return "DEBUG"
}

if (logLevel != null && !logLevel.isEmpty()) {
    switch (logLevel.toUpperCase()) {
        case OFF.toString():
            levelExpression = 'e.level.toInt() < OFF.toInt()'
            break
        case ERROR.toString():
            levelExpression = 'e.level.toInt() < ERROR.toInt()'
            break
        case WARN.toString():
            levelExpression = 'e.level.toInt() < WARN.toInt()'
            break
        case "INFO":
            levelExpression = 'e.level.toInt() < INFO.toInt()'
            break
        case TRACE.toString():
            levelExpression = 'e.level.toInt() < TRACE.toInt()'
            break
        case ALL.toString():
            levelExpression = 'e.level.toInt() < ALL.toInt()'
            break
        default:
            levelExpression = 'e.level.toInt() < DEBUG.toInt()'
            break
    }
}

/**
 * Console gets only specified logs, by applied filter.
 * While 'Extent' appender gets all logs since it has own filter
 */
appender("console", ConsoleAppender) {
    filter(EvaluatorFilter) {
        evaluator(GEventEvaluator) {
            expression = levelExpression
        }
        onMismatch = NEUTRAL
        onMatch = DENY
    }
    encoder(PatternLayoutEncoder) {
        pattern = "[%-4p] %m%n"
    }
}
appender("Extent", ReportAppender)

logger("org.apache.http", WARN)
logger("freemarker.cache", WARN)
root(ALL, ["console", "Extent"])
