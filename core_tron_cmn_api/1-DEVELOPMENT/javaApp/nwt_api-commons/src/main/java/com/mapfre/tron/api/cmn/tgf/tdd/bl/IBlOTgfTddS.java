package com.mapfre.tron.api.cmn.tgf.tdd.bl;

import java.util.Map;

import com.mapfre.nwt.trn.tgf.tdd.bo.OTgfTddS;

public interface IBlOTgfTddS {

	 /**
     *  Get treasury document definition
     *
     * @param map    -> The map with the query params values
     * @return       -> Treasury document definition object
     */
	OTgfTddS getInoTypNam(Map<String, Object> map);

}
