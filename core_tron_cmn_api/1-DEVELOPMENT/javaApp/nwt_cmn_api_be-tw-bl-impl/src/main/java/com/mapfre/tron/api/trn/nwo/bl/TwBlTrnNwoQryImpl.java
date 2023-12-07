package com.mapfre.tron.api.trn.nwo.bl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.trn.nwo.dl.ISrTrnNwoQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlTrnNwoQryImpl extends TwBlCmnBase implements IBlTrnNwoQry {

    @Autowired
    protected ISrTrnNwoQryDAO iSrTrnNwoQryDAO;

    /**
     * Query Newtron operation.
     *
     * @author Cristian Saball
     *
     * @param lngVal -> Language code
     * @return       -> The newtron operations
     */
    @Override
    public List<OTrnNwoS> getNewtronOperationList(final String lngVal) {
        log.debug("Tronweb business logic implementation getNewtronOperationList have been called...");

        // reseting session
        resetSession();

        try {
            List<OTrnNwoS> lOTrnNwoSList = iSrTrnNwoQryDAO.getNewtronOperationList(lngVal);

            if (lOTrnNwoSList == null || lOTrnNwoSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lOTrnNwoSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "NWO", cmpValDefault);
        }

        return Collections.emptyList();
    }

}
