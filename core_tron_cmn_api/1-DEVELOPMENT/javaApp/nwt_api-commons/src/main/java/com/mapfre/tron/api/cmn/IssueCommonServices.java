package com.mapfre.tron.api.cmn;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mapfre.nwt.ard.c.CArd;
import com.mapfre.nwt.cmn.c.CCmn;
import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.ins.c.CInsCmp;
import com.mapfre.nwt.ins.c.CInsConstant;
import com.mapfre.nwt.pid.c.CPid;
import com.mapfre.nwt.ply.c.CPly;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmu.cmp.sr.ISrCmuCmpQry;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.nwt.trn.cmu.thl.sr.ISrCmuThlQry;
import com.mapfre.nwt.trn.pid.img.bo.OPidImgP;
import com.mapfre.nwt.trn.pid.img.bo.OPidImgS;
import com.mapfre.nwt.trn.pid.img.sr.ISrPidImgCrt;
import com.mapfre.nwt.trn.ply.anx.bo.OPlyAnxCPT;
import com.mapfre.nwt.trn.ply.anx.bo.OPlyAnxP;
import com.mapfre.nwt.trn.ply.anx.sr.ISrPlyAnxIsuPly;
import com.mapfre.nwt.trn.ply.atc.bo.OPlyAtcC;
import com.mapfre.nwt.trn.ply.atc.bo.OPlyAtcPC;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrCPT;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.nwt.trn.ply.atr.sr.ISrPlyAtrIsu;
import com.mapfre.nwt.trn.ply.cci.bo.OPlyCciCPT;
import com.mapfre.nwt.trn.ply.cci.bo.OPlyCciP;
import com.mapfre.nwt.trn.ply.cci.sr.ISrPlyCciIsuPly;
import com.mapfre.nwt.trn.ply.cvc.bo.OPlyCvcPC;
import com.mapfre.nwt.trn.ply.cvr.bo.OPlyCvrS;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniP;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniS;
import com.mapfre.nwt.trn.ply.gni.sr.ISrPlyGniIsuPlyOch;
import com.mapfre.nwt.trn.ply.ine.bo.OPlyIneCPT;
import com.mapfre.nwt.trn.ply.ine.bo.OPlyIneP;
import com.mapfre.nwt.trn.ply.ine.bo.OPlyIneS;
import com.mapfre.nwt.trn.ply.ine.sr.ISrPlyIneAltApl;
import com.mapfre.nwt.trn.ply.ine.sr.ISrPlyIneCue;
import com.mapfre.nwt.trn.ply.ine.sr.ISrPlyIneIsuPfq;
import com.mapfre.nwt.trn.ply.oca.bo.OPlyOcaCPT;
import com.mapfre.nwt.trn.ply.oca.sr.ISrPlyOcaIsu;
import com.mapfre.nwt.trn.ply.rsk.bo.OPlyRskCPT;
import com.mapfre.nwt.trn.ply.rsk.bo.OPlyRskP;
import com.mapfre.nwt.trn.ply.rsk.bo.OPlyRskS;
import com.mapfre.nwt.trn.ply.rsk.sr.ISrPlyRskIsuPly;
import com.mapfre.nwt.trn.ply.utc.bo.OPlyUtcCPT;
import com.mapfre.nwt.trn.ply.utc.sr.ISrPlyUtcCuePly;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobP;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.nwt.trn.prt.lob.sr.ISrPrtLobQry;
import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;
import com.mapfre.nwt.trn.trn.cnx.sr.ISrTrnCnxCue;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import com.mapfre.tron.api.bo.CnxCmpThlBasicEitDto;

/**
 * Agrupación de funcionalidades comunes reutilizadas en diferentes clases
 * 
 * @author fjlorente
 */
@Component
public final class IssueCommonServices {
    
    final NwtUtils nwtUtils = new NwtUtils();
    
    final IssueUtils isuUtils = new IssueUtils();
    
    @Autowired
    private ISrTrnCnxCue srTrnCnxCue;
    
    @Autowired
    private ISrCmuCmpQry srCmuCmpQry;
    
    @Autowired
    private ISrCmuThlQry srCmuThlQry;
    
    @Autowired
    private ISrPrtLobQry srPrtLobQry;
    
    @Autowired
    private ISrPlyGniIsuPlyOch srPlyGniIsuPlyOch;
    
    @Autowired
    private ISrPidImgCrt srPidImgCrt;
    
    @Autowired
    private ISrPlyCciIsuPly srPlyCciIsuPly;
    
    @Autowired
    private ISrPlyAtrIsu srPlyAtrIsu;
    
    @Autowired
    com.mapfre.nwt.trn.ply.cvc.sr.INwtPlyCvcSbtDnpPic nwtPlyCvcSbtDnpPic;
    
    private ISrPlyOcaIsu srPlyOcaIsu;
    private ISrPlyIneIsuPfq srPlyIneIsuPfq;
    private ISrPlyIneAltApl srPlyIneAltApl; 
    private ISrPlyIneCue srPlyIneCue;   
    private ISrPlyUtcCuePly srPlyUtcCuePly; 
    private ISrPlyRskIsuPly srPlyRskIsuPly;
    private ISrPlyAnxIsuPly srPlyAnxIsuPly;
    
    @Autowired    
    public IssueCommonServices(
                               ISrPlyOcaIsu srPlyOcaIsu,
                               ISrPlyIneIsuPfq srPlyIneIsuPfq, 
                               ISrPlyIneAltApl srPlyIneAltApl,
                               ISrPlyIneCue srPlyIneCue, 
                               ISrPlyUtcCuePly srPlyUtcCuePly,
                               ISrPlyRskIsuPly srPlyRskIsuPly, 
                               ISrPlyAnxIsuPly srPlyAnxIsuPly) {
        super();
        this.srPlyOcaIsu = srPlyOcaIsu;
        this.srPlyIneIsuPfq = srPlyIneIsuPfq;
        this.srPlyIneAltApl = srPlyIneAltApl;
        this.srPlyIneCue = srPlyIneCue;
        this.srPlyUtcCuePly = srPlyUtcCuePly;
        this.srPlyRskIsuPly = srPlyRskIsuPly;
        this.srPlyAnxIsuPly = srPlyAnxIsuPly;
    }
    //------------------------------------------------------------------------------
    // Orquestar llamadas a los servicios de CONSULTAR contexto, compañia y tercer nivel 
    //------------------------------------------------------------------------------
    public CnxCmpThlBasicEitDto getBasicDtoSrv(String pmLngVal, 
                           BigDecimal pmCmpVal, 
                           String pmUsrVal){
      CnxCmpThlBasicEitDto rtDto = new CnxCmpThlBasicEitDto();
        //------------------------------------
        // NFU-431 - OFRECER HALLAR contexto, datos basicos
        OTrnCnxS cnx = getCnxSrv(pmLngVal, 
                                 pmCmpVal, 
                                 pmUsrVal);
        rtDto.setOTrnCnxS(cnx);
        // CMN-1236 - OFRECER CONSULTAR compañia
        rtDto.setOCmuCmpS(srCmuCmpQry.get(cnx.getCmpVal(), 
                      CTrn.GET_NAM_NNE, 
                      cnx.getLngVal()).getOCmuCmpS ());
        // CMN-1334 - OFRECER CONSULTAR tercer nivel
        rtDto.setOCmuThlP(srCmuThlQry.get(cnx.getCmpVal(), 
                              cnx.getThrLvlVal(), 
                              CTrn.GET_NAM_NNE, 
                              cnx.getLngVal()));
        nwtUtils.isTrmOk(rtDto.getOCmuThlP(), "SrCmuThlQry.get");
        return rtDto;
    }
    //------------------------------------------------------------------------------
    // servicio recuperar contexto 
    //------------------------------------------------------------------------------
    public OTrnCnxS getCnxSrv(String pmLngVal, 
                           BigDecimal pmCmpVal, 
                           String pmUsrVal) {
        // CNX
        OTrnCnxS cnx = new OTrnCnxS();
        String sesionId = UUID.randomUUID().toString();
        cnx.setSesVal(sesionId);
        cnx.setPrcTypVal(CTrn.PRC_TYP_ONL);
        cnx.setLngVal(pmLngVal);
        cnx.setCmpVal(pmCmpVal);
        cnx.setUsrVal(pmUsrVal);
        //------------------------------------
        // NFU-431 - OFRECER HALLAR contexto, datos basicos
        cnx = srTrnCnxCue.daaBsc(cnx.getUsrVal(), 
                                 cnx.getCmpVal(), 
                                 cnx);
        return cnx;
    }
    //------------------------------------------------------------------------------
    // Servicio CONSULTAR ramo
    //------------------------------------------------------------------------------
    public OPrtLobP qryLobSrv(OTrnCnxS cnx, OPlyGniS pmGniS) {
        // OFRECER CONSULTAR ramo (CMN-1222)
        OPrtLobP lvLobP = srPrtLobQry.lob(cnx.getCmpVal(), 
                                          pmGniS.getLobVal(), 
                                          cnx.getLngVal(), 
                                          CTrn.GET_NAM_ALL);
        nwtUtils.isTrmOk(lvLobP, "SrPrtLobQry.lob");
        return lvLobP;
    }
    //------------------------------------------------------------------------------
    // Servicio orquestacion informacion general ramo
    //------------------------------------------------------------------------------
    public OPlyGniP ochGniLobSrv(OTrnCnxS cnx, 
                                 OPrtLobP pmLobP, 
                                 OCmuThlS pmThlS,
                                 OPlyGniP pmGniP){
        // OFRECER EMITIR informacion general ramo orquestacion (ISU-15431)
        OPlyGniP lvGniP = srPlyGniIsuPlyOch.lob(cnx.getCmpVal(), 
                                                pmLobP, 
                                                pmThlS.getFrsLvlVal(), 
                                                pmThlS.getScnLvlVal(), 
                                                pmThlS.getThrLvlVal(), 
                                                cnx);
        nwtUtils.isTrmOk(lvGniP, "SrPlyGniIsuPlyOch.lob");
        lvGniP.setOPlyGniS((OPlyGniS) nwtUtils.assignValuesObjS(pmGniP.getOPlyGniS(), lvGniP.getOPlyGniS()));
        return pmGniP;
    }
    //------------------------------------------------------------------------------
    // Servicio CREAR imagen
    //------------------------------------------------------------------------------
    public OPidImgP crtImgSrv(OTrnCnxS cnx, 
                              OPrtLobS pmLobS, 
                              OPlyGniS pmGniS) {
        // OFRECER CREAR imagen completo (ISD-1366)
        OPidImgP lvOPidImgP = srPidImgCrt.cpe(cnx.getCmpVal(), 
                                              pmLobS.getLobVal(),
                                              pmGniS.getDelVal(),
                                              pmGniS.getSblVal(),
                                              CPid.ENR_TYP_NEW_PDC, 
                                              pmLobS.getImgGnrTypVal(),
                                              pmGniS.getPlyEfcDat(),
                                              cnx);
        nwtUtils.isTrmOk(lvOPidImgP, "SrPidImgCrt.cpe");
        return lvOPidImgP;
    }
    //------------------------------------------------------------------------------
    // Servicio Validar coaseguro
    //------------------------------------------------------------------------------
    public OPlyCciCPT vldCciSrv(OTrnCnxS cnx, 
                                OPlyGniS pmGniS, 
                                OPrtLobS pmLobS, 
                                List<OPlyCciP> pmCciPT) {
        // OFRECER EMITIR coaseguro emision conjunto (ISU-15511)
        OPlyCciCPT lvCciCPT = srPlyCciIsuPly.prcTbl(cnx.getCmpVal(), 
                                                    pmGniS.getPlyVal(), 
                                                    pmLobS.getCinCedCmsWitRpt(), 
                                                    pmCciPT, 
                                                    pmGniS.getCinTypVal(), 
                                                    cnx);
        nwtUtils.isTrmOk(lvCciCPT, "SrPlyCciIsuPly.prcTbl");
        return lvCciCPT;
    }
    //---------------------------------------------------------------------------------------------------------------------
    // Servicio orquestar validacion atributos
    //------------------------------------------------------------------------------
    public OPlyAtrCPT ochVldAtrSrv(OTrnCnxS cnx, 
                                    OPlyGniS pmGniS, 
                                    OPrtLobS pmLobS, 
                                    OPidImgS pmImgS, 
                                    List<OPlyAtcC> pmAtcCT, 
                                    String pmNivel, 
                                    String pmSubnivel,
                                    BigDecimal pmRskVal,
                                    String pmOrgTypVal,
                                    String pmIsuTypVal) {
        //-----------------------------------------
        // si existen atributos en este nivel
        if ( pmAtcCT != null && !pmAtcCT.isEmpty()) {
            //-----------------------------------------
            // OFRECER EMITIR previo atributo conjunto (ISU-22851)
            prrAtrSrv(cnx, 
                      pmGniS, 
                      pmLobS, 
                      pmImgS,
                      pmNivel, 
                      pmSubnivel,
                      pmRskVal,
                      pmOrgTypVal,
                      pmIsuTypVal);
            //-----------------------------------------
            List<OPlyAtrP> lvAtrPT = new ArrayList<>();
            for (OPlyAtcC lvAtcC : pmAtcCT) {
                // si hay ocurrencias hay que validarlas
                if (lvAtcC.getOPlyOcaPT() != null && 
                    !lvAtcC.getOPlyOcaPT().isEmpty()) {
                    // OFRECER EMITIR atributo ocurrencia, cobertura, conjunto orquestación (ISU-22839)
                    vldOcaSrv(cnx, 
                            pmGniS, 
                            pmLobS, 
                            pmImgS, 
                            lvAtcC, 
                            pmRskVal,
                            pmOrgTypVal);
                }
                // se añade el objeto atributo al array de objetos PT que es el que espera la validacion
                lvAtrPT.add(lvAtcC.getOPlyAtrP());
            }
            //-----------------------------------------
            // OFRECER EMITIR atributo conjunto orquestacion (ISU-22854)
            return vldAtrSrv(cnx, 
                             pmGniS, 
                             pmLobS, 
                             pmImgS,
                             lvAtrPT,
                             pmNivel, 
                             pmSubnivel,
                             pmRskVal,
                             pmOrgTypVal);
        }else{
            return new OPlyAtrCPT();
        }
    }
    //------------------------------------------------------------------------------
    // Servicio previo atributos (ISI-9082 CONTENER EMITIR atributo conjunto)
    //------------------------------------------------------------------------------
    private OPlyAtrCPT prrAtrSrv(OTrnCnxS cnx, 
                                 OPlyGniS pmGniS, 
                                 OPrtLobS pmLobS, 
                                 OPidImgS pmImgS,
                                 String pmNivel, 
                                 String pmSubnivel,
                                 BigDecimal pmRskVal,
                                 String pmOrgTypVal,
                                 String pmIsuTypVal) {
        //-----------------------------------------
        // OFRECER EMITIR previo atributo conjunto (ISU-22851)
        OPlyAtrCPT rtAtrCPT = srPlyAtrIsu.prrTbl(cnx.getCmpVal(),
                                       pmImgS.getFraIdnVal(),
                                       pmGniS.getPlyVal(),
                                       pmGniS.getAplVal(),
                                       pmGniS.getQtnVal(),
                                       pmGniS.getOrgQtnVal(),
                                       pmLobS.getLobVal(),
                                       pmRskVal,
                                       CPid.PED_BAS,
                                       CPly.GNC_MDT,
                                       CPly.GNC_CVR,
                                       CPly.GNC_AGC,
                                       pmImgS.getVldDat(),
                                       pmGniS.getDelVal(),
                                       pmImgS.getDelVldDat(),
                                       pmGniS.getSblVal(),
                                       pmImgS.getSblVldDat(),
                                       null,
                                       pmGniS.getTnrPlyTypVal(),
                                       CTrn.INL_STS,
                                       cnx,
                                       pmNivel,
                                       pmSubnivel,
                                       pmOrgTypVal,
                                       null,
                                       pmIsuTypVal);
        nwtUtils.isTrmOk(rtAtrCPT, "SrPlyAtrIsu.prrTbl ");
        return rtAtrCPT;
    }
    //------------------------------------------------------------------------------
    // Servicio Validar atributos (ISI-9082 CONTENER EMITIR atributo conjunto)
    //------------------------------------------------------------------------------
    private OPlyAtrCPT vldAtrSrv(OTrnCnxS cnx, 
                                 OPlyGniS pmGniS, 
                                 OPrtLobS pmLobS, 
                                 OPidImgS pmImgS, 
                                 List<OPlyAtrP> pmAtrPT, 
                                 String pmNivel, 
                                 String pmSubnivel,
                                 BigDecimal pmRskVal,
                                 String pmOrgTypVal){
        //-----------------------------------------
        //OFRECER EMITIR atributo conjunto orquestacion (ISU-22854)
        OPlyAtrCPT rtAtrCPT = srPlyAtrIsu.tblOch(cnx.getCmpVal(),        // Compañia
                                       pmGniS.getPlyVal(),     // Poliza
                                       pmGniS.getQtnVal(),     // Presupuesto
                                       pmLobS.getLobVal(),      // Ramo
                                       pmImgS.getFraIdnVal(),   // Identificador marco
                                       pmAtrPT,                // Atributo conjunto
                                       pmRskVal,               // Riesgo
                                       CPid.PED_BAS,           // Periodo
                                       CPly.GNC_MDT,           // Modalidad acceso
                                       null,                   // Cobertura
                                       null,                   // Agravante
                                       pmGniS.getDelVal(),     // Acuerdo
                                       pmImgS.getDelVldDat(),   // Fecha validez acuerdo
                                       pmGniS.getSblVal(),     // Subacuerdo
                                       pmImgS.getSblVldDat(),   // Fecha validez subacuerdo
                                       pmGniS.getCrnVal(),     // Moneda
                                       pmImgS.getVldDat(),      // Fecha validez ramo
                                       pmGniS.getPlyEfcDat(),  // Fecha efecto poliza
                                       pmGniS.getPlyExpDat(),  // Fecha vencimiento poliza
                                       cnx,                    // Contexto
                                       pmNivel,                // Nivel
                                       pmSubnivel,             // Subnivel
                                       pmOrgTypVal,            // Origen 
                                       null);                  // Marca modalidad
        nwtUtils.isTrmOk(rtAtrCPT, "SrPlyAtrIsu.tblOch");
        return rtAtrCPT;
    }
    //------------------------------------------------------------------------------
    // Servicio Validar ocurrencias (ISI-9082 CONTENER EMITIR atributo conjunto)
    //------------------------------------------------------------------------------
    private OPlyOcaCPT vldOcaSrv(OTrnCnxS cnx, 
                                 OPlyGniS pmGniS, 
                                 OPrtLobS pmLobS, 
                                 OPidImgS pmImgS, 
                                 OPlyAtcC pmAtcC,  
                                 BigDecimal pmRskVal,
                                 String pmOrgTypVal) {
        //-----------------------------------------
        // para obtener el codigo de la lista de ocurrencias es necesario que venga informado en el objeto dependencias primera posicion (OTrnDpnT)
        BigDecimal lvLstVal = pmAtcC.getOPlyOcaPT().get(0).getOPlyOcaS().getLstVal();
        // el numero de ocurrencias se obtiene del atributo, esta en formato String pero debe contener un numerico para no provocar un NumberFormatException
        BigDecimal lvOcrQntVal = new BigDecimal(pmAtcC.getOPlyAtrP().getOPlyAtrS().getFldValVal());
        //-----------------------------------------

        // OFRECER EMITIR previo atributo ocurrencia conjunto (ISU-22833)
        OPlyOcaCPT prrOcaCPT = srPlyOcaIsu.prrTbl(cnx.getCmpVal(), 
                           pmGniS.getPlyVal(),
                           pmGniS.getAplVal(),
                           pmGniS.getQtnVal(),
                           pmGniS.getOrgQtnVal(),
                           pmRskVal,
                           CPid.PED_BAS,
                           CPly.GNC_MDT,
                           pmLobS.getLobVal(),
                           pmImgS.getFraIdnVal(),
                           pmImgS.getVldDat(),
                           lvLstVal,
                           lvOcrQntVal,
                           CTrn.INL_STS,
                           cnx,
                           pmOrgTypVal);
        nwtUtils.isTrmOk(prrOcaCPT, "SrPlyOcaIsu.prrTbl");

        // OFRECER EMITIR atributo ocurrencia, cobertura, conjunto orquestación (ISU-22839)
        OPlyOcaCPT rtOcaCPT = srPlyOcaIsu.tblOch(cnx.getCmpVal(),
                                      pmGniS.getPlyVal(),
                                      pmGniS.getQtnVal(),
                                      pmImgS.getFraIdnVal(),
                                      pmAtcC.getOPlyOcaPT(),
                                      pmRskVal,
                                      CPid.PED_BAS,
                                      CPly.GNC_MDT,
                                      pmLobS.getLobVal(),
                                      lvLstVal,
                                      pmImgS.getVldDat(),
                                      pmGniS.getCrnVal(),
                                      cnx,
                                      pmOrgTypVal,
                                      lvOcrQntVal); 
        nwtUtils.isTrmOk(rtOcaCPT, "SrPlyOcaIsu.tblOch");
        return rtOcaCPT;
    }
    //------------------------------------------------------------------------------
    // Servicio Validar atributos coberturas
    //------------------------------------------------------------------------------
    public OPlyAtrCPT vldAtrCvrSrv(OTrnCnxS cnx, 
                                    OPlyGniS pmGniS, 
                                    OPrtLobS pmLobS, 
                                    OPidImgS pmImgS,  
                                    OPlyRskS pmRskS, 
                                    OPlyCvrS pmCvrS,
                                    List<OPlyAtcPC> pmAtcCT,
                                    String pmOrgTypVal,
                                    String pmIsuTypVal) {
        //-----------------------------------------
        List<OPlyAtrP> lvAtrPT = new ArrayList<>();
        OPlyAtrCPT rtAtrCPT = new OPlyAtrCPT();
        // OFRECER EMITIR previo atributo conjunto (ISU-22851)
        OPlyAtrCPT prrAtrCPT = srPlyAtrIsu.prrTbl(cnx.getCmpVal(), 
                         pmImgS.getFraIdnVal(),
                         pmGniS.getPlyVal(),
                         pmGniS.getAplVal(),
                         pmGniS.getQtnVal(),
                         pmGniS.getOrgQtnVal(),
                         pmLobS.getLobVal(),
                         pmRskS.getRskVal(),
                         CPid.PED_BAS,
                         pmRskS.getMdtVal(),
                         pmCvrS.getCvrVal(),
                         CPly.GNC_AGC,
                         pmImgS.getVldDat(),
                         pmGniS.getDelVal(),
                         pmImgS.getDelVldDat(),
                         pmGniS.getSblVal(),
                         pmImgS.getSblVldDat(),
                         pmCvrS.getSqnVal(),
                         pmGniS.getTnrPlyTypVal(),
                         CTrn.INL_STS,
                         cnx, 
                                       CArd.LVL_TYP_CVR,
                                       CArd.SVL_TYP_CMN,
                                       pmOrgTypVal,
                                       null,
                                       pmIsuTypVal);
        nwtUtils.isTrmOk(prrAtrCPT, "SrPlyAtrIsu.prrTbl");
        //-----------------------------------------
        if ( pmAtcCT != null && !pmAtcCT.isEmpty()) {
            for (OPlyAtcPC lvAtcPC : pmAtcCT) {
              OPlyAtrP lvAtrP = lvAtcPC.getOPlyAtcC().getOPlyAtrP();
              lvAtrP.getOTrnPrcS().setAcnTypVal(CTrn.ACN_TYP_MDF);
              lvAtrPT.add(lvAtrP);
            }
            // OFRECER EMITIR atributo cobertura conjunto orquestacion (ISU-22858)
            rtAtrCPT =  srPlyAtrIsu.cvrTblOch(cnx.getCmpVal(),
                                              pmGniS.getPlyVal(),
                                              pmGniS.getQtnVal(),
                                              pmLobS.getLobVal(),
                                              pmImgS.getFraIdnVal(),
                                              lvAtrPT,
                                              pmRskS.getRskVal(),
                                              CPid.PED_BAS,
                                              pmRskS.getMdtVal(),
                                              pmCvrS.getCvrVal(),
                                              CPly.GNC_AGC,
                                              pmGniS.getDelVal(),
                                              pmImgS.getDelVldDat(),
                                              pmGniS.getSblVal(),
                                              pmImgS.getSblVldDat(),
                                              pmImgS.getVldDat(),
                                              pmGniS.getPlyEfcDat(),
                                              pmGniS.getPlyExpDat(),
                                              cnx,
                                              CArd.LVL_TYP_CVR,
                                              CArd.SVL_TYP_CMN,
                                              pmGniS.getCrnVal(),
                                              pmOrgTypVal);
            nwtUtils.isTrmOk(rtAtrCPT, "SrPlyAtrIsu.cvrTblOch");
        }
        return rtAtrCPT;
    }
    //------------------------------------------------------------------------------
    // Servicio Validar atributos nivel cero
    //------------------------------------------------------------------------------
    public OPlyAtrCPT vldAtrLvlZroSrv(OTrnCnxS cnx, 
                                    OPlyGniS pmGniS, 
                                    OPrtLobS pmLobS, 
                                    OPidImgS pmImgS,  
                                    OPlyRskS pmRskS, 
                                    OPlyCvrS pmCvrS,
                                    List<OPlyAtcPC> pmAtcCT,
                                    String pmOrgTypVal,
                                    String pmIsuTypVal){
        //codigo repetido
        return this.vldAtrCvrSrv( cnx, 
                 pmGniS, 
                 pmLobS, 
                 pmImgS,  
                 pmRskS, 
                 pmCvrS,
                 pmAtcCT,
                 pmOrgTypVal,
                 pmIsuTypVal);
        
    }
    //---------------------------------------------------------------------------------------------------------------------
    // Servicio Consultar listado de beneficiarios y obtener las intervenciones correspondientes del listado 
    //------------------------------------------------------------------------------
    private List<OPlyIneP> getPrrBnfTypPTSrv(OTrnCnxS cnx, 
                                             OPlyGniS pmGniS, 
                                             OPrtLobS pmLobS, 
                                             OPidImgS pmImgS, 
                                             OPlyIneS pmInePlh,
                                             BigDecimal pmRskVal,
                                             String pmThpPttTypVal,
                                             List<OPlyIneP> pmInePT){
        // OFRECER EMITIR previo intervencion, tipo beneficiario, desde presupuesto conjunto(ISU-17883)
        OPlyIneCPT lvBnfTypCPT = srPlyIneIsuPfq.prrBnfTypTbl(pmGniS.getDelVal(), 
                                                             cnx.getCmpVal(), 
                                                             pmInePlh.getThpDcmVal(), 
                                                             CTrn.INL_STS, 
                                                             pmImgS.getAcsDfnDat(), 
                                                             pmImgS.getDelVldDat(), 
                                                             pmImgS.getVldDat(), 
                                                             pmImgS.getSblVldDat(), 
                                                             pmImgS.getFraIdnVal(), 
                                                             cnx, 
                                                             CInsCmp.get(CInsConstant.MLC_VAL, cnx.getCmpVal()), 
                                                             pmThpPttTypVal, // CPid.INE_PTT_(PLY_PRR_ATR, RSK_PRR_ATR, RSK_POS_ATR)
                                                             pmGniS.getPlyVal(), 
                                                             pmGniS.getOrgQtnVal(), 
                                                             pmLobS.getLobVal(), 
                                                             pmRskVal, // CPid.RSK_PLY, rskVal 
                                                             pmGniS.getSblVal());
        nwtUtils.isTrmOk(lvBnfTypCPT, "SrPlyIneIsuPfq.prrBnfTypTbl");
        
        return isuUtils.getBnfTypPT(pmInePT, lvBnfTypCPT.getOPlyInePT(), pmRskVal);
    }
    //------------------------------------------------------------------------------
    // Servicio Validar intervenciones
    //------------------------------------------------------------------------------
    public OPlyIneCPT vldIneSrv(OTrnCnxS cnx, 
                                 OPlyGniS pmGniS, 
                                 OPrtLobS pmLobS, 
                                 OPidImgS pmImgS, 
                                 List<OPlyIneP> pmInePT, 
                                 OPlyIneS pmInePlh,
                                 BigDecimal pmRskVal,
                                 String pmThpPttTypVal) {
        // obtener listado de intervinientes del nivel correspondiente consultando ISU-17883
        List<OPlyIneP> lvInePT = getPrrBnfTypPTSrv(cnx, 
                                                   pmGniS, 
                                                   pmLobS, 
                                                   pmImgS, 
                                                   pmInePlh,
                                                   pmRskVal,
                                                   pmThpPttTypVal,
                                                   pmInePT);
        // OFRECER ALTERAR intervencion aplicacion conjunto orquestacion (ISU-17023)
        OPlyIneCPT rtIneCPT = srPlyIneAltApl.ochSet(pmGniS.getDelVal(), 
                                                    cnx.getCmpVal(), 
                                                    cnx, 
                                                    pmInePlh.getThpDcmVal(), 
                                                    pmImgS.getAcsDfnDat(), 
                                                    pmImgS.getDelVldDat(), 
                                                    pmImgS.getVldDat(), 
                                                    pmImgS.getSblVldDat(), 
                                                    pmImgS.getFraIdnVal(), 
                                                    lvInePT, 
                                                    CInsCmp.get(CInsConstant.MLC_VAL, cnx.getCmpVal()), 
                                                    pmThpPttTypVal, 
                                                    pmGniS.getPlyVal(), 
                                                    pmLobS.getLobVal(), 
                                                    pmRskVal, 
                                                    pmGniS.getSblVal(), 
                                                    pmInePlh.getThpDcmTypVal(),
                                                    pmGniS.getAplVal(),
                                                    pmGniS.getEnrSqn(),
                                                    pmGniS.getAplEnrSqn(),
                                                    pmGniS.getEnrVal(),
                                                    pmGniS.getEnrSbdVal());
        nwtUtils.isTrmOk(rtIneCPT, "SrPlyIneAltApl.ochSet");
        return rtIneCPT;
    }
    //---------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------
    // Servicio HALLAR intervencion, gestor cobro
    //------------------------------------------------------------------------------
    public OPlyIneP cueCtmSrv(OTrnCnxS cnx, 
                              OPlyGniS pmGniS, 
                              OPidImgS pmImgS) {
        //OFRECER HALLAR intervencion, gestor cobro, defecto (ISU-22871)
        OPlyIneP lvIneCtmP = srPlyIneCue.ctmDfl(cnx.getCmpVal(), 
                                                pmGniS.getPlyVal(), 
                                                CPly.APL_FXD, 
                                                pmGniS.getEnrSqn(), 
                                                pmGniS.getAplEnrSqn(), 
                                                pmImgS.getAcsDfnDat(),
                                                cnx);
        nwtUtils.isTrmOk(lvIneCtmP, "SrPlyIneCue.ctmDfl");
        return lvIneCtmP;
    }
    //------------------------------------------------------------------------------
    // Servicio Validar controles tecnicos informacion general
    //------------------------------------------------------------------------------
    public OPlyUtcCPT vldUtcGniSrv(OTrnCnxS cnx, 
                                   OPlyGniS pmGniS) {
        // OFRECER HALLAR control tecnico emision, informacion general, conjunto (ISU-16043)   
        OPlyUtcCPT rtUtcCPT = srPlyUtcCuePly.gniTbl(cnx.getCmpVal(), 
                                                    pmGniS.getPlyVal(), 
                                                    pmGniS.getEnrSqn(), 
                                                    pmGniS.getAplVal(), 
                                                    pmGniS.getAplEnrSqn(), 
                                                    cnx);
        nwtUtils.isTrmOk(rtUtcCPT, "SrPlyUtcCuePly.gniTbl");
        return rtUtcCPT;
    }
    //------------------------------------------------------------------------------
    // Servicio Validar riesgo
    //------------------------------------------------------------------------------
    public OPlyRskP vldRskDtlSrv(OTrnCnxS cnx, 
                                 OPlyGniS pmGniS, 
                                 OPrtLobS pmLobS, 
                                 OPlyRskP pmRskP) {
        // OFRECER emitir previo riesgo, estado inicial, conjunto (ISU-16069)
        OPlyRskCPT rtRskCPT =  srPlyRskIsuPly.prrItaStsTbl(cnx.getCmpVal(), 
                                                           pmGniS.getPlyVal(), 
                                                           pmGniS.getPlyEfcDat(), 
                                                           pmGniS.getPlyExpDat(), 
                                                           pmLobS.getPocVal(), 
                                                           CTrn.INL_STS, 
                                                           cnx,
                                                           pmLobS.getCerLob());
        nwtUtils.isTrmOk(rtRskCPT, "SrPlyRskIsuPly.prrItaStsTbl");
        // OFRECER EMITIR riesgo(ISU-15595)
        OPlyRskP rtRskP =  srPlyRskIsuPly.rsk(pmRskP , 
                                              pmGniS.getPlyEfcDat(), 
                                              pmGniS.getPlyExpDat(), 
                                              pmLobS.getMulRsk()   , 
                                              cnx);
        nwtUtils.isTrmOk(rtRskP, "SrPlyRskIsuPly.rsk");
        return rtRskP;
    }
    //------------------------------------------------------------------------------
    // Servicio finalizar riesgo
    //------------------------------------------------------------------------------
    public OPlyRskCPT fnsRskSrv(OTrnCnxS cnx, 
                                 OPlyGniS pmGniS, 
                                 OPrtLobS pmLobS, 
                                 OPidImgS pmImgS,
                                 OPlyRskP pmRskP) {
        // OFRECER EMITIR riesgo por riesgo (ISU-16697)
        OPlyRskCPT rtRskCPT = srPlyRskIsuPly.rskTbl(cnx.getCmpVal(), 
                                                    pmGniS.getPlyVal(), 
                                                    pmLobS.getPocVal(), 
                                                    pmGniS.getMnlPre(), 
                                                    pmLobS.getLobVal(), 
                                                    pmImgS.getVldDat(), 
                                                    pmGniS.getCrnVal(), 
                                                    pmGniS.getPlyExpDat(),  // PlyRskIsuQtnCtnSteTwoPrcInpDto.enrExpDat??
                                                    pmRskP, 
                                                    pmLobS.getRskDspPrdNam(), 
                                                    pmImgS.getFraIdnVal(), 
                                                    cnx);
        nwtUtils.isTrmOk(rtRskCPT, "SrPlyRskIsuPly.rskTbl");
        return rtRskCPT;
    }
    //------------------------------------------------------------------------------
    // orquestar validaciones anexos
    //------------------------------------------------------------------------------
    public void vldAnxSrv(OTrnCnxS cnx, 
                           OPlyGniS pmGniS,
                           List<OPlyAnxP> pmAnxPT) {
        // OFRECER EMITIR anexo conjunto (ISU-14831)
        OPlyAnxCPT lvAnxCPT = srPlyAnxIsuPly.tbl(cnx.getCmpVal(), 
                pmGniS.getQtnVal(), 
                pmGniS.getPlyVal(), 
                pmGniS.getEnrSqn(), 
                pmGniS.getAplVal(),
                pmGniS.getAplEnrSqn(), 
                CPid.RSK_PLY, 
                CPly.GNC_ANX, 
                null, // AnxNam 
                isuUtils.getRskAnxTxtVal(pmAnxPT), // Texto anexo riesgo
                pmAnxPT,
                cnx);
        nwtUtils.isTrmOk(lvAnxCPT, "SrPlyAnxIsuPly.tbl");
    } 
    //------------------------------------------------------------------------------
    // ISI-16699 - servicio BE poliza y presupuesto para consulta PSYPD	pricing dinamico
    //------------------------------------------------------------------------------
    public void dnpPicPSYPD(OTrnCnxS cnx,
                            OPlyGniS pmGniS, 
                            OPidImgS pmImgS,
                            OPlyRskS pmRskS,
                            List<OPlyCvcPC> pmCvcCT) {
        OTrnPrcS prcS = nwtPlyCvcSbtDnpPic.isuPlyQtnPSYPD(pmCvcCT, 
                                                          cnx, 
                                                          pmImgS.getAcsDfnDat(), 
                                                          pmRskS.getMdtVal(), 
                                                          new BigDecimal(1), 
                                                          pmGniS.getPlyVal(), 
                                                          pmRskS.getRskVal(),
                                                          CCmn.OPR_IDN_ISU_PLY,
                                                          pmImgS.getFraIdnVal(),
                                                          pmImgS.getVldDat());
        nwtUtils.isTrmOk(prcS, prcS.getGetNam());
    }
}
