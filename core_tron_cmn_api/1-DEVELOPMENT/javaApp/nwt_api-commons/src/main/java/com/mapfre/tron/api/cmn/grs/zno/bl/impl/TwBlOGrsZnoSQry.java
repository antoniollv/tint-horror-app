package com.mapfre.tron.api.cmn.grs.zno.bl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.grs.zno.bl.IBlOGrsZnoSQry;
import com.mapfre.tron.api.cmn.grs.zno.dl.OgrsZnoDao;
import com.mapfre.tron.api.cmn.grs.zno.dl.OgrsZnoPK;

@Service
public class TwBlOGrsZnoSQry extends TwBlCmnBase implements IBlOGrsZnoSQry {

    @Autowired
    protected OgrsZnoDao ogrsZnoDao;

    @Override
    public OGrsZnoS getOGrsZnoS(final OgrsZnoPK pk) {

        try {
            OGrsZnoS lvOGrsZnoS = ogrsZnoDao.get(pk);

            if (lvOGrsZnoS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZnoS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "ZNO", pk.getCmpVal());
        }

        return null;
    }

    @Override
    public List<OGrsZnoS> getAllOGrsZnoS(final OgrsZnoPK pk) {

        try {
            List<OGrsZnoS> lvOGrsZnoSList = ogrsZnoDao.getAll();

            if (lvOGrsZnoSList == null || lvOGrsZnoSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZnoSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "ZNO", pk.getCmpVal());
        }

        return Collections.emptyList();
    }

}
