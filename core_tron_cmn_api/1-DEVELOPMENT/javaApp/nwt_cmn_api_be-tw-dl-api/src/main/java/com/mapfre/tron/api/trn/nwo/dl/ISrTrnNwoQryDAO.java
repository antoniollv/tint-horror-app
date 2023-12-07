package com.mapfre.tron.api.trn.nwo.dl;

import java.util.List;

import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;

public interface ISrTrnNwoQryDAO {


    /**
     * Query Newtron operation.
     *
     * @author Cristian Saball
     *
     * @param lngVal 	-> Language code
     * @return       	-> The newtron operations
     */
    List<OTrnNwoS> getNewtronOperationList(String lngVal);
}
