package com.mapfre.tron.sfv.pgm.beans.impl.cm;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mapfre.tron.api.isu.client.model.InNewCompleteIssue1;
import com.mapfre.tron.api.isu.client.model.OPlyGniS;
import com.mapfre.tron.api.isu.client.model.OPlyPlyC;

@Component
public class PreviousToRealImpl implements PreviousToReal {
	
	private static final String LNG_VAL = "lngVal";
	private static final String USR_VAL = "usrVal";
	private static final String CMP_VAL = "cmpVal";
	private static final String BASIC = "Basic ";
	@Value("${app.env.tron.api.isu.basePath:https://trnic.desa.mapfre.net/nwt_isu_api_be-web/newtron/api/issue}")
    private String basePath;
    @Value("${app.env.tron.api.isu.userName:APITRON}")
    private String userName;
    @Value("${app.env.tron.api.isu.password:Mapfre2019}")
    private String password;
    private RestTemplate rest = new RestTemplate();

	@Override
	public void callPreviousToReal(OPlyPlyC oPlyPlyC, BigDecimal cmpVal, String usrVal, String lngVal) {
	   	// en el momento del desarrollo el previousToReal no aparece en el API cliente
	   	HttpHeaders headers = new HttpHeaders();
		String auth = userName + ":" + password;
		String authHeader = BASIC + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.US_ASCII));
		headers.set(HttpHeaders.AUTHORIZATION, authHeader);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(LNG_VAL, lngVal);	
		HttpEntity<OPlyPlyC> request = new HttpEntity<>(oPlyPlyC, headers);
		String url = UriComponentsBuilder.fromHttpUrl(basePath + "/business_line/policy/previousToReal")
				.queryParam(CMP_VAL, cmpVal)
				.queryParam(USR_VAL, usrVal)
				.build(false)
				.toUriString();
			
		rest.exchange(url, HttpMethod.POST, request, String.class);
	}

	@Override
	public OPlyGniS callQuotationQuotation2(InNewCompleteIssue1 o, BigDecimal cmpVal, String usrVal, String lngVal) {
		// en el momento del desarrollo el previousToReal no aparece en el API cliente
	   	HttpHeaders headers = new HttpHeaders();
		String auth = userName + ":" + password;
		String authHeader = BASIC + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.US_ASCII));
		headers.set(HttpHeaders.AUTHORIZATION, authHeader);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(LNG_VAL, lngVal);	
		headers.set(CMP_VAL, cmpVal.toPlainString());	
		headers.set(USR_VAL, usrVal);	
		HttpEntity<InNewCompleteIssue1> request = new HttpEntity<>(o, headers);
		String url = UriComponentsBuilder.fromHttpUrl(basePath + "/business_line/2.0/quotation/issue/quotation")
				.queryParam(CMP_VAL, cmpVal)
				.queryParam(USR_VAL, usrVal)
				.build(false)
				.toUriString();
		
		ResponseEntity<OPlyGniS> res = rest.exchange(url, HttpMethod.POST, request, OPlyGniS.class);
		return res.getBody();
	}

	@Override
	public OPlyGniS callPolicyQuotation2(InNewCompleteIssue1 o, BigDecimal cmpVal, String usrVal, String lngVal) {
		// en el momento del desarrollo el previousToReal no aparece en el API cliente
	   	HttpHeaders headers = new HttpHeaders();
		String auth = userName + ":" + password;
		String authHeader = BASIC + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.US_ASCII));
		headers.set(HttpHeaders.AUTHORIZATION, authHeader);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(LNG_VAL, lngVal);	
		headers.set(CMP_VAL, cmpVal.toPlainString());	
		headers.set(USR_VAL, usrVal);	
		HttpEntity<InNewCompleteIssue1> request = new HttpEntity<>(o, headers);
		String url = UriComponentsBuilder.fromHttpUrl(basePath + "/business_line/2.0/policy/issue/quotation")
				.queryParam(CMP_VAL, cmpVal)
				.queryParam(USR_VAL, usrVal)
				.build(false)
				.toUriString();
		
		ResponseEntity<OPlyGniS> res = rest.exchange(url, HttpMethod.POST, request, OPlyGniS.class);
		return res.getBody();
	}

}
