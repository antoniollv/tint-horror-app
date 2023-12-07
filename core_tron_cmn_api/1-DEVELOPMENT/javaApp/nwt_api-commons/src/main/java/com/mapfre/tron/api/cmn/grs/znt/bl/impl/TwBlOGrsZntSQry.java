package com.mapfre.tron.api.cmn.grs.znt.bl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.grs.znt.bl.IBlOGrsZntSQry;
import com.mapfre.tron.api.cmn.grs.znt.dl.OgrsZntDao;
import com.mapfre.tron.api.cmn.grs.znt.dl.OgrsZntPK;

@Service
public class TwBlOGrsZntSQry extends TwBlCmnBase implements IBlOGrsZntSQry {

    @Autowired
    protected OgrsZntDao ogrsZntDao;

    @Override
    public OGrsZntS getOGrsZntS(final OgrsZntPK pk) {

        try {
            OGrsZntS lvOGrsZntS = ogrsZntDao.get(pk);

            if (lvOGrsZntS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZntS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "ZNT", pk.getCmpVal());
        }

        return null;
    }

    @Override
    public List<OGrsZntS> getAllOGrsZntS(final OgrsZntPK pk) {

        try {
            List<OGrsZntS> lvOGrsZntSList = ogrsZntDao.getAllWithPK(pk);

            if (lvOGrsZntSList == null || lvOGrsZntSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZntSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "ZNT", pk.getCmpVal());
        }

        return Collections.emptyList();
    }

}
