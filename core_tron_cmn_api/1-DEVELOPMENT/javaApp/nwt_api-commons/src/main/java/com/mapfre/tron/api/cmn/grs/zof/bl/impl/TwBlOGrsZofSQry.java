package com.mapfre.tron.api.cmn.grs.zof.bl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.grs.zof.bl.IBlOGrsZofSQry;
import com.mapfre.tron.api.cmn.grs.zof.dl.OgrsZofDao;
import com.mapfre.tron.api.cmn.grs.zof.dl.OgrsZofPK;

@Service
public class TwBlOGrsZofSQry extends TwBlCmnBase implements IBlOGrsZofSQry {

    @Autowired
    protected OgrsZofDao ogrsZofDao;

    @Override
    public List<OGrsZofS> getZoneFiveByPslCodVal(final OgrsZofPK pk) {

        try {
            List<OGrsZofS> lvOGrsZofSList = ogrsZofDao.getZoneFiveByPslCodVal(pk);

            if (lvOGrsZofSList == null || lvOGrsZofSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZofSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "ZOF", pk.getCmpVal());
        }

        return Collections.emptyList();
    }

    @Override
    public List<OGrsZofS> getZoneFiveList(final OgrsZofPK pk) {

        try {
            List<OGrsZofS> lvOGrsZofSList = ogrsZofDao.getZoneFiveList(pk);

            if (lvOGrsZofSList == null || lvOGrsZofSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZofSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "ZOF", pk.getCmpVal());
        }

        return Collections.emptyList();
    }

}
