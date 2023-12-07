package com.mapfre.tron.api.cmn.tpd.snf.bl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.tpd.snf.bo.OTpdSnfS;


@Service
public interface IBlTpdSnfQry {
	
	List<OTpdSnfS> getSupplierNotifDefByOprGnr(BigDecimal cmpVal, String oprIdnVal, String gnrTypVal);


}
