package com.mapfre.tron.api.cmn.grs.zot.bl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.grs.zot.bl.IBlOGrsZotSQry;
import com.mapfre.tron.api.cmn.grs.zot.dl.OgrsZotDao;
import com.mapfre.tron.api.cmn.grs.zot.dl.OgrsZotPK;

@Service
public class TwBlOGrsZotSQry extends TwBlCmnBase implements IBlOGrsZotSQry {

    @Autowired
    protected OgrsZotDao ogrsZotDao;

    @Override
    public OGrsZotS getOGrsZotS(final OgrsZotPK pk) {

        try {
            OGrsZotS lvOGrsZotS = ogrsZotDao.get(pk);

            if (lvOGrsZotS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZotS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "ZOT", pk.getCmpVal());
        }

        return null;
    }

    @Override
    public List<OGrsZotS> getAllOGrsZotS(final OgrsZotPK pk) {

        try {
            List<OGrsZotS> lvOGrsZotSList = ogrsZotDao.getAllWithPK(pk);

            if (lvOGrsZotSList == null || lvOGrsZotSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZotSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "ZOT", pk.getCmpVal());
        }

        return Collections.emptyList();
    }

}
