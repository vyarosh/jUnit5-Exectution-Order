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

import org.apiguardian.api.API;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.lang.annotation.*;

import static org.apiguardian.api.API.Status.STABLE;



/**
 * {@code @Description} is used to declare a {@linkplain #value custom test
 * description} for the annotated test class or test method.
 *
 * <p>Display names are typically used for test reporting in IDEs and build
 * tools and may contain spaces, special characters, and even emoji.
 *
 * @since 5.0
 * @see Test
 * @see Tag
 * @see TestInfo
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = STABLE, since = "5.0")
public @interface TestDescription {

    /**
     * Custom display name for the annotated class or method.
     *
     * @return a custom display name; never blank or consisting solely of
     * whitespace
     */
    String value();

}
