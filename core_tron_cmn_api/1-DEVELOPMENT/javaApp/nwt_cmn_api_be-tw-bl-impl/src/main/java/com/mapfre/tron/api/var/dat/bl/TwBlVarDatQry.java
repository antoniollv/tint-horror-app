package com.mapfre.tron.api.var.dat.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.var.dat.dl.IDlVarDatQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Javier Sangil
 * @version 27/11/2023
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlVarDatQry extends TwBlCmnBase implements IBLVarDatQry {

    /** The cmnTyoQry repository. */
    @Autowired
    protected IDlVarDatQryDAO iSrCmnAtrQryDAO;

    /**
     * Post variable data . It will be mandatory send the parameters defined in the
     * service and the service will response with the output object defined.
     * 
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param fldNam    -> Field code
     * @param fldValVal -> Field Value
     * @param qtnVal    -> Quotation value
     * @param cmpVal    -> company
     * 
     */
    @Override
    public void postVariableData(Integer cmpVal, String usrVal, String lngVal, String fldNam, String fldValVal,
	    Long qtnVal) {

        log.info("Tronweb business logic implementation TwBlVarDatQry.postVariableData have been called...");

        // reseting session
        resetSession();

	int i = iSrCmnAtrQryDAO.postVariableData(cmpVal, usrVal, lngVal, fldNam, fldValVal, qtnVal);

	if (i != 1) {
	    throw new NwtException("Error updating or inserting postVariableData");
	}

    }

}
