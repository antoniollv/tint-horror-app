package com.mapfre.tron.api.cmn.mvd.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;

public interface IBlCmnMvdCrt {

	String putSupplierNotificationComplete(BigDecimal cmpVal, String usrVal, String lngVal, Integer thpAcvVal,
			String thpDcmTypVal, String thpDcmVal, String svoVal, Integer sswVal, String oprIdnVal, String gnrTypVal,
			BigDecimal lssVal, String plyVal, Integer rskVal, List<OTrnVrbS> oTrnVrbS);

}
