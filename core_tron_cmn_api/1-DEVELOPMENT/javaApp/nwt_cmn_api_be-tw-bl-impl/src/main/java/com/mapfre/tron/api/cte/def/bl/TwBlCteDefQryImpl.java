package com.mapfre.tron.api.cte.def.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrCPT;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.model.InConstantInformation;
import com.mapfre.tron.api.cte.def.dl.ISrCteDefQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * TwBlCteDefQryImpl
 *
 * @author pvraul1
 * @since 1.8
 * @version 17 oct. 2019 12:54:02
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCteDefQryImpl extends TwBlCmnBase implements IBlCteDefQry {

    /** The CteDefQry repository. */
    @Autowired
    protected ISrCteDefQryDAO iSrCteDefQryDAO;

    @Override
    public List<OPlyAtrS> xxCnn(final InConstantInformation inConstantInformation, final String strFldNam,
            final String lngVal) {

        log.debug("Tronweb business logic implementation xxCnn has been called...");

        // reseting session
        resetSession();

        try {
            OPlyAtrCPT lvOPlyAtrCPT = iSrCteDefQryDAO.xxCnn(inConstantInformation, strFldNam);

            if (lvOPlyAtrCPT == null || lvOPlyAtrCPT.getOPlyAtrPT().isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOPlyAtrCPT.getOPlyAtrPT().stream().map(TwBlCteDefQryImpl::getOPlyAtrS)
                    .collect(Collectors.toList());

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ATR", BigDecimal.valueOf(inConstantInformation.getCmpVal()));
        }

        return Collections.emptyList();
    }

    protected static OPlyAtrS getOPlyAtrS(OPlyAtrP oPlyAtrP) {
        return oPlyAtrP.getOPlyAtrS();
    }

}
