package com.mapfre.tron.api.cmn.dsr.hdc.dl;

import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The DsrHdc respository interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 11:15:51
 *
 */
public interface ODsrHdcDao extends NewtronDao<ODsrHdcS, ODsrHdcPK> {

    /**
     * Get ODsrHdcS by PK.
     *
     * @param o -> The PK
     * @return  -> The ODsrHdcS
     */
    ODsrHdcS get(ODsrHdcPK o);

}
