
package com.mapfre.tron.api.cmn.prt.lob.bl.impl.tw;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.prt.lob.bl.IBlPrtLobQry;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobQryDAO;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobQryPK;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobVdaDAO;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobVdaPK;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb CmnBL business service implementation.
 *
 * @author pvraul1
 * @since 1.8
 * @version 6 ago. 2019 12:41:13
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper=false)
public class TwCmnBlPrtLobQryImpl extends TwBlCmnBase implements IBlPrtLobQry {

    @Autowired
    protected OPrtLobQryDAO oPrtLobQryDAO;

    @Autowired
    protected OPrtLobVdaDAO oPrtLobVdaDAO;

    @Override
    @Transactional("transactionManagerTwDl")
    public OPrtLobS lob(final OPrtLobQryPK pmOPrtLobQryPK) {

        log.info("Tronweb business logic implementation lob have been called...");

        try {
            OPrtLobS oPrtLobS = oPrtLobQryDAO.get(pmOPrtLobQryPK);

            if (oPrtLobS == null) {

                throw new EmptyResultDataAccessException(ERROR_CODE);
            } else {
                oPrtLobS.setSecNam(cacheableAttribute.getSecNam(pmOPrtLobQryPK.getCmpVal(), oPrtLobS.getSecVal()));
            }

            return oPrtLobS;
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pmOPrtLobQryPK.getLngVal(), "LOB", pmOPrtLobQryPK.getCmpVal());
        }

        return null;
    }

    @Override
    @Transactional("transactionManagerTwDl")
    public List<OPrtLobS> get(OPrtLobVdaPK pmOPrtLobVdaPK) {

        log.info("Tronweb business logic implementation get have been called...");

        try {
            List<OPrtLobS> lvOPrtLobS = oPrtLobVdaDAO.getAllWithPK(pmOPrtLobVdaPK);

            if (lvOPrtLobS == null || lvOPrtLobS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOPrtLobS;
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(pmOPrtLobVdaPK.getLngVal(), "LOB", pmOPrtLobVdaPK.getCmpVal());
        }

        return Collections.emptyList();
    }

}
