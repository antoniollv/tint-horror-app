package com.mapfre.tron.sfv.pgm.beans.impl.rkse.client;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.RiskSelectionRequest;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.RiskSelectionResponse;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.utils.RestUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RiskSelectionClient {
	
	@Autowired private RestUtils restUtils; 

	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${app.env.risksel.basePath}")
	private String envBasePath;


	public RiskSelectionResponse postRiskSelection(RiskSelectionRequest riskSelectionRequest) throws IOException {
		log.info("POST a select-risk:");

		ObjectMapper objectMapper = new ObjectMapper();
		
		RiskSelectionResponse riskSelectionResponse = null;
		String finalPath = envBasePath + "/api/1.00/select-risk";

		HttpEntity<?> request;
		try {
			String tokenOauth = restUtils.getToken();

			request = new HttpEntity<>(riskSelectionRequest, getHeaders(tokenOauth));
			restTemplate.setRequestFactory(restUtils.setTimeout());

			ResponseEntity<RiskSelectionResponse> respEntity = restTemplate.postForEntity(finalPath, request, RiskSelectionResponse.class);

			riskSelectionResponse = respEntity.getBody();
		} catch (HttpClientErrorException ex400) {
			log.error(ex400.getMessage());
			riskSelectionResponse = getRiskSelectionResponseFromClientErrorException(ex400, objectMapper);
		}

		return riskSelectionResponse;
	}

	// si la excepcion trae un body con la estructura de RiskSelectionResponse lo devuelve, si no, propaga la excepcion   
	private RiskSelectionResponse getRiskSelectionResponseFromClientErrorException(HttpClientErrorException pmEx, ObjectMapper pmObjectMapper) {
		RiskSelectionResponse riskSelectionResponse = null;
		// si tenemos body
		if(!pmEx.getResponseBodyAsString().isEmpty()) {
			// si el body cuadra con la clase de respuesta
			try {
				log.info(pmEx.getResponseBodyAsString());
				riskSelectionResponse = pmObjectMapper.readValue(pmEx.getResponseBodyAsString(), RiskSelectionResponse.class);
			} catch (Exception e) {
				throw pmEx;
			}
		}
		return riskSelectionResponse;
	}

	private HttpHeaders getHeaders(String oauthToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.ALL));
		headers.add("Content-Type",MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", "Bearer " + oauthToken);

		return headers;
	}
}
