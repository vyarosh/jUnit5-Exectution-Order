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

/**
 * This class is used any time the Framework work needs to be interrupted
 * by serious error. Created to override some Exceptions and convert it to
 * <code>RuntimeExceptions</code> because I hate when method has throws, ex.:
 * <code>public void doSomething() throws EnyBlahBlahBlahException</code>
 *
 * @author <a href="mailto:ddmargarin@gmail.com">Vadym Yarosh</a>
 */
public class QaRuntimeException extends RuntimeException {

    /**
     * Create a new <code>QaRuntimeException</code> with no
     * detail message.
     */
    public QaRuntimeException() {
        super();
    }

    /**
     * Create a new <code>QaRuntimeException</code> with
     * the <code>String</code> specified as an error message.
     *
     * @param   message
     *          The error message for the exception.
     */
    public QaRuntimeException(String message) {
        super(message);
    }

    /**
     * Create a new <code>QaRuntimeException</code> with the
     * given <code>Exception</code> base cause and detail message.
     *
     * @param   message
     *          The detail message.
     * @param   cause
     *          The exception to be encapsulated in a
     *          QaRuntimeException
     */
    public QaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Create a new <code>QaRuntimeException</code> with a
     * given <code>Exception</code> base cause of the error.
     *
     * @param   cause
     *          The exception to be encapsulated in a
     *          QaRuntimeException.
     */
    public QaRuntimeException(Throwable cause) {
        super(cause);
    }
}
