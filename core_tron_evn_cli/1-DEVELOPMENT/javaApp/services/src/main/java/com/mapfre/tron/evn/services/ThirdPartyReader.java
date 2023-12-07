package com.mapfre.tron.evn.services;

import com.mapfre.nwt.trn.thp.ipc.bo.OThpIpcPC;
import com.mapfre.tron.evn.dto.Environment;
import com.mapfre.tron.evn.dto.ThirdPartyReaderRequestDTO;
import com.mapfre.tron.evn.exceptions.ThirdPartyReaderException;
import org.springframework.stereotype.Component;

@Component
public interface ThirdPartyReader {

    OThpIpcPC read (ThirdPartyReaderRequestDTO thirdPartyReaderRequest, Environment environment) throws ThirdPartyReaderException;
}
