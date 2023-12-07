
package com.mapfre.tron.api.nmr.gpp.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.nmr.gpp.bo.ONmrGppS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.nmr.gpp.dl.ISrNmrGppQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The tronweb CmnBL business service implementation.
 *
 * @author pvraul1
 * @since 1.8
 * @version 6 ago. 2019 12:41:13
 *
 */
@Data
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlNmrGppQryImpl extends TwBlCmnBase implements IBlNmrGppQry {

    @Autowired
    protected ISrNmrGppQryDAO iSrNmrGppQryDAO;

    /**
     * Query group policies. It will return the group policies that belong to a line
     * of business. It will be mandatory send the parameters defined in the service
     * and the service will response with the output object defined.
     *
     * @param lngVal -> Language code
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lobVal -> Line of Business
     * @param vldDat -> Valid Date
     * 
     * @return List<ONmrGppS>
     */
    @Override
    public List<ONmrGppS> get(final String usrVal, final String lngVal, final Integer cmpVal, final Long vldDat,
            final Integer lobVal) {

        try {
            List<ONmrGppS> oNmrGppSList = iSrNmrGppQryDAO.get(usrVal, lngVal, cmpVal, vldDat, lobVal);

            if (oNmrGppSList == null || oNmrGppSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oNmrGppSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "GPP", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

}
