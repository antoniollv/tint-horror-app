package com.mapfre.tron.sfv.pgm.beans.impl.rkse.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RestUtils {

	static Logger logger = LoggerFactory.getLogger(RestUtils.class);

	@Value("${app.env.psypd.clientId}")
	private String envClientId;
	@Value("${app.env.psypd.clientSecret}")
	private String envClientSecret;
	@Value("${app.env.psypd.scope}")
	private String envScope;
	@Value("${app.env.psypd.tokenPath}")
	private String tokenPath;
	@Value("${app.env.psypd.timeout}")
	private String timeout;

	private final Pattern pattern = Pattern.compile("(?<=\"access_token\":\")([^\"]+)");

	RestTemplate restTemplate = new RestTemplate();

	public String getToken() {
		String rtToken = null;
		try {
			restTemplate.setRequestFactory(setTimeout());

			ResponseEntity<String> responseEntity = restTemplate.exchange(tokenPath, HttpMethod.POST, getHttpEntity(), String.class);

			Matcher matcher = pattern.matcher(responseEntity.getBody());
			if (matcher.find()) {
				rtToken = matcher.group(1);
				logger.info("Token OK");
			}else {
				logger.error("Token not found, return null");
			}

		} catch (Exception e) {
			logger.error("Token Error, return null");
		}
		return rtToken;
	}

	private HttpEntity<MultiValueMap<String, String>> getHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap <>();
		multiValueMap.add("client_id", envClientId);
		multiValueMap.add("client_secret", envClientSecret);
		multiValueMap.add("grant_type", "client_credentials");
		multiValueMap.add("scope", envScope);

		return new HttpEntity<>(multiValueMap, headers);
	}

	public SimpleClientHttpRequestFactory setTimeout() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setReadTimeout(Integer.parseInt(timeout));
		requestFactory.setConnectTimeout(Integer.parseInt(timeout));
		return requestFactory;
	}
}
