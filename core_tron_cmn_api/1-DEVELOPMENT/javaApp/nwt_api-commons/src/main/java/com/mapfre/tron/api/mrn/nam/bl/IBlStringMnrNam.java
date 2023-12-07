package com.mapfre.tron.api.mrn.nam.bl;

import java.math.BigDecimal;

public interface IBlStringMnrNam {

    /**
     * Get MnrNam from gc_p_saca_nom_gestor procedure.
     *
     * @param cmpVal    -> company code
     * @param usrVal    -> user code
     * @param lngVal    -> language code
     * @param mnrTypVal -> Tip gestor
     * @param mnrVal    -> gestor code
     * @return -> mnrNam name gestor
     */
    String getMnrNam(BigDecimal cmpVal, String usrVal, String lngVal, String mnrTypVal, String mnrVal);
}
