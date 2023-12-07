package com.mapfre.tron.sfv.pgm.beans.impl.cm;

import com.mapfre.tron.api.thp.client.model.OThpPrsP;

public interface ThpApiProxy {
	public OThpPrsP thirdPartybyActivity(Integer cmpVal, String thpDcmTypVal, String thpDcmVal, String usrVal, Integer thpAcvVal, String lngVal);
}
