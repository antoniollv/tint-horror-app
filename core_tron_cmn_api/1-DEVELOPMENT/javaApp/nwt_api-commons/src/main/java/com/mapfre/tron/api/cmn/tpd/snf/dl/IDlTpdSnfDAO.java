package com.mapfre.tron.api.cmn.tpd.snf.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.tpd.snf.bo.OTpdSnfS;

public interface IDlTpdSnfDAO {
	
	List<OTpdSnfS> getSupplierNotifDefByOprGnr(BigDecimal cmpVal, String oprIdnVal, String gnrTypVal);

}
