package com.mapfre.tron.api.pfc.lod.bl;

/**
 * The PidPfcLod business logic service interface.
 *
 * @author magarafr
 * @since 1.8
 * @version 25 sept. 2020 13:05:08
 *
 */
public interface IBlPidPfcLod {

    /**
     * PidPfcLod : AMBT-87 - Save portfolio fund composition
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
    void savPffBtc(String lngVal, String usrVal, Integer cmpVal) throws Exception ;
}
