package com.mapfre.tron.api.sfcm.dl;

import java.io.InputStream;

import com.mapfre.tron.api.cmn.bo.RequestSFMCEvent;
import com.mapfre.tron.api.cmn.bo.ResponseSFMCEvent;
import com.mapfre.tron.api.cmn.bo.ResponseSFMCToken;

/**
 * The DL SFCM token rest client.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 25 may. 2021 - 13:47:39
 *
 */
public interface IDlSfcm {

    /**
     * Get the Bearer token for SFMC services.
     *
     * @return -> The SFMCToken response
     */
    ResponseSFMCToken getToken();

    /**
     * Launch the event.
     *
     * @param request     -> The event request
     * @param accessToken -> The bearer access token
     * @return            -> The event instance response
     */
    ResponseSFMCEvent launchEvent(RequestSFMCEvent request, String accessToken);

    /**
     * Launch the event.
     *
     * @param request     -> The string json event request
     * @param accessToken -> The bearer access token
     * @return            -> The event instance identifier
     */
    String launchEvent(String jsonRequestSFMCEvent, String accessToken);

    /**
     * Upload a file using SFTP to SFCM.
     *
     * @param inputStream -> the file input stream
     * @param filename    -> The completed filename
     * @param remoteDir   -> The remote dir
     */
    void uploadFileUsingSFTP(InputStream inputStream, String filename, String remoteDir);

}
