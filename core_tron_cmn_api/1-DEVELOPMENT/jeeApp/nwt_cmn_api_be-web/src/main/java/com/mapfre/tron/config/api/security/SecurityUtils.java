package com.mapfre.tron.config.api.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * The proxy security rest template.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Feb 2023 - 16:02:02
 *
 */
public class SecurityUtils {

    /** Create a new instance of the class.*/
    private SecurityUtils() {
        super();
    }

    /**
     * Generate a REST TEMPLATE with proxy and timeout configuration.
     *
     * @param env
     * @return
     */
    public static RestTemplate getRestOperations(Environment env) {
        if (StringUtils.isNotEmpty(getProperty("security.oauth2.proxy.url", env))) {
            final int proxyPort = Integer.parseInt(getProperty("security.oauth2.proxy.port", env));
            final String proxyHost = getProperty("security.oauth2.proxy.url", env);

            final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            clientBuilder.useSystemProperties();
            clientBuilder.setProxy(new HttpHost(proxyHost, proxyPort));
            clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());

            if (StringUtils.isNotEmpty(getProperty("security.oauth2.proxy.usr", env))
                    && StringUtils.isNotEmpty(getProperty("security.oauth2.proxy.pass", env))) {
                final CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(new AuthScope(proxyHost, proxyPort), new UsernamePasswordCredentials(
                        getProperty("security.oauth2.proxy.usr", env), getProperty("security.oauth2.proxy.pass", env)));
                clientBuilder.setDefaultCredentialsProvider(credsProvider);
            }

            final CloseableHttpClient client = clientBuilder.build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(client);
            if (StringUtils.isNotEmpty(getProperty("security.oauth2.timeout", env))) {
                final int timeout = Integer.parseInt(getProperty("security.oauth2.timeout", env));
                requestFactory.setConnectTimeout(timeout);
                requestFactory.setReadTimeout(timeout);
                requestFactory.setConnectionRequestTimeout(timeout);
            }
            return new RestTemplate(requestFactory);
        } else {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            if (StringUtils.isNotEmpty(getProperty("security.oauth2.timeout", env))) {
                final int timeout = Integer.parseInt(getProperty("security.oauth2.timeout", env));
                requestFactory.setConnectTimeout(timeout);
                requestFactory.setReadTimeout(timeout);
            }
            return new RestTemplate(requestFactory);
        }
    }

    /**
     * Read property.
     *
     * @param prop property name without app.env
     * @param env spring environment
     * @return propery value
     */
    private static String getProperty(String prop, Environment env) {
        String appProperty = "app.env." + prop;
        String val = env.getProperty(appProperty);
        if (StringUtils.isNotEmpty(val) && !val.equals("{" + appProperty + "}")) {
            return val;
        }
        return "";
    }
}
