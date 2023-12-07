package com.mapfre.tron.api.pid.pfc.bl;

import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;

public interface IBlPidPfcCue {

    /**
     * ISD-XXXX - OFRECER HALLAR distribución cestas documentación proveedor
     *
     */
    public OPidPfcEitDto getPfcSle(OTrnCnxS pmOTrnCnxS) throws Exception;

}
