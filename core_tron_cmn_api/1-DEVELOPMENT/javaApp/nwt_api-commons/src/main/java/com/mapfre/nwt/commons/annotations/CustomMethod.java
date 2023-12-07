package com.mapfre.nwt.commons.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated method is a "custom service".
 * Such classes are considered as candidates for custom-detection service
 * when using annotation-based configuration and classpath scanning.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 1 Sept 2023 - 11:27:17
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface CustomMethod {

    /** A list of the custom rest services nicknames.*/
    String[] services();

}
