package com.mapfre.tron.evn.services.impl;

import com.mapfre.nwt.trn.thp.ipc.bo.OThpIpcC;
import com.mapfre.nwt.trn.thp.ipc.bo.OThpIpcPC;
import com.mapfre.tron.events.dto.ActivityMessage;
import com.mapfre.tron.events.dto.InsuredMessage;
import com.mapfre.tron.events.dto.data.activity.OThpAcvS;
import com.mapfre.tron.evn.dto.Environment;
import com.mapfre.tron.evn.dto.ThirdPartyReaderRequestDTO;
import com.mapfre.tron.evn.mapper.OThpIpcCMapper;
import com.mapfre.tron.evn.services.EnrichActivityMessageUseCase;
import com.mapfre.tron.evn.services.InsuredEventProducer;
import com.mapfre.tron.evn.services.ThirdPartyReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EnrichActivityMessageUseCaseImpl implements EnrichActivityMessageUseCase {

    private static final String INSURED = "insured";

    @Autowired
    OThpIpcCMapper oThpIpcCMapper;

    @Value("${app.env.tron.api.thp.origin.basePath}")
    private String basePathOrigin;

    @Value("${app.env.tron.api.thp.origin.userName}")
    private String userNameOrigin;

    @Value("${app.env.tron.api.thp.origin.password}")
    private String passwordOrigin;

    private final EmailServiceImpl emailService;

    private final ThirdPartyReader thirdPartyReader;

    private final InsuredEventProducer insuredEventProducer;

    public EnrichActivityMessageUseCaseImpl(
            ThirdPartyReader thirdPartyReader,
            InsuredEventProducer insuredEventProducer,
            EmailServiceImpl emailService) {
        this.thirdPartyReader = thirdPartyReader;
        this.insuredEventProducer = insuredEventProducer;
        this.emailService = emailService;
    }

    @Override
    public void handle(ActivityMessage activityMessage) {

        OThpAcvS data = activityMessage.getData();
        ThirdPartyReaderRequestDTO thirdPartyReaderRequestDTO = getThirdPartyReaderRequestDTO(data);

        try {
            OThpIpcPC oThpIpcPCOrigin = thirdPartyReader.read(thirdPartyReaderRequestDTO, getOriginEnvironment());
            OThpIpcC oThpIpcC = oThpIpcPCOrigin.getOThpIpcC();

            InsuredMessage insuredMessage = new InsuredMessage();
            activityMessage.getHeader().setType(INSURED);
            insuredMessage.setHeader(activityMessage.getHeader());
            insuredMessage.setData(oThpIpcCMapper.fromBO(oThpIpcC));
            insuredEventProducer.write(insuredMessage);
        } catch (Exception e) {
            log.error("Error handling message", e);
            emailService.sendMail(thirdPartyReaderRequestDTO, e);
        }
    }


    private static ThirdPartyReaderRequestDTO getThirdPartyReaderRequestDTO(OThpAcvS data) {
        Integer cmpVal = (int)data.getCmpVal();
        String thpDcmTypVal = data.getThpDcmTypVal();
        String thpDcmVal = data.getThpDcmVal();
        return new ThirdPartyReaderRequestDTO(cmpVal, thpDcmTypVal, thpDcmVal);
    }

    private Environment getOriginEnvironment() {
        return new Environment("Origin", basePathOrigin, userNameOrigin, passwordOrigin);
    }
}
