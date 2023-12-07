import com.mapfre.arch.sscc.config.ConfigUtil;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URL;

        package com.mapfre;


public class AppEnvResourceBundle extends PropertiesConfiguration {
	
	public static final String BUNDLE_NAME = "test.application.env.properties"; 
	
	private static PropertiesConfiguration properties;
	
	public static PropertiesConfiguration getProperties() {
		if (properties == null) {
			synchronized (AppEnvResourceBundle.class) {
				if (properties == null) { 
					URL urlResourcePath = ConfigUtil.getInstance().getURL(AppEnvResourceBundle.class.getSimpleName(), BUNDLE_NAME);
					if (urlResourcePath != null) {
						try {
							properties = new PropertiesConfiguration(urlResourcePath);
						} catch (ConfigurationException e) {
							throw new IllegalStateException("Cannot find file: " + BUNDLE_NAME); 
						}
					} else {
						throw new IllegalStateException("Cannot find file: " + BUNDLE_NAME); 
					}
				}
			}
		}
		return properties;
	}
}