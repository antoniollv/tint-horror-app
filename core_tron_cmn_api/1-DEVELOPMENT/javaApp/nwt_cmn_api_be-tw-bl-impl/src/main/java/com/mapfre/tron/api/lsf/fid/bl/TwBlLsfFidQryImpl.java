package com.mapfre.tron.api.lsf.fid.bl;

import java.math.BigDecimal;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.lsf.fid.bo.OLsfFidS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The IBlLsfFidQry business logic service implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 9 feb 2023 - 17:49:28
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlLsfFidQryImpl extends TwBlCmnBase implements IBlLsfFidQry {

    
    
    /**
     * Query file type definition
     *
     * @author Javier Sangil
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param filTypVal -> File Type Value
     * 
     * @return OLsfFidS -> File type defintion
     */
    @Override
    public OLsfFidS getFileTypeDefinition(Integer cmpVal, String usrVal, String lngVal,
	    String filTypVal) {
	log.debug("Tronweb business logic implementation getTbl have been called...");

        // reseting session
        resetSession();

	try {
	    OLsfFidS oLsfFidS = cacheableAttribute.getFileTypeDefinition(cmpVal, usrVal, lngVal, filTypVal);

	    if (oLsfFidS == null) {
		throw new EmptyResultDataAccessException(ERROR_CODE);
	    }

	    return oLsfFidS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "FID", new BigDecimal(cmpVal));
        }

        return null;
    }

}
