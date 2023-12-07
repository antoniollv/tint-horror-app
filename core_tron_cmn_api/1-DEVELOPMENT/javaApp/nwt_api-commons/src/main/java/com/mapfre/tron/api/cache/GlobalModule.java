package com.mapfre.tron.api.cache;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mapfre.nwt.cmn.c.CCmn;
import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.thp.c.CThp;
import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.cache.tau.bl.IBlCacheTau;
import com.mapfre.tron.api.cmn.prt.lob.bl.IBlCacDelQry;
import com.mapfre.tron.api.cmn.prt.lob.bl.IBlPrtLobQry;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobQryPK;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GlobalModule {

    /** The PrtLobQry business interface. */
    @Autowired
    protected IBlPrtLobQry iBLPrtLobQry;

    @Autowired
    protected IBlCacheTau iBlCacheTau;
    
    
    @Autowired
    protected IBlCacDelQry iBlCacDelQry;
    
    

    /**
     * Call getLineOfBusiness service
     * 
     * @author Cristian Saball
     * @param cmpVal ->
     * @param usrVal -> User code
     * @param lngVal -> Language
     * @param lobVal ->
     * @return OPrtLobS
     */
    public OPrtLobS tablaRamo(Integer cmpVal, String usrVal, String lngVal, Integer lobVal) {
	
	log.info("Tabla ramo has been called...");
	
	BigDecimal lobValBd = (lobVal != null) ? new BigDecimal(lobVal) : null;
	BigDecimal cmpValBd = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

	return iBLPrtLobQry.lob(OPrtLobQryPK.builder().lobVal(lobValBd).cmpVal(cmpValBd).build());

    }

    /**
     * Query Acceso table by user val and return a list
     * 
     * @author Cristian Saball
     * @param usrVal -> User code
     * @return List<OCmnAedS>
     */
    public List<OCmnAedS> tablaAccesoPorUsuario(String usrVal) {
	
	log.info("Tabla acceso por usuario has been called...");

	return iBlCacheTau.tabAccPorUsu(usrVal);

    }

    /**
     * 
     * 
     * @author Cristian Saball
     * 
     * @param usrVal    -> User code
     * @param cmpVal    -> Company code
     * @param frsLvlVal -> First level
     * @param scnLvlVal -> Second level
     * @param thrLvlVal -> Third level
     * @param agnVal    -> Agent intervention
     * @param secVal    -> Sector
     * @param sbsVal    -> Sub-Sector
     * @param lobVal    -> Ramo
     * @return boolean
     */
    public boolean validacionRegistroConAcceso(String usrVal, BigDecimal cmpVal, BigDecimal frsLvlVal,
	    BigDecimal scnLvlVal, BigDecimal thrLvlVal, BigDecimal agnVal, BigDecimal secVal, BigDecimal sbsVal,
	    BigDecimal lobVal) {

	List<OCmnAedS> lvOCmnAedS = tablaAccesoPorUsuario(usrVal);

	if (lvOCmnAedS != null && !lvOCmnAedS.isEmpty()) {
	    for (OCmnAedS oCmnAedS : lvOCmnAedS) {
		if (oCmnAedS.getCmpVal() != null && oCmnAedS.getCmpVal().compareTo(cmpVal) == 0
			&& (oCmnAedS.getFrsLvlVal().compareTo(frsLvlVal) == 0
				|| oCmnAedS.getFrsLvlVal().compareTo(CCmn.GNC_FSL_VAL) == 0)
			&& (oCmnAedS.getScnLvlVal().compareTo(scnLvlVal) == 0
				|| oCmnAedS.getScnLvlVal().compareTo(CCmn.GNC_SNL_VAL) == 0)
			&& (oCmnAedS.getThrLvlVal().compareTo(thrLvlVal) == 0
				|| oCmnAedS.getThrLvlVal().compareTo(CCmn.GNC_THL_VAL) == 0)
			&& (oCmnAedS.getAgnVal().compareTo(agnVal) == 0
				|| oCmnAedS.getAgnVal().compareTo(CThp.GNC_AGN_VAL) == 0)
			&& (oCmnAedS.getSecVal().compareTo(secVal) == 0
				|| oCmnAedS.getSecVal().compareTo(CPrt.GNC_SEC_VAL) == 0)
			&& (oCmnAedS.getSbsVal().compareTo(sbsVal) == 0
				|| oCmnAedS.getSbsVal().compareTo(CPrt.GNC_SBS_VAL) == 0)
			&& (oCmnAedS.getLobVal().compareTo(lobVal) == 0
				|| oCmnAedS.getLobVal().compareTo(CPrt.GNC_LOB_VAL) == 0)) {
		    return true;
		}
	    }
	}
	return false;
    }

    public boolean validacionRegistroConAccesoAgt(final String usrVal, final BigDecimal cmpVal,
            final BigDecimal agnVal) {

        List<OCmnAedS> lvOCmnAedS = tablaAccesoPorUsuario(usrVal);

        if (lvOCmnAedS != null && !lvOCmnAedS.isEmpty()) {
            for (OCmnAedS oCmnAedS : lvOCmnAedS) {
                if (oCmnAedS.getCmpVal() != null && oCmnAedS.getCmpVal().compareTo(cmpVal) == 0
                        && (oCmnAedS.getAgnVal().compareTo(agnVal) == 0
                                || oCmnAedS.getAgnVal().compareTo(CThp.GNC_AGN_VAL) == 0)) {
                    return true;
                }
            }
        }

        return false;
    }

}
