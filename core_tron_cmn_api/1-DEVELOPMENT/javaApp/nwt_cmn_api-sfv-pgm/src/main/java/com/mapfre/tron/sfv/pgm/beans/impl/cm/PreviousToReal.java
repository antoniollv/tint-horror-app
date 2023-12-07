package com.mapfre.tron.sfv.pgm.beans.impl.cm;

import java.math.BigDecimal;

import com.mapfre.tron.api.isu.client.model.InNewCompleteIssue1;
import com.mapfre.tron.api.isu.client.model.OPlyGniS;
import com.mapfre.tron.api.isu.client.model.OPlyPlyC;

public interface PreviousToReal {

	public void callPreviousToReal(OPlyPlyC oPlyPlyC, BigDecimal cmpVal, String usrVal, String lngVal);
	
	public OPlyGniS callQuotationQuotation2(InNewCompleteIssue1 o, BigDecimal cmpVal, String usrVal, String lngVal);
	
	public OPlyGniS callPolicyQuotation2(InNewCompleteIssue1 o, BigDecimal cmpVal, String usrVal, String lngVal);
}
