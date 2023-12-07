package com.mapfre.tron.api.sfcm.dl.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.tron.api.cmn.bo.RequestSFMCEvent;
import com.mapfre.tron.api.cmn.bo.RequestSFMCToken;
import com.mapfre.tron.api.cmn.bo.ResponseSFMCEvent;
import com.mapfre.tron.api.cmn.bo.ResponseSFMCToken;
import com.mapfre.tron.api.sfcm.dl.IDlSfcm;

import lombok.extern.slf4j.Slf4j;

/**
 * The DL SFCM token rest client implementation.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 25 may. 2021 - 13:52:12
 *
 */
@Slf4j
@Component
public class DlSfcmImpl implements IDlSfcm {

    protected static final String CLIENT_CREDENTIALS = "client_credentials";

    /** The grant type constant.*/
    protected static final String GRANT_TYPE = CLIENT_CREDENTIALS;

    /** The clientId token.*/
    @Value("${app.env.clientIdSFMCtoken}")
    protected String clientId;

    /** The client secret token.*/
    @Value("${app.env.clientSecretSFMCtoken}")
    protected String clientSecret;

    /** The uri to call SFMC Token rest service.*/
    @Value("${app.env.urlSFMCtoken}")
    protected String tokenUri;

    /** The uri to call SFMC Event rest service.*/
    @Value("${app.env.urlSFMCevent}")
    protected String eventUri;

    @Value("${app.env.usernameSFTPSFM}")
    protected String username;

    @Value("${app.env.passwordSFTPSFM}")
    protected String password;

    @Value("${app.env.remoteHostSFTPSCM}")
    protected String remoteHost;

    @Value("${app.env.proxy.socksProxyHost}")
    protected String socksProxyHost;

    @Value("${app.env.proxy.socksProxyPort}")
    protected Integer socksProxyPort;

    @Value("${app.env.proxy.username}")
    protected String socksUsername;

    @Value("${app.env.proxy.password}")
    protected String socksPassword;

    /**
     * Get the Bearer token for SFMC services.
     *
     * @return -> The SFMCToken response
     */
    @Override
    public ResponseSFMCToken getToken() {
        ResponseSFMCToken response = null;

        RestTemplate restTemplate = new RestTemplate();

        RequestSFMCToken request =  RequestSFMCToken.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type(GRANT_TYPE)
                .build();

        try {
            response = restTemplate.postForObject(tokenUri, request, ResponseSFMCToken.class);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new NwtException("Error calling token rest service (SFCM): " + e.getMessage());
        }

        return response;
    }

    /**
     * Launch the event.
     *
     * @param request     -> The event request
     * @param accessToken -> The bearer access token
     * @return            -> The event response
     */
    @Override
    public ResponseSFMCEvent launchEvent(final RequestSFMCEvent request, final String accessToken) {
        ResponseSFMCEvent response = null;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+ accessToken);

        HttpEntity<RequestSFMCEvent> entity = new HttpEntity<>(request, headers);
        
        try {
            response = restTemplate.postForObject(eventUri, entity, ResponseSFMCEvent.class);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new NwtException("Error calling launch event rest service (SFCM): " + e.getMessage());
        }

        return response;
    }

    /**
     * Launch the event.
     *
     * @param request     -> The string json event request
     * @param accessToken -> The bearer access token
     * @return            -> The event instance identifier
     */
    @Override
    public String launchEvent(final String jsonRequestSFMCEvent, final String accessToken) {
        String identifier = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            RequestSFMCEvent eventRequest = objectMapper.readValue(jsonRequestSFMCEvent, RequestSFMCEvent.class);
            ResponseSFMCEvent response = this.launchEvent(eventRequest, accessToken);

            if (response != null) {
                identifier = response.getEventInstanceId();
            }

        } catch (JsonParseException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error parsing event request json: " + e.getMessage());
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error mapping event request json: " + e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException("IO Error: " + e.getMessage());
        }

        return identifier;
    }

    /**
     * Upload a file using SFTP to SFCM.
     *
     * @param inputStream -> the file input stream
     * @param filename    -> The completed filename
     * @param remoteDir   -> The remote dir
     */
    @Override
    public void uploadFileUsingSFTP(final InputStream inputStream, final String filename, String remoteDir) {
        ChannelSftp channelSftp;

        if (remoteDir == null) {
            remoteDir = "Import/";
        } else if (!remoteDir.endsWith("/"))
            remoteDir = remoteDir.concat("/");
        try {
            channelSftp = this.setupJsch();
            channelSftp.connect();

            StringBuilder filePath = new StringBuilder()
                    .append(remoteDir)
                    .append(filename)
                    .append(".csv");

            channelSftp.put(inputStream, filePath.toString());

            channelSftp.exit();
        } catch (JSchException | SftpException e) {
            log.error(e.getMessage(), e);
            throw new NwtException("Error uploading file using SFTP: " + e.getMessage());
        }

    }

    protected ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        Session jschSession = jsch.getSession(username, remoteHost);
        jschSession.setPassword(password);

        Properties config = new java.util.Properties(); 
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);

        // setting proxy
        if (socksProxyHost != null && socksProxyHost.trim().length() > 0 && socksProxyPort != null) {
            ProxyHTTP proxy = new ProxyHTTP(socksProxyHost, socksProxyPort.intValue());
            if (socksUsername != null && socksUsername.trim().length() > 0
                    && socksPassword != null && socksPassword.trim().length() > 0) {
                proxy.setUserPasswd(socksUsername, socksPassword);
            }
            jschSession.setProxy(proxy);
        }

        jschSession.connect();

        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    /**
     * @param args
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        final String json = new StringBuilder()
                .append("{")
                .append("    \"ContactKey\": \"CIP-12345678\",")
                .append("    \"EventDefinitionKey\":\"APIEvent-92a715f0-efd1-bc24-8d5f-37159034ffc2\",")
                .append("    \"Data\": {")
                .append("        \"SubscriberKey\":\"CIP-12345678\",")
                .append("        \"EmailAddress\":\"numa@mapfre.com\",")
                .append("        \"Name\":\"MyName\",")
                .append("        \"Product\": \"Su seguro de Auto\",")
                .append("        \"Actions\": \"<ul><li>Indicar el inicio de vigencia</li> <li>Confirmar método de pago</li> </ul>\"")
                .append("    }")
                .append("}")
                .toString();

        final String TOKEN_URI = "https://mcq6zgrtgr273f2ky047vrt3hz1y.auth.marketingcloudapis.com/v2/token";
        final String EVENT_URI = "https://mcq6zgrtgr273f2ky047vrt3hz1y.rest.marketingcloudapis.com/interaction/v1/events";

        RestTemplate restTemplate = new RestTemplate();

        RequestSFMCToken tokenRequest =  RequestSFMCToken.builder()
                .client_id("p2qugbpes4dmx4nc6ia0d4r2")
                .client_secret("VENqRWh5aRgxTD3V10hYJK3O")
                .grant_type(CLIENT_CREDENTIALS)
                .build();

        ResponseSFMCToken tokenResponse = restTemplate.postForObject(TOKEN_URI, tokenRequest, ResponseSFMCToken.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+ tokenResponse.getAccess_token());

        ObjectMapper objectMapper = new ObjectMapper();
        RequestSFMCEvent eventRequest = objectMapper.readValue(json, RequestSFMCEvent.class);

        HttpEntity<RequestSFMCEvent> entity = new HttpEntity<>(eventRequest, headers);
        ResponseSFMCEvent eventResponse = restTemplate.postForObject(EVENT_URI, entity, ResponseSFMCEvent.class);

       log.info("eventResponse: " + eventResponse);
    }

}
