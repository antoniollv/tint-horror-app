/**
 * 
 */
package com.mapfre.tron.api.pid.dfq.bl;

import java.util.List;

import com.mapfre.nwt.trn.pid.dfq.bo.OPidDfqP;
import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;


public interface IBlPidDfqCue {

    /**
     * ISD-314 - OFRECER HALLAR cotizacion diaria fondo proveedor  
     *
     */
    public List<OPidDfqP> savSle(OTrnCnxS pmOTrnCnxS) throws Exception;

}
