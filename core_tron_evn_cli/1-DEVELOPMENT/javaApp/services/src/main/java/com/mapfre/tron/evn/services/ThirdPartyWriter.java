package com.mapfre.tron.evn.services;

import com.mapfre.nwt.trn.thp.ipc.bo.OThpIpcC;
import com.mapfre.tron.evn.dto.Environment;
import com.mapfre.tron.evn.exceptions.ThirdPartyWriterException;
import org.springframework.stereotype.Component;

@Component
public interface ThirdPartyWriter {

    void write(OThpIpcC oThpIpcC, Environment environment) throws ThirdPartyWriterException;
}
