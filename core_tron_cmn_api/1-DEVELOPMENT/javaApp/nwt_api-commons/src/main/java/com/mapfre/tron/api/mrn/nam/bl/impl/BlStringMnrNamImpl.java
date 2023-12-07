package com.mapfre.tron.api.mrn.nam.bl.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.mrn.nam.bl.IBlStringMnrNam;
import com.mapfre.tron.api.trnkglobals.PAsignaVARCHAR;
import com.mapfre.tron.api.trnkglobals.PSacaNomGestor;

import lombok.Data;


@Data
@Service
public class BlStringMnrNamImpl implements IBlStringMnrNam {

    @Autowired
    protected PAsignaVARCHAR trnKGlobal;

    @Autowired
    protected PSacaNomGestor pSacaNomGestor;

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
    @Override
    public String getMnrNam(final BigDecimal cmpVal, final String usrVal, final String lngVal, final String mnrTypVal,
            final String mnrVal) {

        trnKGlobal.asigna("cod_cia", cmpVal.toString());
        trnKGlobal.asigna("cod_usr", usrVal);
        trnKGlobal.asigna("cod_idioma", lngVal);

        return pSacaNomGestor.getMnrNam(cmpVal, usrVal, mnrTypVal, mnrVal);

    }

}
