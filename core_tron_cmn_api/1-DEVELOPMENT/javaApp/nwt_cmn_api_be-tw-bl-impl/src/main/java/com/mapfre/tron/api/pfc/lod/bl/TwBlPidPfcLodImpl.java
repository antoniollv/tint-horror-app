package com.mapfre.tron.api.pfc.lod.bl;

import com.mapfre.tron.api.bo.TronWebAccess;

import lombok.Data;

/**
 * TwBlPidPfcLodImpl
 *
 * @author magarafr
 * @since 1.8
 * @version 25 ene. 2021 12:54:02
 *
 */
@Data
@TronWebAccess
public class TwBlPidPfcLodImpl implements IBlPidPfcLod {

    /**
     * savPffBtc : AMBT-87 - Save portfolio fund composition
     *
     * @author magarafr
     * @purpose Save portfolio fund composition. It will be mandatory send the
     *          parameters defined in the service and the service will response with
     *          the output object defined.
     * 
     * @param lngVal -> Idioma
     * @param usrVal -> usuario
     * @param cmpVal -> Compañia
     *
     * @return -> Registro de movimiento
     */
    @Override
    public void savPffBtc(final String lngVal, final String usrVal, final Integer cmpVal) throws Exception {
        throw new UnsupportedOperationException("Operation not supported in tronweb profile");
    }

}
