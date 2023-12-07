package com.mapfre.tron.api.cmn.agn.cpe.dl;

import java.util.Map;

import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
*
* @author Javier Sangil
* @since 1.8
* @version 17 nov 2021 - 16:57:19
*
*/
public interface OAgnCpeDao extends NewtronDao<String, Map<String, Object>> {

    String getAgnCpeNam(Map<String, Object> map);

}
