package com.mapfre.tron.api.trn.evn.dl;

import java.util.List;

import com.mapfre.nwt.trn.trn.evn.bo.OTrnEvnS;

/**
 * The TrnEvnQry interface.
 *
 * @author Cristian Saball
 * @version 31 ener. 2022
 *
 */
public interface ISrlTrnEvnQryDAO {

    /**
     * Query message list for event not processed.
     *
     * @author Cristian Saball
     *
     * @param cmpVal 	-> Company code
     * @param usrVal 	-> User code
     * @param lngVal 	-> Language code
     * @param qryDat 	-> Query Date
     * @param evnIdnVal -> Event Identifier
     * @return       	-> The message for event
     */
    public List<OTrnEvnS> getQueryEventNotProcessed(Integer cmpVal, String lngVal, String usrVal, Long qryDat,
	    Integer evnIdnVal);

}
