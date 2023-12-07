package com.mapfre.tron.api.cmn.crn.crn.bl.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnP;
import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.crn.crn.bl.IBlOCrnCrnQry;
import com.mapfre.tron.api.cmn.crn.crn.dl.OCrnCrnDao;
import com.mapfre.tron.api.cmn.crn.crn.dl.OCrnCrnPK;


@Service
public class TwBlCrnCrnQry extends TwBlCmnBase implements IBlOCrnCrnQry {

    @Autowired
    protected OCrnCrnDao oCrnCrnDao;

    @Override
    public OCrnCrnS getSdrCrnVal(OCrnCrnPK pmOCrnCrnPK) {

        return oCrnCrnDao.get(pmOCrnCrnPK);
    }

    @Override
    public OCrnCrnS getOCrnCrnS(final OCrnCrnPK pmOCrnCrnPK) {
        return oCrnCrnDao.get(pmOCrnCrnPK);
    }

    @Override
    public List<OCrnCrnP> getOCrnCrnSAll(final OCrnCrnPK pk) {

        try {
            List<OCrnCrnS> lvOCrnCrnS = oCrnCrnDao.getAllWithCmpVal(pk);

            if (lvOCrnCrnS == null || lvOCrnCrnS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCrnCrnS.stream().map(TwBlCrnCrnQry::getOCrnCrnP).collect(Collectors.toList());

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "CRN", pk.getCmpVal());
        }

        return Collections.emptyList();
    }

    @Override
    public OCrnCrnS getOCrnCrnSCurrency(final OCrnCrnPK build) {
        return oCrnCrnDao.getOCrnCrnSCurrency(build);
    }

    protected static OCrnCrnP getOCrnCrnP(OCrnCrnS oCrnCrnS) {
        OCrnCrnP oCrnCrnP = new OCrnCrnP();
        oCrnCrnP.setOCrnCrnS(oCrnCrnS);

        return oCrnCrnP;
    }

}
