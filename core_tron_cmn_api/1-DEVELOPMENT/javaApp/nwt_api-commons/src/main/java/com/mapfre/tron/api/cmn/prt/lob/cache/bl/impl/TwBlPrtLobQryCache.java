package com.mapfre.tron.api.cmn.prt.lob.cache.bl.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.prt.lob.cache.bl.IBlPrtLobQryCache;
import com.mapfre.tron.api.cmn.prt.lob.cache.dl.OPrtLobCacheDao;
import com.mapfre.tron.api.cmn.prt.lob.cache.dl.OPrtLobPK;

@Service
public class TwBlPrtLobQryCache extends TwBlCmnBase implements IBlPrtLobQryCache {

    @Autowired
    protected OPrtLobCacheDao oPrtLobCacheDao;

    @Override
    public OPrtLobS getLobNam(final OPrtLobPK pk) {
        return oPrtLobCacheDao.get(pk);
    }

    @Override
    public List<OPrtLobS> getAllForLineOfBusiness(final OPrtLobPK pk) {

        try {
            List<OPrtLobS> lvOPrtLobSList = oPrtLobCacheDao.getAllForLineOfBusiness(pk);

            if (lvOPrtLobSList == null || lvOPrtLobSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOPrtLobSList;
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pk.getLngVal(), "LOB", BigDecimal.valueOf(1));
        }

        return Collections.emptyList();
    }

}
