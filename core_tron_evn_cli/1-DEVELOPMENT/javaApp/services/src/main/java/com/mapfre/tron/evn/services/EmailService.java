package com.mapfre.tron.evn.services;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface EmailService {

    public void sendMail(String pmSenEml,
                         Map<String, String> pmAdeEmlT,
                         Map<String, String> pmAdeEmlCpyT,
                         Map<String, String> pmAdeEmlHdnT,
                         String pmEmlSbjVal,
                         List<File> pmArhAnxT,
                         String pmMsgTxt,
                         String pmTypCtnBdy) throws Exception;

    public void sendMail(Object dto, Exception e);

}
