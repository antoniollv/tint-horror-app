package com.mapfre.tron.api.trn.err.dl.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.gls.bo.OTrnGlsS;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.err.dl.OTrnErrDao;
import com.mapfre.tron.api.trn.err.dl.OTrnErrPK;
import com.mapfre.tron.api.trn.gls.dl.OTrnGlsDao;
import com.mapfre.tron.api.trn.gls.dl.OTrnGlsPK;
import com.mapfre.tron.api.trn.prp.dl.OTrnPrpDao;
import com.mapfre.tron.api.trn.prp.dl.OTrnPrpPK;

@Service
public class DlTrnErrImpl implements DlTrnErr {

    @Autowired protected OTrnErrDao oTrnErrDao;
    @Autowired protected OTrnGlsDao oTrnGlsDao;
    @Autowired protected OTrnPrpDao oTrnPrpDao;
    
    @Override
    //@Cacheable("Error")
    public OTrnErrS getError(BigDecimal codMensaje, String lngVal, String temVal, String prpIdn, BigDecimal cmpVal) {
	assert codMensaje != null && lngVal != null;
	
	OTrnErrS oTrnErrSDao = oTrnErrDao.get(new OTrnErrPK(codMensaje, lngVal, cmpVal));
	OTrnGlsS oTrnGlsSDao = new OTrnGlsS();
	OTrnGlsS oTrnPrpSDao = new OTrnGlsS();
	if (temVal != null)
	     oTrnGlsSDao = oTrnGlsDao.get(new OTrnGlsPK(temVal, lngVal));
	if (prpIdn != null)
	    oTrnPrpSDao = oTrnPrpDao.get(new OTrnPrpPK(prpIdn, lngVal));
	String errIdnVal = this.getDescriptionError(oTrnErrSDao, oTrnGlsSDao, oTrnPrpSDao);
	
	return new OTrnErrS(null, errIdnVal, oTrnErrSDao.getErrVal(), oTrnErrSDao.getErrNam(), oTrnPrpSDao.getPrpIdn());
	
    }
    
    
    private String getDescriptionError(OTrnErrS oTrnErrSDao, OTrnGlsS oTrnGlsSDao, OTrnGlsS oTrnPrpSDao) {
	StringBuilder errIdnVal = new StringBuilder();
	errIdnVal.append(oTrnErrSDao.getErrVal())
		 .append(":");
	if (oTrnGlsSDao.getTemTxtT() != null && !oTrnGlsSDao.getTemTxtT().isEmpty()) {
	    errIdnVal.append(" ")
	             .append(oTrnGlsSDao.getTemTxtT().get(0).getTxtVal());
	}
	if (oTrnPrpSDao.getTemTxtT() != null && !oTrnPrpSDao.getTemTxtT().isEmpty()) {
	    errIdnVal.append(" ")
	    	     .append(oTrnPrpSDao.getTemTxtT().get(0).getTxtVal());
	}
	    errIdnVal.append(" ")
	    	     .append(oTrnErrSDao.getErrNam());
	return errIdnVal.toString();
    }


    @Override
    public OTrnErrS getErrorWithoutPrpIdn(BigDecimal codMensaje, String lngVal, String temVal, BigDecimal cmpVal) {
	return this.getError(codMensaje, lngVal, temVal, null, cmpVal);
    }


    @Override
    public OTrnErrS getErrorWithoutTemVal(BigDecimal codMensaje, String lngVal, String prpIdn, BigDecimal cmpVal) {
	return this.getError(codMensaje, lngVal, null, prpIdn, cmpVal);
    }


    @Override
    public OTrnErrS getError(BigDecimal codMensaje, String lngVal, BigDecimal cmpVal) {
	return this.getError(codMensaje, lngVal, null, null, cmpVal);
    }

}
