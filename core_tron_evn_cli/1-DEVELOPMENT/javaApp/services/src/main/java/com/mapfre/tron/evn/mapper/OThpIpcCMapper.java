package com.mapfre.tron.evn.mapper;

import com.mapfre.tron.events.dto.data.insured.OThpAdrS;
import com.mapfre.tron.events.dto.data.insured.OThpArmS;
import com.mapfre.tron.events.dto.data.insured.OThpCntS;
import com.mapfre.tron.events.dto.data.insured.OThpInyS;
import com.mapfre.tron.events.dto.data.insured.Insured;
import com.mapfre.tron.events.dto.data.insured.OThpLcnS;
import com.mapfre.tron.events.dto.data.insured.OThpLgrS;
import com.mapfre.tron.events.dto.data.insured.OThpPceS;
import com.mapfre.tron.events.dto.data.insured.OThpPcmS;
import com.mapfre.tron.events.dto.data.insured.OThpShlS;
import com.mapfre.tron.events.dto.data.insured.OThpTmnS;
import com.mapfre.tron.events.dto.data.insured.OThpTxlS;
import com.mapfre.tron.events.dto.data.insured.OThpUniS;
import com.mapfre.tron.events.dto.data.insured.OTpdAlpS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper
public interface OThpIpcCMapper {

    OThpIpcCMapper INSTANCE = Mappers.getMapper(OThpIpcCMapper.class);

    @Mapping(source = "OThpAcvP.OThpAcvS", target = "OThpAcvS")
    @Mapping(source = "OThpAdrPT", target = "OThpAdrT")
    @Mapping(source = "OThpArmPT", target = "OThpArmT")
    @Mapping(source = "OThpCntPT", target = "OThpCntT")
    @Mapping(source = "OThpInyP.OThpInyS", target = "OThpInyS")
    @Mapping(source = "OThpLcnPT", target = "OThpLcnT")
    @Mapping(source = "OThpLgrPT", target = "OThpLgrT")
    @Mapping(source = "OThpPcePT", target = "OThpPceT")
    @Mapping(source = "OThpPcmPT", target = "OThpPcmT")
    @Mapping(source = "OThpPrsP.OThpPrsS", target = "OThpPrsS")
    @Mapping(source = "OThpShlPT", target = "OThpShlT")
    @Mapping(source = "OThpTmnPT", target = "OThpTmnT")
    @Mapping(source = "OThpTxlPT", target = "OThpTxlT")
    @Mapping(source = "OThpUniPT", target = "OThpUniT")
    @Mapping(source = "OTpdAlpPT", target = "OTpdAlpT")
    Insured fromBO(com.mapfre.nwt.trn.thp.ipc.bo.OThpIpcC o);

    List<OThpAdrS> mapListOThpAdrT(List<com.mapfre.nwt.trn.thp.adr.bo.OThpAdrP> o);
    List<OThpArmS> mapListOThpArmT(List<com.mapfre.nwt.trn.thp.arm.bo.OThpArmP> o);
    List<OThpCntS> mapListOThpCntT(List<com.mapfre.nwt.trn.thp.cnt.bo.OThpCntP> o);
    List<OThpLcnS> mapListOThpLcnT(List<com.mapfre.nwt.trn.thp.lcn.bo.OThpLcnP> o);
    List<OThpLgrS> mapListOThpLgrT(List<com.mapfre.nwt.trn.thp.lgr.bo.OThpLgrP> o);
    List<OThpPceS> mapListOThpPceT(List<com.mapfre.nwt.trn.thp.pce.bo.OThpPceP> o);
    List<OThpPcmS> mapListOThpPcmT(List<com.mapfre.nwt.trn.thp.pcm.bo.OThpPcmP> o);
    List<OThpShlS> mapListOThpShlT(List<com.mapfre.nwt.trn.thp.shl.bo.OThpShlP> o);
    List<OThpTmnS> mapListOThpTmnT(List<com.mapfre.nwt.trn.thp.tmn.bo.OThpTmnP> o);
    List<OThpTxlS> mapListOThpTxlT(List<com.mapfre.nwt.trn.thp.txl.bo.OThpTxlP> o);
    List<OThpUniS> mapListOThpUniT(List<com.mapfre.nwt.trn.thp.uni.bo.OThpUniP> o);
    List<OTpdAlpS> mapListOTpdAlpT(List<com.mapfre.nwt.trn.tpd.alp.bo.OTpdAlpP> o);

    @Mapping(source = "OThpAdrS", target = ".")
    OThpAdrS mapOThpAdrS(com.mapfre.nwt.trn.thp.adr.bo.OThpAdrP o);

    @Mapping(source = "OThpArmS", target = ".")
    OThpArmS mapOThpArmS(com.mapfre.nwt.trn.thp.arm.bo.OThpArmP o);

    @Mapping(source = "OThpCntS", target = ".")
    OThpCntS mapOThpCntS(com.mapfre.nwt.trn.thp.cnt.bo.OThpCntP o);

    @Mapping(source = "OThpInyS", target = ".")
    OThpInyS mapOThpInyS(com.mapfre.nwt.trn.thp.iny.bo.OThpInyP o);

    @Mapping(source = "OThpLcnS", target = ".")
    OThpLcnS mapOThpLcnS(com.mapfre.nwt.trn.thp.lcn.bo.OThpLcnP o);

    @Mapping(source = "OThpLgrS", target = ".")
    OThpLgrS mapOThpLgrS(com.mapfre.nwt.trn.thp.lgr.bo.OThpLgrP o);

    @Mapping(source = "OThpPceS", target = ".")
    OThpPceS mapOThpPceS(com.mapfre.nwt.trn.thp.pce.bo.OThpPceP o);

    @Mapping(source = "OThpPcmS", target = ".")
    OThpPcmS mapOThpPcmS(com.mapfre.nwt.trn.thp.pcm.bo.OThpPcmP o);

    @Mapping(source = "OThpShlS", target = ".")
    OThpShlS mapOThpShlS(com.mapfre.nwt.trn.thp.shl.bo.OThpShlP o);

    @Mapping(source = "OThpTmnS", target = ".")
    OThpTmnS mapOThpTmnS(com.mapfre.nwt.trn.thp.tmn.bo.OThpTmnP o);

    @Mapping(source = "OThpTxlS", target = ".")
    OThpTxlS mapOThpTxlS(com.mapfre.nwt.trn.thp.txl.bo.OThpTxlP o);

    @Mapping(source = "OTpdAlpS", target = ".")
    OTpdAlpS mapOTpdAlpS(com.mapfre.nwt.trn.tpd.alp.bo.OTpdAlpP o);

    default Long map(Date value) {
        return value != null ? value.getTime() : null;
    }
}