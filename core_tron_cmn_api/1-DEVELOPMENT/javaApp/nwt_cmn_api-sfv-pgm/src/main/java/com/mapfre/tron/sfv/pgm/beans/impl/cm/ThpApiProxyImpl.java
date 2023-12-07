package com.mapfre.tron.sfv.pgm.beans.impl.cm;

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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapfre.tron.api.thp.client.model.OThpPrsP;

@Component
public class ThpApiProxyImpl implements ThpApiProxy {
	
	private static final String LNG_VAL = "lngVal";
	private static final String USR_VAL = "usrVal";
	private static final String CMP_VAL = "cmpVal";
	private static final String ACV_VAL = "thpAcvVal";
	private static final String DCM_TYP = "thpDcmTypVal";
	private static final String DCM_VAL = "thpDcmVal";
	private static final String BASIC = "Basic ";
	@Value("${app.env.tron.api.thp.basePath:https://trnic.desa.mapfre.net/nwt_thp_api_be-web/newtron/api/thirdparty}")
    private String basePath;
    @Value("${app.env.tron.api.thp.userName:APITRON}")
    private String userName;
    @Value("${app.env.tron.api.thp.password:Mapfre2019}")
    private String password;
    private RestTemplate rest = new RestTemplate();
    
    public static class ModelThp {
    	@JsonProperty("oThpPrsP")
    	private OThpPrsP oThpPrsP;

		public OThpPrsP getoThpPrsP() {
			return oThpPrsP;
		}

		public void setoThpPrsP(OThpPrsP oThpPrsP) {
			this.oThpPrsP = oThpPrsP;
		}
    }

	@Override
	public OThpPrsP thirdPartybyActivity(Integer cmpVal, String thpDcmTypVal, String thpDcmVal, String usrVal, Integer thpAcvVal, String lngVal) {
	   	// en el momento del desarrollo el ThirdPartybyActivity no aparece en el API cliente
	   	HttpHeaders headers = new HttpHeaders();
		String auth = userName + ":" + password;
		String authHeader = BASIC + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.US_ASCII));
		headers.set(HttpHeaders.AUTHORIZATION, authHeader);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(LNG_VAL, lngVal);	
		HttpEntity<Void> request = new HttpEntity<>(headers);
		String url = UriComponentsBuilder.fromHttpUrl(basePath + "/ThirdPartybyActivity")
				.queryParam(CMP_VAL, cmpVal)
				.queryParam(USR_VAL, usrVal)
				.queryParam(ACV_VAL, thpAcvVal)
				.queryParam(DCM_TYP, thpDcmTypVal)
				.queryParam(DCM_VAL, thpDcmVal)
				.build(false)
				.toUriString();
			
		ResponseEntity<ModelThp> res = rest.exchange(url, HttpMethod.GET, request, ModelThp.class);		
		ModelThp  tmp = res.getBody();
		if (tmp != null) {
			return tmp.getoThpPrsP();
		}
		return null;
	}
}
