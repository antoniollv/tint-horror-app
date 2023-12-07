package com.mapfre.tron.api.cmn.tgf.tdd.dl;

import java.util.Map;

import com.mapfre.nwt.trn.tgf.tdd.bo.OTgfTddS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface IDlOTgfTddSDao extends NewtronDao<String, Map<String, Object>> {

    /**
     *  Get treasury document definition
     *
     * @param map    -> The map with the query params values
     * @return       -> Treasury document definition object
     */
	OTgfTddS getInoTypNam(Map<String, Object> map);

}
