package com.mapfre.tron.api.cmn.cmn.typ.bl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.cmn.typ.bl.IBlOCmnTypQry;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnCltPK;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnTypDao;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnTypPK;

@Service
public class TwBlOCmnTypQry extends TwBlCmnBase implements IBlOCmnTypQry {

    @Autowired
    protected OCmnTypDao oCmnTypDao;

    @Override
    public OCmnTypS getOCmnTypS(final OCmnTypPK pk) {

        try {
            OCmnTypS lvOCmnTypS = oCmnTypDao.get(pk);

            if (lvOCmnTypS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCmnTypS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "TYP", pk.getCmpVal());
        }

        return null;
    }

    @Override
    public List<OCmnTypS> getListOCmnTypS(final OCmnTypPK pk) {

        try {
            List<OCmnTypS> lvOCmnTypSList = oCmnTypDao.getAllWithPK(pk);

            if (lvOCmnTypSList == null || lvOCmnTypSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCmnTypSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "TYP", pk.getCmpVal());
        }

        return Collections.emptyList();
    }

    @Override
    public List<OCmnTypS> getAllTypes(OCmnTypPK pk) {
        return oCmnTypDao.getAllTypes(pk);
    }

    @Override
    public OCmnTypS getCollectorType(OCmnCltPK pk) {
        return oCmnTypDao.getCollectorType(pk);
    }

}
