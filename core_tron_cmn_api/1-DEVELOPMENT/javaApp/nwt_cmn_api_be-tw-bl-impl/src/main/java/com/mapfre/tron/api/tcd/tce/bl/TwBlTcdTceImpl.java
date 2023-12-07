package com.mapfre.tron.api.tcd.tce.bl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.tcd.tce.bo.OTcdTceS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.tcd.tce.dl.IDlTcdTceDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlTcdTceImpl extends TwBlCmnBase implements IBlTcdTce {

    @Autowired
    protected IDlTcdTceDAO iDlTcdTceDAO;

    /**
     * Query Technical Control Definition
     *
     * @author Javier Sangil
     * 
     * 
     * @param cmpVal -> company code
     * @param usrVal -> user value
     * @param lngVal -> language value
     * @param errValList -> Error List Value
     * 
     * @return List<OTcdTceS>
     * 
     */
    @Override
    public List<OTcdTceS> getTechnicalControlDefinition(Integer cmpVal, String lngVal, String usrVal,
	    List<String> errValList) {

	if (log.isInfoEnabled()) {
	    log.info("The tronweb getTechnicalControlDefinition service implementation had been called.");
	}
	BigDecimal pmCmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

	// resetting session
	resetSession();

	try {
	    List<OTcdTceS> lvOTcdTceS = iDlTcdTceDAO.getTechnicalControlDefinition(pmCmpVal, usrVal, lngVal, errValList);
	    
	    
            if (lvOTcdTceS == null || lvOTcdTceS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }
            return lvOTcdTceS;
            
	} catch (IncorrectResultSizeDataAccessException e) {
	    getErrorWithoutPrpIdn(lngVal, "TCE", new BigDecimal(cmpVal));
	}

	return null;
    }

}
