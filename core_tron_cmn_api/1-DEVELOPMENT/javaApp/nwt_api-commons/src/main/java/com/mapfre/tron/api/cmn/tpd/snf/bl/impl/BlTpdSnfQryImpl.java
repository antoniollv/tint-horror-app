package com.mapfre.tron.api.cmn.tpd.snf.bl.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.tpd.snf.bo.OTpdSnfS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.tpd.snf.bl.IBlTpdSnfQry;
import com.mapfre.tron.api.cmn.tpd.snf.dl.IDlTpdSnfDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j	
@Service
public class BlTpdSnfQryImpl extends TwBlCmnBase implements IBlTpdSnfQry{
	
	
	/** The repository.*/
    @Autowired
    protected IDlTpdSnfDAO iDlThpSnfDAO;
	

	@Override
	public List<OTpdSnfS> getSupplierNotifDefByOprGnr(BigDecimal cmpVal, String oprIdnVal, String gnrTypVal) {
	
		log.info("Tronweb business logic implementation getSupplierNotifDefByOprGnr have been called...");
		
		return iDlThpSnfDAO.getSupplierNotifDefByOprGnr(cmpVal, oprIdnVal, gnrTypVal);		

		
	}

}
