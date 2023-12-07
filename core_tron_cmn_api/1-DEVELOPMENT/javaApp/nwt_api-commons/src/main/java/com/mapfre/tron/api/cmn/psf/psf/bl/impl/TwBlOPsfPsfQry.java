package com.mapfre.tron.api.cmn.psf.psf.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.psf.psf.bo.OPsfPsfS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.psf.psf.bl.IBlOPsfPsfQry;
import com.mapfre.tron.api.cmn.psf.psf.dl.OPsfPsfDao;
import com.mapfre.tron.api.cmn.psf.psf.dl.OPsfPsfPK;

@Service
public class TwBlOPsfPsfQry extends TwBlCmnBase implements IBlOPsfPsfQry {

    @Autowired
    protected OPsfPsfDao oPsfPsfDao;

    public OPsfPsfS getPmsNam(final OPsfPsfPK pk, final String lngVal) {

        try {
            OPsfPsfS lvOPsfPsfS = oPsfPsfDao.get(pk);

            if (lvOPsfPsfS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOPsfPsfS;
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "PSF", pk.getCmpVal());
        }

        return null;
    }

}
