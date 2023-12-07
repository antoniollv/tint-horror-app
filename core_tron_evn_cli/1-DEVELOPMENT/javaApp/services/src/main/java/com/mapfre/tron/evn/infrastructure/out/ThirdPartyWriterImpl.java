package com.mapfre.tron.evn.infrastructure.out;

import com.mapfre.nwt.trn.thp.ipc.bo.OThpIpcC;
import com.mapfre.tron.evn.dto.Environment;
import com.mapfre.tron.evn.exceptions.ThirdPartyWriterException;
import com.mapfre.tron.evn.services.ThirdPartyWriter;
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
public class ThirdPartyWriterImpl implements ThirdPartyWriter {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.env.tron.api.thp.endpointPath}")
    private String endpointPath;

    @Value("${app.env.tron.api.thp.destination.data.usrVal}")
    private String usrVal;

    @Value("${app.env.tron.api.thp.destination.data.lngVal}")
    private String lngVal;

    private Environment environment;

    @Override
    public void write(OThpIpcC oThpIpcC, Environment environment) throws ThirdPartyWriterException {
        log.debug("input: " + oThpIpcC);
        this.environment = environment;
        try {
            HttpEntity<OThpIpcC> request = createHttpEntityRequest(oThpIpcC);
            String urlTemplate = UriComponentsBuilder.fromHttpUrl(environment.getBaseUrl() + endpointPath + "/insuredparty/process")
                   .queryParam("usrVal", usrVal)
                   .encode()
                   .toUriString();
            ResponseEntity<OThpIpcC> response = restTemplate.exchange(urlTemplate, HttpMethod.POST, request, OThpIpcC.class);
            log.info("Rest api write response: "+ response.getStatusCode());
        } catch (Exception e) {
            log.error("Exception writing to API", e);
            throw new ThirdPartyWriterException(e);
        }
    }

    private HttpEntity<OThpIpcC> createHttpEntityRequest(OThpIpcC oThpIpcC) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + getBase64Creds());
        headers.add("lngVal", lngVal);
        return new HttpEntity<>(oThpIpcC, headers);
    }

    private String getBase64Creds() {
        String plainCreds = environment.getUserName().concat(":").concat(environment.getPassword());
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        return new String(base64CredsBytes);
    }
}
