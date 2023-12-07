package com.mapfre.tron.api.cmn.ply.gni.bl.impl.tw;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.tpd.c.CTpd;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.aed.sr.ISrCmnAedQry;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniP;
import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaCPT;
import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaP;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.nwt.trn.prt.lob.sr.ISrPrtLobQry;
import com.mapfre.tron.api.cache.GlobalModule;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.ply.gni.bl.IBlPlyGniQry;
import com.mapfre.tron.api.cmn.ply.gni.dl.ISrPlyGniQryDAO;
import com.mapfre.tron.api.cmn.ply.ina.bl.IBlPlyInaQry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The commons tronweb PlyGniQry business service interface implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 dic. 2020 - 14:37:50
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper=false)
public class TwCmnBlPlyGniQryImpl extends TwBlCmnBase implements IBlPlyGniQry {

    /** The PlyGniQry repository.*/
    @Autowired
    protected ISrPlyGniQryDAO iSrPlyGniQryDAO;
    
    /** The PlyInaQry commons business service.*/
    @Autowired
    protected IBlPlyInaQry iBlPlyInaQry;

    /** The PrtLobQry gaia service.*/
    @Autowired
    protected ISrPrtLobQry iSrPrtLobQry;

    /** The CmnAedQry gaia service.*/
    @Autowired 
    protected ISrCmnAedQry iSrCmnAedQry;

    @Autowired
    protected GlobalModule globalModule;

    /**
     * Query policy general information valid for a date.
     *
     * @author arquitectura - pvraul1
     * 
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param plyVal    -> Policy value
     * @param aplVal    -> Application value
     * @param qryDat    -> Query date
     * @param exlPrvVal    -> Exclude provisional values
     * @return OPlyGniP -> General information response
     */
    @Override
    public OPlyGniP opvDat(final BigDecimal cmpVal, final String usrVal, final String lngVal, final String plyVal,
            final BigDecimal aplVal, final Date qryDat, final String exlPrvVal) {

        log.info("Tronweb business logic implementation IBlPlyGniQry have been called...");

        // reseting session
        resetSession();

        try {
            OPlyGniP lvOPlyGniP = iSrPlyGniQryDAO.opvDat(cmpVal, plyVal, aplVal, qryDat, CTrn.GET_NAM_ALL, CTrn.REA_ORG,
                    lngVal, exlPrvVal);

            if (lvOPlyGniP != null && lvOPlyGniP.getOPlyGniS() != null) {
                getSdrCrnVal(cmpVal, lvOPlyGniP);
                getLobNam(cmpVal, lvOPlyGniP);
                getSecNam(cmpVal, lvOPlyGniP);
                getPmsNam(cmpVal, lngVal, lvOPlyGniP);
                getCptThrLvlNam(cmpVal, lvOPlyGniP);
                getCmpNam(cmpVal, lvOPlyGniP);
                getPlyDrtNam(cmpVal, lngVal, lvOPlyGniP);
                getCinTypNam(cmpVal, lngVal, lvOPlyGniP);
                getEnrTypNam(cmpVal, lngVal, lvOPlyGniP);
                getEnrCasNam(cmpVal, lngVal, lvOPlyGniP);
                getRglTypNam(cmpVal, lngVal, lvOPlyGniP);
                getTnrPlyTypNam(cmpVal, lngVal, lvOPlyGniP);
                getRnsTypNam(cmpVal, lngVal, lvOPlyGniP);
            }

            String pmOrgTypVal = CTrn.REA_ORG;
            String pmGetNamTypVal = CTrn.GET_NAM_NNE;
            OPlyInaCPT lvOPlyInaCPT = iBlPlyInaQry.plyTbl(
                    lngVal,
                    usrVal,
                    cmpVal.intValueExact(),
                    plyVal,
                    aplVal.intValueExact(),
                    pmOrgTypVal,
                    pmGetNamTypVal);

            // recorrer el INA
            BigDecimal lvCodAgt = null;
            BigDecimal lvCodNivel1 = null;
            BigDecimal lvCodNivel2 = null;
            BigDecimal lvCodNivel3 = null;

            for (OPlyInaP lvOPlyInaP : lvOPlyInaCPT.getOPlyInaPT()) {
                // se evalua actuación
                if (CTpd.MPR_PDR.equals(lvOPlyInaP.getOPlyInaS().getItcVal())) {
                    // Si actuacion es 'Productor/Principal' (c_tpd.mpr_pdr)
                    // codigo agente toma el valor de intervencion agente conjunto(posicion).tercero
                    lvCodAgt = lvOPlyInaP.getOPlyInaS().getThpVal();
                    // primer nivel toma el valor de intervencion agente conjunto(posicion).primer
                    // nivel
                    lvCodNivel1 = lvOPlyInaP.getOPlyInaS().getFrsLvlVal();
                    // segundo nivel toma el valor de intervencion agente conjunto(posicion).segundo
                    // nivel
                    lvCodNivel2 = lvOPlyInaP.getOPlyInaS().getScnLvlVal();
                    // tercer nivel toma el valor de intervencion agente conjunto(posicion).tercer
                    // nivel
                    lvCodNivel3 = lvOPlyInaP.getOPlyInaS().getThrLvlVal();
                }

            }

            int lobVal = 0;
            if (lvOPlyGniP.getOPlyGniS() != null && lvOPlyGniP.getOPlyGniS().getLobVal() != null) {
                lobVal = lvOPlyGniP.getOPlyGniS().getLobVal().intValue();
            }
            OPrtLobS oPrtLobS = globalModule.tablaRamo(cmpVal.intValue(), usrVal, lngVal, lobVal);

            // Llamo al módulo validacionRegistroConAcceso con los parámetros de entrada
            // (usrVal, oLssLsiS.cmpVal, oLssLsiS.frsLvlVal, oLssLsiS.scnLvlVal,
            // oLssLsiS.thrLvlVal, oLssLsiS.agnVal, oLssLsiS.secVal, oPrtLobS.sbsVal,
            // oLssLsiS.lobVal)
            boolean isRegistered = globalModule.validacionRegistroConAcceso(usrVal,
                    cmpVal,
                    lvCodNivel1,
                    lvCodNivel2,
                    lvCodNivel3,
                    lvCodAgt,
                    lvOPlyGniP.getOPlyGniS().getSecVal(),
                    oPrtLobS.getSbsVal(),
                    lvOPlyGniP.getOPlyGniS().getLobVal());

            if (!isRegistered) {
        	getErrorNotRegistered(lngVal, cmpVal);
            }

            return lvOPlyGniP;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "GNI", cmpVal);
        }

        return null;
    }

    @Override
    public OPlyGniP opvDatBool(final BigDecimal cmpVal, final String usrVal, final String lngVal, final String plyVal,
            final BigDecimal aplVal, final Date qryDat, final boolean cache) {

        return this.opvDat(cmpVal, usrVal, lngVal, plyVal, aplVal, qryDat, null);
    }

}
