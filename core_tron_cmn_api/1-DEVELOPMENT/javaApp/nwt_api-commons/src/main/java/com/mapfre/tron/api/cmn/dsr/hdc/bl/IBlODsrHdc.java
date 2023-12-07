package com.mapfre.tron.api.cmn.dsr.hdc.bl;

import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;
import com.mapfre.tron.api.cmn.dsr.hdc.dl.ODsrHdcPK;

/**
 * The ODsrHdc service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 11:10:04
 *
 */
public interface IBlODsrHdc {

    /**
     * Get ODsrHdcS by PK.
     *
     * @param o -> The PK
     * @return  -> The ODsrHdcS
     */
    ODsrHdcS get(ODsrHdcPK o);

}
