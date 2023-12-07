/**
 * 
 */
package com.mapfre.tron.api.bo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * The Tronweb access profile annotation.
 *
 * @author pvraul1
 * @since 1.0.0
 * @version 31 jul. 2019 11:16:09
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Service
@Profile("tronweb-access")
public @interface TronWebAccess {

}
