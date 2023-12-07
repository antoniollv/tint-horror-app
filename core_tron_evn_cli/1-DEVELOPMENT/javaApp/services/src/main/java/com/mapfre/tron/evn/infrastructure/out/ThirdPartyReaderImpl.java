package com.mapfre.tron.evn.infrastructure.out;

import com.mapfre.nwt.trn.thp.ipc.bo.OThpIpcPC;
import com.mapfre.tron.evn.dto.Environment;
import com.mapfre.tron.evn.dto.ThirdPartyReaderRequestDTO;
import com.mapfre.tron.evn.exceptions.ThirdPartyReaderException;
import com.mapfre.tron.evn.services.ThirdPartyReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class ThirdPartyReaderImpl implements ThirdPartyReader {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.env.tron.api.thp.endpointPath}")
    private String endpointPath;

    @Value("${app.env.tron.api.thp.origin.data.usrVal}")
    private String usrVal;

    @Value("${app.env.tron.api.thp.origin.data.lngVal}")
    private String lngVal;

    private Environment environment;

    @Override
    public OThpIpcPC read(ThirdPartyReaderRequestDTO requestDTO, Environment environment) throws ThirdPartyReaderException {

        this.environment = environment;
        log.debug("input: " + requestDTO);

        HttpEntity<String> request = createHttpEntityRequest(requestDTO);
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(environment.getBaseUrl() + endpointPath + "/insuredpartyV1")
                .queryParam("ThpDcmTypVal", requestDTO.getThpDcmTypVal())
                .queryParam("ThpDcmVal", requestDTO.getThpDcmVal())
                .encode()
                .toUriString();
        try {
            ResponseEntity<OThpIpcPC> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, request, OThpIpcPC.class);
            log.info("Object read successfully from environment " + environment.getName());
            return response.getBody();
        } catch (Exception e) {
            log.info("Object does not exist in environment " + environment.getName());
            throw new ThirdPartyReaderException("Error reading origin api", e);
        }
    }

    private HttpEntity<String> createHttpEntityRequest(ThirdPartyReaderRequestDTO thirdPartyReaderRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + getBase64Creds());
        headers.add("cmpVal", thirdPartyReaderRequest.getCmpVal().toString());
        headers.add("usrVal", usrVal);
        headers.add("lngVal", lngVal);
        return new HttpEntity<>(headers);
    }

    private String getBase64Creds() {
        String plainCreds = environment.getUserName().concat(":").concat(environment.getPassword());
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        return new String(base64CredsBytes);
    }
}
