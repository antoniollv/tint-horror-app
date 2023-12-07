package com.mapfre.tron.api.cmn.bl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypP;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniP;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.nwt.trn.rcd.tmd.bo.ORcdTmdS;
import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cache.CacheableAttribute;
import com.mapfre.tron.api.cmn.ResetPacakge;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.extern.slf4j.Slf4j;

/**
 * The Tronweb Commons base business class.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 20 Oct 2022 - 09:25:07
 *
 */
@Slf4j
public class TwBlCmnBase {

    private static final String ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_SEC_NAM = "Error calling cacheableAttribute.getSecNam: ";

    protected static final String ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_O_CMN_TYP_S = "Error calling cacheableAttribute.getOCmnTypS: ";

    /** The TIP_DURACION constant.*/
    protected static final String TIP_DURACION = "TIP_DURACION";

    /** The TIP_COASEGURO constant.*/
    protected static final String TIP_COASEGURO = "TIP_COASEGURO";

    /** The TIP_POLIZA_TR constant.*/
    protected static final String TIP_POLIZA_TR = "TIP_POLIZA_TR";

    /** The TIP_REA constant.*/
    protected static final String TIP_REA = "TIP_REA";

    /** The TIP_REGULARIZA_ESP constant.*/
    protected static final String TIP_REGULARIZA_ESP = "TIP_REGULARIZA_ESP";

    /** The TIP_SPTO constant.*/
    protected static final String TIP_SPTO = "TIP_SPTO";

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The newtron nof found code. */
    protected static final int ERROR_CODE_20018 = 20018;
    
    /** The resetPackage property. */
    @Autowired
    protected ResetPacakge resetPackage;

    /** The error respository */
    @Autowired
    protected DlTrnErr lvDlTrnErr;

    @Autowired
    protected CacheableAttribute cacheableAttribute;

    @Value("${default.app.cmpVal}")
    protected BigDecimal cmpValDefault;

    @Value("${default.app.language:EN}")
    protected String lngDefault;

    /** Reset the session. */
    protected void resetSession() {
        if (log.isDebugEnabled()) {
            log.debug("Reseting session...");
        }
        resetPackage.executeRP();
        if (log.isDebugEnabled()) {
            log.debug("The session has been reset.");
        }
    }
    
    protected void getErrorWithPrpIdn(final String lngVal, final String temVal, final String prpIdn, final BigDecimal cmpVal) throws NwtException {
    	BigDecimal codError = new BigDecimal(ERROR_CODE);
		OTrnErrS error = lvDlTrnErr.getError(codError, lngVal, temVal, prpIdn, cmpVal);
		NwtException exception = new NwtException(error.getErrIdnVal());
		exception.add(error);
		throw exception;
    }

    protected void getErrorWithoutPrpIdn(final String lngVal, final String temVal, final BigDecimal pmCmpVal)
            throws NwtException {

        BigDecimal codError = new BigDecimal(ERROR_CODE);
        OTrnErrS error = lvDlTrnErr.getErrorWithoutPrpIdn(codError, lngVal, temVal, pmCmpVal);
        NwtException exception = new NwtException(error.getErrIdnVal());
        exception.add(error);
        throw exception;
    }
    

    protected void getError(final String lngVal, final BigDecimal pmCmpVal)
            throws NwtException {

        OTrnErrS error = lvDlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, pmCmpVal);
        NwtException exception = new NwtException(error.getErrIdnVal());
        exception.add(error);
        throw exception;
    }
    
    protected void getErrorNotRegistered(final String lngVal, final BigDecimal pmCmpVal)
            throws NwtException {

        OTrnErrS error = lvDlTrnErr.getError(new BigDecimal(ERROR_CODE_20018), lngVal, pmCmpVal);
        NwtException exception = new NwtException(error.getErrIdnVal());
        exception.add(error);
        throw exception;
    }

    protected void getCtmCssTypNam(final String lngVal, BigDecimal cmpValValue, List<ORcdTmdS> lvORcdTmdS) {
        try {
            for (ORcdTmdS oRcdTmdS : lvORcdTmdS) {
                OCmnTypS lvOCmnTypS = cacheableAttribute.getOCmnTypS("TIP_CLASE_GESTOR", oRcdTmdS.getCtmCssTypVal(),
                        lngVal, cmpValValue);
                if (lvOCmnTypS != null) {
                    oRcdTmdS.setCtmCssTypNam(lvOCmnTypS.getItcNam());
                }
            }
        } catch (Exception e) {
            log.debug("No result for the G1010031 table");
        }
    }

    protected void getCtmCssTypNam(String lngVal, BigDecimal cmpValValue, ORcdTmdS lvORcdTmdS) {
        try {
            OCmnTypS lvOCmnTypS = cacheableAttribute.getOCmnTypS("TIP_CLASE_GESTOR", lvORcdTmdS.getCtmCssTypVal(),
                    lngVal, cmpValValue);

            if (lvOCmnTypS != null) {
                lvORcdTmdS.setCtmCssTypNam(lvOCmnTypS.getItcNam());
            }

        } catch (Exception e) {
            log.debug("No result for the G1010031 table");
        }
    }

    protected void setTwnNam(final Integer cmpVal, final String usrVal, final String lngVal, OGrsZnfS lvOGrsZnfS) {
        try {
            lvOGrsZnfS.setTwnNam(cacheableAttribute.getZoneFour(new BigDecimal(cmpVal), usrVal, lngVal,
                    lvOGrsZnfS.getCnyVal(), lvOGrsZnfS.getPvcVal(), lvOGrsZnfS.getTwnVal()).getTwnNam());
        } catch (Exception e) {
            log.error("Error calling cacheableAttribute.getZoneFour: " + e.getMessage());
        }
    }

    protected void setPvcNam(final Integer cmpVal, final String lngVal, OGrsZnfS lvOGrsZnfS) {
        try {
            lvOGrsZnfS.setPvcNam(cacheableAttribute
                    .getZoneThree(lvOGrsZnfS.getCnyVal(), lvOGrsZnfS.getPvcVal(), lngVal, new BigDecimal(cmpVal))
                    .getPvcNam());
        } catch (Exception e) {
            log.error("Error calling cacheableAttribute.getZoneThree: " + e.getMessage());
        }
    }

    protected void setSttNam(final Integer cmpVal, final String usrVal, final String lngVal, OGrsZnfS lvOGrsZnfS) {
        try {
            lvOGrsZnfS.setSttNam(cacheableAttribute
                    .getZoneTwo(new BigDecimal(cmpVal), usrVal, lngVal, lvOGrsZnfS.getCnyVal(), lvOGrsZnfS.getSttVal())
                    .getSttNam());
        } catch (Exception e) {
            log.error("Error calling cacheableAttribute.getZoneTwo: " + e.getMessage());
        }
    }

    protected void setCnyNam(final Integer cmpVal, final String usrVal, final String lngVal, OGrsZnfS lvOGrsZnfS) {
        try {
            lvOGrsZnfS.setCnyNam(cacheableAttribute
                    .getZoneOne(new BigDecimal(cmpVal), usrVal, lngVal, lvOGrsZnfS.getCnyVal()).getCnyNam());
        } catch (Exception e) {
            log.error("Error calling cacheableAttribute.getZoneOne: " + e.getMessage());
        }
    }

    protected void getSecNam(final Integer cmpVal, OPrtLobS oPrtLobS2) {
        try {
            oPrtLobS2.setSecNam(
                    cacheableAttribute.getSecNam(BigDecimal.valueOf(cmpVal), oPrtLobS2.getSecVal()));
        } catch (Exception e) {
            log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_SEC_NAM + e.getMessage());
        }
    }

    protected void getSecNam(BigDecimal pmCmpVal, OPrtSbsS oPrtSbsS) {
        try {
            oPrtSbsS.setSecNam(cacheableAttribute.getSecNam(pmCmpVal, oPrtSbsS.getSecVal()));
        } catch (Exception e) {
            log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_SEC_NAM + e.getMessage());
        }
    }

    protected void getRnsTypNam(final BigDecimal cmpVal, final String lngVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS().setRnsTypNam(cacheableAttribute
                    .getOCmnTypS(TIP_REA, lvOPlyGniP.getOPlyGniS().getRnsTypVal(), lngVal, cmpVal).getItcNam());

        } catch (Exception e) {
            log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_O_CMN_TYP_S + e.getMessage());
        }
    }

    protected void getTnrPlyTypNam(final BigDecimal cmpVal, final String lngVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS()
                    .setTnrPlyTypNam(cacheableAttribute
                            .getOCmnTypS(TIP_POLIZA_TR, lvOPlyGniP.getOPlyGniS().getTnrPlyTypVal(), lngVal, cmpVal)
                            .getItcNam());
        } catch (Exception e) {
            log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_O_CMN_TYP_S + e.getMessage());
        }
    }

    protected void getRglTypNam(final BigDecimal cmpVal, final String lngVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS()
                    .setRglTypNam(cacheableAttribute
                            .getOCmnTypS(TIP_REGULARIZA_ESP, lvOPlyGniP.getOPlyGniS().getRglTypVal(), lngVal, cmpVal)
                            .getItcNam());
        } catch (Exception e) {
            log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_O_CMN_TYP_S + e.getMessage());
        }
    }

    protected void getEnrCasNam(final BigDecimal cmpVal, final String lngVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS().setEnrCasNam(cacheableAttribute
                    .getOCmnTypS(TIP_SPTO, lvOPlyGniP.getOPlyGniS().getEnrTypVal(), lngVal, cmpVal).getItcNam());
        } catch (Exception e) {
            log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_O_CMN_TYP_S + e.getMessage());
        }
    }

    protected void getEnrTypNam(final BigDecimal cmpVal, final String lngVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS().setEnrTypNam(cacheableAttribute
                    .getOCmnTypS(TIP_SPTO, lvOPlyGniP.getOPlyGniS().getEnrTypVal(), lngVal, cmpVal).getItcNam());
        } catch (Exception e) {
            log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_O_CMN_TYP_S + e.getMessage());
        }
    }

    protected void getCinTypNam(final BigDecimal cmpVal, final String lngVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS().setCinTypNam(cacheableAttribute
                    .getOCmnTypS(TIP_COASEGURO, lvOPlyGniP.getOPlyGniS().getCinTypVal(), lngVal, cmpVal).getItcNam());
        } catch (Exception e) {
            log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_O_CMN_TYP_S + e.getMessage());
        }
    }

    protected void getPlyDrtNam(final BigDecimal cmpVal, final String lngVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS().setPlyDrtNam(cacheableAttribute
                    .getOCmnTypS(TIP_DURACION, lvOPlyGniP.getOPlyGniS().getPlyDrtVal(), lngVal, cmpVal).getItcNam());
        } catch (Exception e) {
            log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_O_CMN_TYP_S + e.getMessage());
        }
    }

    protected void getCmpNam(final BigDecimal cmpVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS().setCmpNam(cacheableAttribute.getCmpNam(cmpVal));
        } catch (Exception e) {
            log.error("Error calling cacheableAttribute.setCmpNam: " + e.getMessage());
        }
    }

    protected void getCptThrLvlNam(final BigDecimal cmpVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS().setCptThrLvlNam(
                    cacheableAttribute.getCptThrLvlNam(cmpVal, lvOPlyGniP.getOPlyGniS().getCptThrLvlVal()));
        } catch (Exception e) {
            log.error("Error calling cacheableAttribute.getCptThrLvlNam: " + e.getMessage());
        }
    }

    protected void getPmsNam(final BigDecimal cmpVal, final String lngVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS()
                    .setPmsNam(cacheableAttribute.getPmsNam(cmpVal, lvOPlyGniP.getOPlyGniS().getPmsVal(), lngVal));
        } catch (Exception e) {
            log.error("Error calling cacheableAttribute.getPmsNam: " + e.getMessage());
        }
    }

    protected void getSecNam(final BigDecimal cmpVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS()
                    .setSecNam(cacheableAttribute.getSecNam(cmpVal, lvOPlyGniP.getOPlyGniS().getSecVal()));
        } catch (Exception e) {
            log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_SEC_NAM + e.getMessage());
        }
    }

    protected void getLobNam(final BigDecimal cmpVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS()
                    .setLobNam(cacheableAttribute.getLobNam(cmpVal, lvOPlyGniP.getOPlyGniS().getLobVal()));
        } catch (Exception e) {
            log.error("Error calling cacheableAttribute.getLobNam: " + e.getMessage());
        }
    }

    protected void getSdrCrnVal(final BigDecimal cmpVal, OPlyGniP lvOPlyGniP) {
        try {
            lvOPlyGniP.getOPlyGniS()
                    .setSdrCrnVal(cacheableAttribute.getSdrCrnVal(lvOPlyGniP.getOPlyGniS().getCrnVal(), cmpVal));
        } catch (Exception e) {
            log.error("Error calling cacheableAttribute.getSdrCrnVal: " + e.getMessage());
        }
    }

    protected static OCmnTypP getOCmnTypS(OCmnTypS oCmnTypS) {
        OCmnTypP oCmnTypP = new OCmnTypP();
        oCmnTypP.setOCmnTypS(oCmnTypS);

        return oCmnTypP;
    }


    protected void getOThpAdrSDescriptions(final Integer cmpVal, final String lngVal, final String usrVal,
	    OThpAdrS oThpAdrS) {
	try {
	oThpAdrS.setCmlCmpNam(cacheableAttribute.getCmpNam(new BigDecimal(cmpVal)));
	} catch (Exception e) {
	log.error("Error calling cacheableAttribute.getCmpNam: " + e.getMessage());
	}
	try {
	oThpAdrS.setTwnNam(cacheableAttribute.getZoneFour(new BigDecimal(cmpVal), usrVal, lngVal,
		oThpAdrS.getCnyVal(), oThpAdrS.getPvcVal(), oThpAdrS.getTwnVal()).getTwnNam());
	} catch (Exception e) {
	log.error("Error calling cacheableAttribute.getZoneFour: " + e.getMessage());
	}
	try {
	oThpAdrS.setPvcNam(cacheableAttribute.getZoneThree(oThpAdrS.getCnyVal(), oThpAdrS.getPvcVal(),
		lngVal, new BigDecimal(cmpVal)).getPvcNam());
	} catch (Exception e) {
	log.error("Error calling cacheableAttribute.getZoneThree: " + e.getMessage());
	}
	try {
	oThpAdrS.setCnyNam(cacheableAttribute
		.getZoneOne(new BigDecimal(cmpVal), usrVal, lngVal, oThpAdrS.getCnyVal()).getCnyNam());
	} catch (Exception e) {
	log.error("Error calling cacheableAttribute.getZoneOne: " + e.getMessage());
	}
	try {
	oThpAdrS.setDmlTypNam(cacheableAttribute.getOCmnTypS("TIP_DOMICILIO", oThpAdrS.getDmlTypVal(),
		lngVal, new BigDecimal(cmpVal)).getItcNam());
	} catch (Exception e) {
	log.error(ERROR_CALLING_CACHEABLE_ATTRIBUTE_GET_O_CMN_TYP_S + e.getMessage());
	}
    }
}
