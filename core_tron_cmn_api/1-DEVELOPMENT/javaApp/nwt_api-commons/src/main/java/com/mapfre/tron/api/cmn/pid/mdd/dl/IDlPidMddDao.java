package com.mapfre.tron.api.cmn.pid.mdd.dl;

import java.util.Map;

import com.mapfre.tron.api.cmn.dl.NewtronDao;


public interface IDlPidMddDao extends NewtronDao<String, Map<String, Object>> {

    String getMdtNam(final Map<String, Object> map);

}
