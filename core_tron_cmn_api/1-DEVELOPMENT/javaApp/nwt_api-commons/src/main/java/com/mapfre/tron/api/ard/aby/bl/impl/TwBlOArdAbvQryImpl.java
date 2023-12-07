package com.mapfre.tron.api.ard.aby.bl.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvCPT;
import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvP;
import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvS;
import com.mapfre.tron.api.ard.aby.bl.IBlOArdAbvQry;
import com.mapfre.tron.api.ard.aby.dl.OArdAbvQryDAO;
import com.mapfre.tron.api.ard.aby.dl.OArdAbvQryPK;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlOArdAbvQryImpl extends TwBlCmnBase implements IBlOArdAbvQry {

    @Autowired
    protected OArdAbvQryDAO oArdAbvQryDAO;

    @Override
    public List<OArdAbvS> tbl(OArdAbvQryPK pmOArdAbvQryPK) {

        log.info("Tronweb business logic implementation tbl have been called...");

        try {
            OArdAbvCPT lvOArdAbvCPT = oArdAbvQryDAO.get(pmOArdAbvQryPK);

            if (lvOArdAbvCPT == null || lvOArdAbvCPT.getOArdAbvPT() == null || lvOArdAbvCPT.getOArdAbvPT().isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOArdAbvCPT.getOArdAbvPT().stream().map(TwBlOArdAbvQryImpl::getOArdAbvS)
                    .collect(Collectors.toList());

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pmOArdAbvQryPK.getLngVal(), "ABV", pmOArdAbvQryPK.getCmpVal());
        }

        return Collections.emptyList();
    }

    protected static OArdAbvS getOArdAbvS(OArdAbvP oArdAbvP) {
        OArdAbvS oArdAbvS = null;
        if (oArdAbvP != null) {
            oArdAbvS = oArdAbvP.getOArdAbvS();
        }

        return oArdAbvS;
    }

}
