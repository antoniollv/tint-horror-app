package com.mapfre.tron.api.cmn.cmu.cmp.bl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.cmu.cmp.bl.IBlOCmuCmpQry;
import com.mapfre.tron.api.cmn.cmu.cmp.dl.OCmuCmpDao;
import com.mapfre.tron.api.cmn.cmu.cmp.dl.OCmuCmpPK;

@Service
public class TwBlOCmuCmpQry extends TwBlCmnBase implements IBlOCmuCmpQry {

    @Autowired
    protected OCmuCmpDao oCmuCmpDao;

    @Override
    public OCmuCmpS getCmpNam(final OCmuCmpPK pk) {
        return oCmuCmpDao.get(pk);
    }

    @Override
    public List<OCmuCmpS> getCmpNamList(final String usrVal, final String lngVal) {

        try {
            return oCmuCmpDao.getAll2();
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "CMP", cmpValDefault);
        }

        return Collections.emptyList();
    }

    @Override
    public OCmuCmpS getCmpNam(final String usrVal, final String lngVal, final OCmuCmpPK pk) {
        try {
            return oCmuCmpDao.get2(pk);
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "CMP", pk.getCmpVal());
        }

        return null;
    }

}
