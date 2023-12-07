package com.mapfre.tron.api.cmn.sfmc.bl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;
import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;
import com.mapfre.tron.api.cmn.ResetPacakge;
import com.mapfre.tron.api.cmn.bo.RequestSFMCEvent;
import com.mapfre.tron.api.cmn.bo.ResponseSFMCEvent;
import com.mapfre.tron.api.cmn.bo.ResponseSFMCToken;
import com.mapfre.tron.api.cmn.mvr.dl.IBlCmnMvrCrtDAO;
import com.mapfre.tron.api.cmn.mvr.dl.ISrCmnMvsQryDAO;
import com.mapfre.tron.api.cmn.security.Encryptor;
import com.mapfre.tron.api.sfcm.dl.IDlSfcm;
import com.mapfre.tron.api.trv.vrb.bl.IBlTrvVrbQry;

import lombok.extern.slf4j.Slf4j;

/**
 * The send comunications to SFCMC online process service interface
 * implementation.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 4 jun. 2021 - 13:06:59
 *
 */
@Slf4j
@Service
public class TwBlSFMCProcessServiceImpl implements IBlSFMCProcessService {

    /** The resetPackage property. */
    @Autowired
    protected ResetPacakge resetPackage;

    /** The SFCM rest client. */
    @Autowired
    protected IDlSfcm iDlSfcm;

    /** The encryptor utility. */
    @Autowired
    protected Encryptor encryptor;

    /** The CmnMvsQry repository. */
    @Autowired
    protected ISrCmnMvsQryDAO iSrCmnMvsQryDAO;

    /** The env mvmIdnCommunication property. */
    @Value("${app.env.mvmIdnCommunication}")
    protected String mvmIdnCommunication;

    /** The env realEnviroment property. */
    @Value("${app.env.realEnvironment}")
    protected Boolean realEnviroment;

    /** The CmnMvrCrt repository. */
    @Autowired
    protected IBlCmnMvrCrtDAO iBlCmnMvrCrtDAO;

    /** The TrvVrbQry business interface. */
    @Autowired
    protected IBlTrvVrbQry iBLTrvVrbQry;

    /**
     * Send communications to SFMC online process.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     */
    @Override
    public void sendToSFMConlineProcess(final Integer cmpVal, final String lngVal) {

        log.debug("Tronweb business logic implementation sendToSFMConlineProcess have been called...");

        // reseting session
        resetSession();

        List<OCmnMvrS> lvOCmnMvrST = null;
        List<String> mvmIdn = new ArrayList<>(Arrays.asList(mvmIdnCommunication.split(",")));
        String stsTypVals = "10,21";
        List<String> stsTypVal = new ArrayList<>(Arrays.asList(stsTypVals.split(",")));

        // calling dcmTbl
        lvOCmnMvrST = iSrCmnMvsQryDAO.dcmTbl(lngVal, null, cmpVal, null, null, null, mvmIdn, null, null, null,
                stsTypVal, "0", false);

        if (lvOCmnMvrST != null && !lvOCmnMvrST.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseSFMCEvent response = null;
            for (OCmnMvrS oCmnMvrS : lvOCmnMvrST) {
                response = threadOCmnMvrS(objectMapper, response, oCmnMvrS);
            }
        }

    }

    protected ResponseSFMCEvent threadOCmnMvrS(ObjectMapper objectMapper, ResponseSFMCEvent response,
            OCmnMvrS oCmnMvrS) {
        ResponseSFMCToken accessToken;
        RequestSFMCEvent eventRequest;
        try {
            // getting the access token
            accessToken = iDlSfcm.getToken();

            // getting de request
            eventRequest = objectMapper.readValue(oCmnMvrS.getEtcVal(), RequestSFMCEvent.class);

            if (oCmnMvrS.getRskIdn() != null) {
                String encryptedRskIdn = encryptor.encrypt(oCmnMvrS.getRskIdn());
                if (eventRequest.getContactKey() != null) {
                    eventRequest.setContactKey(encryptedRskIdn);
                }
                if (eventRequest.getData() != null && eventRequest.getData().getSubscriberKey() != null) {
                    eventRequest.getData().setSubscriberKey(encryptedRskIdn);
                }
            }
            if (realEnviroment.booleanValue() || eventRequest.getData().getEmailAddress().contains("mapfre.com"))
                response = iDlSfcm.launchEvent(eventRequest, accessToken.getAccess_token());

            if (response != null && response.getEventInstanceId() != null
                    && response.getEventInstanceId().trim().length() > 0) {
                log.debug("iDlSfcm.launchEvent: " + response.getEventInstanceId());

                iBlCmnMvrCrtDAO.rlCmnNwtXxMvrUpdate(oCmnMvrS.getCmpVal(), oCmnMvrS.getRskIdn(),
                        oCmnMvrS.getSqnVal(), "", "20", "S");
            }

        } catch (Exception e) {
            log.error("General Error: " + e.getMessage());

            iBlCmnMvrCrtDAO.rlCmnNwtXxMvrUpdate(oCmnMvrS.getCmpVal(), oCmnMvrS.getRskIdn(),
                    oCmnMvrS.getSqnVal(), e.getMessage(), "21", null);
        }
        return response;
    }

    /**
     * Send communications to SFMC batch process.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     */
    @Override
    public void sendToSFMCbatchProcess(final Integer cmpVal, final String lngVal) {

        log.debug("Tronweb business logic implementation sendToSFMCbatchProcess have been called...");

        // reseting session
        resetSession();

        List<OCmnMvrS> lvOCmnMvrST = null;
        List<String> mvmIdn = new ArrayList<>(Arrays.asList(mvmIdnCommunication.split(",")));
        String stsTypVals = "10,21";
        List<String> stsTypVal = new ArrayList<>(Arrays.asList(stsTypVals.split(",")));

        // calling dcmTbl
        lvOCmnMvrST = iSrCmnMvsQryDAO.dcmTbl(lngVal, null, cmpVal, null, null, null, mvmIdn, null, null, null,
                stsTypVal, "1", true);

        if (lvOCmnMvrST != null && !lvOCmnMvrST.isEmpty()) {
            String agrValPrn = null; // control flag
            List<OCmnMvrS> oCmnMvrST = null;
            for (OCmnMvrS oCmnMvrS : lvOCmnMvrST) {

                // next group control
                if (!oCmnMvrS.getAgrValPrn().equals(agrValPrn)) {
                    // Generate csv and send it
                    if (oCmnMvrST != null && !oCmnMvrST.isEmpty()) {
                        this.generateAndSendCsv(oCmnMvrST, cmpVal, lngVal);
                    }

                    // Initializer next group and control flag
                    agrValPrn = oCmnMvrS.getAgrValPrn();
                    oCmnMvrST = new ArrayList<>();
                }

                oCmnMvrST.add(oCmnMvrS);
            }

            // last group
            if (oCmnMvrST != null && !oCmnMvrST.isEmpty()) {
                this.generateAndSendCsv(oCmnMvrST, cmpVal, lngVal);
            }

        }

    }

    /** Reset the session. */
    protected void resetSession() {
        log.debug("Reseting session...");
        resetPackage.executeRP();
        log.debug("The session has been reset.");
    }

    /**
     * Generate and send a csv.
     *
     * @param pmOCmnMvrST -> the CSV data
     */
    protected void generateAndSendCsv(final List<OCmnMvrS> pmOCmnMvrST, final Integer cmpVal, final String lngVal) {
        OCmnMvrS lvOCmnMvrS = pmOCmnMvrST.get(0);

        // Getting the csv head
        String vrbNamVal = "AS_".concat(lvOCmnMvrS.getAgrValPrn());
        List<OTrnVrbS> lvOPlyAtrST = iBLTrvVrbQry.get(null, lngVal, cmpVal, vrbNamVal, null);
        if (lvOPlyAtrST != null && !lvOPlyAtrST.isEmpty()) {
            OTrnVrbS lvOTrnVrbS = lvOPlyAtrST.get(0);

            // setting filename
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            final String fileName = new StringBuilder().append(sdf.format(date)).append("_")
                    .append(lvOCmnMvrS.getAgrValPrn()).append("_").append(lvOTrnVrbS.getVrbVal()).toString();

            File tmpFile = getTmpFile(fileName);
            createTmpFile(pmOCmnMvrST, lvOTrnVrbS, tmpFile);
            threadTmpFile(pmOCmnMvrST, lvOTrnVrbS, fileName, tmpFile);

        }

    }

    protected void threadTmpFile(final List<OCmnMvrS> pmOCmnMvrST, OTrnVrbS lvOTrnVrbS, final String fileName,
            File tmpFile) {
        if (tmpFile != null && tmpFile.exists()) {
            try (InputStream targetStream = new FileInputStream(tmpFile)) {
                // uploading csv
                iDlSfcm.uploadFileUsingSFTP(targetStream, fileName, lvOTrnVrbS.getAdtVal());

                // updating rlCmnNwtXxMvr
                if (pmOCmnMvrST != null && !pmOCmnMvrST.isEmpty()) {
                    for (OCmnMvrS oCmnMvrS : pmOCmnMvrST) {
                        iBlCmnMvrCrtDAO.rlCmnNwtXxMvrUpdate(oCmnMvrS.getCmpVal(), oCmnMvrS.getRskIdn(),
                                oCmnMvrS.getSqnVal(), "", "20", "S");
                    }
                }

            } catch (Exception e) {
                log.error("Error processing csv: " + e.getMessage());
                // updating rlCmnNwtXxMvr
                updateRlCmnNwtXxMvr(pmOCmnMvrST, e);

            } finally {
                deleteTmpFile(tmpFile);
            }
        }
    }

    protected void updateRlCmnNwtXxMvr(final List<OCmnMvrS> pmOCmnMvrST, Exception e) {
        if (pmOCmnMvrST != null && !pmOCmnMvrST.isEmpty()) {
            for (OCmnMvrS oCmnMvrS : pmOCmnMvrST) {
                iBlCmnMvrCrtDAO.rlCmnNwtXxMvrUpdate(oCmnMvrS.getCmpVal(), oCmnMvrS.getRskIdn(),
                        oCmnMvrS.getSqnVal(), e.getMessage(), "21", null);
            }
        }
    }

    protected void deleteTmpFile(File tmpFile) {
        try {
            boolean isDeleted = tmpFile.delete();
            if (!isDeleted) {
                log.error(String.format("Error deleteting de tmpFile %s", tmpFile.getName()));
            }
        } catch (Exception e) {
            log.error("Error deleting csv tmpFile: " + e.getMessage());
        }
    }

    protected void createTmpFile(final List<OCmnMvrS> pmOCmnMvrST, OTrnVrbS lvOTrnVrbS, File tmpFile) {
        String line;
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(tmpFile), StandardCharsets.UTF_8)) {
            // creating csv
            BufferedWriter writer = new BufferedWriter(osw);
            line = lvOTrnVrbS.getVrbDspVal();
            // writting csv head
            writer.write(line);
            writer.write(System.lineSeparator());
            List<String> csv = new ArrayList<>(Arrays.asList(line.split(";")));
            int pos = 0;
            for (String a : csv) {
                if (!a.equalsIgnoreCase("EmailAddress"))
                    pos++;
                else
                    break;
            }
            // Getting the csv body lines
            if (pmOCmnMvrST != null && !pmOCmnMvrST.isEmpty()) {
                for (OCmnMvrS oCmnMvrS : pmOCmnMvrST) {
                    line = oCmnMvrS.getEtcVal();
                    line = line.replaceFirst("SubscriberKey", encryptor.encrypt(oCmnMvrS.getRskIdn()));
                    line = line.replaceFirst("ContactKey", encryptor.encrypt(oCmnMvrS.getRskIdn()));
                    // writting csv body lines
                    csv = new ArrayList<>(Arrays.asList(line.split(";")));
                    if (realEnviroment.booleanValue() || csv.size() > pos && csv.get(pos).contains("mapfre.com")) {
                        writer.write(line);
                        writer.write(System.lineSeparator());
                    }
                }
            }

            writer.close();

        } catch (IOException e) {
            log.error("Error writing csv tmpFile: " + e.getMessage(), e);
        }
    }

    protected File getTmpFile(final String fileName) {
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(fileName, ".csv");
        } catch (IOException e1) {
            log.error("Error getting tmp file " + fileName);
        }
        return tmpFile;
    }

}
