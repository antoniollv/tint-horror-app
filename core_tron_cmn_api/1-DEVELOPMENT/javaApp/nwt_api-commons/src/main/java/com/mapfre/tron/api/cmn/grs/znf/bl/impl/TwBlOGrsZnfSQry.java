package com.mapfre.tron.api.cmn.grs.znf.bl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.grs.znf.bl.IBlOGrsZnfSQry;
import com.mapfre.tron.api.cmn.grs.znf.dl.OgrsZnfDao;
import com.mapfre.tron.api.cmn.grs.znf.dl.OgrsZnfPK;

@Service
public class TwBlOGrsZnfSQry extends TwBlCmnBase implements IBlOGrsZnfSQry {

    @Autowired
    protected OgrsZnfDao ogrsZnfDao;

    @Override
    public OGrsZnfS getOGrsZnfS(final OgrsZnfPK pk) {

        try {
            OGrsZnfS lvOGrsZnfS = ogrsZnfDao.get(pk);

            if (lvOGrsZnfS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZnfS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "ZNF", pk.getCmpVal());
        }

        return null;
    }

    @Override
    public List<OGrsZnfS> getAllOGrsZnfS(final OgrsZnfPK build) {

        try {
            List<OGrsZnfS> lvOGrsZnfSList = ogrsZnfDao.getAllWithPK(build);

            if (lvOGrsZnfSList == null || lvOGrsZnfSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZnfSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(build.getLngVal(), "ZNF", build.getCmpVal());
        }

        return Collections.emptyList();
    }

}
