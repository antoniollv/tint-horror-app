package com.mapfre.tron.api.cmn.cmn.col.bl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.cmn.col.bl.IBlOCmnColQry;
import com.mapfre.tron.api.cmn.cmn.col.dl.OCmnColDao;
import com.mapfre.tron.api.cmn.cmn.col.dl.OCmnColPK;

@Service
public class TwBlOCmnColQry extends TwBlCmnBase implements IBlOCmnColQry {

    @Autowired
    protected OCmnColDao oCmnColDao;

    @Override
    public List<OCmnColS> getAllColours(final OCmnColPK build) {
        try {
            List<OCmnColS> lvOCmnColSList = oCmnColDao.getAll();

            if (lvOCmnColSList == null || lvOCmnColSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCmnColSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(build.getLngVal(), "COL", build.getCmpVal());
        }

        return Collections.emptyList();
    }

    @Override
    public OCmnColP col(final OCmnColPK build) {

        try {
            OCmnColS oCmnColS = oCmnColDao.get(build);

            if (oCmnColS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            OCmnColP oCmnColP = new OCmnColP();
            OTrnPrcS oTrnPrcS = new OTrnPrcS();
            oCmnColP.setOCmnColS(oCmnColS);
            oCmnColP.setOTrnPrcS(oTrnPrcS);

            return oCmnColP;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(build.getLngVal(), "COL", build.getCmpVal());
        }

        return null;
    }

}
